package ecom.bookstore.wbsbackend.models.enums;

/**
 * @author minh phuong
 * @created 18/10/2022 - 10:39 PM
 */
public enum ELanguage {
  VN(Names.VN),
  EN(Names.EN);

  public static class Names {
    public static final String VN = "VN";
    public static final String EN = "EN";
  }

  private final String label;

  private ELanguage(String label) {
    this.label = label;
  }

  public String toString() {
    return this.label;
  }
}
