import React from 'react';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './Order.scss';
import { useDispatch, useSelector } from 'react-redux';
import { numberWithCommas } from '../../utils';
import { Navigate, useNavigate } from 'react-router-dom';
import { customerService, orderService } from '~/services';
import { momo, vnpay } from '../../services/payment';
import { updatePaymentOrders } from '~/redux/order/ordersApi';
import { ENUM } from '../../utils';
import { deleteOrdersByIdApi } from '../../redux/order/ordersApi';

function Order({ title }) {
    
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const order = localStorage.getItem('order')
        ? JSON.parse(localStorage.getItem('order'))
        : useSelector((state) => state.orders.order.data);
    if (!order) {
        console.log('orer', title);
        navigate('/');
    }
    const hasOrder = Object.keys(order).length > 0 && order.constructor === Object;
    let customer = { gender: ENUM.EGender.UNKNOWN.index, fullName: '', phone: '', address: {} };
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
        console.log('payment', payment);
        // const data = { ...order, payment };
        const data = { ...payment, status: payment.payment > 0 ? ENUM.EOrderStatus.ORDER_AWAITING_PAYMENT.name :  ENUM.EOrderStatus.ORDER_PENDING.name};
        updatePaymentOrders(dispatch, data, order.id);
        localStorage.removeItem('order');
        if (payment.payment === ENUM.EPayment.MOMO.index) {
            const dataMomo = {
                orderId: info.id,
                orderInfo: `${customer.fullName} thanh toán đơn hàng ${info.id} với MoMo`,
                redirectUrl: window.location.origin,
                amount: 5000,
                extraData: '',
            };
            const res = await momo.createMomoPayment(dataMomo);
            localStorage.removeItem('order');
            window.location = res.data.payUrl;
        } else if (payment.payment === ENUM.EPayment.VNPAY.index) {
            const dataVNPay = {
                orderId: info.id,
                // orderInfo: `${customer.fullName} thanh toán đơn hàng ${info.id} với MoMo`,
                fullName: customer.fullName,
                redirectUrl: window.location.origin,
                totalPrice: info.totalPrice,
                // extraData: '',
            };
            const res = await vnpay.createVNPayPayment(dataVNPay);
            localStorage.removeItem('order');
            window.location = res.data.payUrl;
        } else {
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
            : { payment: ENUM.EPayment.CASH.index, paid: false };
    };
    const handleCancel = async () => {
        deleteOrdersByIdApi(dispatch, order.id, navigate);
    };
    useEffect(() => {
        document.title = title;
    }, []);
    return (
    <>
        {hasOrder ? (
            <div className="order">
                <div className="alertsuccess-new">
                    <i className="new-cartnew-success"></i>
                    <strong>Đặt hàng thành công</strong>
                </div>
                <div className="ordercontent">
                    <div>
                        <p>
                            Cảm ơn {ENUM.EGender.getNameFromIndex(customer.gender)} <b>{customer.fullName}</b> đã cho
                            Deadblock cơ hội được phục vụ.
                        </p>
                    </div>
                    <div>
                        <div className="info-order">
                            <div className="info-order-header">
                                <h6>
                                    Đơn hàng: <span className="text-blue-400 font-bold">#{info.id}</span>
                                </h6>
                                <div className="header-right">
                                    <Link to="/account">Quản lý đơn hàng</Link>
                                    <div className="cancel-order-new">
                                        <div>
                                            <div className="cancel-order-new">
                                                <span>.</span>
                                                <label
                                                    onClick={handleCancel}
                                                    style={{ color: 'blue', cursor: 'pointer', padding: '0 0' }}
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
                                            {ENUM.EGender.getNameFromIndex(customer.gender)} {customer.fullName}
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
                                        {customer.address?.district}, Thành phố {customer.address?.city} (nhân viên sẽ
                                        gọi xác nhận trước khi giao).
                                    </span>
                                </span>
                            </label>
                            <label htmlFor="">
                                <span>
                                    <i className="info-order__dot-icon"></i>
                                    <span>
                                        <strong>
                                            Phí giao hàng: {numberWithCommas(Number(info.transportFee)) || 0}{' '}
                                        </strong>
                                    </span>
                                </span>
                            </label>
                            <label htmlFor="">
                                <span>
                                    <i className="info-order__dot-icon"></i>
                                    <span>
                                        <strong>Tổng tiền: {numberWithCommas(Number(info.totalPrice)) || 0} </strong>
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
                            <ul className="formality-pay-new" style={{listStyle: 'none'}}>
                                <li className="normal-payment">
                                    <div className="text-payment">
                                        <span>
                                            <input
                                                type="radio"
                                                id="cash"
                                                name="payment"
                                                value={ENUM.EPayment.CASH.index}
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
                                                value={ENUM.EPayment.MOMO.index}
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
                                                value={ENUM.EPayment.VNPAY.index}
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

                            <button onClick={handleConfirm} className="confirm-payment-button">
                                Xác nhận
                            </button>
                        </div>
                        <div className="refund-popup">
                            <a href="">Xem chính sách hoàn tiền online</a>
                        </div>
                        <hr />

                        <div className="buyanotherNew">
                            <Link to="/"> Mua thêm sản phẩm khác </Link>
                        </div>
                        <span className="customer-rating">
                            <div className="customer-rating__top">
                                <div className="customer-rating__top__desc">
                                    {ENUM.EGender.getNameFromIndex(customer.gender)}{' '}
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
                        </span>
                    </div>
                </div>
            </div>
        ) : (
            <Navigate to="/cart" />
        )}
    </>);
}

export default Order;
