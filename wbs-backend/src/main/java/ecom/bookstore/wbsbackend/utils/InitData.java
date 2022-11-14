package ecom.bookstore.wbsbackend.utils;

import ecom.bookstore.wbsbackend.dto.request.AuthRequest;
import ecom.bookstore.wbsbackend.dto.request.OrderCreationDTO;
import ecom.bookstore.wbsbackend.dto.request.OrderDetailCreationDTO;
import ecom.bookstore.wbsbackend.entities.*;
import ecom.bookstore.wbsbackend.models.clazzs.FullAddress;
import ecom.bookstore.wbsbackend.models.enums.*;
import ecom.bookstore.wbsbackend.repositories.*;
import ecom.bookstore.wbsbackend.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author minh phuong
 * @created 12/10/2022 - 5:01 PM
 */
@Component
public class InitData {
  private final Logger LOGGER = LoggerFactory.getLogger(InitData.class);
  private final Random generator = new Random();
  private AddressService addressService;
  @Autowired public void AddressService(AddressService addressService) {
    this.addressService =addressService;
  }
  private AuthorRepo authorRepo;

  @Autowired
  public void AuthorRepo(AuthorRepo authorRepo) {
    this.authorRepo = authorRepo;
  }

  private ProductRepo productRepo;

  @Autowired
  public void BookRepo(ProductRepo productRepo) {
    this.productRepo = productRepo;
  }

  private CategoryRepo categoryRepo;

  @Autowired
  public void CategoryRepo(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  private CommentRepo commentRepo;

  @Autowired
  public void CommentRepo(CommentRepo commentRepo) {
    this.commentRepo = commentRepo;
  }

  private GenreRepo genreRepo;

  @Autowired
  public void GenreRepo(GenreRepo genreRepo) {
    this.genreRepo = genreRepo;
  }

  private ImageRepo imageRepo;

  @Autowired
  public void ImageRepo(ImageRepo imageRepo) {
    this.imageRepo = imageRepo;
  }

  private LanguageRepo languageRepo;

  @Autowired
  public void LanguageRepo(LanguageRepo languageRepo) {
    this.languageRepo = languageRepo;
  }

  private LocationService locationService;
  @Autowired public void
  LocationService(LocationService locationService) {
    this.locationService = locationService;
  }
private OrderService orderService;
  @Autowired public void OrderService(OrderService orderService) {
    this.orderService = orderService;
  }
  private PaymentRepo paymentRepo;

  @Autowired
  public void PaymentRepo(PaymentRepo paymentRepo) {
    this.paymentRepo = paymentRepo;
  }

  private PublisherRepo publisherRepo;

  @Autowired
  public void PublisherRepo(PublisherRepo publisherRepo) {
    this.publisherRepo = publisherRepo;
  }

  private TranslatorRepo translatorRepo;

  @Autowired
  public void TranslatorRepo(TranslatorRepo translatorRepo) {
    this.translatorRepo = translatorRepo;
  }

  private ReviewRepo reviewRepo;

  @Autowired
  public void ReviewRepo(ReviewRepo reviewRepo) {
    this.reviewRepo = reviewRepo;
  }
private RoleService roleService;
  @Autowired public void RoleService(RoleService roleService) {
    this.roleService = roleService;
  }
  private SaleRepo saleRepo;
  @Autowired public void SaleRepo(SaleRepo saleRepo) {
    this.saleRepo = saleRepo;
  }
  private SeriesRepo seriesRepo;

  @Autowired
  public void SeriesRepo(SeriesRepo seriesRepo) {
    this.seriesRepo = seriesRepo;
  }

  private ShippingMethodRepo shippingMethodRepo;

  @Autowired
  public void ShippingMethodRepo(ShippingMethodRepo shippingMethodRepo) {
    this.shippingMethodRepo = shippingMethodRepo;
  }

  private SupplierRepo supplierRepo;

  @Autowired
  public void SupplierRepo(SupplierRepo supplierRepo) {
    this.supplierRepo = supplierRepo;
  }

  private UserRepo userRepo;
  @Autowired public void UserRepo (UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  private UserService userService;

  @Autowired
  public void UserService(UserService userService) {
    this.userService = userService;
  }

  public void init() {

    this.LOGGER.info("Start init data to database");

    Pageable pageable = PageRequest.of(0, 1000);
    Date currentDate = new Date();
    long hourTime = 1000 * 3600;
    long dayTime = hourTime * 24;
    long monthTime = dayTime * 30;

    RoleVariable rv = new RoleVariable();
    createRole(rv);

    LocationVariable lv = new LocationVariable();
    createLocation(lv);

    UserVariable uv = new UserVariable();
    createUser(uv, rv, lv);

    LanguageVariable lgv = new LanguageVariable();
    createLanguage(lgv);

    CategoryVariable cv = new CategoryVariable();
    createCategory(cv);

    GenreVariable gv = new GenreVariable();
    createGenre(gv);

    SupplierVariable sv = new SupplierVariable();
    createSupplier(sv);

    PublisherVariable pv = new PublisherVariable();
    createPublisher(pv);

    AuthorVariable av = new AuthorVariable();
    createAuthor(av);

    TranslatorVariable tv = new TranslatorVariable();
    createTranslator(tv);

    SeriesVariable srv = new SeriesVariable();
    createSeries(srv, av, sv, pv);

    Product[] products = new Product[500];
    int maxIndexProduct = createBook(products, lgv, cv, gv, sv, pv, av, tv, srv);

    Sale[] sales = new Sale[100];
    int maxIndexSale =
        createSale(sales, products, maxIndexProduct, uv.customers, uv.maxIndexCustomer, currentDate, dayTime, monthTime,
                   pageable, cv);

    Review[] mainFeedbacks = new Review[500];
    int maxIndexMainFeedback =
        createMainReviews(
            mainFeedbacks, products, maxIndexProduct, uv.customers, uv.maxIndexCustomer);

    Comment[] childReviews = new Comment[500];
    int maxChildReview =
        createChildReviews(
            childReviews, mainFeedbacks, maxIndexMainFeedback, uv.customers, uv.maxIndexCustomer);

    Payment paymentCash = new Payment(EPayment.CASH, "", "");
    paymentCash = this.paymentRepo.save(paymentCash);

    Payment paymentMoMo = new Payment(EPayment.MOMO, "", "");
    paymentMoMo = this.paymentRepo.save(paymentMoMo);

    ShippingMethod shippingMethodGHN = new ShippingMethod(EShippingMethod.GHN_EXPRESS, "", "");
    shippingMethodGHN = this.shippingMethodRepo.save(shippingMethodGHN);

    ShippingMethod shippingMethodGHTK =
        new ShippingMethod(EShippingMethod.GIAOHANGTIETKIEM, "", "");
    shippingMethodGHTK = this.shippingMethodRepo.save(shippingMethodGHTK);

    createOrder(products, maxIndexProduct, uv.customers, uv.maxIndexCustomer);

    this.LOGGER.info("Init data to database is done!");
  }

  private void createOrder(Product[] products, int maxIndexProduct, User[] customers, int maxIndexCustomer) {
    OrderCreationDTO[] orders = new OrderCreationDTO[500];
    List<OrderDetailCreationDTO> orderItems;
    int countOrder = 50;
    int maxProductOrder = 5;
    int maxQuantityProduct = 3;
    for (int i = 0; i < countOrder; i++) {
      orderItems = new ArrayList<>();
      int rand = generator.nextInt(maxProductOrder) + 1;
      for (int j = 0; j < rand; j++) {
        orderItems.add(new OrderDetailCreationDTO(products[generator.nextInt(maxIndexProduct + 1)],
                                                  generator.nextInt(maxQuantityProduct) + 1));
      }
      User user = customers[generator.nextInt(maxIndexCustomer + 1)];
      orders[i] = new OrderCreationDTO(user, orderItems);
      this.orderService.createOrder(user.getPhone() != null ? user.getPhone() : user.getEmail(), orders[i]);
    }
  }

  private int createMainReviews(
      Review[] mainReviews,
      Product[] products,
      int maxIndexProduct,
      User[] customers,
      int maxIndexCustomer) {
    int i = 0;

    mainReviews[i] =
        new Review(
            products[0],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I got it",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[0],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "No way!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[0],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "This is too good to be true!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[0],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I can't say for sure.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[0],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "There's no way to know.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[0],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I guess so.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[0],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "You better believe it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[0],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Of course!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[0],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Definitely!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[0],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Absolutely!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[0],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "How come?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Is that so?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "It's none of your business.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I was just daydreaming.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I was just thinking.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What's on your mind?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Nothing much.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What have you been doing?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Right on!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I did it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Got a minute?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "About when",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I won't take but a minute",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Speak up!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Seen Melissa?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "So we've met again, eh?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Come here.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Come over.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Don't go yet.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Please go first. After you.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Thanks for letting me go first.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Speak up!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Seen Melissa?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "So we’ve met again, eh?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Come here.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Come over.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Don’t go yet.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Please go first. After you.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Thanks for letting me go first.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What a relief.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What the hell are you doing?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "You’re a lifesaver.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I know I can count on you.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Get your head out of your ass!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "That’s a lie!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Do as I say.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "This is the limit!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Explain to me why.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Ask for it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "In the nick of time.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "No litter.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Go for it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What a jerk!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "How cute!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "None of your business.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Don’t peep!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Say cheese!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Be good !",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Bottom up!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Me? Not likely!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Scratch one’s head",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Take it or leave it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Hell with haggling!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Mark my words!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Bored to death!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What a relief!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Enjoy your meal!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "It serves you right!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "The more, the merrier!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Beggars can’t be choosers!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Boys will be boys!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Good job!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Well done!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Just for fun!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Try your best!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Make some noise!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Congratulations!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Rain cats and dogs.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Love me love my dog.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Always the same.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Hit it off.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Hit or miss.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Add fuel to the fire.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "To eat well and can dress beautifully.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Don’t mention it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "You’re welcome",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "That’s all right!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Just kidding.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Enjoy your meal!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "No, not a bit.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Nothing particular!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "After you.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Have I got your word on that?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "The same as usual!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Almost!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "You‘ll have to step on it.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I’m in a hurry.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What the hell is going on?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Sorry for bothering!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Give me a certain time!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "It’s a kind of once-in-life!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Out of sight, out of mind!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "The God knows!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Women love through ears, while men love through eyes!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Poor you/me/him/her…!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Go away!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Let me see.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "None your business.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Mark my words!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Help yourself!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Absolutely!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What have you been doing?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Nothing much.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What's on your mind?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I was just thinking.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I was just daydreaming.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "It's none of your business.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Is that so?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "How come?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "How's it going?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Definitely!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Of course!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I guess so.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "There's no way to know.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I can't say for sure.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "This is too good to be true!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "No way!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Stop joking!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I got it.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Right on!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "(Great!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I did it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I made it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Got a minute?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "About when?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Speak up!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Seen Melissa?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "So we've met again, eh?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Come here.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Don't go yet.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Please go first. After you.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Thanks for letting me go first.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What the hell are you doing?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "You're a life saver.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I know I can count on you.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Get your head out of your ass!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "That's a lie!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Do as I say.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "This is the limit!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Explain to me why.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Ask for it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "In the nick of time.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "No litter.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Go for it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What a jerk!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "How cute!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "None of your business!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Don't peep!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Stop it right away!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "A wise guy, eh?!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "You'd better stop dawdling.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Say cheese!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Be good!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Bottoms up!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Me? Not likely!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Scratchăone’săhead.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Take it or leave it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Mark my words!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "What a relief!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Enjoy your meal!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "It serves you right!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "The more, the merrier!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Boys will be boys!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Good job!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Well done!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Just for fun!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Try your best!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Make some noise!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Rain cats and dogs.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Love you love your dog.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Strike it",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Always the same.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Hit it off",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Hit or miss.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Add fuel to the fire.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Don't mention it!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Not at all.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Just kidding!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "No, not a bit.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Nothing particular!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Have I got your word on that?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "The same as usual",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Almost!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "I'm in a hurry.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Sorry for bothering!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Give me a certain time!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Provincial!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Discourages me much!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "It's a kind of once-in-life!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "The God knows!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Poor you",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Got a minute?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "to argue hot and long",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "After you.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Is that so?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Brilliant idea!",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "Do you really mean it?",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    i++;
    mainReviews[i] =
        new Review(
            products[generator.nextInt(maxIndexProduct + 1)],
            customers[generator.nextInt(maxIndexCustomer + 1)],
            "You are a great help.",
            (int) (Math.random() * 5 + 1));
    mainReviews[i] = this.reviewRepo.save(mainReviews[i]);

    return i;
  }

  private int createChildReviews(
      Comment[] childReviews,
      Review[] mainReviews,
      int maxIndexMainReview,
      User[] customers,
      int maxIndexCustomer) {
    int i = 0;

    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[0], customers[generator.nextInt(maxIndexCustomer + 1)], "What a relief.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[0],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "What the hell are you doing?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[0], customers[generator.nextInt(maxIndexCustomer + 1)], "You're a life saver.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[0],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "I know I can count on you");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[0],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Get your head out of your ass!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[0], customers[generator.nextInt(maxIndexCustomer + 1)], "That's a lie!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[0], customers[generator.nextInt(maxIndexCustomer + 1)], "Do as I say.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "This is the limit!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Explain to me why.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Ask for it!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "In the nick of time");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "No litter.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Go for it!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "What a jerk!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "How cute!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "None of your business");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Don't peep!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Say cheese!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Be good !");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Bottom up!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Me? Not likely!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Scratch one’s head");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Take it or leave it!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Hell with haggling!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Mark my words!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Bored to death!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "What a relief!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Enjoy your meal!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "It serves you right!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "The more, the merrier!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Beggars can’t be choosers!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Boys will be boys!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Good job!= well done!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Just for fun!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Try your best!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Make some noise!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Congratulations!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Rain cats and dogs");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Love me love my dog.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Always the same.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Hit it off.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Hit or miss.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Add fuel to the fire.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "To eat well and can dress beautifully.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Don’t mention it! = You’re welcome = That’s all right!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Just kidding.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Enjoy your meal!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "No, not a bit.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Nothing particular!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "After you.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Have I got your word on that?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "The same as usual!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Almost!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "You‘ll have to step on it.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "I’m in a hurry.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "What the hell is going on?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Sorry for bothering!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Give me a certain time!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "It’s a kind of once-in-life!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Out of sight, out of mind!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "The God knows!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Women love through ears, while men love through eyes!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Poor you/me/him/her…!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Go away!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Let me see.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "None your business.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Mark my words!  ");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "What’s up?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "How’s it going?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "What have you been doing?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Nothing much.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "What’s on your mind?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "I was just thinking.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "I was just daydreaming.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "It’s none of your business.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Is that so?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "How come?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Absolutely!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Definitely!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Of course!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "You better believe it!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "I guess so");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "There’s no way to know.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "I can’t say for sure.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "This is too good to be true!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "No way!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "I got it.");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Right on!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "I did it!");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "Got a minute?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "About when?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    i++;
    childReviews[i] = new Comment();
    childReviews[i].setChildReview(
        mainReviews[generator.nextInt(maxIndexMainReview + 1)],
        customers[generator.nextInt(maxIndexCustomer + 1)],
        "About when?");
    childReviews[i] = this.commentRepo.save(childReviews[i]);

