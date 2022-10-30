package ecom.bookstore.wbsbackend.models.enums;

/**
 * @author minh phuong
 * @created 11/09/2022 - 5:26 PM
 */
public enum EBookStatus {
  PRODUCT_UN_TRADING(Names.PRODUCT_UN_TRADING),
  PRODUCT_TRADING(Names.PRODUCT_TRADING),
  PRODUCT_TRADED(Names.PRODUCT_TRADED);

  public static class Names {
    public static final String PRODUCT_UN_TRADING = "PRODUCT_UN_TRADING";
    public static final String PRODUCT_TRADING = "PRODUCT_TRADING";
    public static final String PRODUCT_TRADED = "PRODUCT_TRADED";
  }

  private final String label;

  private EBookStatus(String label) {
    this.label = label;
  }

  public String toString() {
    return this.label;
  }
}
