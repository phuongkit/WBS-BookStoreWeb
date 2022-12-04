package ecom.bookstore.wbsbackend.models.enums;

/**
 * @author minh phuong
 * @created 17/09/2022 - 10:11 PM
 * @project gt-backend
 */
public enum EOrderStatus {
  ORDER_PENDING(Names.ORDER_PENDING),
  ORDER_AWAITING_PAYMENT(Names.ORDER_AWAITING_PAYMENT),
  ORDER_COMPLETED(Names.ORDER_COMPLETED),
  ORDER_CANCELLED(Names.ORDER_CANCELLED),
  ORDER_AWAITING_FULFILLMENT(Names.ORDER_AWAITING_FULFILLMENT),
  ORDER_AWAITING_SHIPMENT(Names.ORDER_AWAITING_SHIPMENT),
  ORDER_AWAITING_PICKUP(Names.ORDER_AWAITING_PICKUP),
  ORDER_PARTIALLY_SHIPPED(Names.ORDER_PARTIALLY_SHIPPED),
  ORDER_SHIPPED(Names.ORDER_SHIPPED),
  ORDER_DECLINED(Names.ORDER_DECLINED),
  ORDER_REFUNDED(Names.ORDER_REFUNDED),
  ORDER_DISPUTED(Names.ORDER_DISPUTED),
  ORDER_MANUAL_VERIFICATION_REQUIRED(Names.ORDER_MANUAL_VERIFICATION_REQUIRED),
  ORDER_PARTIALLY_REFUNDED(Names.ORDER_PARTIALLY_REFUNDED),
  ORDER_READY_TO_PICK(Names.ORDER_READY_TO_PICK),
  ORDER_PICKING(Names.ORDER_PICKING),
  ORDER_CANCEL(Names.ORDER_CANCEL),
  ORDER_MONEY_COLLECT_PICKING(Names.ORDER_MONEY_COLLECT_PICKING),
  ORDER_PICKED(Names.ORDER_PICKED),
  ORDER_STORING(Names.ORDER_STORING),
  ORDER_TRANSPORTING(Names.ORDER_TRANSPORTING),
  ORDER_SORTING(Names.ORDER_SORTING),
  ORDER_DELIVERING(Names.ORDER_DELIVERING),
  ORDER_MONEY_COLLECT_DELIVERING(Names.ORDER_MONEY_COLLECT_DELIVERING),
  ORDER_DELIVERED(Names.ORDER_DELIVERED),
  ORDER_DELIVERY_FAIL(Names.ORDER_DELIVERY_FAIL),
  ORDER_WAITING_TO_RETURN(Names.ORDER_WAITING_TO_RETURN),
  ORDER_RETURN(Names.ORDER_RETURN),
  ORDER_RETURN_TRANSPORTING(Names.ORDER_RETURN_TRANSPORTING),
  ORDER_RETURN_SORTING(Names.ORDER_RETURN_SORTING),
  ORDER_RETURNING(Names.ORDER_RETURNING),
  ORDER_RETURN_FAIL(Names.ORDER_RETURN_FAIL),
  ORDER_RETURNED(Names.ORDER_RETURNED),
  ORDER_EXCEPTION(Names.ORDER_EXCEPTION),
  ORDER_DAMAGE(Names.ORDER_DAMAGE),
  ORDER_LOST(Names.ORDER_LOST);