    return i;
  }

  private int createSale(Sale[] sales, Product[] products, int maxIndexProduct, User[] customers, int maxIndexCustomer,
                         Date currentDate, long dayTime, long monthTime, Pageable pageable, CategoryVariable cv) {
    int i = 0;
    Set<Product> productSet1 = new HashSet<>(this.productRepo.findAll());

    sales[i] =
        new Sale("Sinh nhật", "", 0.2, productSet1, customers[generator.nextInt(maxIndexCustomer + 1)], currentDate,
                 new Date(currentDate.getTime() + 5 * dayTime));
    sales[i] = this.saleRepo.save(sales[i]);

    Set<Product> productSet2 =
        this.productRepo.findAllByCategory(cv.categoryTamLy, pageable).stream().collect(Collectors.toSet());
    productSet2.addAll(
        this.productRepo.findAllByCategory(cv.categorySachChoTuoiMoiLon, pageable).stream().collect(Collectors.toSet()));
    productSet2.addAll(
        this.productRepo.findAllByCategory(cv.categoryTruyenThieuNhi, pageable).stream().collect(Collectors.toSet()));
    i++;
    sales[i] =
        new Sale("Giảm sốc", "", 0.35, productSet2, customers[generator.nextInt(maxIndexCustomer + 1)], currentDate,
                 new Date(currentDate.getTime() + 10 * dayTime));
    sales[i] = this.saleRepo.save(sales[i]);

    Set<Product> productSet3 =
        this.productRepo.findAllByCategory(cv.categoryTruyenNganTanVan, pageable).stream().collect(Collectors.toSet());
    productSet3.addAll(
        this.productRepo.findAllByCategory(cv.categoryNgheThuatGiaiTri, pageable).stream().collect(Collectors.toSet()));

    i++;
    sales[i] = new Sale("Giá sốc cuối tuần", "", 0.23, productSet3, customers[generator.nextInt(maxIndexCustomer + 1)],
                        currentDate, new Date(currentDate.getTime() + 30 * 24 * 3600));
    sales[i] = this.saleRepo.save(sales[i]);

    Set<Product> productSet4 =
        this.productRepo.findAllByCategory(cv.categoryCauChuyenCuocDoi, pageable).stream().collect(Collectors.toSet());
    i++;
    sales[i] = new Sale("Gói Samsung care+", "", 0.08, productSet4, customers[generator.nextInt(maxIndexCustomer + 1)],
                        currentDate, new Date(currentDate.getTime() + monthTime));
    sales[i] = this.saleRepo.save(sales[i]);

    Set<Product> productSet5 =
        this.productRepo.findAllByCategory(cv.categoryDaiHoc, pageable).stream().collect(Collectors.toSet());
    i++;
    sales[i] =
        new Sale("Intel gen 12", "", 0.15, productSet5, customers[generator.nextInt(maxIndexCustomer + 1)], currentDate,
                 new Date(currentDate.getTime() + monthTime));
    sales[i] = this.saleRepo.save(sales[i]);

    return i;
  }

  private int createBook(
      Product[] products,
      LanguageVariable lv,
      CategoryVariable cv,
      GenreVariable gv,
      SupplierVariable sv,
      PublisherVariable pv,
      AuthorVariable av,
      TranslatorVariable tv,
      SeriesVariable srv) {
    int i = 0;
    products[i] =
        new Product(
            "Nhà Giả Kim",
            new BigDecimal("79000"),
            cv.categoryTieuThuyet,
            null,
            null,
            sv.supplierNhaNam,
            new HashSet<>(Collections.singletonList(av.authorPauloCoelho)),
            new HashSet<>(Collections.singletonList(tv.translatorLeChuCau)),
            null,
            lv.languageVN,
            pv.publisherHoiNhaVan,
            1988,
            2020,
            220,
            20.5,
            13d,
            null,
            227,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_195509_1_36793.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {"8935235226272_1.jpg", "8935235226272_2.jpg"}))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Cây Cam Ngọt Của Tôi",
            new BigDecimal("108000"),
            cv.categoryTieuThuyet,
            null,
            null,
            sv.supplierNhaNam,
            new HashSet<>(Collections.singletonList(av.authorJoseMauro)),
            new HashSet<>(Arrays.asList(tv.translatorNguyenBichLan, tv.translatorToYenLy)),
            null,
            lv.languageVN,
            pv.publisherHoiNhaVan,
            2020,
            null,
            280,
            20d,
            14.5d,
            null,
            244,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_217480.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2020_12_17_16_50_30_1-390x510.jpg",
                      "2020_12_17_16_50_30_2-390x510.jpg",
                      "2020_12_17_16_50_30_3-390x510.jpg",
                      "2020_12_17_16_50_30_4-390x510.jpg",
                      "2020_12_17_16_50_30_5-390x510.jpg",
                      "2020_12_17_16_50_30_6-390x510.jpg",
                      "2020_12_17_16_50_30_7-390x510.jpg",
                      "2020_12_17_16_50_30_8-390x510.jpg",
                      "2020_12_17_16_50_30_9-390x510.jpg",
                      "2020_12_17_16_50_30_10-390x510.jpg",
                      "2020_12_17_16_50_30_11-390x510.jpg",
                      "2020_12_17_16_50_30_12-390x510.jpg"
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Từ Điển Tiếng “Em”",
            new BigDecimal("69000"),
            cv.categoryTruyenNganTanVan,
            null,
            null,
            sv.supplierSkyBooks,
            new HashSet<>(Collections.singletonList(av.authorKhotudien)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherPhuNuVietNam,
            null,
            2021,
            300,
            12d,
            10d,
            null,
            280,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("bia_tudientiengem-_1_.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "bia_tudientiengem_1_.png",
                      "122794058_3479567975430769_1331743639861310999_o_1.jpg",
                      "123026494_3479568002097433_2539352269014278525_o_1.jpg",
                      "122702904_3479568285430738_3407610756011378446_o_1.jpg",
                      "125495151_3288476084615326_7140752655633785953_o_1.jpg"
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Chiến Thắng Con Quỷ Trong Bạn",
            new BigDecimal("115000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierThaiHa,
            new HashSet<>(Collections.singletonList(av.authorNapoleonHill)),
            new HashSet<>(),
            srv.seriesComboSachKyNangTuyetChieuLamGiau,
            lv.languageVN,
            pv.publisherLaoDong,
            2011,
            2021,
            300,
            24d,
            16d,
            null,
            293,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("chien-thang-con-quy-trong-ban_10.2021_bia-01.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "chien_thang_con_quy_trong_ban_10.2021_bia_01.jpg",
                      "chien_thang_con_quy_trong_ban_10.2021_bia_4.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "13 Nguyên Tắc Nghĩ Giàu Làm Giàu - Think And Grow Rich",
            new BigDecimal("110000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierThaiHa,
            new HashSet<>(Collections.singletonList(av.authorNapoleonHill)),
            new HashSet<>(),
            srv.seriesComboSachKyNangTuyetChieuLamGiau,
            lv.languageVN,
            pv.publisherLaoDong,
            1937,
            2020,
            550,
            24d,
            15.5d,
            null,
            399,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_195509_1_39473.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_22_16_46_12_1-390x510.jpg",
                      "2021_06_22_16_46_12_2-390x510.jpg",
                      "2021_06_22_16_46_12_3-390x510.jpg",
                      "2021_06_22_16_46_12_4-390x510.jpg",
                      "2021_06_22_16_46_12_5-390x510.jpg"
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lời Thú Tội Mới Của Một Sát Thủ Kinh Tế",
            new BigDecimal("190000"),
            cv.categoryNhanVatBaiHoc,
            null,
            null,
            sv.supplierTanViet,
            new HashSet<>(Collections.singletonList(av.authorJohnPerkins)),
            new HashSet<>(Arrays.asList(tv.translatorThuyHang, tv.translatorAnhThu)),
            srv.seriesComboLoiThuToiVaNgheThuatLanhDao,
            lv.languageVN,
            pv.publisherThongTan,
            2017,
            null,
            500,
            17d,
            24d,
            null,
            496,
            EProductLayout.HARDBACK,
            null,
            null,
            Image.createBookThumbnail(
                "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_1_2019_02_22_14_51_50.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_2_2019_02_22_14_51_50.jpg",
                      "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_3_2019_02_22_14_51_50.jpg",
                      "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_4_2019_02_22_14_51_50.jpg",
                      "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_5_2019_02_22_14_51_50.jpg",
                      "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_6_2019_02_22_14_51_50.jpg",
                      "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_7_2019_02_22_14_51_50.jpg",
                      "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_8_2019_02_22_14_51_50.jpg",
                      "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_9_2019_02_22_14_51_50.jpg",
                      "loi_thu_toi_moi_cua_mot_sat_thu_kinh_te_10_2019_02_22_14_51_50.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Nghệ Thuật Lãnh Đạo - Chân Dung Những CEO, Nhà Sáng Lập Và Những Người Thay Đổi Cuộc Chơi Vĩ Đại Nhất Thế Giới",
            new BigDecimal("105000"),
            cv.categoryNhanVatBaiHoc,
            null,
            null,
            sv.supplierTanViet,
            new HashSet<>(Collections.singletonList(av.authorDavidMRubenstei)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherDanTri,
            2021,
            null,
            500,
            24d,
            16d,
            null,
            480,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("nghethuanhdao.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "nghethuatlanhdao.jpg",
                      "nghe-thuat-lanh-dao-01.jpg",
                      "z2489592573766_cdeb8b40cf0b129581493f28c40e22ef.jpg",
                      "z2489592586233_22751e1e3f964b82763770c99de86bde.jpg",
                      "z2489592635433_30da3d9ea8e3af38ed505a7dffe192b8.jpg",
                      "z2489592575618_414925546e6caaf2f0cc9bf5841aaf11.jpg",
                      "2021_05_14_16_26_15_1-390x510.jpg",
                      "2021_05_14_16_26_15_2-390x510.jpg",
                      "2021_05_14_16_26_15_3-390x510.jpg",
                      "2021_05_14_16_26_15_4-390x510.jpg",
                      "2021_05_14_16_26_15_5-390x510.jpg",
                      "2021_05_14_16_26_15_6-390x510.jpg",
                      "2021_05_14_16_26_15_7-390x510.jpg",
                      "2021_05_14_16_26_15_8-390x510.jpg",
                      "2021_05_14_16_26_15_9-390x510.jpg",
                      "2021_05_14_16_26_15_10-390x510.jpg",
                      "2021_05_14_16_26_15_11-390x510.jpg",
                      "2021_05_14_16_26_15_12-390x510.jpg"
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Phù Thủy Sàn Chứng Khoán Thế Hệ Mới",
            new BigDecimal("259000"),
            cv.categoryChungKhoangBatDongSan,
            null,
            null,
            sv.supplierAlphaBooks,
            new HashSet<>(Collections.singletonList(av.authorJackDSchwager)),
            new HashSet<>(),
            srv.seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang,
            lv.languageVN,
            pv.publisherLaoDong,
            2022,
            null,
            560,
            24d,
            16d,
            2d,
            528,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("ph_-th_y-s_n-ch_ng-kho_n-th_-h_-m_i-24.03.2022.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "ph_th_y_s_n_ch_ng_kho_n_th_h_m_i_24.03.2022.jpg",
                      "2022_09_17_11_03_26_1-390x510.gif",
                      "2022_09_17_11_03_26_2-390x510.gif",
                      "2022_09_17_11_03_26_3-390x510.gif",
                      "2022_09_17_11_03_26_4-390x510.gif",
                      "2022_09_17_11_03_26_5-390x510.gif",
                      "2022_09_17_11_03_26_6-390x510.gif",
                      "2022_09_17_11_03_26_7-390x510.gif",
                      "2022_09_17_11_03_26_8-390x510.gif",
                      "2022_09_17_11_03_26_9-390x510.gif",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Chiến Lược Đầu Tư Chứng Khoán",
            new BigDecimal("169000"),
            cv.categoryChungKhoangBatDongSan,
            null,
            null,
            sv.supplierAlphaBooks,
            new HashSet<>(Arrays.asList(av.authorDavidBrown, av.authorKassandraBentley)),
            new HashSet<>(),
            srv.seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang,
            lv.languageVN,
            pv.publisherLaoDong,
            2021,
            2021,
            300,
            20.5d,
            13d,
            null,
            368,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_232600.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_07_03_21_20_01_1-390x510.gif",
                      "2021_07_03_21_20_01_2-390x510.gif",
                      "2021_07_03_21_20_01_3-390x510.gif",
                      "2021_07_03_21_20_01_4-390x510.gif",
                      "2021_07_03_21_20_01_5-390x510.gif",
                      "2021_07_03_21_20_01_6-390x510.gif",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Phong Thái Của Bậc Thầy Thuyết Phục",
            new BigDecimal("99000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierAlphaBooks,
            new HashSet<>(Collections.singletonList(av.authorDaveLakhani)),
            new HashSet<>(),
            srv.seriesComboSachBacThayThuyetPhuc,
            lv.languageVN,
            pv.publisherLaoDongXaHoi,
            2020,
            2020,
            250,
            20.5d,
            13d,
            null,
            244,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("phong-thai-cua-bac-thay-thuyet-phuc-01.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "phong_thai_cua_bac_thay_thuyet_phuc-01.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Những Đòn Tâm Lý Trong Thuyết Phục",
            new BigDecimal("169000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierAlphaBooks,
            new HashSet<>(Collections.singletonList(av.authorRobertBCialdini)),
            new HashSet<>(),
            srv.seriesComboSachBacThayThuyetPhuc,
            lv.languageVN,
            pv.publisherLaoDong,
            2020,
            2020,
            400,
            23d,
            15d,
            null,
            360,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_244718_1_5349.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "V2021_06_23_08_03_10_1-390x510.jpg",
                      "V2021_06_23_08_03_10_2-390x510.jpg",
                      "V2021_06_23_08_03_10_3-390x510.jpg",
                      "V2021_06_23_08_03_10_4-390x510.jpg",
                      "V2021_06_23_08_03_10_5-390x510.jpg",
                      "V2021_06_23_08_03_10_6-390x510.jpg",
                      "V2021_06_23_08_03_10_7-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 1",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            128,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("z2023256882537_9ed40a5bf624cf04f14a8eed6f5090ae_1.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "z2023256882537_9ed40a5bf624cf04f14a8eed6f5090ae.jpg",
                      "z2023256899175_c0dca866625680e4d8a8a5908417ba43.jpg",
                    }))));

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 2",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn1.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_21_08_54_09_1-390x510.jpg",
                      "2021_06_21_08_54_09_2-390x510.jpg",
                      "2021_06_21_08_54_09_3-390x510.jpg",
                      "2021_06_21_08_54_09_4-390x510.jpg",
                      "2021_06_21_08_54_09_5-390x510.jpg",
                      "2021_06_21_08_54_09_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 3",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn3.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_21_08_54_50_1-390x510.jpg",
                      "2021_06_21_08_54_50_2-390x510.jpg",
                      "2021_06_21_08_54_50_3-390x510.jpg",
                      "2021_06_21_08_54_50_4-390x510.jpg",
                      "2021_06_21_08_54_50_5-390x510.jpg",
                      "2021_06_21_08_54_50_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 4",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn4.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_21_08_55_41_1-390x510.jpg",
                      "2021_06_21_08_55_41_2-390x510.jpg",
                      "2021_06_21_08_55_41_3-390x510.jpg",
                      "2021_06_21_08_55_41_4-390x510.jpg",
                      "2021_06_21_08_55_41_5-390x510.jpg",
                      "2021_06_21_08_55_41_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 5",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn5.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_21_08_56_31_1-390x510.jpg",
                      "2021_06_21_08_56_31_2-390x510.jpg",
                      "2021_06_21_08_56_31_3-390x510.jpg",
                      "2021_06_21_08_56_31_4-390x510.jpg",
                      "2021_06_21_08_56_31_5-390x510.jpg",
                      "2021_06_21_08_56_31_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 6",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn6.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(this.imageRepo.saveAllAndFlush(Image.createBookGallery(new String[] {}))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 7",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn7.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_21_08_57_12_1-390x510.jpg",
                      "2021_06_21_08_57_12_2-390x510.jpg",
                      "2021_06_21_08_57_12_3-390x510.jpg",
                      "2021_06_21_08_57_12_4-390x510.jpg",
                      "2021_06_21_08_57_12_5-390x510.jpg",
                      "2021_06_21_08_57_12_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 8",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn8.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_21_08_57_55_1-390x510.jpg",
                      "2021_06_21_08_57_55_2-390x510.jpg",
                      "2021_06_21_08_57_55_3-390x510.jpg",
                      "2021_06_21_08_57_55_4-390x510.jpg",
                      "2021_06_21_08_57_55_5-390x510.jpg",
                      "2021_06_21_08_57_55_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 9",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn9.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_21_08_58_32_1-390x510.jpg",
                      "2021_06_21_08_58_32_2-390x510.jpg",
                      "2021_06_21_08_58_32_3-390x510.jpg",
                      "2021_06_21_08_58_32_4-390x510.jpg",
                      "2021_06_21_08_58_32_5-390x510.jpg",
                      "2021_06_21_08_58_32_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 10",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn10.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_21_08_59_39_1-390x510.jpg",
                      "2021_06_21_08_59_39_2-390x510.jpg",
                      "2021_06_21_08_59_39_3-390x510.jpg",
                      "2021_06_21_08_59_39_4-390x510.jpg",
                      "2021_06_21_08_59_39_5-390x510.jpg",
                      "2021_06_21_08_59_39_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 11",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn11.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_21_09_00_59_1-390x510.jpg",
                      "2021_06_21_09_00_59_2-390x510.jpg",
                      "2021_06_21_09_00_59_3-390x510.jpg",
                      "2021_06_21_09_00_59_4-390x510.jpg",
                      "2021_06_21_09_00_59_5-390x510.jpg",
                      "2021_06_21_09_00_59_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 12",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            150,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn12_1.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_21_08_51_41_1-390x510.jpg",
                      "2021_06_21_08_51_41_2-390x510.jpg",
                      "2021_06_21_08_51_41_3-390x510.jpg",
                      "2021_06_21_08_51_41_4-390x510.jpg",
                      "2021_06_21_08_51_41_5-390x510.jpg",
                      "2021_06_21_08_51_41_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 13",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            150,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("lhmn13.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "lhmn13_1.jpg",
                      "2020_04_24_15_41_08_1-390x510.jpg",
                      "2020_04_24_15_41_08_2-390x510.jpg",
                      "2020_04_24_15_41_08_3-390x510.jpg",
                      "2020_04_24_15_41_08_4-390x510.jpg",
                      "2020_04_24_15_41_08_5-390x510.jpg",
                      "2020_04_24_15_41_08_6-390x510.jpg",
                      "2020_04_24_15_41_08_7-390x510.jpg",
                      "2020_04_24_15_41_08_8-390x510.jpg",
                      "2020_04_24_15_41_08_9-390x510.jpg",
                      "2020_04_24_15_41_08_10-390x510.jpg",
                      "2020_04_24_15_41_08_11-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 14",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("z2117335097693_89800b4f2dc73ddfdf8ade8747580206.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "z2117335097693_89800b4f2dc73ddfdf8ade8747580206_1.jpg",
                      "lop_hoc_mat_ngu___tap_14_tai_ban_2020___tang_kem_huy_hieu_mau_ngau_nhien_2_2021_11_18_15_37_21.png",
                      "lop_hoc_mat_ngu___tap_14_tai_ban_2020___tang_kem_huy_hieu_mau_ngau_nhien_3_2021_11_18_15_37_21.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 15",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            135,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("8938507002840.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "", "",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 16",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            136,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("8938507002857_1.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "", "",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 17",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            136,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("8938507002864_1.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "", "",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 18",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            150,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("05b0dc68643430dd9e28edb7a04c4d62_2_1.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "z2548854923559_05b0dc68643430dd9e28edb7a04c4d62_2_1.jpg",
                      "z2548855058041_2f48d83ab73967924ad07cd139cccab3.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 19",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("l_p-h_c-m_t-ng_-t_p-19.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "l_p-h_c-m_t-ng_-t_p-19-qu_.jpg", "l_p-h_c-m_t-ng_-19.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 20",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            250,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("cover_lhmn20.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "1_1.jpeg",
                      "lhmn20_fbcover_hht.jpg",
                      "insta_lhmn20_hht_1.jpg",
                      "untitled-2hhtchd_1.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lớp Học Mật Ngữ - Tập 21",
            new BigDecimal("35000"),
            cv.categoryTruyenThieuNhi,
            null,
            null,
            sv.supplierBaoTienPhong,
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            new HashSet<>(),
            srv.seriesLopHocMatNgu,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            2020,
            130,
            22d,
            15d,
            0.5d,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("untitled-2hhtchd.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "untitled-2hhtchd_1.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Khi Hơi Thở Hóa Thinh Không",
            new BigDecimal("109000"),
            cv.categoryCauChuyenCuocDoi,
            null,
            null,
            sv.supplierAlphaBooks,
            new HashSet<>(Collections.singletonList(av.authorPaulKalanithi)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherLaoDong,
            2020,
            2020,
            250,
            20.5d,
            14d,
            null,
            236,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_195509_1_34396.jpg"),
            "Khi hơi thở hóa thinh không là tự truyện của một bác sĩ bị mắc bệnh ung thư phổi. Trong cuốn sách này, tác giả đã chia sẻ những trải nghiệm từ khi mới bắt đầu học ngành y, tiếp xúc với bệnh nhân cho tới khi phát hiện ra mình bị ung thư và phải điều trị lâu dài.\n"
                + "\n"
                + "Kalanithi rất yêu thích văn chương nên câu chuyện của anh đã được thuật lại theo một phong cách mượt mà, dung dị và đầy cảm xúc. Độc giả cũng được hiểu thêm về triết lý sống, triết lý nghề y của Kalanithi, thông qua ký ức về những ngày anh còn là sinh viên, rồi thực tập, cho đến khi chính thức hành nghề phẫu thuật thần kinh. “Đối với bệnh nhân và gia đình, phẫu thuật não là sự kiện bi thảm nhất mà họ từng phải đối mặt và nó có tác động như bất kỳ một biến cố lớn lao trong đời. Trong những thời điểm nguy cấp đó, câu hỏi không chỉ đơn thuần là sống hay chết mà còn là cuộc sống nào đáng sống.” – Kalanithi luôn biết cách đưa vào câu chuyện những suy nghĩ sâu sắc và đầy sự đồng cảm như thế.\n"
                + "\n"
                + "Bạn bè và gia đình đã dành tặng những lời trìu mến nhất cho con người đáng kính trọng cả về tài năng lẫn nhân cách này. Dù không thể vượt qua cơn bệnh nan y, nhưng thông điệp của tác giả sẽ còn khiến người đọc nhớ mãi.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "image_220964.jpg",
                      "2021_06_22_18_40_48_1-390x510.png",
                      "2021_06_22_18_40_48_2-390x510.png",
                      "2021_06_22_18_40_48_3-390x510.png",
                      "2021_06_22_18_40_48_4-390x510.png",
                      "2021_06_22_18_40_48_5-390x510.png",
                      "2021_06_22_18_40_48_6-390x510.png",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Hello Các Bạn Mình Là Tôi Đi Code Dạo",
            new BigDecimal("189000"),
            cv.categoryCauChuyenCuocDoi,
            null,
            null,
            sv.supplier1980Books,
            new HashSet<>(Collections.singletonList(av.authorPhamHuyHoang)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherThanhNien,
            2022,
            null,
            400,
            20d,
            13d,
            null,
            368,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("hello-c_c-b_n---m_nh-l_-t_i-_i-code-d_o---b_a-1.jpg"),
            "Hello Các Bạn Mình Là Tôi Đi Code Dạo\n"
                + "\n"
                + "Cho tới thời điểm hiện tại, IT vẫn đang là một ngành hot và là sự lựa chọn của rất nhiều các bạn học sinh, sinh viên. Tuy nhiên, cho đến nay, những nguồn tài nguyên học tập trong ngành còn quá ít. Ngoài những tài liệu học tập của trường học thì những tài liệu khác chủ yếu vẫn là các thông tin trên internet hoặc từ những kinh nghiệm của những người đi trước. Để bạn đọc có cái nhìn chân thực và rõ nét hơn về ngành IT, thông qua cuốn sách này, tác giả Phạm Huy Hoàng đã tóm tắt và chia sẻ lại những câu chuyện, bài học kinh nghiệm đã gặp, đã đúc rút được để bạn đọc tham khảo và tìm ra những điểm tương đồng với những gì mình gặp trong khi học tập và làm việc để từ đó rút ra được bài học cho riêng mình.\n"
                + "\n"
                + "“Tôi Đi Code Dạo” là một blog, fanpage và Youtube channel được sáng lập bởi Phạm Huy Hoàng - một full-stack developer đã tốt nghiệp Thạc sĩ ngành Computer Science tại Đại học Lancaster ở Anh (Học bổng 18.000 bảng) và hiện đang làm việc cho một startup công nghệ tại Singapore.\n"
                + "\n"
                + "Nội dung mà \"Tôi Đi Code Dạo\" chia sẻ một nửa là về kĩ thuật lập trình, một nửa còn lại là những kinh nghiệm mà \"Mr. Code Dạo\" đã học được như: cách deal lương, sắp xếp thời gian, kĩ năng mềm, ngôn ngữ lập trình nên học, con đường phát triển nghề nghiệp… Đó là những điều quan trọng không thua gì kĩ năng lập trình.\n"
                + "\n"
                + "Phương châm của “Tôi Đi Code Dạo\" là: Developer cần biết nhiều điều hơn ngoài code, blog sẽ vừa dạy bạn code, vừa dạy bạn những điều còn lại.\n"
                + "\n"
                + "“Hello các bạn, mình là tôi đi code dạo” tổng hợp và chọn lọc hơn 60 bài viết trên blog của tác giả Phạm Huy Hoàng, chủ blog và fanpage “Tôi đi code dạo”. Cuốn sách hướng dẫn những kỹ năng mềm và những phần kỹ thuật thuần code, đồng thời chia sẻ những kinh nghiệm trong quá trình học tập và làm việc của tác giả.\n"
                + "\n"
                + "“Hello các bạn, mình là Tôi Đi Code Dạo” được tóm tắt khá ngắn gọn, lồng ghép một số câu chuyện vui để bạn đọc có thể cảm thấy thư giãn và tham khảo thêm thông tin một cách dễ dàng hơn.\n"
                + "\n"
                + "Không chỉ là những kiến thức chuyên ngành khô khan, những câu chuyện đời, chuyện nghề rất thật và nhiều cảm xúc qua lối viết giản dị nhưng dí dỏm sẽ mang đến cho người đọc cảm giác gần gũi, dễ tiếp thu và đồng cảm.\n"
                + "\n"
                + "Nếu bạn đã, đang và sẽ theo con đường lập trình viên thì đây chính là cuốn sách hữu ích dành cho bạn, giúp bạn có thêm những kỹ năng căn bản cũng như kinh nghiệm cần thiết trong hành trình theo đuổi ước mơ và nghề nghiệp của bản thân.\n"
                + "\n"
                + "LỜI BÌNH LUẬN DÀNH CHO CUỐN SÁCH\n"
                + "\n"
                + "Cuốn sách chia sẻ về việc lựa chọn ngành phát triển phần mềm dựa trên các yếu tố “sự kiên trì và nhẫn nại (tiến từng bước nhỏ), improve bản thân dựa trên các thiếu sót và sự mạo hiểm” cùng với yếu tố “khả năng tư duy”.\n"
                + "\n"
                + "Bên cạnh đó, tác giả cũng chia sẻ định hướng sau khi chọn ngành học này (xây dựng một learning/study path để xác định mục tiêu học tập để tập trung xây dựng “căn cơ nền móng” trên các kiến thức nền tảng được trang bị khi con người ta “mài đũng quần” trên ghế nhà trường), rèn luyện trong quá trình (teamwork - luyện tập làm việc theo nhóm; sharing - chia sẻ kiến thức cùng bạn bè, partner, đàn em, cộng đồng...) cho đến xây dựng một career path thông qua trải nghiệm (trải nghiệm = học thức + kinh nghiệm + thái độ + improve).\n"
                + "\n"
                + "Qua các nội dung chia sẻ dí dỏm, tác giả cũng đã trả lời các câu hỏi mà các bạn sinh viên chọn ngành - chuyên ngành thường hỏi.\n"
                + "\n"
                + "Đặc biệt nhất, các câu trả lời giải quyết tâm lý của các bạn sinh viên về việc “tại sao nhà trường luôn dạy các nội dung nền tảng cũ rích mà không cập nhật các nội dung mới, cập nhật theo thị trường?”.\n"
                + "\n"
                + "Qua cuốn sách này, các bạn sinh viên có lẽ sẽ nhận ra rằng học thức là do có nền móng - căn cơ (nội dung có lẽ “cũ” nên không ai dạy khi làm thực tế dẫn đến chúng ta “vô tình hay cố ý lướt qua nhau” để rồi...), kinh nghiệm là do rèn luyện để nâng cao tư duy lẫn kỹ năng - tích lũy qua công việc, dự án - chia sẻ kiến thức để có hỗ trợ và tạo ra các liên kết mới, cầu thị để từ đó chúng ta cải thiện chính bản thân hướng tới mơ ước của mình.\n"
                + "\n"
                + "Nội dung sách dẫn dắt chúng ta hiểu được những chia sẻ về cách ứng dụng framework, công nghệ khi chúng ta có “căn cơ nền móng”. Các bạn học sinh, sinh viên, các độc giả đã trải nghiệm qua nhiều dự án có thể đọc để có kim chỉ nam trong lựa chọn ngành nghề và nhìn lại chính mình để thấy sự trải nghiệm của chúng ta đã và sẽ như thế nào.\n"
                + "\n"
                + "- Thầy Kiều Trọng Khánh - Giảng viên Đại học FPT\n"
                + "\n"
                + "Nội dung viết hay và khá hài hước, dễ tiếp thu, phản ánh thực tế khá thú vị cuộc sống ở các công ty hiện tại. 5 thái độ cần có sẽ giúp nhiều cho các bạn trẻ khi vừa ra trường đi làm, không phải tự nhiên mà người ta hay nói “Thái độ hơn trình độ”.\n"
                + "\n"
                + "Các nội dung về testing giúp hiểu thêm về sự tương quan giữa dev và test, nếu dev có một chút kiến thức về test sẽ giúp hạn chế một số lỗi cơ bản hay gặp phải.\n"
                + "\n"
                + "-Thầy Phạm Công Thành - Là thầy của một lứa FU và FAT\n"
                + "\n"
                + "Cuốn sách “gối đầu giường” cho các bạn trẻ đã, đang và sẽ trên con đường trở thành lập trình viên đa-zi-năng. Những kinh nghiệm, bài học được Hoàng đúc kết và tinh gọn rất sát với thực tế, giúp cho các bạn tiết kiệm được rất nhiều thời gian cũng như có nhiều góc nhìn hơn trong ngành Công nghệ. Hãy biến mình trở thành developer “nội công thâm hậu” thay vì là coder “chỉ đâu đánh đó” các bạn nhé.\n"
                + "\n"
                + "-Huỳnh Đào Hoàng Vũ - Product Owner - Digital Experience tại Manulife Vietnam\n"
                + "\n"
                + "VỀ TÁC GIẢ\n"
                + "\n"
                + "Phạm Huy Hoàng - một full-stack developer đã tốt nghiệp Thạc sĩ ngành Computer Science tại Đại học Lancaster ở Anh (Học bổng 18.000 bảng) và hiện đang làm việc cho một startup công nghệ tại Singapore.\n"
                + "\n"
                + "-Fanpage: facebook.com/toidicodedao\n"
                + "\n"
                + "-Blog: toidicodedao.com/about/\n"
                + "\n"
                + "Youtube: youtube.com/c/toidicodedaoblog");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "hello_c_c_b_n_-_m_nh_l_t_i_i_code_d_o_-_b_a_1.png",
                      "hello_c_c_b_n_-_m_nh_l_t_i_i_code_d_o_-_b_a_fullpng.jpg",
                      "hello-c_c-b_n-m_nh-l_-t_i-_i-code-d_o---_nh-2.jpg",
                      "hello-c_c-b_n---m_nh-l_-t_i-_i-code-d_o---_nh-1.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Gã Nghiện Giày - Tự Truyện Của Nhà Sáng Lập Nike",
            new BigDecimal("160000"),
            cv.categoryKinhTe,
            null,
            null,
            sv.supplierNXBTre,
            new HashSet<>(Collections.singletonList(av.authorPhilKnight)),
            new HashSet<>(Collections.singletonList(tv.translatorTranLe)),
            null,
            lv.languageVN,
            pv.publisherTre,
            2017,
            null,
            480,
            15.5d,
            23d,
            null,
            452,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("8934974150961.jpg"),
            "Gã Nghiện Giày - Tự Truyện Của Nhà Sáng Lập Nike\n"
                + "\n"
                + "Một câu chuyện cuốn hút, và truyền cảm hứng.... 24 tuổi, lấy bằng MBA ở Đại học Stanford, trăn trở với những câu hỏi lớn của cuộc đời, băn khoăn không biết tiếp tục làm việc cho một tập đoàn lớn hay tạo dựng sư nghiệp riêng cho mình... 24 tuổi, năm 1962, Phil Knight quyết định rằng con đường khác thường mới là lựa chọn duy nhất dành cho ông... Rồi ông khoác ba lô đi đến Tokyo, Hongkong, Bangkok, Việt Nam, Calcutta, Kathmandu, Bombay, Cairo, Jerusalem, Rome, Florence, Milan, Venice, Paris,, Munich, Vienna, London, Hy Lạp... Để rồi khi về lại quê nhà ở bang Oregon, ông quyết định mở công ty nhập khẩu giày chạy từ Nhật. Ban đầu chỉ một đôi để thử, rồi vài chục đôi, bán tại tầng hầm của gia đình bố mẹ Phil. Đam mê, quyết tâm, dám chấp nhận thất bại, vượt qua nhiều chông gai từ chuyện thiếu vốn, đến chuyện cạnh tranh từ đối thủ nhập khẩu khác… Phil Knight đã đưa công ty nhập khẩu giày ra đời với 50 đô-la mượn của bố phát triển đến doanh nghiệp có doanh số hơn 1 triệu đô-la chỉ 10 năm sau đó, năm 1972.\n"
                + "\n"
                + "Nhưng không may, đối tác Nhật Bản trở chứng, đứng trước nguy cơ phải giải tán công ty mình dày công thành lập, ông và cộng sự đã chuyển hướng sang sản xuất giày, từ đó logo và thương hiệu Nike ra đời.\n"
                + "\n"
                + "Ông không đưa ra bí quyết, chiến lược, hay bước hành động dành cho các bạn trẻ mê kinh doanh, các chủ doanh nghiệp đang dồn hết tâm sức cho đứa con doanh nghiệp của mình; nhưng qua câu chuyện và cách xử lý của ông, người đọc sẽ học được rất nhiều những bài học quý giá về gầy dựng doanh nghiệp, vượt qua khó khăn và thất bại không thể tránh khỏi, để từ đó có tư duy kinh doanh cho riêng mình. Đó là những câu chuyện là nhân sự (chọn được người để cùng mình bước lên hành trình xây dựng công ty đầy gian nan), dòng tiền, về làm ăn với các đối tác, khi bị đối thủ tấn công, rồi mặt bằng, rồi biến động tỉ giá, rồi cả những sự vụ pháp lý có liên quan…\n"
                + "\n"
                + "Con đường Phil Knight cùng những gã nghiện giày khác xây dựng thương hiệu Nike đầy chông gai, những cuộc “chiến đấu” bất tận và cả thất bại, và Knight đã chia sẻ thẳng thắn tất cả trong quyển sách này. Ông không giấu giếm, kể cả những chuyện xấu của ông, ví dụ như một lần lục cặp tài liệu của đối tác Nhật Bản!\n"
                + "\n"
                + "Bằng giọng văn mộc mạc, câu ngắn gọn, người đọc như bị lôi cuốn vào câu chuyện truyền cảm hứng này.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "gaynghiengiaytutruyencuanhasanglapnike_1.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Chấn Hưng Nhật Bản",
            new BigDecimal("132000"),
            cv.categoryKinhTe,
            null,
            null,
            sv.supplierThaiHa,
            new HashSet<>(Collections.singletonList(av.authorClydePrestowitz)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherChinhTriQuocGiaSuThat,
            2018,
            null,
            350,
            16d,
            24d,
            null,
            327,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("bia_chan_hung_nhat_ban.jpg"),
            "Chấn Hưng Nhật Bản\n"
                + "\n"
                + "Nhật Bản là một trong ba nền kinh tế lớn nhất thế giới với nguồn vốn dồi dào, công nghệ tiên tiến, có kinh nghiệm quản lý hiện đại và hiệu quả. Là nước xuất khẩu vốn khổng lồ, với hơn 1 nghìn tỉ USD đầu tư trực tiếp ở nước ngoài nên Nhật Bản là đối tác kinh tế quan trọng với nhiều nước trên thế giới, trong đó có Việt Nam.\n"
                + "\n"
                + "Trên con đường đi lên trở thành một trong ba nền kinh tế lớn nhất thế giới, Nhật Bản đã phải trải qua hai cuộc tái thiết đất nước vô cùng quan trọng, diễn ra vào thời kỳ Phục hưng Minh Trị năm 1868 và diễn ra sau Chiến tranh thế giới thứ hai. Trong cả hai lần tự tái thiết này, Nhật Bản đã tạo ra một nỗ lực to lớn để du nhập, làm chủ, cải tiến, thay đổi công nghệ và bí quyết ngành công nghiệp của phương Tây. Và nhờ đó, Nhật Bản dường như trở nên bất bại trong lĩnh vực công nghiệp và công nghệ.\n"
                + "\n"
                + "Tuy nhiên, từ năm 1990 trở về đây, nền kinh tế Nhật Bản có dấu hiệu phát triển chậm lại, đặc biệt là vị trí đứng đầu của Nhật Bản trong lĩnh vực công nghiệp và công nghệ đang bị đe dọa thay thế bởi các đối thủ cạnh tranh như Hàn Quốc, Trung Quốc và Đài Loan. Hoạt động đổi mới sáng tạo của Nhật Bản cũng có dấu hiệu đi xuống vào đầu những năm 1990 mặc dù Chính phủ Nhật Bản đã hết sức nỗ lực tăng gấp đôi chi tiêu dành cho hoạt động nghiên cứu và phát triển. Một phần nguyên nhân là Nhật Bản chỉ tập trung đầu tư nghiên cứu và phát triển trong lĩnh vực ôtô và điện tử mà bỏ qua những lĩnh vực khác có quy mô toàn cầu và lại những lĩnh vực đòi hỏi đầu tư cao cho nghiên cứu và phát triển như dược phẩm, công nghệ sinh học, phần mềm và dịch vụ máy tính. Đứng trước nguy cơ này, câu hỏi đặt ra là liệu Nhật Bản có cần một cuộc tái thiết nữa hay không? Và nếu cần thì phải tái thiết như thế nào\n"
                + "\n"
                + "Nhằm giúp bạn đọc có thêm tài liệu nghiên cứu, tham khảo về phát triển nước Nhật Bản, Thái Hà Books và NXB Chính trị quốc gia Sự thật xuất bản cuốn sách Chấn hưng Nhật Bản: Làm cách nào Nhật Bản có thể tự tái thiết và tại sao điều này lại quan trọng với Hoa Kỳ và thế giới của tác giả Clyde Prestowitz.\n"
                + "\n"
                + "Cuốn sách được chia thành 10 chương và được viết theo phong cách hết sức độc đáo. Thay vì mô tả đất nước Nhật Bản của hiện tại sau đó đưa ra những khuyến nghị về mặt chính sách,... thì ở đây, tác giả đã phác họa một bức tranh về đất nước Nhật Bản trong tương lai của năm 2050, một đất nước đã được tái thiết lần thứ ba với rất nhiều sự thay đổi cả về kinh tế - xã hội và chính trị, chẳng hạn như GDP tăng 4,5%/năm, vượt xa so với bất kỳ nước lớn nào và gần gấp đôi GDP của Trung Quốc; dân số có chiều hướng tăng lên và vượt mức 150 triệu người; phụ nữ tham gia nhiều hơn vào lực lượng lao động, sinh nhiều con hơn;... và đáng ngạc nhiên hơn là Nhật Bản trở thành quốc gia nói tiếng Anh! Có thể thấy đây là một đất nước trong tương lai hoàn toàn thay đổi so với Nhật Bản của hiện tại. Tưởng như cuốn sách mang đến một câu chuyện giả tưởng song đó lại chính là những thông điệp, những lời khuyên và những kỳ vọng của tác giả, một người Mỹ đã từng có thời gian dài sinh sống và học tập tại Nhật Bản, dành cho đất nước này.\n"
                + "\n"
                + "Mục lục:\n"
                + "\n"
                + "Những lời khen tặng dành cho Chấn Hưng Nhật Bản\n"
                + "\n"
                + "Lời Nhà xuất bản\n"
                + "\n"
                + "Giới thiệu: Nền tảng của cuốn sách\n"
                + "\n"
                + "Chương 1: Tokyo, năm 2050\n"
                + "\n"
                + "Chương 2: 2016 - Năm của những cuộc khủng hoảng\n"
                + "\n"
                + "Chương 3: Hòa bình kiểu Thái Bình Dương\n"
                + "\n"
                + "Chương 4: Phụ nữ giải cứu đất nước\n"
                + "\n"
                + "Chương 5: Nhật Bản trở thành nước nói tiếng Anh\n"
                + "\n"
                + "Chương 6: Quốc gia đổi mới sáng tạo\n"
                + "\n"
                + "Chương 7: Tự chủ về năng lượng\n"
                + "\n"
                + "Chương 8: Tập đoàn Nhật Bản đến tập đoàn Đức - với những đặc trưng Nhật Bản\n"
                + "\n"
                + "Chương 9: Đánh bại kẻ trong cuộc\n"
                + "\n"
                + "Chương 10: Đi lên với dân, đi xuống với quan\n"
                + "\n"
                + "Kết luận: Tại sao Nhật Bản lại là vấn đề đáng quan tâm với Hoa Kỳ và thế giới?\n"
                + "\n"
                + "Lời cảm ơn\n"
                + "\n"
                + "Thông tin về tác giả:\n"
                + "\n"
                + "Clyde Prestowitz là tác giả của những cuốn sách bán chạy nhất và được đánh giá cao như Trading Places, Rogue Nation và Three Billion New Capitalists. Ông giữ chức vụ cố vấn cho Bộ trưởng Bộ Thương mại Hoa Kỳ và là nhà đàm phán thương mại đứng đầu tại châu Á. Ông cũng là Phó Chủ tịch của Ủy ban Thương mại và Đầu tư khu vực châu Á - Thái Bình Dương của Tổng thống Clinton. Là người thành lập Viện Chiến lược kinh tế ở Washington D.C, ông cũng đưa ra lời khuyên cho chính phủ, các tập đoàn quốc tế,các liên đoàn lao động về vấn đề cạnh tranh và chiến lược toàn cầu hoá.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_22_18_26_33_1-390x510.jpg",
                      "2021_06_22_18_26_33_2-390x510.jpg",
                      "2021_06_22_18_26_33_3-390x510.jpg",
                      "2021_06_22_18_26_33_4-390x510.jpg",
                      "2021_06_22_18_26_33_5-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Hồi Ký Tiến Sĩ Lê Thẩm Dương",
            new BigDecimal("280000"),
            cv.categoryCauChuyenCuocDoi,
            null,
            null,
            sv.supplierBaoSinhVienVNHocTro,
            new HashSet<>(Collections.singletonList(av.authorLeThamDuong)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherBaoSinhVienVietNamHoaHocTro,
            2019,
            null,
            500,
            25d,
            15d,
            null,
            300,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("untitled_1_1.jpg"),
            "");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "hoi-ky-tien-si-le-tham-duong.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Hiểu Về Trái Tim",
            new BigDecimal("138000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierFIRSTNEWS,
            new HashSet<>(Collections.singletonList(av.authorMinhNiem)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherTongHopTPHCM,
            2019,
            null,
            500,
            13d,
            20.5d,
            null,
            480,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_195509_1_14922.jpg"),
            "HIỂU VỀ TRÁI TIM – CUỐN SÁCH MỞ CỬA THẾ GIỚI CẢM XÚC CỦA MỖI NGƯỜI  \n"
                + "\n"
                + "“Hiểu về trái tim” là một cuốn sách đặc biệt được viết bởi thiền sư Minh Niệm. Với phong thái và lối hành văn gần gũi với những sinh hoạt của người Việt, thầy Minh Niệm đã thật sự thổi hồn Việt vào cuốn sách nhỏ này.\n"
                + "\n"
                + "Xuất bản lần đầu tiên vào năm 2011, Hiểu Về Trái Tim trình làng cũng lúc với kỷ lục: cuốn sách có số lượng in lần đầu lớn nhất: 100.000 bản. Trung tâm sách kỷ lục Việt Nam công nhận kỳ tích ấy nhưng đến nay, con số phát hành của Hiểu về trái tim vẫn chưa có dấu hiệu chậm lại.\n"
                + "\n"
                + "Là tác phẩm đầu tay của nhà sư Minh Niệm, người sáng lập dòng thiền hiểu biết (Understanding Meditation), kết hợp giữa tư tưởng Phật giáo Đại thừa và Thiền nguyên thủy Vipassana, nhưng Hiểu Về Trái Tim không phải tác phẩm thuyết giáo về Phật pháp. Cuốn sách rất “đời” với những ưu tư của một người tu nhìn về cõi thế. Ở đó, có hạnh phúc, có đau khổ, có tình yêu, có cô đơn, có tuyệt vọng, có lười biếng, có yếu đuối, có buông xả… Nhưng, tất cả những hỉ nộ ái ố ấy đều được khoác lên tấm áo mới, một tấm áo tinh khiết và xuyên suốt, khiến người đọc khi nhìn vào, đều thấy mọi sự như nhẹ nhàng hơn…\n"
                + "\n"
                + "Trong dòng chảy tất bật của cuộc sống, có bao giờ chúng ta dừng lại và tự hỏi: Tại sao ta giận? Tại sao ta buồn? Tại sao ta hạnh phúc? Tại sao ta cô đơn?... Tất cả những hiện tượng tâm lý ấy không ngừng biến hóa trong ta và tác động lên đời sống của ta, nhưng ta lại biết rất ít về nguồn gốc và sự vận hành của nó. Chỉ cần một cơn giận, hay một ý niệm nghi ngờ, cũng có thể quét sạch năng lượng bình yên trong ta và khiến ta nhìn mọi thứ đều sai lệch. Từ thất bại này đến đổ vỡ khác mà ta không lý giải nổi, chỉ biết dùng ý chí để tự nhắc nhở mình cố gắng tiến bộ hơn. Cho nên, hiểu về trái tim chính là nhu cầu căn bản nhất của con người.\n"
                + "\n"
                + "Hiểu về trái tim là thái độ trở về tiếp nhận và làm mới lại tâm hồn mình. Bởi hiểu được chính mình, ta sẽ dễ dàng hiểu được người khác, để ta có thể thương nhau chân thật.\n"
                + "\n"
                + "Xuyên suốt cuốn sách, tác giả đã đưa ra 50 khái niệm trong cuộc sống, 50 bài viết tâm lý trị liệu, được trình bày rất chân phương, dễ hiểu, thực tế,  vốn dĩ rất đời thường nhưng nếu suy ngẫm một chút chúng ta sẽ thấy thật sâu sắc như Khổ đau, Hạnh phúc, Tình yêu, Tức giận, Ghen tuông, Ích kỷ, Tham vọng, Thành thật, Nghi ngờ, Lo lắng, Do dự, Buông xả, Thảnh thơi, Bình yên, Cô đơn, Ái ngữ, Lắng nghe… Đúng như tựa đề sách, sách sẽ giúp ta hiểu về trái tim, hiểu về những tâm trạng, tính cách sâu thẳm trong trái tim ta.\n"
                + "\n"
                + "Lúc sinh thời cố Giáo sư, Tiến sĩ Trần Văn Khuê, có dịp tiếp cận với Hiểu Về Trái Tim. Ông nhận xét, như một cuốn sách đầu tiên thuộc chủ đề Hạt Giống Tâm Hồn do một tác giả Việt Nam viết, cuốn sách sẽ giúp người đọc hiểu được cảm xúc của tâm hồn, trái tim của chính mình và của người khác. Để, tận cùng là loại bỏ nỗi buồn, tổn thương và tìm được hạnh phúc trong cuộc sống. Có lẽ, vì điều này mà hơn 10 năm qua, Hiểu về trái tim vẫn là cuốn sách liên tục được tái bản và chưa có dấu hiệu “hạ nhiệt”, nhiều năm trời liên tục nằm trong top sách bán chạy nhất tại Việt Nam.\n"
                + "\n"
                + "Đáng quý hơn, tòan bộ lợi nhuận thu được từ việc phát hành cuốn sách này đều được chuyển về quỹ từ thiện cùng tên “Hiểu về trái tim” để giúp đỡ trẻ em có hoàn cảnh khó khăn, bất hạnh tại Việt Nam. Và đây cũng chính là niềm hạnh phúc cũng như ý nghĩa nhân ái lớn nhất mà cuốn sách đã mang lại, đặc biệt là khi tất cả hành trình này còn có sự đồng hành và góp sức của hàng trăm nghìn bản đọc trên khắp cả nước Việt Nam.\n"
                + "\n"
                + "Người nổi tiếng nói về cuốn sách:\n"
                + "\n"
                + "“Để chữa lành những tổn thương và nổi đau, cách tốt nhứt và hữu hiệu nhất là cần hiểu rõ được trái tim, tâm hồn của mình, và của người khác, cuốn sách Hiểu về Trái Tim chính là cuốn sách giúp bạn đọc làm được điều đó: Hiểu rõ và chữa lành trái tim, tâm hồn của mình và của những người xung quanh, để mọi người cùng được sống trong hạnh phúc và yêu thương. Với cuốn sách này, chúc bạn đọc sẽ luôn hạnh phúc và không bao giờ phải sống với một trái tim tan vỡ hay một tâm hồn tổn thương”  - Giáo sư – Tiến sĩ Trần Văn Khê\n"
                + "\n"
                + "\"Cuốn sách Hiểu về trái tim được viết ra với những trải nghiệm sâu sắc, nhằm giúp con người hiểu rõ và lý giải những cảm xúc của chính mình để tìm được sự bình an và hạnh phúc thật sự”. - Anh hùng Lao động, Thầy thuốc nhân dân, GS Bác sĩ Nguyễn Thị Ngọc Phượng\n"
                + "\n"
                + "\"Đây chính là con đường của đạo Tâm, với những nguyên tắc sống hạnh phúc – một thứ “an lạc hạnh” – từ những sẻ chia chân thành của tác giả. Con đường hạnh phúc đó đòi hỏi sự khổ luyện, chứng nghiệm qua quán chiếu bản thân, từ đó thấy biết bản chất của khổ đau, phiền não, và, vượt thoát…” - Bác sĩ Đỗ Hồng Ngọc. Nguyên Giám Đốc Trung Tâm Truyền Thông – Giáo Dục Sức Khoẻ TP.HCM\n"
                + "\n"
                + "\"Một cuốn sách hay, thực tế và rất hữu ích cho mọi người, đặc biệt đối với thanh thiếu niên và các bạn trẻ. Nếu rèn luyện được theo những điều hay như thế thì cuộc sống sẽ tốt đẹp hơn rất nhiều\". - Tạ Bích Loan, Trưởng Ban Thanh thiếu niên Đài truyền hình Việt Nam\n"
                + "\n"
                + "\"Đây là một cuốn sách đặc biệt, có tính giáo dục, tự nhận thức cao, được viết từ trái tim để chữa lành những trái tim. Một cuốn sách rất ý nghĩa!”. - Nhà báo Trần Tử Văn, Phó Tổng biên tập Báo Công an TP.HCM\n"
                + "\n"
                + "\"Hiểu về trái tim là cuốn sách thứ 180 của Tủ sách Hạt giống tâm hồn mà First News đã xuất bản, nhưng đây là cuốn sách của một tác giả Việt Nam đã để lại trong tôi những cảm xúc đặc biệt nhất. Với những trải nghiệm sâu sắc và tâm huyết mà tác giả đã viết trong 8 năm chắc chắn sẽ mang đến cho bạn đọc những khám phá mới mẻ và thú vị. Một cánh cửa rộng mở đang chờ đón bạn”. - Nguyễn Văn Phước, Giám đốc First News - Trí Việt\n"
                + "\n"
                + "Báo chí nói gì về “Hiểu về trái tim”:\n"
                + "\n"
                + "“'Hiểu về trái tim' là một cuốn sách đặc biệt, được viết nên từ tâm huyết của một nhà thiền sư mang tên Minh Niệm. Đã bao giờ giữa cuộc đời hối hả, bạn chợt dừng lại và tự hỏi mình rằng ' hạnh phúc là gì?' , '' khổ đau là gì?' hay chưa? Vâng, cuốn sách này sẽ giải đáp cho bạn tất cả những băn khoăn đó.” – baomoi.vn\n"
                + "\n"
                + "Về tác giả:\n"
                + "\n"
                + "Sinh tại Châu Thành, Tiền Giang, xuất gia tại Phật Học Viện Huệ Nghiêm – Sài Gòn, Minh Niệm từng thọ giáo thiền sư Thích Nhất Hạnh tại Pháp và thiền sư Tejaniya tại Mỹ. Kết quả sau quá trình tu tập, lĩnh hội kiến thức… Ông quyết định chọn con đường hướng dẫn thiền và khai triển tâm lý trị liệu cho giới trẻ làm Phật sự của mình. Tiếp cận với nhiều người trẻ, lắng nghe thế giới quan của họ và quan sát những đổi thay trong đời sống hiện đại, ông phát hiện có rất nhiều vấn đề của cuộc sống. Nhưng, tất cả, chỉ xuất phát từ một nguyên nhân: Chúng ta chưa hiểu, và chưa hiểu đúng về trái tim mình là chưa cơ chế vận động của những hỉ, nộ, ái, ố trong mỗi con người.\n"
                + "\n"
                + "“Tôi đã từng quyết lòng ra đi tìm hạnh phúc chân thật. Dù thời điểm ấy, ý niệm về hạnh phúc chân thật trong tôi rất mơ hồ nhưng tôi vẫn tin rằng nó có thật và luôn hiện hữu trong thực tại. Hơn mười năm sau, tôi mới thấy con đường. Và cũng chừng ấy năm nữa, tôi mới tự tin đặt bút viết về những điều mình đã khám phá và trải nghiệm…”, tác giả chia sẻ.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "qoute-3-hvtt.jpg",
                      "qoute-2-hvtt.jpg",
                      "qoute-1-hvtt.jpg",
                      "qoute-5-hvtt.jpg",
                      "qoute-6-hvtt.jpg",
                      "qoute-4-hvtt.jpg",
                      "qoute-7-hvtt.jpg",
                      "qoute-8-hvtt.jpg",
                      "hieu-ve-trai-tim.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Tâm Lý Học - Phác Họa Chân Dung Kẻ Phạm Tội",
            new BigDecimal("145000"),
            cv.categoryTamLy,
            null,
            null,
            sv.supplierAZVietNam,
            new HashSet<>(Collections.singletonList(av.authorDiepHongVu)),
            new HashSet<>(Collections.singletonList(tv.translatorDoAiNhi)),
            null,
            lv.languageVN,
            pv.publisherThanhNien,
            2021,
            null,
            300,
            24d,
            16d,
            null,
            280,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("ph_c-h_a-ch_n-dung-k_-ph_m-t_i.jpg"),
            "Tâm Lý Học - Phác Họa Chân Dung Kẻ Phạm Tội\n"
                + "\n"
                + "TÂM LÝ HỌC TỘI PHẠM - PHÁC HỌA CHÂN DUNG KẺ PHẠM TỘI\n"
                + "\n"
                + "Tội phạm, nhất là những vụ án mạng, luôn là một chủ đề thu hút sự quan tâm của công chúng, khơi gợi sự hiếu kỳ của bất cứ ai. Một khi đã bắt đầu theo dõi vụ án, hẳn bạn không thể không quan tâm tới kết cục, đặc biệt là cách thức và động cơ của kẻ sát nhân, từ những vụ án phạm vi hẹp cho đến những vụ án làm rúng động cả thế giới.\n"
                + "\n"
                + "Lấy 36 vụ án CÓ THẬT kinh điển nhất trong hồ sơ tội phạm của FBI, “Tâm lý học tội phạm - phác họa chân dung kẻ phạm tội” mang đến cái nhìn toàn cảnh của các chuyên gia về chân dung tâm lý tội phạm. Trả lời cho câu hỏi: Làm thế nào phân tích được tâm lý và hành vi tội phạm, từ đó khôi phục sự thật thông qua các manh mối, từ hiện trường vụ án, thời gian, dấu tích,… để tìm ra kẻ sát nhân thực sự. \n"
                + "\n"
                + "Đằng sau máu và nước mắt là các câu chuyện rợn tóc gáy về tội ác, góc khuất xã hội và những màn đấu trí đầy gay cấn giữa điều tra viên và kẻ phạm tội. Trong số đó có những con quỷ ăn thịt người; những cô gái xinh đẹp nhưng xảo quyệt; và cả cách trả thù đầy man rợ của các nhà khoa học,… Một số đã sa vào lưới pháp luật ngay khi chúng vừa ra tay, nhưng cũng có những kẻ cứ vậy ngủ yên hơn hai mươi năm. \n"
                + "\n"
                + "Bằng giọng văn sắc bén, “Tâm lý học tội phạm - phác họa chân dung kẻ phạm tội” hứa hẹn dẫn dắt người đọc đi qua các cung bậc cảm xúc từ tò mò, ngạc nhiên đến sợ hãi, hoang mang tận cùng. Chúng ta sẽ lần tìm về quá khứ để từng bước gỡ những nút thắt chưa được giải, khiến ta \"ngạt thở\" đọc tới tận trang cuối cùng. \n"
                + "\n"
                + "Hy vọng cuốn sách sẽ giúp bạn có cái nhìn sâu sắc, rõ ràng hơn về bộ môn tâm lý học tội phạm và có thể rèn luyện thêm sự tư duy, nhạy bén.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "b_a-tr_cph_c-h_a-ch_n-dung-k_-ph_m-t_i.jpg",
                      "b_a-sauph_c-h_a-ch_n-dung-k_-ph_m-t_i.jpg",
                      "bm_ph_c_h_a_ch_n_dung_k_ph_m_t_i.jpg",
                      "t_i-ph_m3d-.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Rèn Luyện Tư Duy Phản Biện",
            new BigDecimal("99000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplier1980Books,
            new HashSet<>(Collections.singletonList(av.authorAlbertRutherford)),
            new HashSet<>(Collections.singletonList(tv.translatorNguyenNgocAnh)),
            null,
            lv.languageVN,
            pv.publisherPhuNuVietNam,
            2020,
            null,
            300,
            25d,
            15d,
            null,
            0,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_195509_1_18448.jpg"),
            "Như bạn có thể thấy, chìa khóa để trở thành một người có tư duy phản biện tốt chính là sự tự nhận thức. Bạn cần phải đánh giá trung thực những điều trước đây bạn nghĩ là đúng, cũng như quá trình suy nghĩ đã dẫn bạn tới những kết luận đó. Nếu bạn không có những lý lẽ hợp lý, hoặc nếu suy nghĩ của bạn bị ảnh hưởng bởi những kinh nghiệm và cảm xúc, thì lúc đó hãy cân nhắc sử dụng tư duy phản biện! Bạn cần phải nhận ra được rằng con người, kể từ khi sinh ra, rất giỏi việc đưa ra những lý do lý giải cho những suy nghĩ khiếm khuyết của mình. Nếu bạn đang có những kết luận sai lệch này thì có một sự thật là những đức tin của bạn thường mâu thuẫn với nhau và đó thường là kết quả của thiên kiến xác nhận, nhưng nếu bạn biết điều này, thì bạn đã tiến gần hơn tới sự thật rồi!\n"
                + "\n"
                + "Những người tư duy phản biện cũng biết rằng họ cần thu thập những ý tưởng và đức tin của mọi người. Tư duy phản biện không thể tự nhiên mà có.\n"
                + "\n"
                + "Những người khác có thể đưa ra những góc nhìn khác mà bạn có thể chưa bao giờ nghĩ tới, và họ có thể chỉ ra những lỗ hổng trong logic của bạn mà bạn đã hoàn toàn bỏ qua. Bạn không cần phải hoàn toàn đồng ý với ý kiến của những người khác, bởi vì điều này cũng có thể dẫn tới những vấn đề liên quan đến thiên kiến, nhưng một cuộc thảo luận phản biện là một bài tập tư duy cực kỳ hiệu quả.\n"
                + "\n"
                + "Việc lắng nghe những ý kiến của người khác cũng có thể giúp bạn nhận ra rằng phạm vi tri thức của bạn không phải là vô hạn. Không ai có thể biết hết tất cả mọi thứ. Nhưng với việc chia sẻ và đánh giá phê bình kiến thức, chúng ta có thể mở rộng tâm trí. Nếu điều này khiến bạn cảm thấy không thoải mái, không sao cả. Trên thực tế, bước ra ngoài vùng an toàn là một điều quan trọng để mở rộng niềm tin và suy nghĩ của bạn. Tư duy phản biện không phải là chỉ biết vài thứ, và chắc chắn không phải việc xác nhận những điều bạn đã biết. Thay vào đó, nó xoay quanh việc tìm kiếm sự thật – và biến chúng trở thành thứ bạn biết.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "ren_luyen_tu_duy_phan_bien_1_2020_11_17_13_59_28.jpg",
                      "ren_luyen_tu_duy_phan_bien_2_2020_11_17_13_59_28.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Sức Mạnh Tiềm Thức",
            new BigDecimal("128000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierFIRSTNEWS,
            new HashSet<>(Collections.singletonList(av.authorJosephMurphyc)),
            new HashSet<>(Arrays.asList(tv.translatorBuiThanhChau, tv.translatorMaiSon)),
            null,
            lv.languageVN,
            pv.publisherTongHopTPHCM,
            2021,
            2021,
            370,
            20.5d,
            14.5d,
            1.5,
            335,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_237646.jpg"),
            "Là một trong những quyển sách về nghệ thuật sống nhận được nhiều lời ngợi khen và bán chạy nhất mọi thời đại, Sức Mạnh Tiềm Thức đã giúp hàng triệu độc giả trên thế giới đạt được những mục tiêu quan trọng trong đời chỉ bằng một cách đơn giản là thay đổi tư duy.\n"
                + "\n"
                + "Sức Mạnh Tiềm Thức giới thiệu và giải thích các phương pháp tập trung tâm thức nhằm xoá bỏ những rào cản tiềm thức đã ngăn chúng ta đạt được những điều mình mong muốn.\n"
                + "\n"
                + "Sức Mạnh Tiềm Thức không những có khả năng truyền cảm hứng cho người đọc, mà nó còn rất thực tế vì được minh hoạ bằng những ví dụ sinh động trong cuộc sống hằng ngày. Từ đó, Sức Mạnh Tiềm Thức giúp độc giả vận dụng năng lực trí tuệ phi thường tiềm ẩn troing mỗi người để tạo dựng sự tự tin, xây dựng các mối quan hệ hoà hợp, đạt được thành công trong sự nghiệp, vượt qua những nỗi sợ hãi và ám ảnh, xua đi những thói quen tiêu cực, và thậm chí còn hướng dẫn cách ta chữa lành những vết thương về thể chất cũng như tâm hồn để có sự bình an, hạnh phúc trọn vẹn trong cuộc sống.\n"
                + "\n"
                + "Báo chí nói gì về cuốn sách này\n"
                + "\n"
                + "“Bí mật sức mạnh của mọi vấn đề nằm sâu trong tiềm thức mỗi người, chỉ cần chúng ta thấu hiểu điều đó thì hoàn toàn có thể làm chủ cuộc đời mình.” – Sức mạnh tiềm thức: 'Bạn chính là những gì bạn nghĩ' (Tuổi Trẻ)\n"
                + "\n"
                + "“Hãy quyết tâm làm giàu với sự trợ giúp đắc lực của tiềm thức. Cách làm giàu khôn ngoan là dựa vào sức mạnh tiềm thức. Tham công tiếc việc và ra sức tích cóp của cải chỉ khiến bạn mất đi cơ hội tận hưởng cuộc sống mà thôi.” – Vận dụng sức mạnh tiềm thức để làm giàu (Dân Trí)\n"
                + "\n"
                + "Về tác giả\n"
                + "\n"
                + "Joseph Murphy (1898 – 1981) là Tiến sĩ Tâm lý học, Tác giả và diễn giả người Mỹ gốc Ireland. Các bài diễn thuyết và tác phẩm của ông đã thay đổi nhận thức của hàng triệu người trên thế giới, giúp họ nhìn nhận được sức mạnh bên trong của bản thân để có cuộc sống tốt đẹp và ý nghĩa như mong muốn.\n"
                + "\n"
                + "Tác phẩm Sức Mạnh Tiềm Thức – The Power Of Your Subconscious Mind đã trở thành một trong những cuốn sách về nghệ thuật sống hay nhất và bán chạy nhất mọi thời đại. First News mới tái bản tác phẩm này theo phiên bản bìa cứng tuyệt đẹp, xứng đáng làm quà tặng và có mặt trên những kệ sách giá trị.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2022_08_20_09_20_24_1-390x510.jpg",
                      "2022_08_20_09_20_24_2-390x510.jpg",
                      "2022_08_20_09_20_24_3-390x510.jpg",
                      "2022_08_20_09_20_24_4-390x510.jpg",
                      "2022_08_20_09_20_24_5-390x510.jpg",
                      "2022_08_20_09_20_24_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Đắc Nhân Tâm",
            new BigDecimal("86000"),
            cv.categoryRenLuyenNhanCach,
            null,
            null,
            sv.supplierFIRSTNEWS,
            new HashSet<>(Collections.singletonList(av.authorDaleCarnegie)),
            new HashSet<>(Collections.singletonList(tv.translatorNguyenVanPhuoc)),
            null,
            lv.languageVN,
            pv.publisherTongHopTPHCM,
            2021,
            2021,
            350,
            20.5d,
            14.5d,
            null,
            320,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("dntttttuntitled.jpg"),
            "Đắc nhân tâm của Dale Carnegie là quyển sách của mọi thời đại và một hiện tượng đáng kinh ngạc trong ngành xuất bản Hoa Kỳ. Trong suốt nhiều thập kỷ tiếp theo và cho đến tận bây giờ, tác phẩm này vẫn chiếm vị trí số một trong danh mục sách bán chạy nhất và trở thành một sự kiện có một không hai trong lịch sử ngành xuất bản thế giới và được đánh giá là một quyển sách có tầm ảnh hưởng nhất mọi thời đại.\n"
                + "\n"
                + "Đây là cuốn sách độc nhất về thể loại self-help sở hữu một lượng lớn người hâm mộ. Ngoài ra cuốn sách có doanh số bán ra cao nhất được tờ báo The New York Times bình chọn trong nhiều năm. Cuốn sách này không còn là một tác phẩm về nghệ thuật đơn thuần nữa mà là một bước thay đổi lớn trong cuộc sống của mỗi người.\n"
                + "\n"
                + "Nhờ có tầm hiểu biết rộng rãi và khả năng ‘ứng xử một cách nghệ thuật trong giao  tiếp’ – Dale Carnegie đã viết ra một quyển sách với góc nhìn độc đáo và mới mẻ trong giao tiếp hàng ngày một cách vô cùng thú vị – Thông qua những mẫu truyện rời rạc nhưng lại đầy lý lẽ thuyết phục. Từ đó tìm ra những kinh nghiệm để đúc kết ra những nguyên tắc vô cùng ‘ngược ngạo’, nhưng cũng rất logic dưới cái nhìn vừa sâu sắc, vừa thực tế.\n"
                + "\n"
                + "Hơn thế nữa, Đắc Nhân Tâm còn đưa ra những nghịch lý mà từ lâu con người ta đã hiểu lầm về phương hướng giao tiếp trong mạng lưới xã hội, thì ra, người giao tiếp thông minh không phải là người có thể phát biểu ra những lời hay nhất, mà là những người học được cách mỉm cười, luôn biết cách lắng nghe, và khích lệ câu chuyện của người khác.\n"
                + "\n"
                + "Cuốn sách Đắc Nhân Tâm được chia ra làm 4 nội dung chính và mỗi phần cũng là một bài học về cuộc sống.\n"
                + "\n"
                + "Phần 1: Nghệ thuật ứng xử cơ bản\n"
                + "\n"
                + "- Không nên trách móc và than phiền, thù oán\n"
                + "\n"
                + "- Muốn lấy được mật ong thì không nên phá tổ\n"
                + "\n"
                + "- Trách móc một người nào đó là một việc dễ dàng. Thay vào đó, bạn hãy ngó lơ sự phán xét đó mà rộng lượng. Đồng thời tha thứ cho người đó và bỏ qua hết mọi chuyện thì mới đáng được tự hào.\n"
                + "\n"
                + "- Biết khen ngợi và nhận được ơn nghĩa của người khác mới là bí mật lớn nhất về phép cư xử.\n"
                + "\n"
                + "- Bạn cần phải biết khen ngợi và biết ơn người khác một cách thành thật nhất, chính là chìa khóa tạo nên tình nhân ái.\n"
                + "\n"
                + "Phần 2:\n"
                + "\n"
                + "- Bạn nên thật lòng quan tâm đến người khác\n"
                + "\n"
                + "- Mỉm cười đó là cách để tạo ấn tượng tốt nhất\n"
                + "\n"
                + "- Hay ghi nhớ lấy tên của người bạn đã và đang giao tiếp với bạn\n"
                + "\n"
                + "- Bạn nên lắng nghe và khuyến khích người khác để trở thành người có khả năng giao tiếp cao\n"
                + "\n"
                + "- Hãy nói về cái mà người khác để ý sẽ thu hút được người đó\n"
                + "\n"
                + "Phần 3: Cách hướng người khác làm theo suy nghĩ của mình\n"
                + "\n"
                + "- Không được để ra tranh cãi và cách giải quyết tốt nhất đó là không nên để nó xảy ra\n"
                + "\n"
                + "- Tôn trọng ý kiến của mọi người, không bao giờ được nói người khác sai\n"
                + "\n"
                + "- Thừa nhận được sai làm của mình, nếu phạm phải thì bạn cần phải thừa nhận điều đó\n"
                + "\n"
                + "- Bạn cần phải hỏi những câu hỏi cần thiết để họ trả lời bạn bằng tiếng vâng ngay lập tức\n"
                + "\n"
                + "- Khi nói chuyện bạn hãy để cho đối phương cảm nhận được họ làm chủ trong câu chuyện\n"
                + "\n"
                + "- Để nhận được sự hợp tác thì bạn cần phải để họ nghĩ họ là người đưa ra ý tưởng\n"
                + "\n"
                + "- Bạn cần phải đặt mình vào hoàn cảnh của họ để có thể hiểu hết về bản thân của họ\n"
                + "\n"
                + "- Bạn hãy đồng cảm với mong muốn của mọi người\n"
                + "\n"
                + "- Trong cuộc sống bạn hãy gợi lên sự cao thượng\n"
                + "\n"
                + "- Thân thiện trong giao tiếp đó chính là sử dụng mật ngọt để bắt đầu được câu chuyện\n"
                + "\n"
                + "- Bạn nên trình bày một cách rõ ràng và sinh động nhất\n"
                + "\n"
                + "- Trong cuộc sống bạn cần phải vượt lên được thử thách\n"
                + "\n"
                + "- Trước khi phê bình người khác bạn hãy khen ngợi người đó\n"
                + "\n"
                + "- Khi phê bình bạn nên phê bình một cách gián tiếp\n"
                + "\n"
                + "- Bạn nên khen ngợi người khác để có được một cuộc sống xứng đáng\n"
                + "\n"
                + "- Bạn nên mở đường cho người khác để khắc phục sai lầm\n"
                + "\n"
                + "- Bạn nên tôn vinh người khác khi nói chuyện\n"
                + "\n"
                + "- Trước khi phê bình người khác thì bạn nên nhìn nhận lại bản thân của mình\n"
                + "\n"
                + "- Thay vì ra lệnh cho người khác thì bạn hãy gợi ý cho họ\n"
                + "\n"
                + "- Trong mọi chuyện bạn nên giữ thể diện cho người khác\n"
                + "\n"
                + "- Bạn cần phải lưu ý những mối quan hệ của mình\n"
                + "\n"
                + "Phần 4: Chuyển hóa được con người và không tạo lên sự oán hận và chống đối\n"
                + "\n"
                + "Báo chí nhắc gì về “Đắc Nhân Tâm”\n"
                + "\n"
                + "“Nói đến sách nghệ thuật ứng xử thì không thể không nhắc đến \"Đắc nhân tâm\" của Dale Carnegie. Đây là một trong những cuốn sách gối đầu của nhiều thế hệ đi trước và ngày nay. Với chặng đường hơn 80 năm kể từ khi lần đầu được xuất bản, \"Đắc nhân tâm\" đã mang đến cho chúng ta bài học vô cùng giá trị đó là nghệ thuật ứng xử để được lòng người. \"Đắc nhân tâm\" là quyển sách nổi tiếng và bán chạy nhất và có mặt ở hàng trăm quốc gia khác nhau, và hơn thế nữa đây còn là quyển sách liên tục đứng đầu danh mục sách bán chạy nhất do thời báo NewYork Times bình chọn trọng suốt 10 năm liền.” – Cafebiz.vn, 3 cuốn sách nên đọc đi đọc lại trong đời để ngẫm về cuộc sống\n"
                + "\n"
                + "“Đắc Nhân Tâm – của tác giả Dale Carnegie là quyển sách nổi tiếng nhất, bán chạy nhất và có tầm ảnh hưởng nhất của mọi thời đại. Tác phẩm đã được chuyển ngữ sang hầu hết các thứ tiếng trên thế giới và có mặt ở hàng trăm quốc gia. Một khám phá rất thú vị dành cho các bậc phụ huynh khi đọc cuốn sách này là biết cách lắng nghe trò chuyện cùng con, cách trị chứng tè dầm của trẻ nhỏ, hay cách làm cho một đứa trẻ từ quậy phá thành ngoan ngoãn… Đó hẳn là những câu chuyện nuôi dạy trẻ rất đúng, rất hay, rất đời thường đáng để bạn đọc suy ngẫm và chiêm nghiệm”. – Motthegioi.vn, Đắc nhân tâm: ‘Cha đã quên’ nhắc những điều nên nhớ\n"
                + "\n"
                + "“Đắc Nhân Tâm” đưa ra những lời khuyên về cách cư xử, ứng xử và giao tiếp với mọi người để đạt được thành công trong cuộc sống. Đây được coi là một trong những cuốn sách nổi tiếng nhất, bán chạy nhất và có tầm ảnh hưởng nhất mọi thời đại mà bạn không nên bỏ qua.” – Cafef.vn, 20 câu nói vàng đáng nhớ từ tuyệt tác để đời “Đắc Nhân Tâm”\n"
                + "\n"
                + "Về tác giả\n"
                + "\n"
                + "Dale Breckenridge Carnegie (24 tháng 11 năm 1888 – 1 tháng 11 năm 1955) là một nhà văn và nhà thuyết trình Mỹ và là người phát triển các lớp tự giáo dục, nghệ thuật bán hàng, huấn luyện đoàn thể, nói trước công chúng và các kỹ năng giao tiếp giữa mọi người. Ra đời trong cảnh nghèo đói tại một trang trại ở Missouri, ông là tác giả cuốn Đắc Nhân Tâm, được xuất bản lần đầu năm 1936, một cuốn sách thuộc hàng bán chạy nhất và được biết đến nhiều nhất cho đến tận ngày nay. Ông cũng viết một cuốn tiểu sử Abraham Lincoln, với tựa đề Lincoln con người chưa biết, và nhiều cuốn sách khác.\n"
                + "\n"
                + "Carnegie là một trong những người đầu tiên đề xuất cái ngày nay được gọi là đảm đương trách nhiệm, dù nó chỉ được đề cập tỉ mỉ trong tác phẩm viết của ông. Một trong những ý tưởng chủ chốt trong những cuốn sách của ông là có thể thay đổi thái độ của người khác khi thay đổi sự đối xử của ta với họ.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "dntttttuntitled.png",
                      "dacnhantam_biamem_86k-01.jpg",
                      "dacnhantam-02_1_1.jpg",
                      "dacnhantam-01_1_1.jpg",
                      "dacnhantam-04_1_1.jpg",
                      "dacnhantam-03_1_1.jpg",
                      "dacnhantam-05_1_1.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Tư Duy Nhanh Và Chậm",
            new BigDecimal("269000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierAlphaBooks,
            new HashSet<>(Collections.singletonList(av.authorDanielKahneman)),
            new HashSet<>(Arrays.asList(tv.translatorHuongLan, tv.translatorXuanThanh)),
            null,
            lv.languageVN,
            pv.publisherTheGioi,
            2021,
            2021,
            350,
            24d,
            16d,
            null,
            612,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_230367.jpg"),
            "Thinking fast and slow - Tư duy nhanh và chậm  - cuốn sách được Tạp chí Tài chính Mỹ đặc biệt đánh giá là “một kiệt tác” trong việc nói về tính hợp lý, phi lý của con người trong tư duy, trong việc đánh giá và ra quyết định.\n"
                + "\n"
                + "Trong cuộc sống, dù bạn có cẩn trọng đến mức nào thì vẫn có những lúc bạn đưa ra những quyết định dựa trên cảm tình chủ quan của mình. Thinking fast and slow, cuốn sách nổi tiếng tổng hợp tất cả nghiên cứu được tiến hành qua nhiều thập kỷ của nhà tâm lý học từng đạt giải Nobel Kinh tế Daniel Kahneman sẽ cho bạn thấy những sư hợp lý và phi lý trong tư duy bản thân. Cuốn sách được đánh giá là “kiệt tác” trong việc thay đổi hành vi của con người, Thinking fast and slow đã giành được vô số giải thưởng danh giá, lọt vào Top 11 cuốn sách kinh doanh hấp dẫn nhất năm 2011. Cuốn sách được Alpha Books mua bản quyền và xuất bản vào Quý I năm nay. Thinking fast and slow dù là cuốn sách có tính hàn lâm cao nhưng được truyền tải một cách vui nhộn và dễ hiểu, hứa hẹn sẽ mang lại cho bạn đọc nhiều kiến thức mới mẻ, bổ ích.\n"
                + "\n"
                + "Thinking fast and slow sẽ đưa ra và lý giải hai hệ thống tư duy tác động đến con đường nhận thức của bạn. Kahneman gọi đó là: hệ thống 1 và hệ thống 2. Hệ thống 1, còn gọi là cơ chế nghĩ nhanh, tự động, thường xuyên được sử dụng, cảm tính, rập khuôn và tiềm thức. Hệ thống 2, còn gọi là cơ chế nghĩ chậm, đòi hỏi ít nỗ lực, ít được sử dụng, dùng logic có tính toán và ý thức. Trong một loạt thí nghiệm tâm lý mang tính tiên phong, Kahneman và Tversky chứng minh rằng, con người chúng ta thường đi đến quyết định theo cơ chế nghĩ nhanh hơn là ghĩ chậm. Phần lớn nội dung của cuốn sách chỉ ra những sai lầm của con người khi suy nghĩ theo hệ thống 1. Kahneman chứng minh rằng chúng ta tệ hơn những gì chúng ta tưởng: đó là chúng ta không biết những gì chúng ta không biết!\n"
                + "\n"
                + "Thinking fast and slow đáp ứng hai tiêu chí của một cuốn sách hay, thứ nhất nó thách thức quan điểm của người đọc, thứ hai, nó không phải là những trang sách với những con chữ khô cứng mà vô cùng vui nhộn và hấp dẫn. Không nghi ngờ gì nữa, đây là cuốn sách hàn lâm dành cho tất cả mọi người!\n"
                + "\n"
                + "Về tác giả:\n"
                + "\n"
                + "Daniel Kahneman, sinh năm 1934, nhà tâm lý học người Mỹ  gốc Israel đã dành giải Nobel Kinh tế năm 2002. Ông dành cả cuộc đời của mình để nghiên cứu về tâm lý học hành vi con người. Cùng với người cộng sự đã qua đời Amos Tversky, hai ông đã có được những nghiên cứu sâu sắc về con đường tư duy và nhận thức của con người.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_23_08_21_26_1-390x510.jpg",
                      "2021_06_23_08_21_26_2-390x510.jpg",
                      "2021_06_23_08_21_26_3-390x510.jpg",
                      "2021_06_23_08_21_26_4-390x510.jpg",
                      "2021_06_23_08_21_26_5-390x510.jpg",
                      "2021_06_23_08_21_26_6-390x510.jpg",
                      "2021_06_23_08_21_26_7-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "The Secret - Bí Mật",
            new BigDecimal("298000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierNXBTheGioi,
            new HashSet<>(Collections.singletonList(av.authorRhondaByrne)),
            new HashSet<>(Collections.singletonList(tv.translatorNguyenPhucQuangNgoc)),
            null,
            lv.languageVN,
            pv.publisherTheGioi,
            2021,
            2021,
            437,
            17.5d,
            13.5d,
            1.7,
            201,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_244718_1_5272.jpg"),
            "Liệu Luật hấp dẫn có thực sự là một bí mật? Bạn có thể nghĩ rằng nó chỉ là một thứ huyền bí vớ vẩn, nhưng trong sách Kinh thánh cũng có một câu nói rất kinh điển, “Người có ước mơ, luôn nhận được nhiều hơn”. Đó chính là tự cảm nhận sự đầy đủ ngay từ đầu, thì bạn sẽ hấp dẫn nhiều sự đầy đủ hơn nữa.\n"
                + "\n"
                + "Nếu bạn luôn cảm thấy thiếu thì bạn luôn nhận được tác động tương tự. Tại sao điều này xảy ra luôn là một điều huyền bí? Nhưng nếu bạn có tin hay không đi nữa thì Luật hấp dẫn của Rhonda luôn tồn tại.\n"
                + "\n");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_12_09_13_55_44_1-390x510.jpg",
                      "2021_12_09_13_55_44_2-390x510.jpg",
                      "2021_12_09_13_55_44_3-390x510.jpg",
                      "2021_12_09_13_55_44_4-390x510.jpg",
                      "2021_12_09_13_55_44_5-390x510.jpg",
                      "2021_12_09_13_55_44_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Hãy Khiến Tương Lai Biết Ơn Vì Hiện Tại Bạn Đã Cố Gắng Hết Mình",
            new BigDecimal("99000"),
            cv.categoryKyNangSong,
            null,
            null,
            sv.supplierAZVietNam,
            new HashSet<>(Collections.singletonList(av.authorBachTo)),
            new HashSet<>(Collections.singletonList(tv.translatorCaoBichThuy)),
            null,
            lv.languageVN,
            pv.publisherVanHoc,
            2021,
            null,
            350,
            20.5d,
            14.5d,
            null,
            320,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_226524.jpg"),
            "- Cuốn sách dành cho những bạn trẻ chênh vênh và đầy hoang mang nhưng không ngừng theo đuổi sự nỗ lực\n"
                + "\n"
                + "- Từng được đón đọc bởi 1 triệu độc giả khắp Trung Quốc, 4 chương sách tổng hợp 50 lời khuyên chất lượng nhất dành cho thế hệ trẻ:\n"
                + "\n"
                + "Với sự nghiệp: Bạn hiểu rằng không ai cả đời thuận buồm xuôi gió, mỗi người ít nhiều đều gặp phải khó khăn trắc trở, việc bạn cần làm - là không đổ lỗi cho ngoại cảnh mà âm thầm nâng cao năng lực và rút ra bài học cho bản thân\n"
                + "\n"
                + "Với chính mình: Bạn hiểu rằng mình cần cải thiện rất nhiều nhưng sự đánh giá của người khác không quan trọng bằng sự ghi nhận của chính bản thân\n"
                + "\n"
                + "Với tình yêu: Khi ta là chính mình ở phiên bản tốt nhất, ta sẽ được ở cạnh sự lựa chọn tốt nhất\n"
                + "\n"
                + "Với các mối quan hệ:\n"
                + "\n"
                + "- Bạn bè chân chính là dù không có điều kiện nói chuyện nhiều nhưng vẫn hiểu nhau. Lúc nào bạn cần giúp đỡ, họ luôn là người có mặt.\n"
                + "\n"
                + "- Thời gian dành cho bố mẹ là quý giá nhất. Hiếu kính là điều không thể trì hoãn.\n"
                + "\n"
                + "Những người trẻ tuổi, khi tài năng và kinh nghiệm có hạn nhưng luôn muốn có trọn vẹn mọi thứ trong tay: tiền bạc, sự nghiệp, gia đình, bạn bè và cả tình yêu. Đồng cảm với mong muốn đó, 320 trang của cuốn sách này truyền đi thông điệp cổ vũ \"Chỉ cần bạn nỗ lực cố gắng hết mình ngay bây giờ thì tương lai chính là thời khắc bạn có được tất cả những điều bạn muốn\"\n"
                + "\n"
                + "Nỗ lực sớm, thành công sớm. Cố gắng bao nhiêu, có được bấy nhiêu. Không bỏ ra chút công sức nào, thứ bạn có duy nhất, chắc chắn là sự HỐI TIẾC.\n"
                + "\n"
                + "----\n"
                + "\n"
                + "MỘT SỐ TRÍCH DẪN HAY TRONG SÁCH\n"
                + "\n"
                + "“Hoặc là bạn định hướng cuộc sống, hoặc là cuộc sống điều khiển bạn, thái độ của bạn sẽ quyết định ai là người cầm lái, ai là người bị lái.”\n"
                + "\n"
                + "“Trong cuộc sống, chúng ta đều sẽ gặp phải hình mẫu \"con nhà người ta\" khiến bạn rối tinh rối mù vì không ngừng so sánh chính mình, nhưng nghĩ kĩ lại, có thể chính bạn cũng là \"con nhà người ta\" với một ai đó khác. Nên quan trọng, người cần phải so sách, không phải là ai đó ngoài kia, mà là chính bạn. Sự cố gắng của bạn, sẽ không bao giờ đem lại được sự xuất sắc giống hệt người ta, nhưng bạn có thể dựa vào sự nỗ lực đó để tìm ra giá trị riêng cho bản thân mình\n"
                + "\n"
                + "Nếu một ngày nào đó chúng ta chìm trong biển người và sống một cuộc đời tầm thường, hẳn là chúng ta đã không làm việc chăm chỉ để có một cuộc sống sung túc.”\n"
                + "\n"
                + "“Nếu ngay bây giờ bạn có một ước mơ, hãy im lặng, dốc toàn lực để hành động. Hãy nhớ rằng, nếu hôm nay không đi nhanh một chút, có thể ngày mai sẽ phải chạy, ngày kia có thể không thấy rõ phương hướng phía trước nữa. Sao cứ phải bận tâm ước mơ có thành hiện thực hay không? Hãy cứ tiến lên phía trước, chạy nhanh lên phía trước là được rồi!”");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_05_10_08_57_22_1-390x510.jpg",
                      "2021_05_10_08_57_22_2-390x510.jpg",
                      "2021_05_10_08_57_22_3-390x510.jpg",
                      "2021_05_10_08_57_22_4-390x510.jpg",
                      "2021_05_10_08_57_22_5-390x510.jpg",
                      "2021_05_10_08_57_22_6-390x510.jpg",
                      "2021_05_10_08_57_22_7-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Quản Trị Tài Chính",
            new BigDecimal("590000"),
            cv.categoryDaiHoc,
            null,
            null,
            sv.supplierCengage,
            new HashSet<>(Collections.singletonList(av.authorCengage)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherHongDuc,
            2019,
            null,
            1256,
            23.5d,
            15.5d,
            4.5,
            1036,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("taichinhqt.jpg"),
            "Khi xuất bản lần đầu tiên cuốn QUẢN TRỊ TÀI CHÍNH vào 37 năm về trước, chúng tôi mong muốn cung cấp một cuốn sách giáo khoa mang tính chất giới thiệu để các bạn sinh viên có thể dễ dàng tiếp cận và cảm thấy hứng thú. Cuốn QUẢN TRỊ TÀI CHÍNH này ngay lập tức đã trở thành cuốn sách giáo khoa để giảng dạy ngành tài chính ở bậc đại học và vai trò của nó vẫn được duy trì cho đến hiện nay. Mục tiêu tiếp theo của chúng tôi ở phiên bản này là cho ra đời một cuốn sách có bổ sung một gói hệ thống tiêu chuẩn mới cho những cuốn sách giáo khoa chuyên ngành tài chính.\n"
                + "\n"
                + "Tài chính là một lĩnh vực rất thú vị và đang từng bước có sự thay đổi. So với lần xuất bản đầu tiên, nền tài chính toàn cầu đã xuất hiện nhiều chuyển biến quan trọng. Ở giữa thời điểm môi trường đang chuyển đổi như thế này, đây là khoảng thời gian thú vị để trở thành một sinh viên ngành tài chính. Trong lần xuất bản mới nhất, chúng tôi nhấn mạnh và phân tích những sự kiện dẫn đến những sự thay đổi dựa trên quan điểm tài chính. Mặc dù môi trường tài chính đang thay đổi, những nguyên lý mang tính “thử thách và chân thật” mà cuốn sách tập trung nhấn mạnh hơn trong hơn 3,5 thập kỷ qua vẫn tiếp tục đóng vai trò quan trọng.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2020_05_23_09_30_07_1-390x510.jpg",
                      "2020_05_23_09_30_07_2-390x510.jpg",
                      "2020_05_23_09_30_07_3-390x510.jpg",
                      "2020_05_23_09_30_07_4-390x510.jpg",
                      "2020_05_23_09_30_07_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Giáo Trình Điện Tử Công Suất Mạch Biến Đổi Điện Áp",
            new BigDecimal("148000"),
            cv.categoryDaiHoc,
            null,
            null,
            sv.supplierNXBThanhNien,
            new HashSet<>(
                Arrays.asList(
                    av.authorTSQuachThanhHai,
                    av.authorThSLeNguyenHongPhong,
                    av.authorKSPhamQuangHuy)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherThanhNien,
            2020,
            null,
            400,
            24d,
            16d,
            null,
            392,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_195509_1_51658.jpg"),
            "Điện tử công suất là môn học ngoài việc nghiên cứu bản chất vật lý, các quá trình diễn ra trong các linh kiện điện tử công suất như Diode, Thyristor, GTO, Triac, Mosfet công suất, IGBT, SID, MCT làm việc ở chế độ chuyển mạch trong quá trình biến đổi điện năng. Khảo sát các tính năng kỹ thuật và những ứng dụng của các linh kiện này. Môn học còn tìm hiểu các bộ biến đổi qua việc liên kết các linh kiện điện tử công suất và các thiết bị điện khác tạo thành một mạch điện cụ thể bao gồm mạch điều khiển và mạch động lực và tính toán thiết kế mạch điều khiển.\n"
                + "\n"
                + "Sách gồm 392 trang khổ 16 x 24 cm trình bày qua 16 bài tập và 1 phụ lục.\n"
                + "\n"
                + "Bài tập 1: Mạch biến đổi điện áp xoay chiều.\n"
                + "\n"
                + "Bài tập 2: Mạch biến đổi điện áp một chiều.\n"
                + "\n"
                + "Bài tập 3: Mạch nguồn dc chuyển mạch.\n"
                + "\n"
                + "Bài tập 4: Mạch biến đổi điện áp xoay chiều một pha tải R.\n"
                + "\n"
                + "Bài tập 5: Mạch biến đổi điện áp một chiều bộ tăng áp.\n"
                + "\n"
                + "Bài tập 6: Mạch giảm áp một chiều.\n"
                + "\n"
                + "Bài tập 7: Mạch Buck với dòng điện gián đoạn.\n"
                + "\n"
                + "Bài tập 8: Mạch tăng áp một chiều Boost.\n"
                + "\n"
                + "Bài tập 9: Mạch Buck chỉnh lưu đồng bộ.\n"
                + "\n"
                + "Bài tập 10: Mạch tăng giảm áp một chiều Buck-Boost.\n"
                + "\n"
                + "Bài tập 11: Mạch tăng giảm áp một chiều Cuk.\n"
                + "\n"
                + "Bài tập 12: Mạch tăng giảm áp một chiều Sepic.\n"
                + "\n"
                + "Bài tập 13: Mạch biến đổi điện áp xoay chiều một pha tải R.\n"
                + "\n"
                + "Bài tập 14: Mạch biến đổi điện áp xoay chiều một pha tải R+L.\n"
                + "\n"
                + "Bài tập 15: Mạch Flyback – chế độ dòng điện liên tục.\n"
                + "\n"
                + "Bài tập 16: Mạch Flyback – chế độ dòng điện gián đoạn.\n"
                + "\n"
                + "Phụ lục: Các mạch điều khiển cơ bản với Arduino.\n"
                + "\n"
                + "Một số điểm cần lưu ý khi sử dụng sách\n"
                + "\n"
                + "Một tài liệu biên soạn thực hành rất thực dụng giúp việc học điện tử công suất nhanh chóng và dễ dàng. Sách phục vụ cho những người tự học môn điện tử công suất, thiết kế mạch, kiểm tra kết quả qua việc mô phỏng trên máy tính trước khi lắp ráp mạch thật. . Sách giúp người học kiểm tra, củng cố các kiến thức lý thuyết cho môn học đồng thời nắm vững và khám phá các đặc tính mới của chương trình PSIM qua các hướng dẫn cụ thể và thực tế (hướng dẫn từng bước) để ứng dụng khai thác các tính năng của PSIM phiên bản 9.01, 9.2, 9.3, 10 - Trình vẽ, mô phỏng mạch điện tử công suất đa năng, tiện dụng và phổ cập nhất hiện nay và ứng dụng chúng vào công việc thực tế của mình một cách hiệu quả nhất.\n"
                + "\n"
                + "Việc dùng PSIM trong mô phỏng với những lý do sau:\n"
                + "\n"
                + "PSIM là giải pháp rất tốt cho việc mô phỏng các mạch điện tử công suất. Với thiết kế giao diện rất trực quan, các thiết lập thông số, tạo và hiệu chỉnh đối tượng rất trực quan và dễ dàng. PSIM rất linh hoạt, giao diện rất thân thiện gần gũi với người sử dụng.\n"
                + "\n"
                + "Đã và được đưa vào giảng dạy tại hầu hết các trường Đại học, Cao đẳng chuyên ngành Điện - Điện Tử.\n"
                + "\n"
                + "Chương trình dễ cài đặt và sử dụng, PSIM không yêu cầu cấu hình máy tính cao và cho phép chạy trên nhiều hệ điều hành khác nhau.\n"
                + "\n"
                + "PSIM cho phép người dùng kiểm tra các kết quả theo bảng biểu cũng như các dạng sóng trên màn hình đồ họa máy tính. Với PSIM, người dùng có thể kiểm tra, tính toán nhiều thông số của mạch điều khiển điện tử công suất hiển thị trên màn hình máy tính mà không cần phải dùng thiết bị thật.\n"
                + "\n"
                + "Tài liệu biên soạn trên phiên bản PSIM chủ yếu là 9.2, nếu máy tính các bạn dùng các phiên bản cũ (6.0) hay mới hơn (10) vẫn có thể thực hành các bài tập trong sách.\n"
                + "\n"
                + "Các bạn nên tham khảo các sách học sử dụng Arduino cũng do tủ sách STK biên soạn để hiểu rõ các lệnh trình bày trong phần phụ lục.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(this.imageRepo.saveAllAndFlush(Image.createBookGallery(new String[] {}))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Kinh Tế Học - Tập 1",
            new BigDecimal("120000"),
            cv.categoryDaiHoc,
            null,
            null,
            sv.supplierNXBTaiChinh,
            new HashSet<>(Arrays.asList(av.authorPaulASamuelson, av.authorWillianDNordhalls)),
            new HashSet<>(),
            srv.seriesKinhTeHoc,
            lv.languageVN,
            pv.publisherTaiChinh,
            2011,
            null,
            932,
            24d,
            16d,
            3.5,
            797,
            EProductLayout.HARDBACK,
            null,
            null,
            Image.createBookThumbnail("image_195509_1_43845.jpg"),
            "Kinh tế học là một khoa học động - luôn thay đổi để phản ánh những xu hướng biến chuyển của những vấn đề kinh tếm của môi trường và nền kinh tế thế giới, cũng như của xã hội nói chung. Khi kinh tế học và thế giới rộng lớn hơn xung quanh ta phát triển thì cuốn sách này cũng vậy. Mỗi một chương của nó đều bám sát những thay đổi của những phân tích kinh tế và chính sách kinh tế.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2020_07_28_10_42_19_1-390x510.jpg",
                      "2020_07_28_10_42_19_2-390x510.jpg",
                      "2020_07_28_10_42_19_3-390x510.jpg",
                      "2020_07_28_10_42_19_4-390x510.jpg",
                      "2020_07_28_10_42_19_5-390x510.jpg",
                      "2020_07_28_10_42_19_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Giáo Trình Luật Quốc Tế",
            new BigDecimal("160000"),
            cv.categoryDaiHoc,
            null,
            null,
            sv.supplierNXBChinhTriQuocGia,
            new HashSet<>(Collections.singletonList(av.authorPGSTSBanhQuocTuan)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherChinhTriQuocGiaSuThat,
            2020,
            null,
            400,
            20.5d,
            14.5d,
            null,
            400,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_195509_1_37874.jpg"),
            "Cuốn sách gồm 11 chương với nội dung chính như sau: Khái quát chung; nguồn của Luật quốc tế; các nguyên tắc cơ bản của Luật quốc tế; quốc gia và dân cư trong Luật quốc tế; Luật quốc tế về lãnh thổ; Luật biển quốc tế; Luật ngoại giao và lãnh sự; Luật điều ước quốc tế; hệ thống Liên hợp quốc; giải quyết tranh chấp trong Luật quốc tế.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(this.imageRepo.saveAllAndFlush(Image.createBookGallery(new String[] {}))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Làm Cha Mẹ Tỉnh Thức",
            new BigDecimal("139000"),
            cv.categoryCamNangLamChaMe,
            null,
            null,
            sv.supplierThaiHa,
            new HashSet<>(Collections.singletonList(av.authorTSShefaliTsabary)),
            new HashSet<>(Collections.singletonList(tv.translatorKhanhThuy)),
            null,
            lv.languageVN,
            pv.publisherLaoDong,
            2020,
            null,
            431,
            24d,
            15.5d,
            null,
            312,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_216377.jpg"),
            "Khi nuôi dạy con cái, ta thường xuyên bắt gặp mình đứng trước cuộc chiến giữa lý trí và con tim, như luôn phải đi thăng bằng trên dây. Một phản ứng không phù hợp có thể làm khô héo tâm hồn con, ngược lại, một lời khen ngợi đúng lúc có thể tạo cảm hứng để con thăng hoa. Trong mỗi khoảnh khắc, ta đều phải lựa chọn xây hay phá, sưởi ấm hay làm nguội lạnh tâm hồn con trẻ.\n"
                + "\n"
                + "Hầu hết phụ huynh chúng ta đều vô tình vấp vào cái bẫy của việc áp đặt mong muốn của mình lên con cái. Hệ quả là thay vì nuôi dưỡng, mối quan hệ cha mẹ - con cái lại thường xuyên bóp chết tâm hồn đứa trẻ. Đây là lý do mấu chốt tại sao hiện nay rất nhiều trẻ em lớn lên trong hoang mang và trong nhiều trường hợp để lại hậu quả bệnh lý.\n"
                + "\n"
                + "Điều quan trọng nhất bạn phải nhận ra khi nuôi dạy con là không phải mình đang nuôi dưỡng một \"bản sao thu nhỏ\", mà là một linh hồn sống động riêng biệt. Con cái không phải là vật sở hữu của ta theo bất cứ cách nào. Khi hiểu sâu sắc điều này, ta biết điều chỉnh cách nuôi dưỡng con theo nhu cầu của con, chứ không nhào nặn con cho vừa với nhu cầu của ta.\n"
                + "\n"
                + "Làm cha mẹ tỉnh thức được viết ra với mong muốn độc giả, những người ngày ngày vật lộn với thiên chức làm cha mẹ - đặc biệt là những bậc phụ huynh đang có con ở tuổi vị thành niên, tìm thấy phao cứu sinh của mình.\n"
                + "\n"
                + "Và không bao giờ là muộn ngay cả đối với những phụ huynh đang gặp khó khăn trong việc gần gũi với con ở bất kỳ lứa tuổi nào. Tất nhiên, nếu bạn có con nhỏ hơn thì việc thiết lập nền tảng vững chắc càng sớm càng mang lại nhiều lợi ích.\n"
                + "\n"
                + "- Cuốn sách được ĐỨC ĐẠT LAI LẠT MA viết lời giới thiệu\n"
                + "\n"
                + "Làm cha mẹ tỉnh thức cũng là sách New York Times Besteller 2019\n"
                + "\n"
                + "4,7*/5 với 1,607 lượt đánh giá trên trang Amazon.com\n"
                + "\n"
                + "4,2*/5 với 3,748 lượt đánh giá trên trang Goodreads.com\n"
                + "\n"
                + " \n"
                + "\n"
                + "Mục lục:\n"
                + "\n"
                + "Lời khen tặng\n"
                + "\n"
                + "Lời tựa\n"
                + "\n"
                + "Đôi điều gửi các bậc phụ huynh\n"
                + "\n"
                + "Chương 1: Một con người đích thực, như chính ta\n"
                + "\n"
                + "Chương 2: Mục đích tâm linh khi ta sinh con\n"
                + "\n"
                + "Chương 3: Giải phóng con khỏi sự phán xét của ta\n"
                + "\n"
                + "Chương 4: Thổi bay cái tôi\n"
                + "\n"
                + "Chương 5: Có phải con đang lớn lên\n"
                + "\n"
                + "Chương 6: Cuộc đời khôn ngoan\n"
                + "\n"
                + "Chương 7: Trẻ sơ sinh và “khủng hoảng tuổi lên hai” – thử thách lớn của cuộc đời\n"
                + "\n"
                + "Chương 8: Chuyển từ vai chính sang vai phụ\n"
                + "\n"
                + "Chương 9: Sự điên rồ của việc làm cha mẹ\n"
                + "\n"
                + "Chương 10: Dạy con bằng sự trọn vẹn thay vì những vết thương\n"
                + "\n"
                + "Chương 11: Tổ ấm hiện tại\n"
                + "\n"
                + "Chương 12: Sự kì diệu của những điều bình dị\n"
                + "\n"
                + "Chương 13: Gạt bỏ những kì vọng lớn lao\n"
                + "\n"
                + "Chương 14: Tạo không gian tỉnh thức trong cuộc sống của con\n"
                + "\n"
                + "Chương 15: Luôn hiện hữu để kết nối với con\n"
                + "\n"
                + "Chương 16: Đối mặt với lỗi sai của con\n"
                + "\n"
                + "Chương 17: Đôi cánh đại bàng\n"
                + "\n"
                + "Lời kết: Thấu hiểu sự vô minh\n"
                + "\n"
                + "Phụ lục: Định hướng tỉnh thức\n"
                + "\n"
                + "Thông tin tác giả:\n"
                + "\n"
                + "Tiến sĩ Shefali Tsabary nhận bằng tiến sĩ chuyên ngành Tâm lý lâm sàng, Đại học Columbia, New York. Bà được tiếp cận với quan điểm phương Đông từ rất sớm và lồng ghép quan điểm đó vào tâm lý học phương Tây. Sự lồng ghép giao thoa giữa phương Đông và phương Tây giúp bà tiếp cận với độc giả toàn cầu. Khả năng thu hút độc giả thiên về tâm lý và độc giả thiên về ý thức đã giúp bà trở thành chuyên gia hàng đầu trong lĩnh vực nuôi dạy con.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2020_12_21_16_58_58_1-390x510.jpg",
                      "2020_12_21_16_58_58_2-390x510.jpg",
                      "2020_12_21_16_58_58_3-390x510.jpg",
                      "2020_12_21_16_58_58_4-390x510.jpg",
                      "2020_12_21_16_58_58_5-390x510.jpg",
                      "2020_12_21_16_58_58_6-390x510.jpg",
                      "2020_12_21_16_58_58_7-390x510.jpg",
                      "2020_12_21_16_58_58_8-390x510.jpg",
                      "2020_12_21_16_58_58_9-390x510.jpg",
                      "2020_12_21_16_58_58_10-390x510.jpg",
                      "2020_12_21_16_58_58_11-390x510.jpg",
                      "2020_12_21_16_58_58_12-390x510.jpg",
                      "2020_12_21_16_58_58_13-390x510.jpg",
                      "2020_12_21_16_58_58_14-390x510.jpg",
                      "2020_12_21_16_58_58_15-390x510.jpg",
                      "2020_12_21_16_58_58_16-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Bí Ẩn Của Não Phải",
            new BigDecimal("98000"),
            cv.categoryPhatTrienKyNangTriTueChoTre,
            null,
            null,
            sv.supplierFIRSTNEWS,
            new HashSet<>(Collections.singletonList(av.authorGSMakotoShichida)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherTongHopTPHCM,
            2019,
            2019,
            250,
            20.5d,
            13d,
            0.5d,
            223,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_195509_1_3973.jpg"),
            "Mỗi Đứa Trẻ Là Một Thiên Tài! Những Thành Tựu Của Phương Pháp Tiếp Cận Mới Nhất Trong Giáo Dục\n"
                + "\n"
                + "Đối với trẻ nhỏ việc giúp bé phát triển khả năng tư duy sáng tạo là điều rất quan trọng, vì vậy cha mẹ cần quan tâm giúp trẻ rèn luyện kích thích hoạt động của não phải để bồi dưỡng cho trẻ khả năng tư duy sáng tạo. Bởi lẽ việc phát triển não phải cho trẻ không phải chỉ phát triển về cảm xúc, về sự sáng tạo mà những kết quả đó sẽ giúp ích rất nhiều cho sự phát triển toàn não bộ. Khi được giáo dục đúng cách, não phải sẽ phát triển và tạo sự liên kết với não trái một cách tốt nhất.\n"
                + "\n"
                + "Để giúp các bậc cha mẹ dễ dàng hình dung về dạng năng lực và khả năng sáng tạo của các bé nay Viện Giáo dục Trẻ em Shichida Nhật Bản sẽ gửi đến các bậc phụ huynh những kiến thức về khả năng tư duy của con bạn cũng như những kỹ năng mà bé có thể tự học hỏi, sáng tạo, và phát triển trong chính bộ não tí hon của mình. Điều quan trọng nhất trong giáo dục không phải nhồi nhét kiến thức mà là khơi gợi tài năng thiên bẩm mà mỗi bé có ngay từ khi sinh ra. Để đạt được điều này, ba mẹ cần tạo môi trường tốt nhất cho sự phát triển của bé giàu tính tương tác và giáo dục.\n"
                + "\n"
                + "Bằng cách kết hợp sức mạnh của não trái, Phương pháp Shichida hướng đến mục tiêu khơi dậy khả năng của trẻ một cách cân bằng được gọi là “giáo dục não toàn diện”. Những khả năng như “cảm hứng” và “sáng tạo” được điều khiển bởi những chức năng của não phải. Não phải tư duy bằng hình ảnh và có khả năng nhớ những gì chúng ta thấy chỉ bằng một cái nhìn. Điều này minh họa cho trí tưởng tượng phong phú. Não trái tư duy bằng ngôn ngữ, chi phối việc phân tích và tư duy lôgic của chúng ta. Thật đáng tiếc, chúng ta thường chỉ sử dụng não trái, còn não phải thường ở trong trạng thái ngủ. Những bé được dạy đúng cách để sử dụng não phải từ nhỏ sẽ có thể duy trì các khả năng này thậm chí sau khi bé lớn lên. Có một thực tế là mọi đứa trẻ đều được sinh ra cùng vô vàn tài năng tiềm ẩn. Song những tiềm năng đó sẽ không thể nảy mầm nếu không được tác động đúng lúc và đúng cách. Không ai khác, chính cha mẹ là người khơi dậy những tố chất thiên tài của con mình. Không đơn giản là một cuốn sách nuôi dạy con cái thông thường, mà còn có rất nhiều chủ đề thú vị, bí ẩn về năng lực tiềm ẩn của não phải.\n"
                + "\n"
                + "Qua đó cuốn sách cũng kết hợp nhiều câu chuyện thực tế mà các bé đã được giáo dục qua phương pháp Shichida “Bí Ẩn Của Não Phải” đem đến một triết lí giáo dục lành mạnh, nhân văn mà vẫn rất kỉ luật, nghiêm khắc. Ngoài phần nội dung ra cuốn sách còn có những hình ảnh cụ thể, sinh động, dễ hiểu cùng với những câu chuyện thực tế mà giáo viên tại học viện Shichida đã kể lại rằng: “Ông nội của T mắc bệnh ung thư. Cậu bé nghe được điều này khi bác sĩ giải thích tình trạng bệnh của ông với mẹ. T có khả năng chữa lành cho người khác thông qua việc tưởng tượng mình biến thành một người tí hon và đi vào trong cơ thể của họ để chữa lành những bộ phận bị đau. Mỗi lần đến thăm ông trong bệnh viện, T đều tưởng tượng mình đang đi vào cơ thể của ông và tiêu diệt hết vi khuẩn. Các tế bào ung thư nhỏ lại dần dần. Em T đã làm như vậy không chỉ cho ông nội mà còn cho các bệnh nhân khác trong cùng phòng và thậm chí một số bệnh nhân bị ốm nặng, thật ngạc nhiên khi tất cả những người đó đã đủ khỏe để trở về nhà”.\n"
                + "\n"
                + "Cuốn sách đem đến triết lí giáo dục lành mạnh, nhân văn, coi trọng và tôn vinh mọi đứa trẻ. Nó cũng thức tỉnh cha mẹ Việt tận dụng những năm tháng đầu đời - khoảng thời gian duy nhất chúng ta có thể nắm tay trẻ - để quan tâm hơn đến việc nuôi dạy và giúp con có nền tảng phát triển tốt nhất. Không phải việc giáo dục bằng não phải theo phương pháp Shichida mang lại cho bạn một đứa trẻ thiên tài, mà luôn luôn có một thiên tài trong mỗi đứa trẻ nếu chúng ta biết cách kích thích não phải đúng cách.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2020_05_26_09_44_29_1-390x510.jpg",
                      "2020_05_26_09_44_29_2-390x510.jpg",
                      "2020_05_26_09_44_29_3-390x510.jpg",
                      "2020_05_26_09_44_29_4-390x510.jpg",
                      "2020_05_26_09_44_29_5-390x510.jpg",
                      "2020_05_26_09_44_29_6-390x510.jpg",
                      "2020_05_26_09_44_29_7-390x510.jpg",
                      "2020_05_26_09_44_29_8-390x510.jpg",
                      "2020_05_26_09_44_29_9-390x510.jpg",
                      "2020_05_26_09_44_29_10-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Cha Mẹ Độc Hại - Toxic Parents",
            new BigDecimal("148000"),
            cv.categoryCamNangLamChaMe,
            null,
            null,
            sv.supplierSkyBooks,
            new HashSet<>(Arrays.asList(av.authorSusanForwardPhD, av.authorCraigBuck)),
            new HashSet<>(Collections.singletonList(tv.translatorNguyenThiThanhHang)),
            null,
            lv.languageVN,
            pv.publisherTheGioi,
            2022,
            2022,
            350,
            24d,
            15.7d,
            null,
            344,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("bia-chamedochai.jpg"),
            "Mọi người thường cho rằng cha mẹ là bến đỗ an toàn nhất của con trẻ, mọi chuyện cha mẹ làm đều là vì yêu thương con. Tuy nhiên có một số bậc cha mẹ có những hành vi tiêu cực và gây tổn thương cho con cái vì họ chưa từng đặt mình vào vị trí của con. Đó là những bậc cha mẹ độc hại: toxic parents.\n"
                + "\n"
                + "Và đây cũng là tựa đề cuốn sách được Skymommy chuyển ngữ (Toxic Parents – Cha mẹ độc hại), nhận được sự quan tâm của nhiều cha mẹ, tiếp tục được tái bản, với mong muốn đem lại sự an ủi, chữa lành nỗi đau cho những đứa con trưởng thành từ gia đình độc hại; vượt qua di chứng tổn thương và giành lại cuộc đời.\n"
                + "\n"
                + "Những vấn đề chính được đề cập trong cuốn sách này:\n"
                + "\n"
                + "Phần 1: Nhận diện cha mẹ độc hại\n"
                + "\n"
                + "Tác giả chỉ ra 6 kiểu cha mẹ độc hại thông qua các trường hợp cụ thể, phân tích gốc rễ vấn đề thông qua từng trường hợp, nó làm người đọc dễ dàng nhận ra mình có thuộc trường hợp đấy không, nó sẽ gây ra hậu quả gì, nhận định của mình về vấn đề đó như thế nào và hướng giải quyết ra sao. Bao gồm: Cha mẹ chưa trọn vẹn, cha mẹ kiểm soát, cha mẹ nghiện rượu, cha mẹ bạo hành lời nói, cha mẹ bạo hành thể xác, cha mẹ lạm dụng tình dục.\n"
                + "\n"
                + "Phần 2: Hướng dẫn cách giải phóng bản thân khỏi những tổn thương từ cha mẹ độc hại  bằng sự thông thái và tử tế, chẳng hạn như cách kiểm soát cơn giận đối với cha mẹ độc hại, tác hại của sự tha thứ quá sớm, và cách giải quyết những vấn đề khó nhằn như nghiện rượu và loạn luân.\n"
                + "\n"
                + "Không bao giờ là quá trễ để thay đổi. Cho dù bạn cảm thấy tồi tệ như thế nào trong quá khứ hay bạn có những lối cư xử không lành mạnh ra sao trong hiện tại, bạn vẫn có thể phục hồi từ quá khứ bị cha mẹ bạo hành và có một cuộc sống hạnh phúc, ý nghĩa.\n"
                + "\n"
                + "Thông điệp gửi tới bạn từ cuốn sách này:\n"
                + "\n"
                + "Thứ nhất, cuốn sách lên tiếng cho những đứa con từng bị ngược đãi bởi cha mẹ mình, và cung cấp cho nạn nhân những con đường để lấy lại sự tin tưởng và tính tự chủ. Sự chia sẻ trong cuốn sách như là lời tâm sự, lời động viên và luôn nhắc nhở người đọc rằng, dù thế nào bạn sẽ không cô đơn, luôn có người sẵn sàng lắng nghe bạn, cùng bạn vượt qua những giai đoạn tâm lí khó khăn nhất trong cuộc đời.\n"
                + "\n"
                + "Thứ hai, có lẽ quan trọng nhất là, lời nhắn nhủ đến những người đã làm cha mẹ, nếu bạn là cha mẹ độc hại, mong bạn nhìn nhận lại bản thân, không phải là để trách móc mà là sửa chữa. Bạn sẽ không lặp lại những hành vi độc hại lên con cái bạn và vô tình hủy hoại cuộc đời của chúng.");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "bia-chamedochai.png",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Lần Đầu Làm Mẹ",
            new BigDecimal("260000"),
            cv.categoryDanhChoMeBau,
            null,
            null,
            sv.supplierThaiHa,
            new HashSet<>(Collections.singletonList(av.authorMasatoTakeuchi)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherLaoDong,
            2021,
            null,
            599,
            23d,
            18d,
            2d,
            340,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_235121.jpg"),
            "<div id=\"desc_content\" class=\"std\">\n"
                + "\t\t    <p style=\"text-align: justify;\"><strong>Lần Đầu Làm Mẹ</strong></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Những người phụ nữ lần đầu mang thai và sắp sửa được làm mẹ, bạn có cảm thấy hoang mang với thời đại này vì quá nhiều thông tin kiến thức liên quan đến thai nhi và kiến thức sinh nở. Cách tốt nhất tôi muốn khuyên bạn rằng: “Hãy thu thập thông tin từ nguồn chính thống và lắng nghe tiếng nói của bản thân mình.”</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">“Lần đầu làm mẹ” của bác sĩ sản phụ khoa Masato Takeuchi sẽ giúp bạn thực sự yên tâm hơn với những mối lo lắng của bạn. Tập hợp một cách tỉ mỉ, chi tiết tất cả các vấn đề mà bắt buộc bạn phải biết và trải qua khi lần đầu làm mẹ. Cuốn sách này sẽ giải đáp cho bạn giải quyết được:</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Những điều lo lắng của những người làm mẹ.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Những điều cần biết, nên làm và không nên làm dành cho những người lần đầu làm mẹ.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Những dấu hiệu mà người mang thai sẽ phải trải qua</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Có một điều mà các bà mẹ lưu ý đó là vấn đề về quy luật sinh nở tự nhiên. Cách sống và môi trường sống hiện tại là điều làm bạn không thể so sánh với những ông bà ta khi xưa, thế nên bạn phải hiểu đúng về luật sinh nở tự nhiên để mọi thứ sẽ được diễn ra tốt đẹp nhất.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Cuốn sách mặc dù dài 344 trang, bao gồm 8 phần tuy nhiên kiến thức sẽ được tổng hợp thành từng mục và sẽ chia thành 3 phần chính:</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Giới thiệu cụ thể về sự thay đổi của cơ thể khi mang thai và sự phát triển của thai nhi. Trong phần này sẽ có:</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Lời dặn dò từ tổng biên tập, bác sĩ Masato Takeuchi: sẽ giúp các mẹ bầu cảm thấy ấm áp, có thêm dũng khí và sức mạnh.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Giải thích về những thay đổi cơ thể của&nbsp; thai phụ trong suốt 10 tháng thai kỳ: bằng hình ảnh minh họa và câu chữ súc tích, ngắn gọn. Cũng có những lưu ý về các vấn đề mà thai phụ thường gặp nữa.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Danh sách tiện lợi: Tập hợp những việc cần làm trong từng giai đoạn. Các danh sách này nằm ở nhiều trang nên có thể dùng như sổ tay ghi nhớ.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Sự phát triển của thai nhi trong 10 tháng thai kỳ: được minh họa bằng hình vẽ với kích cỡ thật. Áp hình vẽ này lên bụng, các mẹ bầu sẽ dễ dàng tưởng tượng em bé đã lớn đến chừng nào rồi.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Giới thiệu các bước cơ bản trong việc vệ sinh và chăm sóc trẻ sơ sinh: cùng với những vật dụng cần thiết đúng cách, giúp ích cho các cặp đôi lần đầu làm cha mẹ. Giải thích cẩn thận và&nbsp; rõ ràng các loại bệnh: cũng như các vấn đề thai phụ hay gặp phải trong khi mang thai, sinh nở kèm theo các cách dự phòng và trị liệu.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Bạn có nghĩ rằng việc mang thai một đứa con là một cơ hội tuyệt vời để xem xét lại bản thân mình hay không và đó là một quá trình giúp người mẹ trưởng thành hơn? Vì từ khi có con phải thay đổi mọi thứ từ cách ăn uống, nếp sinh hoạt thường ngày, cách làm việc, quản lý thời gian,…. Và cũng không nên quá lo lắng... ... các sản phụ nên tin tưởng vào sức sống của thai nhi, vào sức mạnh&nbsp; làm mẹ của chính mình&nbsp; để trải qua thời&nbsp; kỳ mang thai&nbsp; và sinh nở&nbsp; một cách hạnh&nbsp; phúc nhé!</p>\t\t    <div class=\"clear\"></div>\n"
                + "\t\t</div>");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2021_06_04_14_33_33_1-390x510.jpg",
                      "2021_06_04_14_33_33_2-390x510.jpg",
                      "2021_06_04_14_33_33_3-390x510.jpg",
                      "2021_06_04_14_33_33_4-390x510.jpg",
                      "2021_06_04_14_33_33_5-390x510.jpg",
                      "2021_06_04_14_33_33_6-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Đọc Vị Mọi Vấn Đề Của Trẻ",
            new BigDecimal("199000"),
            cv.categoryCamNangLamChaMe,
            null,
            null,
            sv.supplierThaiHa,
            new HashSet<>(Arrays.asList(av.authorTracyHogg, av.authorMelindaBlau)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherLaoDong,
            2021,
            2021,
            600,
            23.5d,
            21d,
            null,
            509,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_233887.jpg"),
            "<div id=\"desc_content\" class=\"std\">\n"
                + "\t\t    <p style=\"text-align: justify;\">Thưa các ông bố, bà mẹ, các em bé sơ sinh và các em bé chập chững biết đi yêu quý nhất, chính niềm vui và sự khiêm tốn đã đem lại cho tôi điều bí mật quan trọng nhất trong việc thì thầm với trẻ: cách giải quyết bất cứ vấn đề nào. Tôi vẫn luôn tự hào về khả năng giúp đỡ cha mẹ hiểu và chăm sóc đứa con mình, và cảm thấy vinh hạnh mỗi khi có một gia đình nhờ tôi giúp đỡ. Đó là một trải nghiệm rất sâu sắc và rất đáng tưởng thưởng. Đồng thời, việc là một tác giả cũng giúp tôi được công chúng biết đến. Sau khi xuất bản hai cuốn sách đầu tiên của mình năm 2001 và 2002, tôi đã có một loạt những cuộc phiêu lưu và những điều bất ngờ vượt xa bất cứ điều gì mà tôi có thể tưởng tượng được khi còn là một cô bé ở Yorkshire. Ngoài dịch vụ tư vấn cá nhân thông thường, tôi còn tham gia các chương trình phát thanh và truyền hình. Tôi đi khắp nơi trong nước và trên thế giới, được gặp gỡ những ông bố bà mẹ và những em bé tuyệt vời nhất – những người đã sẵn sàng mở cửa và mở lòng đón tôi. Tôi cũng đã nói chuyện với hàng nghìn người qua trang web của tôi, đọc và trả lời thư điện tử của họ, đồng thời tham gia các diễn đàn.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Ngoài sự nổi tiếng mới có đó, tôi vẫn là tôi, vẫn miệt mài với con đường tôi đã chọn. Tuy nhiên, tôi cũng có thay đổi một chút: Tôi không còn chỉ là người thì thầm với trẻ, mà đã trở thành quý bà vạn năng rồi. Và điều đó là nhờ tất cả các bạn.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Trong những chuyến đi, trên trang web và thư điện tử, tôi đã nhận được rất nhiều lá thư cảm ơn và khẳng định từ những ông bố, bà mẹ đã làm theo lời khuyên của tôi. Nhưng tôi cũng bị ngập trong những yêu cầu nhờ giúp đỡ từ những người mua cuốn sách đầu tiên của tôi quá trễ. Có lẽ bạn đang cố gắng ép con bạn vào một nếp sinh hoạt đã được sắp sẵn, tôi đoán vậy, nhưng bạn không chắc là có thể áp dụng cùng một nguyên tắc cho trẻ 8 tháng tuổi và trẻ mới sinh hay không. Có thể bạn bối rối vì không hiểu tại sao con bạn lại không làm những việc mà các em bé khác vẫn làm. Hoặc có thể bạn đang phải đối mặt với vấn đề khó ngủ, khó ăn hay vấn đề về hành vi – hoặc tệ hơn nữa là tất cả các vấn đề trên. Dù vấn đề đó là gì thì điệp khúc đau khổ của bạn vẫn luôn là: “Tracy, tôi phải bắt đầu từ đâu – tôi phải làm gì trước?” Bạn cũng có thể thắc mắc tại sao một số chiến lược tôi gợi ý cho bạn lại có vẻ không có tác dụng với con của bạn.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Tôi đã nghiên cứu những câu hỏi này nhiều năm, và đã tư vấn cho một số trường hợp khó khăn nhất mà tôi từng gặp: cặp song sinh 3 tháng tuổi hay bị trớ tới mức gần như không ăn được bữa nào và không bao giờ ngủ quá 20 phút, dù là ngày hay đêm; bé gái 19 tháng tuổi không ăn đồ ăn cứng vì gần như cứ một tiếng lại tỉnh dậy để ti mẹ một lần; bé 9 tháng tuổi sợ xa cách tới mức người mẹ gần như không thể đặt con xuống được; bé 2 tuổi thường xuyên nổi cáu tới mức cha mẹ bé rất sợ phải đi đâu vắng nhà. Chính nhờ giải quyết những vấn đề như vậy mà tôi đã trở thành Quý Bà Vạn Năng và tại sao tôi lại biết cần phải giúp bạn thêm ngoài những chiến lược cơ bản mà tôi đã trình bày trong những cuốn sách trước đây của mình.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Trong cuốn sách này, tôi muốn giúp bạn, xoa dịu nỗi sợ của bạn và chỉ cho bạn cách tự tạo cho mình sức mạnh của một người làm cha mẹ. Tôi muốn dạy cho bạn những gì tôi đã học được từ công việc cả đời thì thầm với trẻ cũng như trả lời những câu hỏi mà bạn đặt ra cho tôi. Tôi muốn dạy bạn cách nghĩ giống như tôi. Tất nhiên, dù tôi có cố gắng liệt kê tất cả những vấn đề mà bạn có thể gặp phải thì mỗi một em bé và mỗi một gia đình lại có một chút khác biệt. Vì thế, khi các ông bố bà mẹ tìm đến tôi với một vấn đề cụ thể nào đó, để đánh giá xem chuyện gì đang xảy ra trong ngôi nhà và với đứa con sơ sinh hoặc đứa con mới chập chững biết đi của họ, tôi luôn hỏi ít nhất một, nếu không thì phải một loạt những câu hỏi về cả đứa trẻ và việc mà cha mẹ đã làm để ứng phó với tình huống đó. Sau đó, tôi mới có thể nghĩ ra kế hoạch hành động phù hợp. Mục tiêu của tôi là giúp bạn hiểu được quá trình tư duy và hình thành thói quen tự đặt câu hỏi. Như vậy, bạn sẽ không chỉ là người thì thầm với trẻ, mà còn trở thành một người giải quyết vấn đề xuất sắc – một Quý bà hoặc Quý ông vạn năng. Khi đọc tiếp, tôi muốn bạn nhớ điều quan trọng này:</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Vấn đề không là gì khác ngoài một rắc rối cần phải giải quyết hoặc một tình huống đòi hỏi giải pháp sáng tạo. Hãy đặt ra đúng câu hỏi và bạn sẽ tìm ra câu trả lời chính xác.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>Mục lục:</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Lời giới thiệu</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>TỪ THÌ THẦM VỚI TRẺ TỚI GIẢI QUYẾT VẤN ĐỀ</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Bí mật quan trọng nhất của tôi</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>MỘT: E.A.S.Y KHÔNG CÓ NGHĨA LÀ DỄ DÀNG (NHƯNG LẠI CÓ TÁC DỤNG)</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Buộc con quen với nếp sinh hoạt được sắp xếp</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>HAI: NGAY CẢ TRẺ SƠ SINH CŨNG CÓ CẢM XÚC</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Phán đoán tâm trạng/ cảm xúc trong năm đầu</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>BA: CHẾ ĐỘ UỐNG SỮA CỦA CON BẠN</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Các vấn đề ăn uống trong 6 tháng đầu</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>BỐN: THỨC ĂN KHÔNG CHỈ LÀ NUÔI DƯỠNG</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Ăn dặm và ăn uống vui vẻ mãi về sau</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>NĂM: DẠY CON CÁCH NGỦ</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Ba tháng đầu tiên và sáu biến thể rắc rối</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>SÁU: BẾ LÊN/ ĐẶT XUỐNG</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Công cụ huấn luyện ngủ – bốn tháng tới một tuổi</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>BẢY: “CHÚNG TÔI VẪN CHƯA NGỦ ĐỦ”</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Các vấn đề liên quan đến ngủ sau một tuổi</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>TÁM: THUẦN HÓA TRẺ CHẬP CHỮNG BIẾT ĐI</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Dạy trẻ phát triển HỢP (F.I.T) cảm xúc</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>CHÍN: E.E.A.S.Y CÓ TÁC DỤNG</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Trường hợp huấn luyện đi vệ sinh sớm</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\"><em>MƯỜI: NGAY KHI BẠN NGHĨ BẠN ĐÃ LÀM ĐƯỢC… MỌI THỨ LẠI THAY ĐỔI!</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Mười hai câu hỏi thiết yếu và mười hai nguyên tắc giải quyết vấn đề</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">&nbsp;<em>Giới thiệu tác giả</em></p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Tracy Hogg và Melinda Blau</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Tracy Hogg&nbsp;đã dành ra 25 năm trong sự nghiệp làm cha mẹ để tìm hiểu và giao tiếp với trẻ sơ sinh và trẻ nhỏ. Triết lý hợp lý và giàu tính nhân văn của Hogg, một y tá, một chuyên gia ngành sữa mẹ, một chuyên viên tư vấn về trẻ sơ sinh, đã được dịch ra hơn 20 ngôn ngữ, nổi tiếng trên khắp thế giới.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Melinda Blau&nbsp;là nhà báo đã được vinh danh với 15 cuốn sách và hơn 100 bài báo trên tạp chí. Kể từ năm 2000, khi bắt đầu hợp tác với Tracy Hogg quá cố, Blau đã trở thành “tiếng nói” của loạt sách bán chạy nhất “Baby Whisperer” – Đọc vị mọi vấn đề của trẻ.</p>\t\t    <div class=\"clear\"></div>\n"
                + "\t\t</div>");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2022_09_15_16_49_54_1-390x510.jpg",
                      "2022_09_15_16_49_54_2-390x510.jpg",
                      "2022_09_15_16_49_54_3-390x510.jpg",
                      "2022_09_15_16_49_54_4-390x510.jpg",
                      "2022_09_15_16_49_54_5-390x510.jpg",
                      "2022_09_15_16_49_54_6-390x510.jpg",
                      "2022_09_15_16_49_54_7-390x510.jpg",
                      "2022_09_15_16_49_54_8-390x510.jpg",
                      "2022_09_15_16_49_54_9-390x510.jpg",
                      "2022_09_15_16_49_54_10-390x510.jpg",
                      "2022_09_15_16_49_54_11-390x510.jpg",
                      "2022_09_15_16_49_54_12-390x510.jpg",
                      "2022_09_15_16_49_54_13-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "Chào Con! Ba Mẹ Đã Sẵn Sàng",
            new BigDecimal("95000"),
            cv.categoryDinhDuongSucKhoe,
            null,
            null,
            sv.supplierNXBTre,
            new HashSet<>(Collections.singletonList(av.authorBSTranThiHuyenThao)),
            new HashSet<>(),
            null,
            lv.languageVN,
            pv.publisherTre,
            2020,
            2020,
            250,
            15.5d,
            23d,
            null,
            224,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_195509_1_33297.jpg"),
            "<div id=\"desc_content\" class=\"std\">\n"
                + "\t\t    <p style=\"text-align: justify;\">\"Chào&nbsp;con!&nbsp;Ba&nbsp;mẹ đã sẵn sàng!\"&nbsp;-&nbsp;Cuốn sách không chỉ được viết trên nền tảng khoa học của một bác sĩ mà còn chứa chan tình yêu của một người mẹ.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Chắc chắn bạn cũng sẽ cảm nhận được những điểm hay và thực tế trong quyển sách này, từ nền tảng khoa học vững chắc cho đến kiến thức chăm sóc trẻ mới nhất, và nhất là phù hợp với hoàn cảnh của Việt Nam.</p>\t\t    <div class=\"clear\"></div>\n"
                + "\t\t</div>");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(
            this.imageRepo.saveAllAndFlush(
                Image.createBookGallery(
                    new String[] {
                      "2020_06_18_11_00_18_1-390x510.jpg",
                      "2020_06_18_11_00_18_2-390x510.jpg",
                      "2020_06_18_11_00_18_3-390x510.jpg",
                      "2020_06_18_11_00_18_4-390x510.jpg",
                      "2020_06_18_11_00_18_5-390x510.jpg",
                      "2020_06_18_11_00_18_6-390x510.jpg",
                      "2020_06_18_11_00_18_7-390x510.jpg",
                      "2020_06_18_11_00_18_8-390x510.jpg",
                      "2020_06_18_11_00_18_9-390x510.jpg",
                      "2020_06_18_11_00_18_10-390x510.jpg",
                    }))));
    products[i] = this.productRepo.save(products[i]);

    i++;
    products[i] =
        new Product(
            "90% Trẻ Thông Minh Nhờ Cách Trò Chuyện Đúng Đắn Của Cha Mẹ",
            new BigDecimal("39000"),
            cv.categoryCamNangLamChaMe,
            null,
            null,
            sv.supplierNXBKimDong,
            new HashSet<>(Collections.singletonList(av.authorUrakoKanamori)),
            new HashSet<>(Collections.singletonList(tv.translatorPhamLeDaHuong)),
            null,
            lv.languageVN,
            pv.publisherKimDong,
            2019,
            2019,
            200,
            13d,
            19d,
            null,
            176,
            EProductLayout.PAPERBACK,
            null,
            null,
            Image.createBookThumbnail("image_182756.jpg"),
            "<div id=\"desc_content\" class=\"std\">\n"
                + "\t\t    <p style=\"text-align: justify;\">Bạn có bao giờ thốt ra những câu dù biết là không nên nói như&nbsp; “Còn lề mề đến bao giờ nữa hả?” hay “Chẳng được cái trò trống gì, đưa đây xem nào!”… nhưng vẫn lỡ lời không?</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Trong quá trình trẻ trưởng thành, những lời lẽ kiểu “Mày chẳng được cái tích sự gì!” trẻ phải nghe ngày ngày sẽ thẩm thấu qua vô thức, rồi sau đó trở thành ý thức coi mình chỉ là loại “vô dụng”. Không biết từ lúc nào, trẻ sẽ bắt đầu thực hiện những hành vi, lối sống không tốt.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Trong cuốn sách này, chúng tôi sẽ giới thiệu tới quý vị phụ huynh những câu nói “có phép lạ” khiến các con trở thành những đứa trẻ “tự có ý thức” mà cha mẹ không cần cằn nhằn nhiều. Hơn nữa, đây hoàn toàn là những câu chúng ta có thể áp dụng ngay từ ngày hôm nay như “Mẹ luôn đứng về phía con!”, “Mẹ con mình cùng làm nhé!”…</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Về bản chất, mỗi đứa trẻ đều mang trong mình một “sức mạnh” tuyệt vời. Nhưng trước hết, chúng ta phải tin tưởng vào sức mạnh ấy đã! Khi được tin cậy, “sức mạnh” bên trong trẻ sẽ được nuôi dưỡng một cách tự nhiên.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Cuốn sách này sẽ giới thiệu cách trò chuyện giúp khai phá sức mạnh ấy từ nhiều góc độ. Chắc chắn không chỉ các con mà ngay cả chính các bậc phụ huynh cũng sẽ thay đổi. Cuộc sống sẽ lại một lần nữa trở nên thật tuyệt vời.</p>\n"
                + "\n"
                + "<p style=\"text-align: justify;\">Cuốn sách này sẽ giúp mở rộng tiềm năng của trẻ tới vô hạn!</p>\t\t    <div class=\"clear\"></div>\n"
                + "\t\t</div>");
    products[i] = this.productRepo.save(products[i]);
    products[i].setImageGallery(
        new HashSet<>(this.imageRepo.saveAllAndFlush(Image.createBookGallery(new String[] {}))));
    products[i] = this.productRepo.save(products[i]);

    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);
    //
    //    i++;
    //    books[i] =
    //        new Book(
    //            "",
    //            new BigDecimal("000"),
    //            cv.category,
    //            null,
    //            null,
    //            sv.supplier,
    //            new HashSet<>(
    //                Collections.singletonList(av.author)),
    //            new HashSet<>(),
    //            null,
    //            lv.languageVN,
    //            pv.publisher,
    //            20,
    //            null,
    //            0,
    //            d,
    //            d,
    //            null,
    //            0,
    //            EBookLayout.PAPERBACK,
    //            null,
    //            null,
    //            Image.createBookThumbnail(""),
    //            "");
    //    books[i] = this.bookRepo.save(books[i]);
    //    books[i].setImageGallery(
    //        new HashSet<>(
    //            this.imageRepo.saveAllAndFlush(
    //                Image.createBookGallery(
    //                    new String[] {
    //                        "",
    //                        "",
    //                    }))));
    //    books[i] = this.bookRepo.save(books[i]);

    return i;
  }

  static class SupplierVariable {
    public SupplierVariable() {}

    public Supplier supplierMinhLong;
    public Supplier supplierDongA;
    public Supplier supplierNhaNam;
    public Supplier supplierSkyBooks;
    public Supplier supplierNXBTre;
    public Supplier supplierFIRSTNEWS;
    public Supplier supplierThaiHa;
    public Supplier supplierTanViet;
    public Supplier supplierAlphaBooks;
    public Supplier supplierBaoTienPhong;
    public Supplier supplier1980Books;
    public Supplier supplierBaoSinhVienVNHocTro;
    public Supplier supplierAZVietNam;
    public Supplier supplierNXBTheGioi;
    public Supplier supplierCengage;
    public Supplier supplierNXBTaiChinh;
    public Supplier supplierNXBChinhTriQuocGia;
    public Supplier supplierNXBThanhNien;
    public Supplier supplierNXBKimDong;
    //    public Supplier supplier;
  }

  public void createSupplier(SupplierVariable sv) {
    sv.supplierMinhLong = new Supplier("Minh Long");
    sv.supplierMinhLong = this.supplierRepo.save(sv.supplierMinhLong);

    sv.supplierDongA = new Supplier("Đông A");
    sv.supplierDongA = this.supplierRepo.save(sv.supplierDongA);

    sv.supplierNhaNam = new Supplier("Nhã Nam");
    sv.supplierNhaNam = this.supplierRepo.save(sv.supplierNhaNam);

    sv.supplierSkyBooks = new Supplier("Skybooks");
    sv.supplierSkyBooks = this.supplierRepo.save(sv.supplierSkyBooks);

    sv.supplierNXBTre = new Supplier("NXB Trẻ");
    sv.supplierNXBTre = this.supplierRepo.save(sv.supplierNXBTre);

    sv.supplierFIRSTNEWS = new Supplier("FIRST NEWS");
    sv.supplierFIRSTNEWS = this.supplierRepo.save(sv.supplierFIRSTNEWS);

    sv.supplierThaiHa = new Supplier("Thái Hà");
    sv.supplierThaiHa = this.supplierRepo.save(sv.supplierThaiHa);

    sv.supplierTanViet = new Supplier("Tân Việt");
    sv.supplierTanViet = this.supplierRepo.save(sv.supplierTanViet);

    sv.supplierAlphaBooks = new Supplier("Alpha Books");
    sv.supplierAlphaBooks = this.supplierRepo.save(sv.supplierAlphaBooks);

    sv.supplierBaoTienPhong = new Supplier("Báo Tiền Phong");
    sv.supplierBaoTienPhong = this.supplierRepo.save(sv.supplierBaoTienPhong);

    sv.supplier1980Books = new Supplier("1980 Books");
    sv.supplier1980Books = this.supplierRepo.save(sv.supplier1980Books);

    sv.supplierBaoSinhVienVNHocTro = new Supplier("Báo Sinh Viên VN - Hoa Học Trò");
    sv.supplierBaoSinhVienVNHocTro = this.supplierRepo.save(sv.supplierBaoSinhVienVNHocTro);

    sv.supplierAZVietNam = new Supplier("AZ Việt Nam");
    sv.supplierAZVietNam = this.supplierRepo.save(sv.supplierAZVietNam);

    sv.supplierNXBTheGioi = new Supplier("NXB Thế Giới");
    sv.supplierNXBTheGioi = this.supplierRepo.save(sv.supplierNXBTheGioi);

    sv.supplierCengage = new Supplier("Cengage");
    sv.supplierCengage = this.supplierRepo.save(sv.supplierCengage);

    sv.supplierNXBTaiChinh = new Supplier("NXB Tài Chính");
    sv.supplierNXBTaiChinh = this.supplierRepo.save(sv.supplierNXBTaiChinh);

    sv.supplierNXBChinhTriQuocGia = new Supplier("NXB Chính Trị Quốc Gia");
    sv.supplierNXBChinhTriQuocGia = this.supplierRepo.save(sv.supplierNXBChinhTriQuocGia);

    sv.supplierNXBThanhNien = new Supplier("NXB Thanh Niên");
    sv.supplierNXBThanhNien = this.supplierRepo.save(sv.supplierNXBThanhNien);

    sv.supplierNXBKimDong = new Supplier("Nhà Xuất Bản Kim Đồng");
    sv.supplierNXBKimDong = this.supplierRepo.save(sv.supplierNXBKimDong);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);
    //
    //    sv.supplier = new Supplier("");
    //    sv.supplier = this.supplierRepo.save(sv.supplier);

  }

  static class PublisherVariable {
    public PublisherVariable() {}

    public Publisher publisherPhuNu;
    public Publisher publisherDanTri;
    public Publisher publisherHoiNhaVan;
    public Publisher publisherPhuNuVietNam;
    public Publisher publisherTre;
    public Publisher publisherTongHopTPHCM;
    public Publisher publisherLaoDong;
    public Publisher publisherThongTan;
    public Publisher publisherLaoDongXaHoi;
    public Publisher publisherBaoSinhVienVietNamHoaHocTro;
    public Publisher publisherThanhNien;
    public Publisher publisherChinhTriQuocGiaSuThat;
    public Publisher publisherTheGioi;
    public Publisher publisherVanHoc;
    public Publisher publisherHongDuc;
    public Publisher publisherTaiChinh;
    public Publisher publisherKinhTeTPHCM;
    public Publisher publisherKimDong;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
    //    public Publisher publisher;
  }

  public void createPublisher(PublisherVariable pv) {
    pv.publisherPhuNu = new Publisher("NXB Phụ Nữ");
    pv.publisherPhuNu = this.publisherRepo.save(pv.publisherPhuNu);

    pv.publisherDanTri = new Publisher("NXB Dân Trí");
    pv.publisherDanTri = this.publisherRepo.save(pv.publisherDanTri);

    pv.publisherHoiNhaVan = new Publisher("NXB Hội Nhà Văn");
    pv.publisherHoiNhaVan = this.publisherRepo.save(pv.publisherHoiNhaVan);

    pv.publisherPhuNuVietNam = new Publisher("NXB Phụ Nữ Việt Nam");
    pv.publisherPhuNuVietNam = this.publisherRepo.save(pv.publisherPhuNuVietNam);

    pv.publisherTre = new Publisher("NXB Trẻ");
    pv.publisherTre = this.publisherRepo.save(pv.publisherTre);

    pv.publisherTongHopTPHCM = new Publisher("NXB Tổng Hợp TPHCM");
    pv.publisherTongHopTPHCM = this.publisherRepo.save(pv.publisherTongHopTPHCM);

    pv.publisherLaoDong = new Publisher("NXB Lao Động");
    pv.publisherLaoDong = this.publisherRepo.save(pv.publisherLaoDong);

    pv.publisherThongTan = new Publisher("NXB Thông Tấn");
    pv.publisherThongTan = this.publisherRepo.save(pv.publisherThongTan);

    pv.publisherLaoDongXaHoi = new Publisher("NXB Lao Động - Xã Hội");
    pv.publisherLaoDongXaHoi = this.publisherRepo.save(pv.publisherLaoDongXaHoi);

    pv.publisherBaoSinhVienVietNamHoaHocTro = new Publisher("Báo Sinh Viên VN - Hoa Học Trò");
    pv.publisherBaoSinhVienVietNamHoaHocTro =
        this.publisherRepo.save(pv.publisherBaoSinhVienVietNamHoaHocTro);

    pv.publisherThanhNien = new Publisher("NXB Thanh Niên");
    pv.publisherThanhNien = this.publisherRepo.save(pv.publisherThanhNien);

    pv.publisherChinhTriQuocGiaSuThat = new Publisher("NXB Chính Trị Quốc Gia Sự Thật");
    pv.publisherChinhTriQuocGiaSuThat = this.publisherRepo.save(pv.publisherChinhTriQuocGiaSuThat);

    pv.publisherTheGioi = new Publisher("NXB Thế Giới");
    pv.publisherTheGioi = this.publisherRepo.save(pv.publisherTheGioi);

    pv.publisherVanHoc = new Publisher("NXB Văn Học");
    pv.publisherVanHoc = this.publisherRepo.save(pv.publisherVanHoc);

    pv.publisherHongDuc = new Publisher("NXB Hồng Đức");
    pv.publisherHongDuc = this.publisherRepo.save(pv.publisherHongDuc);

    pv.publisherTaiChinh = new Publisher("NXB Tài Chính");
    pv.publisherTaiChinh = this.publisherRepo.save(pv.publisherTaiChinh);

    pv.publisherKinhTeTPHCM = new Publisher("NXB Kinh tế TP.Hồ Chí Minh");
    pv.publisherKinhTeTPHCM = this.publisherRepo.save(pv.publisherKinhTeTPHCM);

    pv.publisherKimDong = new Publisher("NXB Kim Đồng");
    pv.publisherKimDong = this.publisherRepo.save(pv.publisherKimDong);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
    //
    //    pv.publisher = new Publisher("");
    //    pv.publisher = this.publisherRepo.save(pv.publisher);
  }

  static class AuthorVariable {
    public AuthorVariable() {}

    public Author authorHoaDuong;
    public Author authorMarioPuzo;
    public Author authorPauloCoelho;
    public Author authorKhotudien;
    public Author authorJoseMauro;
    public Author authorHaeMin;
    public Author authorNguyenNhatAnh;
    public Author authorHarukiMurakami;
    public Author authorNguyenPhong;
    public Author authorNapoleonHill;
    public Author authorJohnPerkins;
    public Author authorDavidMRubenstei;
    public Author authorDavidBrown;
    public Author authorKassandraBentley;
    public Author authorRobertBCialdini;
    public Author authorDaveLakhani;
    public Author authorJackDSchwager;
    public Author authorBROgroup;
    public Author authorPaulKalanithi;
    public Author authorPhamHuyHoang;
    public Author authorPhilKnight;
    public Author authorClydePrestowitz;
    public Author authorLeThamDuong;
    public Author authorMinhNiem;
    public Author authorDiepHongVu;
    public Author authorAlbertRutherford;
    public Author authorJosephMurphyc;
    public Author authorDaleCarnegie;
    public Author authorDanielKahneman;
    public Author authorRhondaByrne;
    public Author authorBachTo;
    public Author authorCengage;
    public Author authorTSQuachThanhHai;
    public Author authorThSLeNguyenHongPhong;
    public Author authorKSPhamQuangHuy;
    public Author authorPaulASamuelson;
    public Author authorWillianDNordhalls;
    public Author authorPGSTSBanhQuocTuan;
    public Author authorTSDinhBaHungAnh;
    public Author authorThsLeHuuHoang;
    public Author authorUrakoKanamori;
    public Author authorBSTranThiHuyenThao;
    public Author authorTracyHogg;
    public Author authorMelindaBlau;
    public Author authorMasatoTakeuchi;
    public Author authorSusanForwardPhD;
    public Author authorCraigBuck;
    public Author authorGSMakotoShichida;
    public Author authorTSShefaliTsabary;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
    //    public Author author;
  }

  public void createAuthor(AuthorVariable av) {
    av.authorHoaDuong = new Author("Hoa Dương");
    av.authorHoaDuong = this.authorRepo.save(av.authorHoaDuong);

    av.authorMarioPuzo = new Author("Mario Puzo");
    av.authorMarioPuzo = this.authorRepo.save(av.authorMarioPuzo);

    av.authorPauloCoelho = new Author("Paulo Coelho");
    av.authorPauloCoelho = this.authorRepo.save(av.authorPauloCoelho);

    av.authorKhotudien = new Author("Khotudien");
    av.authorKhotudien = this.authorRepo.save(av.authorKhotudien);

    av.authorJoseMauro = new Author("José Mauro de Vasconcelos");
    av.authorJoseMauro = this.authorRepo.save(av.authorJoseMauro);

    av.authorHaeMin = new Author("Hae Min");
    av.authorHaeMin = this.authorRepo.save(av.authorHaeMin);

    av.authorNguyenNhatAnh = new Author("Nguyễn Nhật Ánh");
    av.authorNguyenNhatAnh = this.authorRepo.save(av.authorNguyenNhatAnh);

    av.authorHarukiMurakami = new Author("Haruki Murakami");
    av.authorHarukiMurakami = this.authorRepo.save(av.authorHarukiMurakami);

    av.authorNguyenPhong = new Author("Nguyên Phong");
    av.authorNguyenPhong = this.authorRepo.save(av.authorNguyenPhong);

    av.authorNapoleonHill = new Author("Napoleon Hill");
    av.authorNapoleonHill = this.authorRepo.save(av.authorNapoleonHill);

    av.authorJohnPerkins = new Author("John Perkins");
    av.authorJohnPerkins = this.authorRepo.save(av.authorJohnPerkins);

    av.authorDavidMRubenstei = new Author("David M Rubenstei");
    av.authorDavidMRubenstei = this.authorRepo.save(av.authorDavidMRubenstei);

    av.authorDavidBrown = new Author("David Brown");
    av.authorDavidBrown = this.authorRepo.save(av.authorDavidBrown);

    av.authorKassandraBentley = new Author("Kassandra Bentley");
    av.authorKassandraBentley = this.authorRepo.save(av.authorKassandraBentley);

    av.authorRobertBCialdini = new Author("Robert B Cialdini");
    av.authorRobertBCialdini = this.authorRepo.save(av.authorRobertBCialdini);

    av.authorDaveLakhani = new Author("Dave Lakhani");
    av.authorDaveLakhani = this.authorRepo.save(av.authorDaveLakhani);

    av.authorJackDSchwager = new Author("Jack D Schwager");
    av.authorJackDSchwager = this.authorRepo.save(av.authorJackDSchwager);

    av.authorBROgroup = new Author("B R O group");
    av.authorBROgroup = this.authorRepo.save(av.authorBROgroup);

    av.authorPaulKalanithi = new Author("Paul Kalanithi");
    av.authorPaulKalanithi = this.authorRepo.save(av.authorPaulKalanithi);

    av.authorPhamHuyHoang = new Author("Phạm Huy Hoàng");
    av.authorPhamHuyHoang = this.authorRepo.save(av.authorPhamHuyHoang);

    av.authorPhilKnight = new Author("Phil Knight");
    av.authorPhilKnight = this.authorRepo.save(av.authorPhilKnight);

    av.authorClydePrestowitz = new Author("Clyde Prestowitz");
    av.authorClydePrestowitz = this.authorRepo.save(av.authorClydePrestowitz);

    av.authorLeThamDuong = new Author("Lê Thẩm Dương");
    av.authorLeThamDuong = this.authorRepo.save(av.authorLeThamDuong);

    av.authorMinhNiem = new Author("Minh Niệm");
    av.authorMinhNiem = this.authorRepo.save(av.authorMinhNiem);

    av.authorDiepHongVu = new Author("Diệp Hồng Vũ");
    av.authorDiepHongVu = this.authorRepo.save(av.authorDiepHongVu);

    av.authorAlbertRutherford = new Author("Albert Rutherford");
    av.authorAlbertRutherford = this.authorRepo.save(av.authorAlbertRutherford);

    av.authorJosephMurphyc = new Author("Joseph Murphyc");
    av.authorJosephMurphyc = this.authorRepo.save(av.authorJosephMurphyc);

    av.authorDaleCarnegie = new Author("Dale Carnegie");
    av.authorDaleCarnegie = this.authorRepo.save(av.authorDaleCarnegie);

    av.authorDanielKahneman = new Author("Daniel Kahneman");
    av.authorDanielKahneman = this.authorRepo.save(av.authorDanielKahneman);

    av.authorRhondaByrne = new Author("Rhonda Byrne");
    av.authorRhondaByrne = this.authorRepo.save(av.authorRhondaByrne);

    av.authorBachTo = new Author("Bạch Tô");
    av.authorBachTo = this.authorRepo.save(av.authorBachTo);

    av.authorCengage = new Author("Cengage");
    av.authorCengage = this.authorRepo.save(av.authorCengage);

    av.authorTSQuachThanhHai = new Author("TS Quách Thanh Hải");
    av.authorTSQuachThanhHai = this.authorRepo.save(av.authorTSQuachThanhHai);

    av.authorThSLeNguyenHongPhong = new Author("ThS Lê Nguyễn Hồng Phong");
    av.authorThSLeNguyenHongPhong = this.authorRepo.save(av.authorThSLeNguyenHongPhong);

    av.authorKSPhamQuangHuy = new Author("KS Phạm Quang Huy");
    av.authorKSPhamQuangHuy = this.authorRepo.save(av.authorKSPhamQuangHuy);

    av.authorPaulASamuelson = new Author("Paul A Samuelson");
    av.authorPaulASamuelson = this.authorRepo.save(av.authorPaulASamuelson);

    av.authorWillianDNordhalls = new Author("Willian D Nordhalls");
    av.authorWillianDNordhalls = this.authorRepo.save(av.authorWillianDNordhalls);

    av.authorPGSTSBanhQuocTuan = new Author("PGS TS Bành Quốc Tuấn");
    av.authorPGSTSBanhQuocTuan = this.authorRepo.save(av.authorPGSTSBanhQuocTuan);

    av.authorTSDinhBaHungAnh = new Author("TS Đinh Bá Hùng Anh");
    av.authorTSDinhBaHungAnh = this.authorRepo.save(av.authorTSDinhBaHungAnh);

    av.authorThsLeHuuHoang = new Author("ThS Lê Hữu Hoàng");
    av.authorThsLeHuuHoang = this.authorRepo.save(av.authorThsLeHuuHoang);

    av.authorUrakoKanamori = new Author("Urako Kanamori");
    av.authorUrakoKanamori = this.authorRepo.save(av.authorUrakoKanamori);

    av.authorBSTranThiHuyenThao = new Author("BS Trần Thị Huyên Thảo");
    av.authorBSTranThiHuyenThao = this.authorRepo.save(av.authorBSTranThiHuyenThao);

    av.authorTracyHogg = new Author("Tracy Hogg");
    av.authorTracyHogg = this.authorRepo.save(av.authorTracyHogg);

    av.authorMelindaBlau = new Author("Melinda Blau");
    av.authorMelindaBlau = this.authorRepo.save(av.authorMelindaBlau);

    av.authorMasatoTakeuchi = new Author("Masato Takeuchi");
    av.authorMasatoTakeuchi = this.authorRepo.save(av.authorMasatoTakeuchi);

    av.authorSusanForwardPhD = new Author("Susan Forward PhD");
    av.authorSusanForwardPhD = this.authorRepo.save(av.authorSusanForwardPhD);

    av.authorCraigBuck = new Author("Craig Buck");
    av.authorCraigBuck = this.authorRepo.save(av.authorCraigBuck);

    av.authorGSMakotoShichida = new Author("GS Makoto Shichida");
    av.authorGSMakotoShichida = this.authorRepo.save(av.authorGSMakotoShichida);

    av.authorTSShefaliTsabary = new Author("Tiến sĩ Shefali Tsabary");
    av.authorTSShefaliTsabary = this.authorRepo.save(av.authorTSShefaliTsabary);
    //
    //    av.author = new Author("");
    //    av.author = this.authorRepo.save(av.author);
    //
    //    av.author = new Author("");
    //    av.author = this.authorRepo.save(av.author);
    //
    //    av.author = new Author("");
    //    av.author = this.authorRepo.save(av.author);
    //
    //    av.author = new Author("");
    //    av.author = this.authorRepo.save(av.author);
    //
    //    av.author = new Author("");
    //    av.author = this.authorRepo.save(av.author);
    //
    //    av.author = new Author("");
    //    av.author = this.authorRepo.save(av.author);
    //
    //    av.author = new Author("");
    //    av.author = this.authorRepo.save(av.author);
  }

  static class TranslatorVariable {
    private TranslatorVariable() {}

    public Translator translatorLeChuCau;
    public Translator translatorNguyenBichLan;
    public Translator translatorToYenLy;
    public Translator translatorThuyHang;
    public Translator translatorAnhThu;
    public Translator translatorTranLe;
    public Translator translatorDoAiNhi;
    public Translator translatorNguyenNgocAnh;
    public Translator translatorBuiThanhChau;
    public Translator translatorMaiSon;
    public Translator translatorNguyenVanPhuoc;
    public Translator translatorHuongLan;
    public Translator translatorXuanThanh;
    public Translator translatorNguyenPhucQuangNgoc;
    public Translator translatorCaoBichThuy;
    public Translator translatorKhanhThuy;
    public Translator translatorNguyenHuongMai;
    public Translator translatorNguyenThiThanhHang;
    public Translator translatorPhamLeDaHuong;
    //    public Translator translator;
  }

  public void createTranslator(TranslatorVariable tv) {
    tv.translatorLeChuCau = new Translator("Lê Chu Cầu", EGender.MALE);
    tv.translatorLeChuCau = this.translatorRepo.save(tv.translatorLeChuCau);

    tv.translatorNguyenBichLan = new Translator("Nguyễn Bích Lan", EGender.FEMALE);
    tv.translatorNguyenBichLan = this.translatorRepo.save(tv.translatorNguyenBichLan);

    tv.translatorToYenLy = new Translator("Tô Yến Ly", EGender.FEMALE);
    tv.translatorToYenLy = this.translatorRepo.save(tv.translatorToYenLy);

    tv.translatorThuyHang = new Translator("Thúy Hằng", EGender.FEMALE);
    tv.translatorThuyHang = this.translatorRepo.save(tv.translatorThuyHang);

    tv.translatorAnhThu = new Translator("Anh Thư", EGender.FEMALE);
    tv.translatorAnhThu = this.translatorRepo.save(tv.translatorAnhThu);

    tv.translatorTranLe = new Translator("Trần Lê");
    tv.translatorTranLe = this.translatorRepo.save(tv.translatorTranLe);

    tv.translatorDoAiNhi = new Translator("Đỗ Ái Nhi");
    tv.translatorDoAiNhi = this.translatorRepo.save(tv.translatorDoAiNhi);

    tv.translatorNguyenNgocAnh = new Translator("Nguyễn Ngọc Anh");
    tv.translatorNguyenNgocAnh = this.translatorRepo.save(tv.translatorNguyenNgocAnh);

    tv.translatorBuiThanhChau = new Translator("Bùi Thanh Châu");
    tv.translatorBuiThanhChau = this.translatorRepo.save(tv.translatorBuiThanhChau);

    tv.translatorMaiSon = new Translator("Mai Sơn");
    tv.translatorMaiSon = this.translatorRepo.save(tv.translatorMaiSon);

    tv.translatorNguyenVanPhuoc = new Translator("Nguyễn Văn Phước");
    tv.translatorNguyenVanPhuoc = this.translatorRepo.save(tv.translatorNguyenVanPhuoc);

    tv.translatorHuongLan = new Translator("Hương Lan");
    tv.translatorHuongLan = this.translatorRepo.save(tv.translatorHuongLan);

    tv.translatorXuanThanh = new Translator("Xuân Thanh");
    tv.translatorXuanThanh = this.translatorRepo.save(tv.translatorXuanThanh);

    tv.translatorNguyenPhucQuangNgoc = new Translator("Nguyễn Phúc Quang Ngọc");
    tv.translatorNguyenPhucQuangNgoc = this.translatorRepo.save(tv.translatorNguyenPhucQuangNgoc);

    tv.translatorCaoBichThuy = new Translator("Cao Bích Thủy");
    tv.translatorCaoBichThuy = this.translatorRepo.save(tv.translatorCaoBichThuy);

    tv.translatorKhanhThuy = new Translator("Khánh Thủy");
    tv.translatorKhanhThuy = this.translatorRepo.save(tv.translatorKhanhThuy);

    tv.translatorNguyenHuongMai = new Translator("Nguyễn Hương Mai");
    tv.translatorNguyenHuongMai = this.translatorRepo.save(tv.translatorNguyenHuongMai);

    tv.translatorNguyenThiThanhHang = new Translator("Nguyễn Thị Thanh Hằng");
    tv.translatorNguyenThiThanhHang = this.translatorRepo.save(tv.translatorNguyenThiThanhHang);

    tv.translatorPhamLeDaHuong = new Translator("Phạm Lê Dạ Hương");
    tv.translatorPhamLeDaHuong = this.translatorRepo.save(tv.translatorPhamLeDaHuong);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
    //
    //    tv.translator = new Translator("");
    //    tv.translator = this.translatorRepo.save(tv.translator);
  }

  static class SeriesVariable {
    private SeriesVariable() {}

    public Series seriesComboSachKyNangTuyetChieuLamGiau;
    public Series seriesComboLoiThuToiVaNgheThuatLanhDao;
    public Series seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang;
    public Series seriesComboSachBacThayThuyetPhuc;

    public Series seriesLopHocMatNgu;
    public Series seriesKinhTeHoc;
    public Series seriesQuanTriChuoiCungUng;
    //    public Series series;
    //    public Series series;
    //    public Series series;
  }

  public void createSeries(
      SeriesVariable srv, AuthorVariable av, SupplierVariable sv, PublisherVariable pv) {
    srv.seriesComboSachKyNangTuyetChieuLamGiau =
        Series.createCombo(
            "Combo Sách Kỹ Năng, Tuyệt Chiêu Làm Giàu: Chiến Thắng Con Quỷ Trong Bạn + Think And Grow Rich - 13 Nguyên Tắc Nghĩ Giàu Làm Giàu (Bộ 2 Cuốn)",
            new HashSet<>(Collections.singletonList(av.authorNapoleonHill)),
            sv.supplierThaiHa,
            pv.publisherLaoDong);
    srv.seriesComboSachKyNangTuyetChieuLamGiau =
        this.seriesRepo.save(srv.seriesComboSachKyNangTuyetChieuLamGiau);

    srv.seriesComboLoiThuToiVaNgheThuatLanhDao =
        Series.createCombo(
            "Combo Sách Lời Thú Tội Mới Của Một Sát Thủ Kinh Tế + Nghệ Thuật Lãnh Đạo (Bộ 2 Cuốn)",
            new HashSet<>(Arrays.asList(av.authorJohnPerkins, av.authorDavidMRubenstei)),
            sv.supplierTanViet,
            null);
    srv.seriesComboLoiThuToiVaNgheThuatLanhDao =
        this.seriesRepo.save(srv.seriesComboLoiThuToiVaNgheThuatLanhDao);

    srv.seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang =
        Series.createCombo(
            "Combo Sách Chiến Lược Đầu Tư Chứng Khoán + Phù Thủy Sàn Chứng Khoán Thế Hệ Mới (Bộ 2 Cuốn)",
            new HashSet<>(Arrays.asList(av.authorDavidBrown, av.authorKassandraBentley)),
            sv.supplierAlphaBooks,
            pv.publisherLaoDong);
    srv.seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang =
        this.seriesRepo.save(srv.seriesComboChienLucDauTuChinhKhoangVaPhuThuySanChinhKhoang);

    srv.seriesComboSachBacThayThuyetPhuc =
        Series.createCombo(
            "Combo Sách Bậc Thầy Thuyết Phục: Những Đòn Tâm Lý Trong Thuyết Phục + Phong Thái Của Bậc Thầy Thuyết Phục (Bộ 2 Cuốn)",
            new HashSet<>(
                Arrays.asList(
                    av.authorRobertBCialdini, av.authorDaveLakhani, av.authorJackDSchwager)),
            sv.supplierAlphaBooks,
            null);
    srv.seriesComboSachBacThayThuyetPhuc =
        this.seriesRepo.save(srv.seriesComboSachBacThayThuyetPhuc);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    //    srv.seriesCombo = Series.createCombo("", new HashSet<>(
    //        Arrays.asList(av.author, av.author)), sv.supplier, pv.publisher);
    //    srv.seriesCombo = this.seriesRepo.save(srv.seriesCombo);
    //
    srv.seriesLopHocMatNgu =
        Series.createSeries(
            "Lớp Học Mật Ngữ",
            new HashSet<>(Collections.singletonList(av.authorBROgroup)),
            pv.publisherBaoSinhVienVietNamHoaHocTro);
    srv.seriesLopHocMatNgu = this.seriesRepo.save(srv.seriesLopHocMatNgu);

    srv.seriesKinhTeHoc =
        Series.createSeries(
            "Kinh Tế Học",
            new HashSet<>(Arrays.asList(av.authorPaulASamuelson, av.authorWillianDNordhalls)),
            pv.publisherTaiChinh);
    srv.seriesKinhTeHoc = this.seriesRepo.save(srv.seriesKinhTeHoc);

    srv.seriesQuanTriChuoiCungUng =
        Series.createSeries(
            "Quản Trị Chuỗi Cung Ứng",
            new HashSet<>(Arrays.asList(av.authorTSDinhBaHungAnh, av.authorThsLeHuuHoang)),
            pv.publisherKinhTeTPHCM);
    srv.seriesQuanTriChuoiCungUng = this.seriesRepo.save(srv.seriesQuanTriChuoiCungUng);
    //
    //    srv.series = Series.createSeries("", new HashSet<>(
    //        Collections.singletonList(av.author)), pv.publisher);
    //    srv.series = this.seriesRepo.save(srv.series);
    //
    //    srv.series = Series.createSeries("", new HashSet<>(
    //        Collections.singletonList(av.author)), pv.publisher);
    //    srv.series = this.seriesRepo.save(srv.series);
    //
    //    srv.series = Series.createSeries("", new HashSet<>(
    //        Collections.singletonList(av.author)), pv.publisher);
    //    srv.series = this.seriesRepo.save(srv.series);
    //
    //    srv.series = Series.createSeries("", new HashSet<>(
    //        Collections.singletonList(av.author)), pv.publisher);
    //    srv.series = this.seriesRepo.save(srv.series);
    //
    //    srv.series = Series.createSeries("", new HashSet<>(
    //        Collections.singletonList(av.author)), pv.publisher);
    //    srv.series = this.seriesRepo.save(srv.series);
    //
    //    srv.series = Series.createSeries("", new HashSet<>(
    //        Collections.singletonList(av.author)), pv.publisher);
    //    srv.series = this.seriesRepo.save(srv.series);
    //
    //    srv.series = Series.createSeries("", new HashSet<>(
    //        Collections.singletonList(av.author)), pv.publisher);
    //    srv.series = this.seriesRepo.save(srv.series);
    //
    //    srv.series = Series.createSeries("", new HashSet<>(
    //        Collections.singletonList(av.author)), pv.publisher);
    //    srv.series = this.seriesRepo.save(srv.series);
    //
    //    srv.series = Series.createSeries("", new HashSet<>(
    //        Collections.singletonList(av.author)), pv.publisher);
    //    srv.series = this.seriesRepo.save(srv.series);
    //
    //    srv.series = Series.createSeries("", new HashSet<>(
    //        Collections.singletonList(av.author)), pv.publisher);
    //    srv.series = this.seriesRepo.save(srv.series);

  }

  static class GenreVariable {
    public GenreVariable() {}
  }

  public void createGenre(GenreVariable gv) {}

  static class CategoryVariable {
    public CategoryVariable() {}

    //
    public Category categorySachVanHoc;
    public Category categoryTieuThuyet;
    public Category categoryTruyenNganTanVan;
    public Category categoryLightNovel;
    public Category categoryNgonTinh;

    //
    public Category categorySachThieuNhi;
    public Category categoryManga;
    public Category categoryKienThucBachKhoa;
    public Category categorySachTranhKyNangSong;
    public Category categoryVuaHocVuaChoi;
    public Category categoryTruyenThieuNhi;

    //
    public Category categorySachKinhTe;
    public Category categoryNhanVatBaiHoc;
    public Category categoryQuanTriLanhDao;
    public Category categoryMarketing;
    public Category categoryPhanTichKinhTe;
    public Category categoryChungKhoangBatDongSan;

    //
    public Category categorySachTieuSuHoiKy;
    public Category categoryCauChuyenCuocDoi;
    public Category categoryChinhTri;
    public Category categoryKinhTe;
    public Category categoryNgheThuatGiaiTri;

    //
    public Category categorySachTamLyKyNangSong;
    public Category categoryKyNangSong;
    public Category categoryRenLuyenNhanCach;
    public Category categoryTamLy;

    //
    public Category categorySachChoTuoiMoiLon;
    public Category categorySachGiaoKhoaThamKhao;
    public Category categorySachGiaoKhoa;
    public Category categorySachThamKhao;
    public Category categoryLuyenThiTHPT;
    public Category categoryMauGiao;
    public Category categoryDaiHoc;

    //
    public Category categorySachNuoiDayCon;
    public Category categoryCamNangLamChaMe;
    public Category categoryPhuongPhapGiaoDucTre;
    public Category categoryPhatTrienKyNangTriTueChoTre;
    public Category categoryPhatTrienTriTueChoTre;
    public Category categoryPhatTrienKyNangChoTre;
    public Category categoryDanhChoMeBau;
    public Category categoryDinhDuongSucKhoe;

    //
    public Category categorySachHocNgoaiNgu;
    public Category categoryTiengAnh;
    public Category categoryTiengNhat;
    public Category categoryTiengHoa;
    public Category categoryTiengHan;
  }

  public void createCategory(CategoryVariable cv) {
    cv.categorySachVanHoc = new Category("Sách Văn Học", null);
    cv.categorySachVanHoc = this.categoryRepo.save(cv.categorySachVanHoc);

    cv.categoryTieuThuyet = new Category("Tiểu Thuyết", cv.categorySachVanHoc);
    cv.categoryTieuThuyet = this.categoryRepo.save(cv.categoryTieuThuyet);

    cv.categoryTruyenNganTanVan = new Category("Truyện Ngắn - Tản Văn", cv.categorySachVanHoc);
    cv.categoryTruyenNganTanVan = this.categoryRepo.save(cv.categoryTruyenNganTanVan);

    cv.categoryLightNovel = new Category("Light Novel", cv.categorySachVanHoc);
    cv.categoryLightNovel = this.categoryRepo.save(cv.categoryLightNovel);

    cv.categoryNgonTinh = new Category("Ngôn Tình", cv.categorySachVanHoc);
    cv.categoryNgonTinh = this.categoryRepo.save(cv.categoryNgonTinh);

    //

    cv.categorySachKinhTe = new Category("Sách Kinh Tế", null);
    cv.categorySachKinhTe = this.categoryRepo.save(cv.categorySachKinhTe);

    cv.categoryNhanVatBaiHoc = new Category("Nhân Vật - Bài Học Kinh Doanh", cv.categorySachKinhTe);
    cv.categoryNhanVatBaiHoc = this.categoryRepo.save(cv.categoryNhanVatBaiHoc);

    cv.categoryQuanTriLanhDao = new Category("Quản Trị - Lãnh Đạo", cv.categorySachKinhTe);
    cv.categoryQuanTriLanhDao = this.categoryRepo.save(cv.categoryQuanTriLanhDao);

    cv.categoryMarketing = new Category("Marketing - Bán Hàng", cv.categorySachKinhTe);
    cv.categoryMarketing = this.categoryRepo.save(cv.categoryMarketing);

    cv.categoryPhanTichKinhTe = new Category("Phân Tích Kinh Tế", cv.categorySachKinhTe);
    cv.categoryPhanTichKinhTe = this.categoryRepo.save(cv.categoryPhanTichKinhTe);

    cv.categoryChungKhoangBatDongSan =
        new Category("Chứng Khoán - Bất Động Sản", cv.categorySachKinhTe);
    cv.categoryChungKhoangBatDongSan = this.categoryRepo.save(cv.categoryChungKhoangBatDongSan);

    //

    cv.categorySachTamLyKyNangSong = new Category("Sách Tâm Lý - Kỹ Năng Sống", null);
    cv.categorySachTamLyKyNangSong = this.categoryRepo.save(cv.categorySachTamLyKyNangSong);

    cv.categoryKyNangSong = new Category("Kỹ Năng Sống", cv.categorySachTamLyKyNangSong);
    cv.categoryKyNangSong = this.categoryRepo.save(cv.categoryKyNangSong);

    cv.categoryRenLuyenNhanCach =
        new Category("Rèn Luyện Nhân Cách", cv.categorySachTamLyKyNangSong);
    cv.categoryRenLuyenNhanCach = this.categoryRepo.save(cv.categoryRenLuyenNhanCach);

    cv.categoryTamLy = new Category("Tâm Lý", cv.categorySachTamLyKyNangSong);
    cv.categoryTamLy = this.categoryRepo.save(cv.categoryTamLy);

    cv.categorySachChoTuoiMoiLon =
        new Category("Sách Cho Tuổi Mới Lớn", cv.categorySachTamLyKyNangSong);
    cv.categorySachChoTuoiMoiLon = this.categoryRepo.save(cv.categorySachChoTuoiMoiLon);

    //

    cv.categorySachNuoiDayCon = new Category("Sách Nuôi Dạy Con", null);
    cv.categorySachNuoiDayCon = this.categoryRepo.save(cv.categorySachNuoiDayCon);

    cv.categoryCamNangLamChaMe = new Category("Cẩm Nang Làm Cha Mẹ", cv.categorySachNuoiDayCon);
    cv.categoryCamNangLamChaMe = this.categoryRepo.save(cv.categoryCamNangLamChaMe);

    cv.categoryPhuongPhapGiaoDucTre =
        new Category("Phương Pháp Giáo Dục Trẻ Các Nước", cv.categorySachNuoiDayCon);
    cv.categoryPhuongPhapGiaoDucTre = this.categoryRepo.save(cv.categoryPhuongPhapGiaoDucTre);

    cv.categoryPhatTrienKyNangTriTueChoTre =
        new Category("Phát Triển Kỹ Năng - Trí Tuệ Cho Trẻ", cv.categorySachNuoiDayCon);
    cv.categoryPhatTrienKyNangTriTueChoTre =
        this.categoryRepo.save(cv.categoryPhatTrienKyNangTriTueChoTre);

    cv.categoryPhatTrienTriTueChoTre =
        new Category("Phát Triển Trí Tuệ Cho Trẻ", cv.categorySachNuoiDayCon);
    cv.categoryPhatTrienTriTueChoTre = this.categoryRepo.save(cv.categoryPhatTrienTriTueChoTre);

    cv.categoryPhatTrienKyNangChoTre =
        new Category("Phát Triển Kỹ Năng Cho Trẻ", cv.categorySachNuoiDayCon);
    cv.categoryPhatTrienKyNangChoTre = this.categoryRepo.save(cv.categoryPhatTrienKyNangChoTre);

    cv.categoryDanhChoMeBau = new Category("Dành Cho Mẹ Bầu", cv.categorySachNuoiDayCon);
    cv.categoryDanhChoMeBau = this.categoryRepo.save(cv.categoryDanhChoMeBau);

    cv.categoryDinhDuongSucKhoe = new Category("Dinh Dưỡng Sức Khỏe", cv.categorySachNuoiDayCon);
    cv.categoryDinhDuongSucKhoe = this.categoryRepo.save(cv.categoryDinhDuongSucKhoe);

    //

    cv.categorySachThieuNhi = new Category("Sách Thiếu Nhi", null);
    cv.categorySachThieuNhi = this.categoryRepo.save(cv.categorySachThieuNhi);

    cv.categoryManga = new Category("Manga - Comic", cv.categorySachThieuNhi);
    cv.categoryManga = this.categoryRepo.save(cv.categoryManga);

    cv.categoryKienThucBachKhoa = new Category("Kiến Thức Bách Khoa", cv.categorySachThieuNhi);
    cv.categoryKienThucBachKhoa = this.categoryRepo.save(cv.categoryKienThucBachKhoa);

    cv.categorySachTranhKyNangSong =
        new Category("Sách Tranh Kỹ Năng Sống Cho Trẻ", cv.categorySachThieuNhi);
    cv.categorySachTranhKyNangSong = this.categoryRepo.save(cv.categorySachTranhKyNangSong);

    cv.categoryVuaHocVuaChoi =
        new Category("Vừa Học - Vừa Học Vừa Chơi Với Trẻ", cv.categorySachThieuNhi);
    cv.categoryVuaHocVuaChoi = this.categoryRepo.save(cv.categoryVuaHocVuaChoi);

    cv.categoryTruyenThieuNhi = new Category("Truyện Thiếu Nhi", cv.categorySachThieuNhi);
    cv.categoryTruyenThieuNhi = this.categoryRepo.save(cv.categoryTruyenThieuNhi);

    //

    cv.categorySachTieuSuHoiKy = new Category("Sách Tiểu sử - hồi ký", null);
    cv.categorySachTieuSuHoiKy = this.categoryRepo.save(cv.categorySachTieuSuHoiKy);

    cv.categoryCauChuyenCuocDoi = new Category("Câu Chuyện Cuộc Đời", cv.categorySachTieuSuHoiKy);
    cv.categoryCauChuyenCuocDoi = this.categoryRepo.save(cv.categoryCauChuyenCuocDoi);

    cv.categoryChinhTri = new Category("Chính Trị", cv.categorySachTieuSuHoiKy);
    cv.categoryChinhTri = this.categoryRepo.save(cv.categoryChinhTri);

    cv.categoryKinhTe = new Category("Kinh Tế", cv.categorySachTieuSuHoiKy);
    cv.categoryKinhTe = this.categoryRepo.save(cv.categoryKinhTe);

    cv.categoryNgheThuatGiaiTri = new Category("Nghệ Thuật - Giải Trí", cv.categorySachTieuSuHoiKy);
    cv.categoryNgheThuatGiaiTri = this.categoryRepo.save(cv.categoryNgheThuatGiaiTri);

    //

    cv.categorySachGiaoKhoaThamKhao = new Category("Sách Giáo Khoa - Tham Khảo", null);
    cv.categorySachGiaoKhoaThamKhao = this.categoryRepo.save(cv.categorySachGiaoKhoaThamKhao);

    cv.categorySachGiaoKhoa = new Category("Sách Giáo Khoa", cv.categorySachGiaoKhoaThamKhao);
    cv.categorySachGiaoKhoa = this.categoryRepo.save(cv.categorySachGiaoKhoa);

    cv.categorySachThamKhao = new Category("Sách Tham Khảo", cv.categorySachGiaoKhoaThamKhao);
    cv.categorySachThamKhao = this.categoryRepo.save(cv.categorySachThamKhao);

    cv.categoryLuyenThiTHPT =
        new Category("Luyện Thi THPT Quốc Gia", cv.categorySachGiaoKhoaThamKhao);
    cv.categoryLuyenThiTHPT = this.categoryRepo.save(cv.categoryLuyenThiTHPT);

    cv.categoryMauGiao = new Category("Mẫu Giáo", cv.categorySachGiaoKhoaThamKhao);
    cv.categoryMauGiao = this.categoryRepo.save(cv.categoryMauGiao);

    cv.categoryDaiHoc = new Category("Đại Học", cv.categorySachGiaoKhoaThamKhao);
    cv.categoryDaiHoc = this.categoryRepo.save(cv.categoryDaiHoc);

    //

    cv.categorySachHocNgoaiNgu = new Category("Sách Học Ngoại Ngữ", null);
    cv.categorySachHocNgoaiNgu = this.categoryRepo.save(cv.categorySachHocNgoaiNgu);

    cv.categoryTiengAnh = new Category("Tiếng Anh", cv.categorySachHocNgoaiNgu);
    cv.categoryTiengAnh = this.categoryRepo.save(cv.categoryTiengAnh);

    cv.categoryTiengNhat = new Category("Tiếng Nhật", cv.categorySachHocNgoaiNgu);
    cv.categoryTiengNhat = this.categoryRepo.save(cv.categoryTiengNhat);

    cv.categoryTiengHoa = new Category("Tiếng Hoa", cv.categorySachHocNgoaiNgu);
    cv.categoryTiengHoa = this.categoryRepo.save(cv.categoryTiengHoa);

    cv.categoryTiengHan = new Category("Tiếng Hàn", cv.categorySachHocNgoaiNgu);
    cv.categoryTiengHan = this.categoryRepo.save(cv.categoryTiengHan);
  }

  static class LanguageVariable {
    public LanguageVariable() {}

    public Language languageVN;
    public Language languageEN;
  }

  private void createLanguage(LanguageVariable lv) {
    lv.languageVN = new Language(ELanguage.VN, "vn");
    lv.languageVN = this.languageRepo.save(lv.languageVN);

    lv.languageEN = new Language(ELanguage.EN, "en");
    lv.languageEN = this.languageRepo.save(lv.languageEN);
  }

  private int createCustomer(AuthRequest[] authRequestCustomers, User[] customers) {
    int i = 0;

    authRequestCustomers[i] = new AuthRequest("039500011" + i, "customer" + i + "@gmail.com", Utils.DEFAULT_PASSWORD);
    this.userService.registerUser(authRequestCustomers[i], false);
    customers[i] = this.userRepo.findUserByPhone(authRequestCustomers[i].getPhone()).orElse(null);
    if (customers[i] != null) {
      Location location = new Location("Phường 15", "Quận Bình Thạnh", "Hồ Chí Minh");
      location = this.locationService.saveLocation(location);
      Address address = new Address(customers[i], location, "Hẻm 2/9 Tú Mỡ");
      address = this.addressService.saveAddress(address);
      customers[i] = this.userRepo.findById(customers[i].getId()).orElse(null);
    }

    i++;
    authRequestCustomers[i] = new AuthRequest("039500011" + i, "customer" + i + "@gmail.com", Utils.DEFAULT_PASSWORD);
    this.userService.registerUser(authRequestCustomers[i], false);
    customers[i] = this.userRepo.findUserByPhone(authRequestCustomers[i].getPhone()).orElse(null);
    if (customers[i] != null) {
      Location location = new Location("Phường 02", "Quận Bình Thạnh", "Hồ Chí Minh");
      location = this.locationService.saveLocation(location);
      Address address = new Address(customers[i], location, "Hẻm 108 Lê Tự Tài");
      address = this.addressService.saveAddress(address);
      customers[i] = this.userRepo.findById(customers[i].getId()).orElse(null);
    }

    i++;
    authRequestCustomers[i] = new AuthRequest("039500011" + i, "customer" + i + "@gmail.com", Utils.DEFAULT_PASSWORD);
    this.userService.registerUser(authRequestCustomers[i], false);
    customers[i] = this.userRepo.findUserByPhone(authRequestCustomers[i].getPhone()).orElse(null);
    if (customers[i] != null) {
      Location location = new Location("Phường 01", "Quận Bình Thạnh", "Hồ Chí Minh");
      location = this.locationService.saveLocation(location);
      Address address = new Address(customers[i], location, "Đường Số 1");
      address = this.addressService.saveAddress(address);
      customers[i] = this.userRepo.findById(customers[i].getId()).orElse(null);
    }

    i++;
    authRequestCustomers[i] = new AuthRequest("039500011" + i, "customer" + i + "@gmail.com", Utils.DEFAULT_PASSWORD);
    this.userService.registerUser(authRequestCustomers[i], false);
    customers[i] = this.userRepo.findUserByPhone(authRequestCustomers[i].getPhone()).orElse(null);
    if (customers[i] != null) {
      Location location = new Location("Phường 03", "Quận Bình Thạnh", "Hồ Chí Minh");
      location = this.locationService.saveLocation(location);
      Address address = new Address(customers[i], location, "Hẻm 119/7 Đường Số 7");
      address = this.addressService.saveAddress(address);
      customers[i] = this.userRepo.findById(customers[i].getId()).orElse(null);
    }

    i++;
    authRequestCustomers[i] = new AuthRequest("039500011" + i, "customer" + i + "@gmail.com", Utils.DEFAULT_PASSWORD);
    this.userService.registerUser(authRequestCustomers[i], false);
    customers[i] = this.userRepo.findUserByPhone(authRequestCustomers[i].getPhone()).orElse(null);
    if (customers[i] != null) {
      Location location = new Location("Phường 17", "Quận Bình Thạnh", "Hồ Chí Minh");
      location = this.locationService.saveLocation(location);
      Address address = new Address(customers[i], location, "Hẻm 7");
      address = this.addressService.saveAddress(address);
      customers[i] = this.userRepo.findById(customers[i].getId()).orElse(null);
    }

    i++;
    authRequestCustomers[i] = new AuthRequest("039500011" + i, "customer" + i + "@gmail.com", Utils.DEFAULT_PASSWORD);
    this.userService.registerUser(authRequestCustomers[i], false);
    customers[i] = this.userRepo.findUserByPhone(authRequestCustomers[i].getPhone()).orElse(null);
    if (customers[i] != null) {
      Location location = new Location("Phường 21", "Quận Bình Thạnh", "Hồ Chí Minh");
      location = this.locationService.saveLocation(location);
      Address address = new Address(customers[i], location, "Hẻm 15/13 Nguyễn Du");
      address = this.addressService.saveAddress(address);
      customers[i] = this.userRepo.findById(customers[i].getId()).orElse(null);
    }

    i++;
    authRequestCustomers[i] = new AuthRequest("039500011" + i, "customer" + i + "@gmail.com", Utils.DEFAULT_PASSWORD);
    this.userService.registerUser(authRequestCustomers[i], false);
    customers[i] = this.userRepo.findUserByPhone(authRequestCustomers[i].getPhone()).orElse(null);
    if (customers[i] != null) {
      Location location = new Location("Phường 22", "Quận Bình Thạnh", "Hồ Chí Minh");
      location = this.locationService.saveLocation(location);
      Address address = new Address(customers[i], location, "Hẻm 47 Bùi Đình Túy");
      address = this.addressService.saveAddress(address);
      customers[i] = this.userRepo.findById(customers[i].getId()).orElse(null);
    }

    i++;
    authRequestCustomers[i] = new AuthRequest("039500011" + i, "customer" + i + "@gmail.com", Utils.DEFAULT_PASSWORD);
    this.userService.registerUser(authRequestCustomers[i], false);
    customers[i] = this.userRepo.findUserByPhone(authRequestCustomers[i].getPhone()).orElse(null);
    if (customers[i] != null) {
      Location location = new Location("Phường 19", "Quận Bình Thạnh", "Hồ Chí Minh");
      location = this.locationService.saveLocation(location);
      Address address = new Address(customers[i], location, "Hẻm 456/31 Cao Thắng");
      address = this.addressService.saveAddress(address);
      customers[i] = this.userRepo.findById(customers[i].getId()).orElse(null);
    }

    i++;
    authRequestCustomers[i] = new AuthRequest("039500011" + i, "customer" + i + "@gmail.com", Utils.DEFAULT_PASSWORD);
    this.userService.registerUser(authRequestCustomers[i], false);
    customers[i] = this.userRepo.findUserByPhone(authRequestCustomers[i].getPhone()).orElse(null);
    if (customers[i] != null) {
      Location location = new Location("Phường 28", "Quận Bình Thạnh", "Hồ Chí Minh");
      location = this.locationService.saveLocation(location);
      Address address = new Address(customers[i], location, "Hẻm 6 Nguyễn Trung Trực");
      address = this.addressService.saveAddress(address);
      customers[i] = this.userRepo.findById(customers[i].getId()).orElse(null);
    }

    return i;
  }

  public int createFullAddress(FullAddress[] fullAddresses) {
    Location[] locations = new Location[500];
    int i = 0;
    locations[i] = new Location("Phường Hiệp Phú", "Thành Phố Thủ Đức", "Thành phố Hồ Chí Minh");
    locations[i] = this.locationService.saveLocation(locations[i]);
    fullAddresses[i] =
        new FullAddress("Trường Đại học Sư phạm Kỹ thuật Tp. Hồ Chí Minh", locations[i]);

    i++;
    locations[i] =
        new Location("Phường Phước Long A", "Thành Phố Thủ Đức", "Thành phố Hồ Chí Minh");
    locations[i] = this.locationService.saveLocation(locations[i]);
    fullAddresses[i] = new FullAddress("Trường Tư Thục Ngô Thời Nhiệm", locations[i]);

    i++;
    locations[i] =
        new Location("Phường Phước Long B", "Thành Phố Thủ Đức", "Thành phố Hồ Chí Minh");
    locations[i] = this.locationService.saveLocation(locations[i]);
    fullAddresses[i] = new FullAddress("Trường Cao Đẳng Công Thương TP.HCM", locations[i]);

    i++;
    locations[i] = new Location("Phường Thảo Điền", "Thành Phố Thủ Đức", "Thành phố Hồ Chí Minh");
    locations[i] = this.locationService.saveLocation(locations[i]);
    fullAddresses[i] = new FullAddress("BIS HCMC, Early Years and Infant Campus", locations[i]);

    i++;
    locations[i] = new Location("Phường Phú Hữu", "Thành Phố Thủ Đức", "Thành phố Hồ Chí Minh");
    locations[i] = this.locationService.saveLocation(locations[i]);
    fullAddresses[i] = new FullAddress("949-939 Đ. Nguyễn Duy Trinh\n", locations[i]);

    i++;
    locations[i] = new Location("Phường Phước Bình", "Thành Phố Thủ Đức", "Thành phố Hồ Chí Minh");
    locations[i] = this.locationService.saveLocation(locations[i]);
    fullAddresses[i] = new FullAddress("Trường Cao đẳng Kinh tế Đối ngoại", locations[i]);

    i++;
    locations[i] = new Location("Phường Long Trường", "Thành Phố Thủ Đức", "Thành phố Hồ Chí Minh");
    locations[i] = this.locationService.saveLocation(locations[i]);
    fullAddresses[i] = new FullAddress("6-32 Đường 7", locations[i]);

    i++;
    locations[i] =
        new Location("Phường Tăng Nhơn Phú A", "Thành Phố Thủ Đức", "Thành phố Hồ Chí Minh");
    locations[i] = this.locationService.saveLocation(locations[i]);
    fullAddresses[i] =
        new FullAddress("Trường Đại học Tài chính - Marketing Cơ sở Thủ Đức", locations[i]);

    i++;
    locations[i] =
        new Location("Phường Tăng Nhơn Phú B", "Thành Phố Thủ Đức", "Thành phố Hồ Chí Minh");
    locations[i] = this.locationService.saveLocation(locations[i]);
    fullAddresses[i] = new FullAddress("Nhà Máy SAMSUNG Khu Công Nghệ Cao", locations[i]);

    i++;
    locations[i] =
        new Location("Phường Long Thạnh Mỹ", "Thành Phố Thủ Đức", "Thành phố Hồ Chí Minh");
    locations[i] = this.locationService.saveLocation(locations[i]);
    fullAddresses[i] = new FullAddress("Nghĩa Trang Phúc An Viên", locations[i]);

    return i;
  }

  static class LocationVariable {
    public LocationVariable() {}

    Location locationHCM;
    Location locationHaNoi;
    Location locationCanTho;
    Location locationDaNang;
    Location locationHaiPhong;
    Location locationAnGiang;
    Location locationLongAn;
    Location locationCaMau;
    Location locationVinhLong;
    Location locationBenTre;
    Location locationDongThap;
  }

  public void createLocation(LocationVariable lv) {
    lv.locationHCM = new Location("Hồ Chí Minh");
    lv.locationHCM = this.locationService.saveLocation(lv.locationHCM);

    lv.locationHaNoi = new Location("Hà Nội");
    lv.locationHaNoi = this.locationService.saveLocation(lv.locationHaNoi);

    lv.locationCanTho = new Location("Cần Thơ");
    lv.locationCanTho = this.locationService.saveLocation(lv.locationCanTho);

    lv.locationDaNang = new Location("Đà Nẵng");
    lv.locationDaNang = this.locationService.saveLocation(lv.locationDaNang);

    lv.locationHaiPhong = new Location("Hải Phòng");
    lv.locationHaiPhong = this.locationService.saveLocation(lv.locationHaiPhong);

    lv.locationAnGiang = new Location("An Giang");
    lv.locationAnGiang = this.locationService.saveLocation(lv.locationAnGiang);

    lv.locationLongAn = new Location("Long An");
    lv.locationLongAn = this.locationService.saveLocation(lv.locationLongAn);

    lv.locationCaMau = new Location("Cà Mau");
    lv.locationCaMau = this.locationService.saveLocation(lv.locationCaMau);

    lv.locationVinhLong = new Location("Vĩnh Long");
    lv.locationVinhLong = this.locationService.saveLocation(lv.locationVinhLong);

    lv.locationBenTre = new Location("Bến Tre");
    lv.locationBenTre = this.locationService.saveLocation(lv.locationBenTre);

    lv.locationDongThap = new Location("Đồng Tháp");
    lv.locationDongThap = this.locationService.saveLocation(lv.locationDongThap);
  }

  static class UserVariable {
    public UserVariable() {}

    User admin;
    User admin1;
    User seller;
    User seller1;
    User seller2;
    User[] customers;
    int maxIndexCustomer;
  }

  public void createUser(UserVariable uv, RoleVariable rv, LocationVariable lv) {
    AuthRequest authAdmin1 =
        new AuthRequest("0375189189", "admin@gmail.com", Utils.DEFAULT_PASSWORD);
    this.userService.registerUser(authAdmin1, false);
    uv.admin = this.userRepo.findUserByPhone(authAdmin1.getPhone()).orElse(null);
    if (uv.admin != null) {
      uv.admin.setRole(rv.roleAdmin);
      uv.admin = this.userRepo.save(uv.admin);
      Address addressAdmin = new Address(uv.admin, lv.locationLongAn, "Nguyễn Trung");
      addressAdmin = this.addressService.saveAddress(addressAdmin);
    }

    AuthRequest authAdmin2 =
        new AuthRequest("0976080909", "admin1@gmail.com", Utils.DEFAULT_PASSWORD);
    this.userService.registerUser(authAdmin2, false);
    uv.admin1 = this.userRepo.findUserByPhone(authAdmin2.getPhone()).orElse(null);
    if (uv.admin1 != null) {
      uv.admin1.setRole(rv.roleAdmin);
      uv.admin1 = this.userRepo.save(uv.admin1);
    }

    AuthRequest[] authRequestCustomers = new AuthRequest[500];
    uv.customers = new User[500];

    uv.maxIndexCustomer = createCustomer(authRequestCustomers, uv.customers);
  }

  static class RoleVariable {
    public RoleVariable() {}

    Role roleAdmin;
    Role roleSeller;
    Role roleCustomer;
    Role roleAssistant;
    Role roleEditor;
    Role roleShipper;
    Role roleSalesPerson;
  }

  public void createRole(RoleVariable rv) {
    rv.roleAdmin = new Role(ERole.ROLE_ADMIN, "Admin role is the administrator's role");
    rv.roleAdmin = this.roleService.createRole(rv.roleAdmin);

    rv.roleSeller = new Role(ERole.ROLE_SELLER, "Seller is a person who sells goods to consumers.");
    rv.roleSeller = this.roleService.createRole(rv.roleSeller);

    rv.roleCustomer =
        new Role(
            ERole.ROLE_CUSTOMER,
            "Customers are the people who have the conditions to make purchasing decisions. They are the beneficiaries of the characteristics and quality of the product or service.");
    rv.roleCustomer = this.roleService.createRole(rv.roleCustomer);

    rv.roleAssistant = new Role(ERole.ROLE_ASSISTANT, "Manage questions and reviews");
    rv.roleAssistant = this.roleService.createRole(rv.roleAssistant);

    rv.roleEditor =
        new Role(ERole.ROLE_EDITOR, "Manage categories, bv.brands, products, articles and menus");
    rv.roleEditor = this.roleService.createRole(rv.roleEditor);

    rv.roleShipper =
        new Role(ERole.ROLE_SHIPPER, "View products, view orders and update order status");
    rv.roleShipper = this.roleService.createRole(rv.roleShipper);

    rv.roleSalesPerson =
        new Role(
            ERole.ROLE_SALESPERSON,
            "Manage product price, customers, shipping, orders and sales report");
    rv.roleSalesPerson = this.roleService.createRole(rv.roleSalesPerson);
  }
}
