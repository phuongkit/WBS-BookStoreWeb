package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.CategoryCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.CategoryResponseDTO;
import ecom.bookstore.wbsbackend.entities.Category;
import ecom.bookstore.wbsbackend.entities.Image;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFound;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.mapper.CategoryMapper;
import ecom.bookstore.wbsbackend.models.enums.EImageType;
import ecom.bookstore.wbsbackend.repositories.CategoryRepo;
import ecom.bookstore.wbsbackend.services.CategoryService;
import ecom.bookstore.wbsbackend.services.ImageService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author minh phuong
 * @created 31/10/2022 - 9:13 AM
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Category.class.getSimpleName();
  private CategoryMapper categoryMapper;

  @Autowired
  public void CategoryMapper(CategoryMapper categoryMapper) {
    this.categoryMapper = categoryMapper;
  }

  private CategoryRepo categoryRepo;

  @Autowired
  public void CategoryRepository(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  private ImageService imageService;

  @Autowired
  public void ImageService(ImageService imageService) {
    this.imageService = imageService;
  }

  @Override
  public Page<CategoryResponseDTO> getAllCategories(String keyword, Pageable pageable) {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT_BY_FIELD, branchName, "Keyword", keyword));
    Page<Category> categoryPage = this.categoryRepo.findAll(keyword, pageable);
    if (categoryPage.getContent().size() < 1) {
      throw new ResourceNotFound(
          String.format(Utils.OBJECT_NOT_FOUND, branchName));
    }
    return categoryPage.map(Category -> this.categoryMapper.categoryToCategoryResponseDTO(Category));
  }

  @Override public List<CategoryResponseDTO> getAllCategoriesWithHierarchy() {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT, branchName));
    List<Category> categoryPage = this.categoryRepo.findAllParentCategory();
    if (categoryPage.size() < 1) {
      throw new ResourceNotFound(
          String.format(Utils.OBJECT_NOT_FOUND, branchName));
    }
    return categoryPage.stream().map(Category -> this.categoryMapper.categoryToCategoryResponseDTO(Category, true)).collect(
        Collectors.toList());
  }

  @Override
  public CategoryResponseDTO getCategoryById(Integer id) {
    this.LOGGER.info(String.format(Utils.LOG_GET_OBJECT, branchName, "ID", id));
    return this.categoryMapper.categoryToCategoryResponseDTO(
        this.categoryRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            branchName,
                            "ID",
                            id))));
  }

  @Override public CategoryResponseDTO getCategoryBySlug(String slug) {
    this.LOGGER.info(String.format(Utils.LOG_GET_OBJECT, branchName, "Slug", slug));
    Category category =
        this.categoryRepo
            .findBySlug(slug)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            branchName,
                            "Slug",
                            slug)));
    return this.categoryMapper.categoryToCategoryResponseDTO(category);
  }

  @Override
  public CategoryResponseDTO createCategory(CategoryCreationDTO creationDTO, MultipartFile imageFile) {
    this.LOGGER.info(
        String.format(
            Utils.LOG_CREATE_OBJECT,
            branchName,
            "Name",
            creationDTO.getName()));

    Category category = new Category();
    category.setName(creationDTO.getName());
    category.setSlug(Utils.toSlug(category.getName()));
    category.setDescription(creationDTO.getDescription());
    if (creationDTO.getParentCategoryId() != null) {
      Category categoryParentFound =
          this.categoryRepo
              .findById(creationDTO.getParentCategoryId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          String.format(
                              Utils.OBJECT_NOT_FOUND_BY_FIELD,
                              branchName,
                              "ID",
                              creationDTO.getParentCategoryId())));
      category.setParentCategory(categoryParentFound);
    }
    if (imageFile != null && !imageFile.isEmpty()) {
      Image image =
          this.imageService.createImageByMultipartFile(
              imageFile, EImageType.IMAGE_CATEGORY);
      category.setThumbnail(image);
    }

    return this.categoryMapper.categoryToCategoryResponseDTO(this.categoryRepo.save(category));
  }

  @Override
  public CategoryResponseDTO updateCategory(
      Integer id, CategoryCreationDTO creationDTO, MultipartFile imageFile) {
    this.LOGGER.info(
        String.format(Utils.LOG_UPDATE_OBJECT, branchName, "ID", id));
    Category categoryFound = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "ID", id)));

    categoryFound.setName(creationDTO.getName());
    categoryFound.setSlug(Utils.toSlug(categoryFound.getName()));
    categoryFound.setDescription(creationDTO.getDescription());
    if (creationDTO.getParentCategoryId() != null) {
      Category categoryParentFound =
          this.categoryRepo
              .findById(creationDTO.getParentCategoryId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          String.format(
                              Utils.OBJECT_NOT_FOUND_BY_FIELD,
                              branchName,
                              "ID",
                              creationDTO.getParentCategoryId())));
      categoryFound.setParentCategory(categoryParentFound);
    }
    if (imageFile != null) {
      if (categoryFound.getThumbnail() != null) {
        this.imageService.deleteImageById(categoryFound.getThumbnail().getId());
      }
      if (!imageFile.isEmpty()) {
        Image image =
            this.imageService.createImageByMultipartFile(
                imageFile, EImageType.IMAGE_CATEGORY);
        categoryFound.setThumbnail(image);
      }
    }

    return this.categoryMapper.categoryToCategoryResponseDTO(this.categoryRepo.save(categoryFound));
  }

  @Override
  public CategoryResponseDTO deleteCategoryById(Integer id) {
    this.LOGGER.info(
        String.format(Utils.LOG_DELETE_OBJECT, branchName, "ID", id));
    Category CategoryFound =
        this.categoryRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            branchName,
                            "ID",
                            id)));
    // delete Thumbnail
    Image image = CategoryFound.getThumbnail();
    if (image != null) {
      this.imageService.deleteImageById(image.getId());
    }

    // delete Category
    this.categoryRepo.deleteById(id);
    return null;
  }
}
