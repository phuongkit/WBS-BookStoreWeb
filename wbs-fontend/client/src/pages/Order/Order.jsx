import React from 'react';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './Order.scss';
import { useDispatch, useSelector } from 'react-redux';
import { numberWithCommas } from '../../utils';
import { Navigate, useNavigate } from 'react-router-dom';
import { customerService, orderService } from '../../services';
import { momo, vnpay } from '../../services/payment';
import { EGender, EOrderStatus, EPayment, MESSAGE } from '../../utils';
import { deleteOrdersByIdApi } from '../../redux/order/ordersApi';
import { ghn } from '../../services/shipping/ghn.service';

function Order({ title }) {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [ship, setShip] = useState({ expectedDeliveryTime: null, transportFee: 0 });
    const order = useSelector((state) => state.orders.order.data) || localStorage.getItem('order')
        ? JSON.parse(localStorage.getItem('order'))
        : undefined;
    if (!order) {
        navigate('/');
    }
    const hasOrder = Object.keys(order).length > 0 && order.constructor === Object;
    let customer = { gender: EGender.UNKNOWN.index, fullName: '', phone: '', address: {} };
    let info = { totalPrice: 0, totalQuantity: 0, transportFee: 0 };
    if (hasOrder) {
        customer = { ...order };
        info = {
            ...info,
            id: order.id,
            totalPrice: order.totalPrice,
            totalQuantity: order.orderItems?.length || 0,
            transportFee: order.transportFee,
        };
    }
    const handleConfirm = async () => {
        const payment = getPayment();
        let orderItems = [...order.orderItems].map((value) => ({ ...value, productId: value.productId?.id }));
        const data = { ...order, orderItems, ...ship, ...payment };
        try {
            const res = await orderService.postOrder(data);
            if (res?.status === 'CREATED') {
                if (data.payment === EPayment.MOMO.index) {
                    const dataMomo = {
                        orderId: res?.data?.id,
                        orderInfo: `${order.fullName} thanh toán đơn hàng ${order.id} với MoMo`,
                        redirectUrl: window.location.origin + '/#/',
                        amount: 1000,
                        extraData: '',
                    };
                    const resMomo = await momo.createMomoPayment(dataMomo);
                    window.location = resMomo.data.payUrl;
                } else if (data.payment === EPayment.VNPAY.index) {
                    const dataVNPay = {
                        orderId: res?.data?.id,
                        // orderInfo: `${order.fullName} thanh toán đơn hàng ${order.id} với MoMo`,
                        fullName: order.fullName,
                        redirectUrl: window.location.origin + '/#/',
                        totalPrice: res?.data?.totalPriceProduct + res?.data?.transportFee,
                        // extraData: '',
                    };
                    const resVNPay = await vnpay.createVNPayPayment(dataVNPay);
                    window.location = resVNPay.data.payUrl || window.location.origin + '/#/';
                } else {
                    alert('Tạo đơn hàng thành công');
                    navigate('/');
                }
            } else {
                alert(MESSAGE.ERROR_ACTION);
                navigate('/');
            }
        } catch (err) {
            alert(MESSAGE.ERROR_ACTION);
            navigate('/');
        }
    };
    const getPayment = () => {
        const radios = document.querySelectorAll('input[name="payment"]');
        let paymentIndex = -1;
        for (let i = 0, length = radios.length; i < length; i++) {
            if (radios[i].checked) {
                paymentIndex = i;
                break;
            }
        }
        return paymentIndex != -1
            ? { payment: Number.parseInt(radios[paymentIndex].value), paid: false }
            : { payment: EPayment.CASH.index, paid: false };
    };
    const handleCancel = async () => {
        deleteOrdersByIdApi(dispatch, order.id, navigate);
    };
    useEffect(() => {
        document.title = title;
    }, []);
    useEffect(() => {
        if (order && ship.expectedDeliveryTime === null) {
            const getShip = async () => {
                let res = await ghn.getPreviewOrderGHN(order);
                console.log(res?.data?.data);
                let date = new Date(Date.parse(res?.data?.data?.expected_delivery_time));
                setShip({
                    expectedDeliveryTime: date,
                    transportFee: res?.data?.data?.total_fee,
                });
            };
            getShip();
        }
    }, [order]);
    return (
        <>
            {hasOrder ? (<>
                <div className="order">
                    <div className="alertsuccess-new">
                        {/* <i className="new-cartnew-success"></i> */}
                        <strong>Đặt hàng thành công</strong>
                    </div>
                    <div className="ordercontent">
                        <div>
                            <p>
                                Cảm ơn {EGender.getNameFromIndex(customer.gender)} <b>{customer.fullName}</b> đã
                                cho Deadblock cơ hội được phục vụ.
                            </p>
                        </div>
                        <div>
                            <div className="info-order">
                                <div className="info-order-header">
                                    <h6>
                                        Đơn hàng: <span className="text-blue-400 font-bold">#{info.id}</span>
                                    </h6>
                                    <div className="header-right" >
                                        <Link to="/account" style={{color: 'rgb(245, 166, 35)'}}>Quản lý đơn hàng</Link>
                                        <div className="cancel-order-new">
                                            <div>
                                                <div className="cancel-order-new">
                                                    <span>.</span>
                                                    <label
                                                        onClick={handleCancel}
                                                        style={{ color: 'rgb(245, 166, 35)', cursor: 'pointer', padding: '0 0' }}
                                                    >
                                                        Hủy
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <label htmlFor="">
                                    <span>
                                        <i className="info-order__dot-icon"></i>
                                        <span>
                                            <strong>Người nhận hàng:</strong>
                                            <h6 id="userName">
                                                {EGender.getNameFromIndex(customer.gender)} {customer.fullName}
                                            </h6>
                                            <br />
                                            <strong>Số điện thoại:</strong>
                                            <h6 id="customerPhone">{customer.phone}</h6>
                                        </span>
                                    </span>
                                </label>
                                <label htmlFor="">
                                    <span>
                                        <i className="info-order__dot-icon"></i>
                                        <span>
                                            <strong>Giao đến: </strong>
                                            {customer.address?.homeAdd}, {customer.address?.ward},
                                            {customer.address?.district}, Thành phố {customer.address?.city}.
                                        </span>
                                    </span>
                                </label>
                                <label htmlFor="">
                                    <span>
                                        <i className="info-order__dot-icon"></i>
                                        <span>
                                            <strong>
                                                Phí giao hàng: {numberWithCommas(Number(info.transportFee || ship.transportFee)) || 0}{' đ'}
                                            </strong>
                                        </span>
                                        <span>
                                            <strong>
                                                Thời gian dự kiến giao hàng: {ship.expectedDeliveryTime?.toISOString() || 'Chưa xác định'}{' '}
                                            </strong>
                                        </span>
                                    </span>
                                </label>
                                <label htmlFor="">
                                    <span>
                                        <i className="info-order__dot-icon"></i>
                                        <span>
                                            <strong>
                                                Tổng tiền: {numberWithCommas(
                                                    Number(ship.transportFee) + Number(order.totalPriceProduct) || 0
                                                ) || 0}{' '}
                                                {' đ'}
                                            </strong>
                                        </span>
                                    </span>
                                </label>
                            </div>
                        </div>

                        <div>
                            <h4 className="order-infor-alert">Đơn hàng chưa được thanh toán</h4>
                        </div>

                        <div className="payment-method-new">
                            <div>
                                <h3>Chọn hình thức thanh toán:</h3>
                                <ul className="formality-pay-new" style={{ listStyle: 'none' }}>
                                    <li className="normal-payment">
                                        <div className="text-payment">
                                            <span>
                                                <input
                                                    type="radio"
                                                    id="cash"
                                                    name="payment"
                                                    value={EPayment.CASH.index}
                                                    defaultChecked
                                                />
                                                <label htmlFor="cash">Thanh toán tiền mặt khi nhận hàng</label>
                                            </span>
                                        </div>
                                    </li>

                                    <li className="normal-payment">
                                        <div className="text-payment">
                                            <span>
                                                <input
                                                    type="radio"
                                                    id="momo"
                                                    name="payment"
                                                    value={EPayment.MOMO.index}
                                                />
                                                <label htmlFor="momo">Ví MoMo</label>
                                            </span>
                                        </div>
                                    </li>

                                    <li className="normal-payment">
                                        <div className="text-payment">
                                            <span>
                                                <input
                                                    type="radio"
                                                    id="vnpay"
                                                    name="payment"
                                                    value={EPayment.VNPAY.index}
                                                />
                                                <label htmlFor="vnpay">Thanh toán qua VNPay</label>
                                            </span>
                                        </div>
                                    </li>

                                    {/**<li className="normal-payment">
                                <div className="text-payment">
                                    <span>
                                        <input type="radio" id="ck" name="payment" value="banking" />
                                        <label htmlFor="ck">Chuyển khoản ngân hàng</label>
                                    </span>
                                </div>
                            </li>

                            <li className="normal-payment">
                                <div className="text-payment">
                                    <span>
                                        <input type="radio" id="atm" name="payment" value="atm" />
                                        <label htmlFor="atm">Qua thẻ ATM (có Internet Banking)</label>
                                    </span>
                                </div>
                            </li>

                            <li className="normal-payment">
                                <div className="text-payment">
                                    <span>
                                        <input type="radio" id="qr" name="payment" value="qrcode" />
                                        <label htmlFor="qr">Qua QR Code</label>
                                    </span>
                                </div>
                            </li>

                            <li className="normal-payment">
                                <div className="text-payment">
                                    <span>
                                        <input type="radio" id="visa" name="payment" value="visa" />
                                        <label htmlFor="visa">Qua thẻ quốc tế Visa, Master, JCB</label>
                                    </span>
                                </div>
                            </li>

                            <li className="normal-payment">
                                <div className="text-payment">
                                    <span>
                                        <input type="radio" id="moca" name="payment" value="moca" />
                                        <label htmlFor="moca">Qua Ví điện tử Moca trên Grab</label>
                                    </span>
                                </div>
                            </li>
                            <li className="normal-payment">
                                <div className="text-payment">
                                    <span>
                                        <input type="radio" id="machine" name="payment" value="marchinecard" />
                                        <label htmlFor="machine">Nhân viên mang máy cà thẻ khi nhận hàng</label>
                                    </span>
                                </div>
                            </li> */}
                                </ul>

                                <button onClick={handleConfirm} className="confirm-payment-button" style={{backgroundColor: 'rgb(245, 166, 35)'}}>
                                    Xác nhận
                                </button>
                            </div>
                            {/* <div className="refund-popup">
                                <a href="">Xem chính sách hoàn tiền online</a>
                            </div>
                            <hr />

                            <div className="buyanotherNew">
                                <Link to="/"> Mua thêm sản phẩm khác </Link>
                            </div>
                            <span className="customer-rating">
                                <div className="customer-rating__top">
                                    <div className="customer-rating__top__desc">
                                        {EGender.getNameFromIndex(customer.gender)}{' '}
                                        <strong>{customer.fullName}</strong> có hài lòng về trải nghiệm mua hàng?
                                    </div>
                                    <div className="customer-rating__top__rating-buttons">
                                        <button className="customer-rating__top__rating-buttons__good">
                                            <p>Hài lòng</p>
                                            <i className="iconrating-good"></i>
                                        </button>
                                        <button className="customer-rating__top__rating-buttons__bad">
                                            <p>Không hài lòng</p>
                                            <i className="iconrating-bad"></i>
                                        </button>
                                    </div>
                                </div>
                            </span> */}
                        </div>
                    </div>
                </div>
            </>) : (
                <Navigate to="/cart" />
            )}
        </>
    );
}

export default Order;
