package ecom.bookstore.wbsbackend.utils;

import ecom.bookstore.wbsbackend.entities.*;
import ecom.bookstore.wbsbackend.exceptions.ResourceNotValidException;
import ecom.bookstore.wbsbackend.models.enums.EPattern;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.text.Normalizer;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author minh phuong
 * @created 07/09/2022 - 11:14 PM
 */
public class Utils {
  private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class.getName());

  //  Regex
  public static final String REGEX_PASSWORD =
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";
  public static final String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
  public static final String REGEX_PHONE =
      "^(0|84?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";

  //  Response
  public static final String OBJECT_EXISTED = "%s is existed";
  public static final String OBJECT_EXISTED_BY_FIELD = "%s is existed with %s = %s";
  public static final String OBJECT_EXISTED_BY_TWO_FIELD = "%s is existed with %s = %s and %s = %s";
  public static final String OBJECT_NOT_FOUND = "Not found %s!";
  public static final String OBJECT_NOT_FOUND_BY_FIELD = "Not found %s by %s = %s";

  public static final String OBJECT_NOT_FOUND_BY_TWO_FIELD = "Not found %s by %s = %s and %s = %s";
  public static final String OBJECT_NOT_CHILD = "%s with %s = %s is not a child of %s with %s = %s";

  public static final String FIELD_NOT_BLANK = "Field %s not blank";
  public static final String FIELD_NOT_VALID = "Field %s not valid";
  public static final String BOTH_FIELDS_NOT_BLANK = "Both field %s or %s not blank";
  public static final String DISCOUNT_INVALID = "Invalid discount code";
  public static final String DISCOUNT_CODE_EXPIRED = "Expired discount code";
  public static final String DISCOUNT_NOT_STARTED = "the discount hasn't started yet";
  public static final String DISCOUNT_USED_UP = "The discount code has been used up";
  public static final String PRODUCT_NOT_ENOUGH = "Product quantity is not enough";
  public static final String USER_NOT_PERMISSION = "User does not have permission to do this";
  public static final String USER_NOT_PRESENT = "User not present";

  public static final String REGISTER_USER_SUCCESSFULLY = "Register User with phone = %s successfully!";
  public static final String CREATE_OBJECT_SUCCESSFULLY = "Create new %s successfully!";
  public static final String CREATE_MAIN_OBJECT_SUCCESSFULLY = "Create new main %s successfully!";
  public static final String CREATE_RELY_OBJECT_SUCCESSFULLY = "Create new rely %s successfully!";
  public static final String UPDATE_OBJECT_SUCCESSFULLY = "Update %s successfully!";
  public static final String UPDATE_MAIN_OBJECT_SUCCESSFULLY = "Update new main %s successfully!";
  public static final String UPDATE_RELY_OBJECT_SUCCESSFULLY = "Update new rely %s successfully!";
  public static final String DELETE_OBJECT_SUCCESSFULLY = "Delete %s successfully!";
  // INFO
  public static final String PAYMENT_ORDER = "%s thanh toan don hang %s voi tong tien %s voi %s";

  //  Log
  public static final String LOG_GET_ALL_OBJECT = "Fetching all %ss";
  public static final String LOG_GET_OBJECT = "Fetching %s with %s = %s";
  public static final String LOG_GET_ALL_OBJECT_BY_FIELD = "Fetching all %ss with %s = %s";
  public static final String LOG_GET_ALL_OBJECT_BY_TWO_FIELD = "Fetching all %ss with %s = %s and %s = %s";
  public static final String LOG_GET_ALL_OBJECT_BY_THREE_FIELD = "Fetching all %ss with %s = %s and %s = %s and %s = %s";
  public static final String AND_FIELD = " and %s = %s";
  public static final String LOG_CREATE_OBJECT = "Creating new %s with %s = %s to the database";
  public static final String LOG_REGISTER_OBJECT = "Registering new %S with %s = %s to database";
  public static final String LOG_CREATE_OBJECT_BY_TWO_FIELD = "Creating new %s with %s = %s and %s = %s to the database";
  public static final String LOG_UPDATE_OBJECT = "Updating %s with %s = %s to the database";
  public static final String LOG_UPDATE_OBJECT_BY_TWO_FIELD = "Updating %s with %s = %s and %s = %s to the database";
  public static final String LOG_DELETE_OBJECT = "Deleting %s with %s = %s from the database";
  public static final String ADD_LOG_FOR_USER = " for User with %s = %s";

  //  Length
  public static final int LENGTH_DISCOUNT_CODE_GENERATE = 10;
  public static final int LENGTH_USERNAME_GENERATE = 20;
  public static final int LENGTH_PASSWORD_GENERATE = 16;
  public static final int DEFAULT_GENERATE_LENGTH = 8;
  //  public static final String DEFAULT_PASSWORD = "Abc@1234567890";
  public static final String DEFAULT_PASSWORD = "12345";

  // Objects Per Page
  public static final String DEFAULT_PAGE = "1";
  public static final String DEFAULT_SIZE = "20";
  public static final String PRODUCT_PER_PAGE = "20";
  public static final String SHOP_PER_PAGE = "20";
  public static final String DISCOUNT_PER_PAGE = "30";
  public static final String IMAGE_PER_PAGE = "50";
  public static final String USERS_PER_PAGE = "20";
  public static final String COMMENT_PER_PAGE = "10";
  public static final String REVIEW_PER_PAGE = "3";

  //
  private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
  private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
  //
  public static final String PRE_API_AUTH = "auth";
  public static final String PRE_API_BRAND = "/api/v1/brands";
  public static final String PRE_API_CATEGORY = "/api/v1/categories";
  public static final String PRE_API_CART = "/api/v1/carts";
  public static final String PRE_API_COMMENT = "/api/v1/comments";
  public static final String PRE_API_FEEDBACK = "/api/v1/feedbacks";
  public static final String PRE_API_IMAGE = "/api/v1/images";
  public static final String PRE_API_PRODUCT = "/api/v1/products";
  //
  public static final String IMAGE_DEFAULT_PATH = "IMAGE_OTHER/l9faer7pevfo5kgs7zztubvgt9ikxy4u.jpg";

  @Value("${server.port}")
  private static Integer port;

  public static String toSlug(String input) {
    String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
    String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
    String slug = NONLATIN.matcher(normalized).replaceAll("");
    return slug.toLowerCase(Locale.ENGLISH);
  }

  @Getter
  @Setter
  public static class TimeDistance {
    public String timeDistance;
    private boolean isUpdated;
  }

  public static TimeDistance getTimeDistance(Date createAt, Date updateAt) {
    TimeDistance timeDistance = new TimeDistance();
    Date lastDate;
    if (updateAt != null && createAt != null && updateAt.after(createAt)) {
      lastDate = updateAt;
      timeDistance.setUpdated(true);
    } else {
      lastDate = createAt;
      timeDistance.setUpdated(false);
    }

    Date currentDate = new Date();

    assert lastDate != null;
    long getDiff = currentDate.getTime() - lastDate.getTime();
    long timeMinute = 1000 * 60;
    long timeHour = 1000 * 60 * 60;
    long timeDay = timeHour * 24;
    long timeWeek = timeDay * 7;
    long timeMonth = timeDay * 30;
    long timeYear = timeDay * 365;
    if (getDiff < timeMinute) {
      timeDistance.setTimeDistance("Vừa mới đây");
    } else if (getDiff < timeHour) {
      timeDistance.setTimeDistance("Khoảng " + getDiff / (timeMinute) + " phút trước");
    } else if (getDiff < timeDay) {
      timeDistance.setTimeDistance("Khoảng " + getDiff / (timeHour) + " giờ trước");
    } else if (getDiff < timeWeek) {
      timeDistance.setTimeDistance("Khoảng " + getDiff / (timeDay) + " ngày trước");
    } else if (getDiff < timeMonth) {
      timeDistance.setTimeDistance("Khoảng " + getDiff / (timeWeek) + " tuần trước");
    } else if (getDiff < timeYear) {
      timeDistance.setTimeDistance("Khoảng " + getDiff / (timeMonth) + " tháng trước");
    } else {
      timeDistance.setTimeDistance("Khoảng " + getDiff / (timeYear) + " năm trước");
    }
    return timeDistance;
  }

  public static Location getLocationFromLocationString(String locationString) {
    Location location = new Location();
    int commaIndex = locationString.lastIndexOf(",");
    if (commaIndex != -1) {
      location.setProvince(locationString.substring(commaIndex + 1).trim());
      locationString = locationString.substring(0, commaIndex);
      commaIndex = locationString.lastIndexOf(",");
      if (commaIndex != -1) {
        location.setDistrict(locationString.substring(commaIndex + 1).trim());
        locationString = locationString.substring(0, commaIndex);
        location.setCommune(locationString);
      } else {
        location.setDistrict(locationString.trim());
      }
    } else {
      location.setProvince(locationString.trim());
    }
    return location;
  }

  public static String getLocationStringFromLocation(Location location) {
    String locationString = "";
    if (location.getProvince() != null) {
      locationString = location.getProvince();
      if (location.getDistrict() != null) {
        locationString = location.getDistrict() + ", " + locationString;
        if (location.getCommune() != null) {
          locationString = location.getCommune() + ", " + locationString;
        }
      }
    }
    return locationString;
  }

  public static String getLocationStringFromLocationAndLine(Location location, String line) {
    String locationString = "";
    if (location.getProvince() != null) {
      locationString = location.getProvince();
      if (location.getDistrict() != null) {
        locationString = location.getDistrict() + ", " + locationString;
        if (location.getCommune() != null) {
          locationString = location.getCommune() + ", " + locationString;
        }
      }
    }
    return line + ", " + locationString;
  }

  public static String getUrlFromPathImage(String path) {
    if (path.startsWith("http")) {
      return path;
    }
//    // Local address
//    String hostAddress = InetAddress.getLocalHost().getHostAddress();
//    String hostName = InetAddress.getLocalHost().getHostName();

    // Remote address
    String remoteAddress = InetAddress.getLoopbackAddress().getHostAddress();
//    String remoteName = InetAddress.getLoopbackAddress().getHostName();
    return "http://" + remoteAddress + ":" + (port == null ? 8080 : port) + PRE_API_IMAGE + "/" + path;
  }

  public static String getFullNameFromLastNameAndFirstName(String lastName, String firstName) {
    String fullName = lastName == null ? "" : lastName;
    if (Objects.equals(fullName, "")) {
      fullName = firstName == null ? "" : firstName;
    } else {
      fullName = (firstName == null ? "" : " " + firstName);
    }
    return fullName;
  }

  public static boolean checkValidDiscount(Discount discount) {
    if (discount.getQuantity() == 0) {
      throw new ResourceNotValidException(String.format(Utils.DISCOUNT_USED_UP));
    }

    return checkValidDate(discount.getStartDate(), discount.getEndDate());
  }

  public static boolean checkValidDate(Date startDate, Date endDate) {
    Date currentDate = new Date();
    if (startDate != null && startDate.after(currentDate)) {
      throw new ResourceNotValidException(String.format(Utils.DISCOUNT_NOT_STARTED));
    } else if (endDate != null && endDate.before(currentDate)) {
      throw new ResourceNotValidException(String.format(Utils.DISCOUNT_CODE_EXPIRED));
    }
    return true;
  }

  public static BigDecimal getPriceProduct(Product product, Sale sale, Integer... quantity) {
    BigDecimal price;
    Double percent = 0d;
    if (sale != null) {
      percent = sale.getPercent();
    }
    Integer count = 1;
    if (quantity.length > 0) {
      count = quantity[0];
    }
    price = product.getListPrice().multiply(BigDecimal.valueOf((1 - percent) * count)).setScale(0, RoundingMode.HALF_UP);

    return price;
  }


  public static BigDecimal getTotalPriceFromOrderItems(Set<OrderItem> orderItemSet) {
    BigDecimal totalPrice = new BigDecimal(0);
    if (orderItemSet != null) {
      for (OrderItem orderItem : orderItemSet) {
          totalPrice = totalPrice.add(orderItem.getTotalPrice().multiply(new BigDecimal(orderItem.getQuantity())));
      }
    }
    return totalPrice;
  }

  public static EPattern isPattern(String key) {
    if (key.matches(Utils.REGEX_PHONE)) {
      return EPattern.PHONE;
    } else if (key.matches(Utils.REGEX_EMAIL)) {
      return EPattern.EMAIl;
    } else if (key.matches(Utils.REGEX_PASSWORD)) {
      return EPattern.PASSWORD;
    } else {
      return EPattern.NONE;
    }
  }

  public static String[] getFirstNameAndLastNameFromFullName(String fullName) {
    fullName = fullName.replaceAll("\\s", " ").trim();
    int firstSpace = fullName.indexOf(" ");
    if (firstSpace == -1) {
      return new String[]{fullName, ""};
    } else {
      String lastName = fullName.substring(0, firstSpace);
      String firstName = fullName.substring(firstSpace+1);
      return new String[]{firstName, lastName};
    }
  }
}
