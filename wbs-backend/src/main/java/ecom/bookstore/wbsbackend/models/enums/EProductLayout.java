package ecom.bookstore.wbsbackend.models.enums;

/**
 * @author minh phuong
 * @created 07/10/2022 - 10:30 PM
 */
public enum EProductLayout {
  PAPERBACK(Names.PAPERBACK),
  HARDBACK(Names.HARDBACK),
  BOX_SET(Names.BOX_SET),
  BOOK_WITH_CD(Names.BOOK_WITH_CD);

  public static class Names {
    public static final String HARDBACK = "Bìa cứng";
    public static final String PAPERBACK = "Bìa mềm";
    public static final String BOX_SET = "Bộ hộp";
    public static final String BOOK_WITH_CD = "Sách Kèm Đĩa";
  }

  private final String label;

  private EProductLayout(String label) {
    this.label = label;
  }

  public String toString() {
    return this.label;
  }
}
