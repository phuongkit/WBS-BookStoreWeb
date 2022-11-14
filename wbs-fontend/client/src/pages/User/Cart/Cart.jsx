import React from 'react';
import { useEffect } from 'react';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getPrice, toAddressSlug } from '../../../utils/utils';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';
import { updateItem, removeItem, clearCart } from '../../../redux/shopping-cart/cartItemsSlide';
import { postOrders } from '../../../redux/order/ordersApi';
import LocationForm from '../../../components/LocationForm';
import './Cart.scss';
import { MESSAGE, ENUM } from '../../../utils/variableDefault';
import validate from 'jquery-validation';
import { getUserByToken } from '../../../redux/user/userApi';

var $ = require('jquery');
function Cart() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [tempPrice, setTempPrice] = useState(0);
    const [totalPrice, setTotalPrice] = useState(0);
    const [addressOption, setAddresOption] = useState({
        ward: '',
        district: '',
        city: '',
    });
    const token = localStorage.getItem('token');
    const user = token
        ? localStorage.getItem('user') !== null
            ? JSON.parse(localStorage.getItem('user'))
            : useSelector((state) => state.users.auth.currentUser)
        : undefined;
    useEffect(() => {
        if (token && !user) {
            getUserByToken(dispatch);
        }
    }, [token]);
    const cartItems = useSelector((state) => state.cartItems.value);
    const removeCartItem = (id) => {
        cartItems.forEach((item) => {
            if (item.id === id) {
                dispatch(removeItem(item));
            }
        });
    };
    const updateCartItem = (id, value) => {
        cartItems.forEach((item) => {
            if (item.id === id) {
                dispatch(updateItem({ ...item, quantity: value }));
            }
        });
    };

    // nut btn-shopping-without-signup
    // $('#step1contentid').toggle('show');
    // $('.btn-shopping-without-signup').on('click', function (e) {
    //     $('#step1contentid').toggle('hide');
    //     $('#step2contentid').toggle('show');
    // });

    $('#form-order').validate({
        rules: {
            name: {
                required: true,
            },
            phone: {
                required: true,
                minlength: 8,
            },
            email: {
                required: true,
                email: true,
            },
            homeAdd: {
                required: true,
            },
            wardId: {
                required: true,
            },
            districtId: {
                required: true,
            },
            cityId: {
                required: true,
            }
        },
        messages: {
            name: {
                required: 'Vui lòng nhập họ và tên',
            },
            phone: {
                required: 'Vui lòng nhập số điện thoại',
                minlength: 'Số máy quý khách vừa nhập là số không có thực',
            },
            email: {
                required: 'Vui lòng nhập email',
                minlength: 'Email không hợp lệ',
                email: 'Vui lòng nhập email',
            },
            homeAdd: {
                required: 'Vui lòng nhập địa chỉ giao hàng',
            },
            wardId: {
                required: "Vui lòng chọn phường/xã",
            },
            districtId: {
                required: "Vui lòng chọn quận/huyện",
            },
            cityId: {
                required: "Vui lòng chọn tỉnh/thành phố",
            }
        },
    });

    useEffect(() => {
        if (cartItems) {
            let tempPrice = cartItems.reduce((acc, item) => acc + item.salePrice * item.quantity, 0);
            setTempPrice(tempPrice);
            setTotalPrice(tempPrice);
        }
    }, [cartItems]);
    let orderDetail = {
        gender: ENUM.EGender.UNKNOWN.index,
        fullName: '',
        email: '',
        phone: '',
        address: {
            homeAdd: user.address.homeAdd || '',
            ward: '',
            district: '',
            city: '',
        },
        payment: ENUM.EPayment.CASH.index,
        shippingMethod: ENUM.EShippingMethod.GHN_EXPRESS.index,
        discountCode: '',
        paid: false,
        note: '',
        orderItems: [],
    };
    let orderItem = {
        productId: 0,
        quantity: 0,
        percent: 0,
        saleName: '',
        note: '',
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        let sex = 2;
        let payment = $("input[name='payment']:checked").val();
        payment = payment ? Number.parseInt(payment) : 0;
        let orderItemDetails = cartItems.map((value) => {
            let item = orderItem;
            item.productId = value.id;
            item.quantity = value.quantity;
            item.saleName = value.tag;
            item.note = value?.note ? value.note : '';
            return item;
        });
        orderDetail = {
            ...orderDetail,
            fullName: $('#inputNameOrder').val(),
            email: $('#inputEmailOrder').val(),
            phone: $('#inputPhoneOrder').val(),
            gender: sex === null || sex === undefined ? EGender.UNKNOWN : Number.parseInt(sex),
            address: {
                homeAdd: $('#inputHomeAddOrder').val(),
                ward: addressOption.ward,
                district: addressOption.district,
                city: addressOption.city,
            },
            payment: payment,
            paid: payment === ENUM.EPayment.MOMO.index,
            note: $('#inputNoteOrder').val(),
            orderItems: orderItemDetails,
        };
        console.log(1);
        postOrders(dispatch, orderDetail);
        dispatch(clearCart());
        navigate('/');
    };
    return (
        <>
            {/* <!-- giao diện giỏ hàng  --> */}
            <section className="content my-3">
                <div className="container">
                    <div className="cart-page bg-white">
                        <div className="row">
                            {cartItems && cartItems.length < 1 ? (
                                <>
                                    {/* <!-- giao diện giỏ hàng khi không có item  --> */}
                                    <div id="cart-empty" className="col-12 cart-empty">
                                        <div className="py-3 pl-3">
                                            <h6 className="header-gio-hang">
                                                GIỎ HÀNG CỦA BẠN <span>(0 sản phẩm)</span>
                                            </h6>
                                            <div className="cart-empty-content w-100 text-center justify-content-center">
                                                <img
                                                    style={{ display: 'inline-block' }}
                                                    src="images/shopping-cart-not-product.png"
                                                    alt="shopping-cart-not-product"
                                                />
                                                <p>Chưa có sản phẩm nào trong giở hàng của bạn</p>
                                                <Link to="/" className="btn nutmuathem mb-3">
                                                    Mua thêm
                                                </Link>
                                            </div>
                                        </div>
                                    </div>
                                </>
                            ) : (
                                <>
                                    {/* <!-- giao diện giỏ hàng khi có hàng (phần comment màu xanh bên dưới là phần 2 sản phẩm trong giỏ hàng nhưng giờ đã demo bằng jquery) --> */}
                                    <div className="col-md-8 cart">
                                        <div className="cart-content py-3 pl-3">
                                            <h6 className="header-gio-hang">
                                                GIỎ HÀNG CỦA BẠN <span>({cartItems.length} sản phẩm)</span>
                                            </h6>
                                            <div className="cart-list-items">
                                                {cartItems &&
                                                    cartItems.map((item, index) => (
                                                        <>
                                                            <div key={index} className="cart-item d-flex">
                                                                <a
                                                                    href={`${item.categorySlug}/${item.slug}`}
                                                                    className="img"
                                                                >
                                                                    <img
                                                                        src={item.img || 'images/image-product.jpg'}
                                                                        className="img-fluid"
                                                                        alt={item.name}
                                                                    />
                                                                </a>
                                                                <div className="item-caption d-flex w-100">
                                                                    <div className="item-info ml-3">
                                                                        <a
                                                                            href={`${item.categorySlug}/${item.slug}`}
                                                                            className="ten"
                                                                        >
                                                                            {item.name || MESSAGE.NAME_NOT_AVAILABLE}
                                                                        </a>
                                                                        <div className="soluong d-flex">
                                                                            <div className="input-number input-group mb-3">
                                                                                <div className="input-group-prepend">
                                                                                    <span
                                                                                        className="input-group-text btn-spin btn-dec"
                                                                                        onClick={() => {
                                                                                            updateCartItem(
                                                                                                item.id,
                                                                                                item.quantity - 1,
                                                                                            );
                                                                                        }}
                                                                                    >
                                                                                        -
                                                                                    </span>
                                                                                </div>
                                                                                <input
                                                                                    id={`inputQuantity${item.id}`}
                                                                                    type="text"
                                                                                    className="soluongsp text-center p-0"
                                                                                    value={item.quantity}
                                                                                    // onChange={(e) => {
                                                                                    //     updateCartItem(
                                                                                    //         item.id,
                                                                                    //         Number.parseInt(e.target.value),
                                                                                    //     );
                                                                                    // }}

                                                                                    readOnly
                                                                                />
                                                                                <div className="input-group-append">
                                                                                    <span
                                                                                        className="input-group-text btn-spin btn-inc"
                                                                                        onClick={() => {
                                                                                            updateCartItem(
                                                                                                item.id,
                                                                                                item.quantity + 1,
                                                                                            );
                                                                                        }}
                                                                                    >
                                                                                        +
                                                                                    </span>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div className="item-price ml-auto d-flex flex-column align-items-end">
                                                                        <div className="giamoi">
                                                                            {getPrice(item.salePrice * item.quantity)} ₫
                                                                        </div>
                                                                        <div className="giacu">
                                                                            {getPrice(item.originPrice * item.quantity)}{' '}
                                                                            ₫
                                                                        </div>
                                                                        <span
                                                                            className="remove mt-auto"
                                                                            onClick={() => {
                                                                                var isDelete = confirm(
                                                                                    "Bạn có muốn xóa sản phẩm với tên '" +
                                                                                        (item.name ||
                                                                                            MESSAGE.NAME_NOT_AVAILABLE) +
                                                                                        "' trong giỏ hàng không",
                                                                                );
                                                                                if (isDelete) {
                                                                                    removeCartItem(item.id);
                                                                                }
                                                                            }}
                                                                        >
                                                                            <i className="far fa-trash-alt"></i>
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <hr />
                                                        </>
                                                    ))}
                                            </div>
                                            <div className="row">
                                                <div className="col-md-3">
                                                    <Link to="/" className="btn nutmuathem mb-3">
                                                        Mua thêm
                                                    </Link>
                                                </div>
                                                <div className="col-md-5 offset-md-4">
                                                    <div className="tonggiatien">
                                                        <div className="group d-flex justify-content-between">
                                                            <p className="label">Tạm tính:</p>
                                                            <p className="tamtinh">{getPrice(tempPrice)} ₫</p>
                                                        </div>
                                                        <div className="group d-flex justify-content-between">
                                                            <p className="label">Giảm giá:</p>
                                                            <p className="giamgia">0 ₫</p>
                                                        </div>
                                                        <div className="group d-flex justify-content-between">
                                                            <p className="label">Phí vận chuyển:</p>
                                                            <p className="phivanchuyen">0 ₫</p>
                                                        </div>
                                                        <div className="group d-flex justify-content-between">
                                                            <p className="label">Phí dịch vụ:</p>
                                                            <p className="phidicvu">0 ₫</p>
                                                        </div>
                                                        <div className="group d-flex justify-content-between align-items-center">
                                                            <strong className="text-uppercase">Tổng cộng:</strong>
                                                            <p className="tongcong">{getPrice(totalPrice)} ₫</p>
                                                        </div>
                                                        <small className="note d-flex justify-content-end text-muted">
                                                            (Giá đã bao gồm VAT)
                                                        </small>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    {/* <!-- giao diện phần thanh toán nằm bên phải  --> */}
                                    <div className="col-md-4 cart-steps pl-0">
                                        <div id="cart-steps-accordion" role="tablist" aria-multiselectable="true">
                                            {/* <!-- bước số 1: đăng nhập hoặc đăng ký --> */}
                                            {!user && (
                                                <div className="card">
                                                    <div
                                                        className={'card-header cardheader active'}
                                                        role="tab"
                                                        id="step1header"
                                                    >
                                                        <h5 className="mb-0">
                                                            <a
                                                                data-toggle="collapse"
                                                                data-parent="#cart-steps-accordion"
                                                                href="#step1contentid"
                                                                aria-expanded="true"
                                                                aria-controls="step1contentid"
                                                                className="text-uppercase header"
                                                                onClick={(e) => {
                                                                    document
                                                                        .getElementById('step1header')
                                                                        .classList.toggle('active');
                                                                }}
                                                            >
                                                                <span className="steps">1</span>
                                                                <span className="label">
                                                                    KHÁCH HÀNG MỚI / ĐĂNG NHẬP
                                                                </span>
                                                                <i className="fa fa-chevron-right float-right"></i>
                                                            </a>
                                                        </h5>
                                                    </div>
                                                    <div
                                                        id="step1contentid"
                                                        className="collapse in stepscontent show"
                                                        role="tabpanel"
                                                        aria-labelledby="step1header"
                                                    >
                                                        <div className="card-body p-0">
                                                            <nav>
                                                                <div
                                                                    className="nav nav-tabs dangnhap-dangky"
                                                                    id="nav-tab"
                                                                    role="tablist"
                                                                >
                                                                    <a
                                                                        className="nav-item nav-link active text-center text-uppercase"
                                                                        id="nav-dangnhap-tab"
                                                                        data-toggle="tab"
                                                                        href="#nav-dangnhap"
                                                                        role="tab"
                                                                        aria-controls="nav-dangnhap"
                                                                        aria-selected="true"
                                                                    >
                                                                        Đăng nhập
                                                                    </a>
                                                                    <a
                                                                        className="nav-item nav-link text-center text-uppercase"
                                                                        id="nav-dangky-tab"
                                                                        data-toggle="tab"
                                                                        href="#nav-dangky"
                                                                        role="tab"
                                                                        aria-controls="nav-dangky"
                                                                        aria-selected="false"
                                                                    >
                                                                        Đăng ký
                                                                    </a>
                                                                </div>
                                                            </nav>
                                                            <div className="tab-content" id="nav-tabContent">
                                                                <div
                                                                    className="tab-pane fade show active"
                                                                    id="nav-dangnhap"
                                                                    role="tabpanel"
                                                                    aria-labelledby="nav-dangnhap-tab"
                                                                >
                                                                    <form
                                                                        id="form-signin-cart"
                                                                        className="form-signin mt-2"
                                                                    >
                                                                        <div className="form-label-group">
                                                                            <input
                                                                                type="email"
                                                                                id="inputEmailLogin"
                                                                                className="form-control"
                                                                                placeholder="Nhập địa chỉ email"
                                                                                name="email"
                                                                                required
                                                                                autoFocus
                                                                            />
                                                                        </div>
                                                                        <div className="form-label-group">
                                                                            <input
                                                                                type="password"
                                                                                id="inputPasswordLogin"
                                                                                className="form-control"
                                                                                placeholder="Mật khẩu"
                                                                                name="password"
                                                                                required
                                                                            />
                                                                        </div>
                                                                        <div className="custom-control custom-checkbox mb-3">
                                                                            <a
                                                                                href="#"
                                                                                className="float-right text-decoration-none"
                                                                                style={{ color: '#F5A623' }}
                                                                            >
                                                                                Quên mật khẩu
                                                                            </a>
                                                                        </div>
                                                                        <button
                                                                            className="btn btn-lg btn-block btn-signin text-uppercase text-white"
                                                                            type="submit"
                                                                            style={{ background: '#F5A623' }}
                                                                        >
                                                                            Đăng nhập
                                                                        </button>

                                                                        <hr className="my-4" />
                                                                        <button
                                                                            className="btn btn-lg btn-google btn-block text-uppercase"
                                                                            type="submit"
                                                                        >
                                                                            <i className="fab fa-google mr-2"></i> Đăng
                                                                            nhập bằng Google
                                                                        </button>
                                                                        <button
                                                                            className="btn btn-lg btn-facebook btn-block text-uppercase"
                                                                            type="submit"
                                                                        >
                                                                            <i className="fab fa-facebook-f mr-2"></i>{' '}
                                                                            Đăng nhập bằng Facebook
                                                                        </button>
                                                                    </form>
                                                                    <div
                                                                        className="btnduoicung"
                                                                        style={{ padding: '0 15px 15px 15px' }}
                                                                    >
                                                                        <button
                                                                            className="btn btn-lg btn-block btn-shopping-without-signup text-uppercase text-white"
                                                                            style={{ background: '#F5A623' }}
                                                                        >
                                                                            Mua hàng không cần đăng ký
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                                <div
                                                                    className="tab-pane fade"
                                                                    id="nav-dangky"
                                                                    role="tabpanel"
                                                                    aria-labelledby="nav-dangky-tab"
                                                                >
                                                                    <form
                                                                        id="form-signup-cart"
                                                                        className="form-signin mt-2"
                                                                    >
                                                                        <div className="form-label-group">
                                                                            <input
                                                                                type="text"
                                                                                id="inputNameRegister"
                                                                                className="form-control"
                                                                                placeholder="Nhập họ và tên"
                                                                                name="name"
                                                                                required
                                                                                autoFocus
                                                                            />
                                                                        </div>
                                                                        <div className="form-label-group">
                                                                            <input
                                                                                type="text"
                                                                                id="inputPhoneRegister"
                                                                                className="form-control"
                                                                                placeholder="Nhập số điện thoại"
                                                                                name="phone"
                                                                                required
                                                                            />
                                                                        </div>
                                                                        <div className="form-label-group">
                                                                            <input
                                                                                type="email"
                                                                                id="inputEmailRegister"
                                                                                className="form-control"
                                                                                placeholder="Nhập địa chỉ email"
                                                                                name="email"
                                                                                required
                                                                            />
                                                                        </div>

                                                                        <div className="form-label-group">
                                                                            <input
                                                                                type="password"
                                                                                id="inputPasswordRegister"
                                                                                className="form-control"
                                                                                placeholder="Nhập mật khẩu"
                                                                                name="password"
                                                                                required
                                                                            />
                                                                        </div>
                                                                        <div className="form-label-group mb-3">
                                                                            <input
                                                                                type="password"
                                                                                id="inputConfirmPasswordRegister"
                                                                                className="form-control"
                                                                                name="confirm_password"
                                                                                placeholder="Nhập lại mật khẩu"
                                                                                required
                                                                            />
                                                                        </div>
                                                                        <div className="custom-control custom-checkbox mb-3">
                                                                            <p className="text-center">
                                                                                Bằng việc đăng ký, bạn đã đồng ý với
                                                                                DealBook về
                                                                            </p>
                                                                            <a
                                                                                href="#"
                                                                                className="text-decoration-none text-center"
                                                                                style={{ color: '#F5A623' }}
                                                                            >
                                                                                Điều khoản dịch vụ & Chính sách bảo mật
                                                                            </a>
                                                                        </div>
                                                                        <hr className="mt-3 mb-2" />
                                                                        <button
                                                                            className="btn btn-lg btn-block btn-signin text-uppercase text-white mt-3"
                                                                            type="submit"
                                                                            style={{ background: '#F5A623' }}
                                                                        >
                                                                            Đăng ký
                                                                        </button>
                                                                    </form>
                                                                    <div
                                                                        className="btnduoicung"
                                                                        style={{ padding: '0 15px 15px 15px' }}
                                                                    >
                                                                        <button
                                                                            className="btn btn-lg btn-block btn-shopping-without-signup text-uppercase text-white"
                                                                            style={{ background: '#F5A623' }}
                                                                        >
                                                                            Mua hàng không cần đăng ký
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            )}
                                            <form id="form-order" className="form-order" onSubmit={handleSubmit}>
                                                {/* <!-- bước số 2: nhập địa chỉ giao hàng  --> */}
                                                <div className="card">
                                                    <div
                                                        className={'card-header ' + (user && 'active')}
                                                        role="tab"
                                                        id="step2header"
                                                    >
                                                        <h5 className="mb-0">
                                                            <a
                                                                data-toggle="collapse"
                                                                data-parent="#cart-steps-accordion"
                                                                href="#step2contentid"
                                                                aria-expanded="true"
                                                                aria-controls="step2contentid"
                                                                className="text-uppercase header"
                                                                onClick={(e) => {
                                                                    document
                                                                        .getElementById('step2header')
                                                                        .classList.toggle('active');
                                                                }}
                                                            >
                                                                <span className="steps">{user ? 1 : 2}</span>
                                                                <span className="label">Địa chỉ giao hàng</span>
                                                                <i className="fa fa-chevron-right float-right"></i>
                                                            </a>
                                                        </h5>
                                                    </div>
                                                    <div
                                                        id="step2contentid"
                                                        className={'collapse in stepscontent ' + (user && 'show')}
                                                        role="tabpanel"
                                                        aria-labelledby="step2header"
                                                    >
                                                        <div className="card-body">
                                                            <div className="form-diachigiaohang">
                                                                <div className="form-label-group">
                                                                    <input
                                                                        type="text"
                                                                        id="inputNameOrder"
                                                                        className="form-control"
                                                                        placeholder="Nhập họ và tên"
                                                                        name="name"
                                                                        required
                                                                        autoFocus
                                                                        defaultValue={user?.fullName}
                                                                    />
                                                                </div>
                                                                <div className="form-label-group">
                                                                    <input
                                                                        type="text"
                                                                        id="inputPhoneOrder"
                                                                        className="form-control"
                                                                        placeholder="Nhập số điện thoại"
                                                                        name="phone"
                                                                        required
                                                                        defaultValue={user?.phone}
                                                                    />
                                                                </div>
                                                                <div className="form-label-group">
                                                                    <input
                                                                        type="email"
                                                                        id="inputEmailOrder"
                                                                        className="form-control"
                                                                        placeholder="Nhập địa chỉ email"
                                                                        name="email"
                                                                        required
                                                                        defaultValue={user?.email}
                                                                    />
                                                                </div>
                                                                <div className="form-label-group">
                                                                    <input
                                                                        type="text"
                                                                        id="inputHomeAddOrder"
                                                                        className="form-control"
                                                                        placeholder="Nhập Địa chỉ giao hàng"
                                                                        name="homeAdd"
                                                                        required
                                                                        defaultValue={user?.address?.homeAdd}
                                                                    />
                                                                </div>
                                                                <LocationForm
                                                                    className="tw-mb-4"
                                                                    onChange={setAddresOption}
                                                                    address={toAddressSlug(user?.address)}
                                                                />
                                                                <div className="form-label-group">
                                                                    <textarea
                                                                        type="text"
                                                                        id="inputNoteOrder"
                                                                        className="form-control"
                                                                        placeholder="Nhập ghi chú (Nếu có)"
                                                                        name="note"
                                                                    ></textarea>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    {/* <!-- bước 3: thanh toán đặt mua  --> */}
                                                    <div className="card">
                                                        <div className="card-header" role="tab" id="step3header">
                                                            <h5 className="mb-0">
                                                                <a
                                                                    data-toggle="collapse"
                                                                    data-parent="#cart-steps-accordion"
                                                                    href="#step3contentid"
                                                                    aria-expanded="true"
                                                                    aria-controls="step3contentid"
                                                                    className="text-uppercase header"
                                                                    onClick={(e) => {
                                                                        document
                                                                            .getElementById('step3header')
                                                                            .classList.toggle('active');
                                                                    }}
                                                                >
                                                                    <span className="steps">{user ? 2 : 3}</span>
                                                                    <span className="label">Thanh toán đặt mua</span>
                                                                    <i className="fa fa-chevron-right float-right"></i>
                                                                </a>
                                                            </h5>
                                                        </div>
                                                        <div
                                                            id="step3contentid"
                                                            className="collapse in stepscontent"
                                                            role="tabpanel"
                                                            aria-labelledby="step3header"
                                                        >
                                                            <div className="card-body" style={{ padding: '15px' }}>
                                                                <div className="goigiaohang">
                                                                    <h6 className="header text-uppercase">
                                                                        Chọn gói giao hàng
                                                                    </h6>
                                                                    <div className="option">
                                                                        <input
                                                                            type="radio"
                                                                            name="goigiaohang"
                                                                            id="ghtc"
                                                                            defaultChecked
                                                                        />
                                                                        <label htmlFor="ghtc" className="tw-pl-1">
                                                                            Giao hàng tiêu chuẩn
                                                                        </label>
                                                                        <p>
                                                                            Từ 1-3 ngày tại TP. Hồ Chí Minh; từ 3-5 ngày
                                                                            đối với các Tỉnh / Thành khác
                                                                        </p>
                                                                    </div>
                                                                    <div className="option">
                                                                        <input
                                                                            type="radio"
                                                                            name="goigiaohang"
                                                                            id="ghn"
                                                                        />
                                                                        <label htmlFor="ghn" className="tw-pl-1">
                                                                            Giao hàng nhanh
                                                                        </label>
                                                                        <p>
                                                                            1 ngày tại TP. Hồ Chí Minh; từ 2-3 ngày đối
                                                                            với các Tỉnh / Thành khác
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                                <hr />
                                                                <div className="pttt">
                                                                    <h6 className="header text-uppercase">
                                                                        Chọn phương thức thanh toán
                                                                    </h6>
                                                                    {/* <div className="option mb-2">
                                                                    <input
                                                                        type="radio"
                                                                        name="pttt"
                                                                        id="cod"
                                                                        defaultChecked
                                                                    />
                                                                    <label htmlFor="cod">
                                                                        Thanh toán bằng tiền mặt khi nhận hàng
                                                                    </label>
                                                                </div>
                                                                <div className="option option2">
                                                                    <input type="radio" name="pttt" id="atm" />
                                                                    <label htmlFor="atm" className="chuyenkhoan">
                                                                        Thanh toán chuyển khoản trước qua Thẻ
                                                                        ATM/Internet Banking
                                                                    </label>
                                                                    <p className="mt-4">
                                                                        - Quý khách chỉ chuyển khoản khi được xác nhận
                                                                        có đủ sách qua điện thoại từ DealBook.
                                                                    </p>
                                                                    <p>
                                                                        - Thời gian chuyển khoản là trong tối đa 2 ngày
                                                                        từ khi có xác nhận đủ sách.
                                                                    </p>
                                                                    <p>
                                                                        - Nội dung chuyển khoản cần ghi:{' '}
                                                                        <a href="#">[Mã đơn hàng]</a> ; hoặc{' '}
                                                                        <a href="#">[số điện thoại dùng đặt hàng]</a>{' '}
                                                                    </p>
                                                                    <p>
                                                                        - Xem thông tin chuyển khoản của NetaBooks{' '}
                                                                        <a href="phuong-thuc-thanh-toan.html">
                                                                            tại đây
                                                                        </a>{' '}
                                                                    </p>
                                                                </div> */}
                                                                    <ul className="formality-pay-new tw-list-none">
                                                                        <li className="normal-payment">
                                                                            <div className="text-payment">
                                                                                <span>
                                                                                    <input
                                                                                        type="radio"
                                                                                        id="cash"
                                                                                        name="payment"
                                                                                        defaultValue={
                                                                                            ENUM.EPayment.CASH.index
                                                                                        }
                                                                                        defaultChecked
                                                                                    />
                                                                                    <label
                                                                                        htmlFor="cash"
                                                                                        className="tw-pl-1"
                                                                                    >
                                                                                        {
                                                                                            ENUM.EPayment.CASH.name
                                                                                        }
                                                                                    </label>
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
                                                                                        defaultValue={
                                                                                            ENUM.EPayment.MOMO.index
                                                                                        }
                                                                                    />
                                                                                    <label
                                                                                        htmlFor="momo"
                                                                                        className="tw-pl-1"
                                                                                    >
                                                                                        {
                                                                                            ENUM.EPayment.MOMO.name
                                                                                        }
                                                                                    </label>
                                                                                </span>
                                                                            </div>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                                <hr />
                                                                <button
                                                                    type="submit"
                                                                    className="form-label-group btn btn-lg btn-block btn-checkout text-uppercase text-white"
                                                                    style={{ background: '#F5A623' }}
                                                                >
                                                                    Đặt mua
                                                                </button>
                                                                <p className="text-center note-before-checkout">
                                                                    (Vui lòng kiểm tra lại đơn hàng trước khi Đặt Mua)
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    {/* <!-- het div cart-steps  --> */}
                                </>
                            )}
                        </div>
                        {/* <!-- het row  --> */}
                    </div>
                    {/* <!-- het cart-page  --> */}
                </div>
                {/* <!-- het container  --> */}
            </section>
            {/* <!-- het khoi content  --> */}
        </>
    );
}

export default Cart;