  public class Names{
    public static final String ORDER_PENDING = "Đang chờ xác nhận";
    public static final String ORDER_AWAITING_PAYMENT = "Đang chờ thanh toán";
    public static final String ORDER_COMPLETED = "Đã hoàn thành";
    public static final String ORDER_CANCELLED = "Đã hủy";
    public static final String ORDER_AWAITING_FULFILLMENT = "";
    public static final String ORDER_AWAITING_SHIPMENT = "";
    public static final String ORDER_AWAITING_PICKUP = "";
    public static final String ORDER_PARTIALLY_SHIPPED = "";
    public static final String ORDER_SHIPPED = "";
    public static final String ORDER_DECLINED = "";
    public static final String ORDER_REFUNDED = "";
    public static final String ORDER_DISPUTED = "";
    public static final String ORDER_MANUAL_VERIFICATION_REQUIRED = "";
    public static final String ORDER_PARTIALLY_REFUNDED = "";
    public static final String ORDER_READY_TO_PICK = "Mới tạo đơn hàng";
    public static final String ORDER_PICKING = "Nhân viên đang lấy hàng";
    public static final String ORDER_CANCEL = "Hủy đơn hàng";
    public static final String ORDER_MONEY_COLLECT_PICKING = "Đang thu tiền người gửi";
    public static final String ORDER_PICKED = "Nhân viên đã lấy hàng";
    public static final String ORDER_STORING = "Hàng đang nằm ở kho";
    public static final String ORDER_TRANSPORTING = "Đang luân chuyển hàng";
    public static final String ORDER_SORTING = "Đang phân loại hàng hóa";
    public static final String ORDER_DELIVERING = "Nhân viên đang giao cho người nhận";
    public static final String ORDER_MONEY_COLLECT_DELIVERING = "Nhân viên đang thu tiền người nhận";
    public static final String ORDER_DELIVERED = "Nhân viên đã giao hàng thành công";
    public static final String ORDER_DELIVERY_FAIL = "Nhân viên giao hàng thất bại";
    public static final String ORDER_WAITING_TO_RETURN = "Đang đợi trả hàng về cho người gửi";
    public static final String ORDER_RETURN = "Trả hàng";
    public static final String ORDER_RETURN_TRANSPORTING = "Đang luân chuyển hàng trả";
    public static final String ORDER_RETURN_SORTING = "Đang phân loại hàng trả";
    public static final String ORDER_RETURNING = "Nhân viên đang đi trả hàng";
    public static final String ORDER_RETURN_FAIL = "Nhân viên trả hàng thất bại";
    public static final String ORDER_RETURNED = "Nhân viên trả hàng thành công";
    public static final String ORDER_EXCEPTION = "Đơn hàng ngoại lệ không nằm trong quy trình";
    public static final String ORDER_DAMAGE = "Hàng bị hư hỏng";
    public static final String ORDER_LOST = "Hàng bị mất";
  }

  private final String label;

  private EOrderStatus(String label) {
    this.label = label;
  }

  public String toString() {
    return this.label;
  }

  //  Pending — Customer started the checkout process but did not complete it. Incomplete orders are
  // assigned a "Pending" status and can be found under the More tab in the View Orders screen.
  //  Awaiting Payment — Customer has completed the checkout process, but payment has yet to be
  // confirmed. Authorize only transactions that are not yet captured have this status.
  //  Awaiting Fulfillment — Customer has completed the checkout process and payment has been
  // confirmed.
  //  Awaiting Shipment — Order has been pulled and packaged and is awaiting collection from a
  // shipping provider.
  //  Awaiting Pickup — Order has been packaged and is awaiting customer pickup from a
  // seller-specified location.
  //  Partially Shipped — Only some items in the order have been shipped.
  //  Completed — Order has been shipped/picked up, and receipt is confirmed; client has paid for
  // their digital product, and their file(s) are available for download.
  //  Shipped — Order has been shipped, but receipt has not been confirmed; seller has used the Ship
  // Items action. A listing of all orders with a "Shipped" status can be found under the More tab
  // of the View Orders screen.
  //  Cancelled — Seller has cancelled an order, due to a stock inconsistency or other reasons.
  // Stock levels will automatically update depending on your Inventory Settings. Cancelling an
  // order will not refund the order. This status is triggered automatically when an order using an
  // authorize-only payment gateway is voided in the control panel before capturing payment.
  //  Declined — Seller has marked the order as declined.
  //  Refunded — Seller has used the Refund action to refund the whole order. A listing of all
  // orders with a "Refunded" status can be found under the More tab of the View Orders screen.
  //  Disputed — Customer has initiated a dispute resolution process for the PayPal transaction that
  // paid for the order or the seller has marked the order as a fraudulent order.
  //  Manual Verification Required — Order on hold while some aspect, such as tax-exempt
  // documentation, is manually confirmed. Orders with this status must be updated manually.
  // Capturing funds or other order actions will not automatically update the status of an order
  // marked Manual Verification Required.
  //  Partially Refunded — Seller has partially refunded the order.
}
