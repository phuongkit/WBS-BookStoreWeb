package ecom.bookstore.wbsbackend.controller;

import ecom.bookstore.wbsbackend.dto.request.ReviewCreationDTO;
import ecom.bookstore.wbsbackend.dto.request.ReviewUpdationDTO;
import ecom.bookstore.wbsbackend.dto.response.ResponseObject;
import ecom.bookstore.wbsbackend.dto.response.ReviewResponseDTO;
import ecom.bookstore.wbsbackend.entities.Review;
import ecom.bookstore.wbsbackend.models.enums.ERole;
import ecom.bookstore.wbsbackend.services.ReviewService;
import ecom.bookstore.wbsbackend.utils.JwtTokenUtil;
import ecom.bookstore.wbsbackend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static ecom.bookstore.wbsbackend.utils.Utils.DEFAULT_PAGE;
import static ecom.bookstore.wbsbackend.utils.Utils.REVIEW_PER_PAGE;

/**
 * @author minh phuong
 * @created 12/09/2022 - 8:19 PM
 */
@RestController
@RequestMapping(value = "/api/v1/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  public static final String branchName = Review.class.getSimpleName();
  private ReviewService reviewService;

  @Autowired
  public void ReviewService(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  private JwtTokenUtil jwtTokenUtil;

  @Autowired public void JwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @GetMapping("/productId/{productId}")
  public ResponseObject<Page<ReviewResponseDTO>> getAllReviewsByProduct(
      @PathVariable(name = "productId") Long productId,
      @RequestParam(name = "isHasChild", required = false, defaultValue = "false")
      Boolean isHasChild,
      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
      @RequestParam(name = "limit", required = false, defaultValue = REVIEW_PER_PAGE) Integer size,
      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
    Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
    isHasChild = true;
    return new ResponseObject<>(
        HttpStatus.OK, "", this.reviewService.getAllReviewsByProduct(productId, isHasChild, pageable));
  }

  @GetMapping("/userId/{userId}")
  public ResponseObject<Page<ReviewResponseDTO>> getAllReviewsByUser(
      @PathVariable(name = "userId") Integer userId,
      @RequestParam(name = "isHasChild", required = false, defaultValue = "false")
      Boolean isHasChild,
      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
      @RequestParam(name = "limit", required = false, defaultValue = REVIEW_PER_PAGE) Integer size,
      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
    Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
    return new ResponseObject<>(
        HttpStatus.OK, "", this.reviewService.getAllReviewsByUser(userId, isHasChild, pageable));
  }

//  @GetMapping("/{mainReviewId}/rely")
//  public ResponseObject<Page<ReviewResponseDTO>> getAllChildReviewsByMainReview(
//      @PathVariable(name = "mainReviewId") Long mainReviewId,
//      @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE) Integer page,
//      @RequestParam(name = "limit", required = false, defaultValue = REVIEW_PER_PAGE) Integer size,
//      @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
//      @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
//    Sort sort = Sort.by(sortField);
//    sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
//    Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, sort);
//    return new ResponseObject<>(
//        HttpStatus.OK, "", this.reviewService.getAllRelyReviewsByMainReview(mainReviewId, pageable));
//  }

  @GetMapping("/{id}")
  public ResponseObject<ReviewResponseDTO> getReviewById(
      @PathVariable(name = "id") Long id,
      @RequestParam(name = "isHasChild", required = false, defaultValue = "false")
      Boolean isHasChild) {
    return new ResponseObject<>(
        HttpStatus.OK, "", this.reviewService.getReviewById(id, isHasChild));
  }

  @GetMapping("/productId/{productId}/userId/{userId}")
  public ResponseObject<ReviewResponseDTO> getReviewByProductAndUser(
      @PathVariable(name = "productId") Long productId,
      @PathVariable(name = "userId") Integer userId,
      @RequestParam(name = "isHasChild", required = false, defaultValue = "false")
      Boolean isHasChild) {
    return new ResponseObject<>(
        HttpStatus.OK, "", this.reviewService.getReviewByProductAndUser(productId, userId, isHasChild));
  }

  @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @RolesAllowed({ERole.Names.CUSTOMER, ERole.Names.SELLER, ERole.Names.ADMIN})
  public ResponseObject<ReviewResponseDTO> createReview(
      @RequestPart("data") @Valid ReviewCreationDTO creationDTO,
      @RequestPart(value = "images", required = false) MultipartFile[] imageFiles,
      HttpServletRequest request) {
    //    this.LOGGER.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
    String loginKey = jwtTokenUtil.getUserNameFromRequest(request);
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.CREATE_OBJECT_SUCCESSFULLY, branchName),
        this.reviewService.createReview(loginKey, creationDTO, imageFiles));
  }

//  @PostMapping("/{mainReviewId}/rely")
//  public > createRelyReview(
//      @PathVariable(name = "mainReviewId") Long relyReviewId,
//      @RequestPart("data") @Valid RelyReviewCreationDTO creationDTO) {
//    return this.reviewService.createRelyReview(relyReviewId, creationDTO);
//  }

  @PutMapping("/{ReviewId}")
  public ResponseObject<ReviewResponseDTO> updateMainReview(
      @PathVariable(name = "ReviewId") Long id,
      @RequestPart("data") @Valid ReviewUpdationDTO updationDTO,
      @RequestPart(value = "images", required = false) MultipartFile[] imageFiles) {
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.UPDATE_OBJECT_SUCCESSFULLY, branchName),
        this.reviewService.updateReview(id, updationDTO, imageFiles));
  }

  @DeleteMapping("/{ReviewId}")
  public ResponseObject<ReviewResponseDTO> deleteReview(@PathVariable(name = "ReviewId") Long id) {
    return new ResponseObject<>(HttpStatus.OK, String.format(Utils.DELETE_OBJECT_SUCCESSFULLY, branchName),
        this.reviewService.deleteReviewById(id));
  }
}
