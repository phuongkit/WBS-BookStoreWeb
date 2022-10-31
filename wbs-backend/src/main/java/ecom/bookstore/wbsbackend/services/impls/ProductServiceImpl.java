package ecom.bookstore.wbsbackend.services.impls;

import ecom.bookstore.wbsbackend.dto.request.ProductCreationDTO;
import ecom.bookstore.wbsbackend.dto.response.ProductResponseDTO;
import ecom.bookstore.wbsbackend.dto.response.ProductGalleryDTO;
import ecom.bookstore.wbsbackend.entities.Category;
import ecom.bookstore.wbsbackend.entities.Image;
import ecom.bookstore.wbsbackend.entities.Location;
import ecom.bookstore.wbsbackend.entities.Product;
import ecom.bookstore.wbsbackend.exceptions.ResourceAlreadyExistsException;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFound;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotFoundException;
import ecom.bookstore.wbsbackend.mapper.ProductMapper;
import ecom.bookstore.wbsbackend.models.enums.EHomeOption;
import ecom.bookstore.wbsbackend.models.enums.EImageType;
import ecom.bookstore.wbsbackend.models.enums.EProductStatus;
import ecom.bookstore.wbsbackend.repositories.CategoryRepo;
import ecom.bookstore.wbsbackend.repositories.ProductRepo;
import ecom.bookstore.wbsbackend.services.ImageService;
import ecom.bookstore.wbsbackend.services.LocationService;
import ecom.bookstore.wbsbackend.services.ProductService;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author minh phuong
 * @created 11/10/2022 - 9:47 PM
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Product.class.getSimpleName();

  private CategoryRepo categoryRepo;

  @Autowired
  public void CategoryRepo(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  private ImageService imageService;

  @Autowired
  public void ImageService(ImageService imageService) {
    this.imageService = imageService;
  }

  private LocationService locationService;

  @Autowired
  public void LocationService(LocationService locationService) {
    this.locationService = locationService;
  }

  private ProductMapper productMapper;

  @Autowired
  public void ProductMapper(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  private ProductRepo productRepo;

  @Autowired
  public void ProductRepo(ProductRepo productRepo) {
    this.productRepo = productRepo;
  }

  @Override
  public Page<ProductResponseDTO> getAllProduct(Pageable pageable) {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT, branchName));
    Page<Product> products = this.productRepo.findAll(pageable);
    if (products.getContent().size() < 1) {
      throw new ResourceNotFound(
          String.format(Utils.OBJECT_NOT_FOUND, branchName));
    }
    return products.map(product -> this.productMapper.productToProductResponseDTO(product));
  }

  @Override
  public Page<ProductGalleryDTO> getAllProductOnTradingByPage(Pageable pageable) {
    this.LOGGER.info(String.format(Utils.LOG_GET_ALL_OBJECT_BY_FIELD, branchName, "Status",
        EProductStatus.PRODUCT_TRADING));
    Page<Product> products =
        this.productRepo.findAllByStatus(EProductStatus.PRODUCT_TRADING, pageable);
    if (products.getContent().size() < 1) {
      throw new ResourceNotFound(
          String.format(Utils.OBJECT_NOT_FOUND, branchName));
    }
    return products.map(product -> this.productMapper.productToProductGalleryDTO(product));
  }

  @Override
  public Page<ProductResponseDTO> filterProduct(
      String keyword,
      String categoryName,
      String locationString,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Pageable pageable) {
    this.LOGGER.info(
        String.format(Utils.LOG_GET_ALL_OBJECT_BY_FIELD, branchName, "Keyword", keyword));
    Category categoryFound = null;
    if (categoryName != null) {
      categoryFound = this.categoryRepo.findByName(categoryName).orElse(null);
    }
    Location locationFound = null;
    if (locationString != null && locationString.length() > 0) {
      locationFound = this.locationService.getLocation(Utils.getLocationFromLocationString(locationString));
    }
    Page<Product> products =
        this.productRepo.filterProduct(keyword, categoryFound, locationFound, minPrice, maxPrice, pageable);
    if (products.getContent().size() < 1) {
      throw new ResourceNotFound(
          String.format(Utils.OBJECT_NOT_FOUND, branchName));
    }
    return products.map(product -> this.productMapper.productToProductResponseDTO(product));
  }

  @Override
  public Page<ProductGalleryDTO> filterProductByKeyword(
      String keyword, String locationString,
      int sortOption, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
    this.LOGGER.info(
        String.format(Utils.LOG_GET_ALL_OBJECT_BY_FIELD, branchName, "Keyword", keyword));
    Location locationFound = null;
    if (locationString != null && locationString.length() > 0) {
      locationFound = this.locationService.getLocation(Utils.getLocationFromLocationString(locationString));
    }
    Page<Product> products =
        this.productRepo.filterProduct(keyword, null, locationFound, minPrice, maxPrice, pageable);
    if (products.getContent().size() < 1) {
      throw new ResourceNotFound(
          String.format(Utils.OBJECT_NOT_FOUND, branchName));
    }
    return products.map(product -> this.productMapper.productToProductGalleryDTO(product));
  }

  @Override public Page<ProductResponseDTO> getAllProductCategoryId(Integer categoryId,
                                                                              String locationString,
                                                                              int sortOption, BigDecimal minPrice,
                                                                              BigDecimal maxPrice, Pageable pageable) {
    this.LOGGER.info(
        String.format(Utils.LOG_GET_ALL_OBJECT_BY_TWO_FIELD, branchName, Category.class.getSimpleName() + "ID", categoryId,
            Location.class.getSimpleName(), locationString));
    Category categoryFound = null;
    Location locationFound = null;

    if (categoryId != null) {
      categoryFound = this.categoryRepo.findById(categoryId).orElseThrow(
          () -> new ResourceNotFound(
              String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, Category.class.getSimpleName(), "ID", categoryId)));
    }
    if (locationString != null && locationString.length() > 0) {
      locationFound = this.locationService.getLocation(Utils.getLocationFromLocationString(locationString));
    }
    Page<Product> products =
        this.productRepo.findAllByCategoryOrLocation(categoryFound, locationFound, minPrice, maxPrice, pageable);
    if (products.getContent().size() < 1) {
      throw new ResourceNotFound(
          String.format(Utils.OBJECT_NOT_FOUND, branchName));
    }
    return products.map(product -> this.productMapper.productToProductResponseDTO(product));
  }

  @Override public Page<ProductGalleryDTO> getAllProductByHomeOption(int homeOptionId, String locationString,
                                                                     int sortOption, BigDecimal minPrice,
                                                                     BigDecimal maxPrice, Pageable pageable) {
    this.LOGGER.info(
        String.format(Utils.LOG_GET_ALL_OBJECT_BY_TWO_FIELD, branchName, EHomeOption.class.getSimpleName(), homeOptionId,
            Location.class.getSimpleName(), locationString));
    Location locationFound = null;

    if (locationString != null && locationString.length() > 0) {
      locationFound = this.locationService.getLocation(Utils.getLocationFromLocationString(locationString));
    }
    Page<Product> products =
        this.productRepo.findAllByCategoryOrLocation(null, locationFound, minPrice, maxPrice, pageable);
    if (products.getContent().size() < 1) {
      throw new ResourceNotFound(
          String.format(Utils.OBJECT_NOT_FOUND, branchName));
    }
    return products.map(product -> this.productMapper.productToProductGalleryDTO(product));
  }

  @Override
  public ProductResponseDTO getProductById(Long id) {
    this.LOGGER.info(String.format(Utils.LOG_GET_OBJECT, branchName, "ID", id));
    Product product =
        this.productRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            branchName,
                            "ID",
                            id)));
    return this.productMapper.productToProductResponseDTO(product);
  }

  @Override public ProductResponseDTO getProductBySlug(String slug) {
    this.LOGGER.info(String.format(Utils.LOG_GET_OBJECT, branchName, "Slug", slug));
    Product product =
        this.productRepo
            .findBySlug(slug)
            .orElseThrow(
                () ->
                    new ResourceNotFound(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            branchName,
                            "Slug",
                            slug)));
    return this.productMapper.productToProductResponseDTO(product);
  }

  @Override
  public ProductResponseDTO createProduct(
      ProductCreationDTO creationDTO,
      MultipartFile thumbnailFile,
      MultipartFile[] imageGalleryFile) {
    this.LOGGER.info(
        String.format(
            Utils.LOG_CREATE_OBJECT, branchName, "Name", creationDTO.getName()));
    // check product name is existed
    Optional<Product> productFound = this.productRepo.findByName(creationDTO.getName());
    if (productFound.isPresent()) {
      throw new ResourceAlreadyExistsException(
          String.format(
              Utils.OBJECT_EXISTED_BY_FIELD,
              branchName,
              "Name",
              creationDTO.getName()));
    }

    // check category input is valid
    Category categoryFound =
        this.categoryRepo
            .findById(creationDTO.getCategoryId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Category.class.getSimpleName(),
                            "ID",
                            creationDTO.getCategoryId())));

    Product product = new Product();
    product.setName(creationDTO.getName());
    product.setSlug(Utils.toSlug(product.getName()) + "." + UUID.randomUUID().toString().replace("-", ""));
    product.setDescription(creationDTO.getDescription());
    product.setStandCost(creationDTO.getStandCost());
    product.setListPrice(creationDTO.getListPrice());
    product.setQuantity(creationDTO.getQuantity());
    product.setStatus(creationDTO.getStatus());
    product.setCategory(categoryFound);

    // Set thumbnail
    if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
      Image thumbnailImage =
          this.imageService.createImageByMultipartFile(thumbnailFile, EImageType.IMAGE_PRODUCT);
      product.setThumbnail(thumbnailImage);
    }

    // Set images
    if (imageGalleryFile != null && imageGalleryFile.length > 0) {
      Set<Image> imageGallery = new HashSet<>();
      for (MultipartFile multipartFile : imageGalleryFile) {
        if (!multipartFile.isEmpty()) {
          Image image =
              this.imageService.createImageByMultipartFile(
                  multipartFile, EImageType.IMAGE_PRODUCT_GALLERY);
          imageGallery.add(image);
        }
      }
      product.setImageGallery(imageGallery);
    }

    return this.productMapper.productToProductResponseDTO(this.productRepo.save(product));
  }

  @Override
  public ProductResponseDTO updateProduct(
      Long id,
      ProductCreationDTO creationDTO,
      MultipartFile thumbnailFile,
      MultipartFile[] imageGalleryFile) {
    this.LOGGER.info(
        String.format(Utils.LOG_UPDATE_OBJECT, branchName, "ID", id));
    // check product is existed
    Product productFound =
        this.productRepo
            .findById(id)
            .orElseThrow(
                () ->
                    new ResourceAlreadyExistsException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            branchName,
                            "ID",
                            id)));

    // check category input is valid
    Category categoryFound =
        this.categoryRepo
            .findById(creationDTO.getCategoryId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        String.format(
                            Utils.OBJECT_NOT_FOUND_BY_FIELD,
                            Category.class.getSimpleName(),
                            "ID",
                            creationDTO.getCategoryId())));

    productFound.setName(creationDTO.getName());
    productFound.setSlug(Utils.toSlug(productFound.getName()) + "." + UUID.randomUUID().toString().replace("-", ""));
    productFound.setDescription(creationDTO.getDescription());
    productFound.setStandCost(creationDTO.getStandCost());
    productFound.setListPrice(creationDTO.getListPrice());
    productFound.setQuantity(creationDTO.getQuantity());
    productFound.setStatus(creationDTO.getStatus());
    productFound.setCategory(categoryFound);

    // update thumbnail
    if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
      // delete old thumbnail if it exists
      if (productFound.getThumbnail() != null) {
        this.imageService.deleteImageById(productFound.getThumbnail().getId());
      }
      // set new thumbnail
      Image thumbnailImage =
          this.imageService.createImageByMultipartFile(thumbnailFile, EImageType.IMAGE_PRODUCT);
      productFound.setThumbnail(thumbnailImage);
    }

    // update image gallery
    if (imageGalleryFile != null && imageGalleryFile.length > 0) {
      // delete old image gallery if it exists
      if (productFound.getImageGallery() != null && productFound.getImageGallery().size() > 0) {
        for (Image image : productFound.getImageGallery()) {
          this.imageService.deleteImageById(image.getId());
        }
      }
      // set new image gallery
      Set<Image> imageGallery = new HashSet<>();
      for (MultipartFile multipartFile : imageGalleryFile) {
        if (!multipartFile.isEmpty()) {
          Image image =
              this.imageService.createImageByMultipartFile(
                  multipartFile, EImageType.IMAGE_PRODUCT_GALLERY);
          imageGallery.add(image);
        }
      }
      productFound.setImageGallery(imageGallery);
    }

    return this.productMapper.productToProductResponseDTO(this.productRepo.save(productFound));
  }

  @Override
  public ProductResponseDTO deleteProductById(Long id) {
    this.LOGGER.info(
        String.format(Utils.LOG_DELETE_OBJECT, branchName, "ID", id));
    Optional<Product> productFound = this.productRepo.findById(id);
    if (productFound.isPresent()) {
      // delete thumbnail
      Image thumbnail = productFound.get().getThumbnail();
      if (thumbnail != null) {
        this.imageService.deleteImageById(thumbnail.getId());
      }

      // delete image gallery
      Set<Image> imageGallery = productFound.get().getImageGallery();
      if (imageGallery != null && imageGallery.size() > 0) {
        for (Image image : imageGallery) {
          this.imageService.deleteImageById(image.getId());
        }
      }

      // delete Product
      this.productRepo.deleteById(id);
      return null;
    }
    throw new ResourceNotFoundException(
        String.format(Utils.OBJECT_NOT_FOUND_BY_FIELD, branchName, "ID", id));
  }
}
