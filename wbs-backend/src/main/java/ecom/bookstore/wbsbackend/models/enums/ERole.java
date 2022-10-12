package ecom.bookstore.wbsbackend.models.enums;

/**
 * @author minh phuong
 * @created 08/09/2022 - 6:09 PM
 */
public enum ERole {
  //  ROLE_ADMIN,
  //  ROLE_SALESPERSON,
  //  ROLE_EDITOR,
  //  ROLE_ASSISTANT,
  //  ROLE_SHIPPER,
  //  ROLE_SELLER,
  //  ROLE_CUSTOMER;
  ROLE_ADMIN(Names.ADMIN),
  ROLE_SELLER(Names.SELLER),
  ROLE_CUSTOMER(Names.CUSTOMER),
  ROLE_SALESPERSON(Names.SALESPERSON),
  ROLE_EDITOR(Names.EDITOR),
  ROLE_ASSISTANT(Names.ASSISTANT),
  ROLE_SHIPPER(Names.SHIPPER);

  public static class Names {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String SELLER = "ROLE_SELLER";
    public static final String CUSTOMER = "ROLE_CUSTOMER";
    public static final String SALESPERSON = "ROLE_SALESPERSON";
    public static final String EDITOR = "ROLE_EDITOR";
    public static final String ASSISTANT = "ROLE_ASSISTANT";
    public static final String SHIPPER = "ROLE_SHIPPER";
  }

  private final String label;

  private ERole(String label) {
    this.label = label;
  }

  public String toString() {
    return this.label;
  }
}
