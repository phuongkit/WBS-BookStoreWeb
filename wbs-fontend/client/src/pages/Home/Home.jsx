import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import validate from 'jquery-validation';
import './Home.scss';
import { ProductBlock } from '@Components/ProductBlock';
import { EHomeOption } from '~/utils';
import { useSearchParams, useNavigate, useLocation } from 'react-router-dom';
import { vnpay } from '../../services/payment';
import swal from 'sweetalert';
import { getUserByToken } from '../../redux/user/userApi';
import { GOOGLE_AUTH_URL } from '../../utils/constants';

var jQueryBridget = require('jquery-bridget');
var Isotope = require('isotope-layout');
var $ = require('jquery');
jQueryBridget('isotope', Isotope, $);

function Home({ title }) {
    const location = useLocation();
    const dispatch = useDispatch();
    const [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();
    useEffect(() => {
        const clearParamByVNPay = async () => {
            if (searchParams && searchParams.get('vnp_ResponseCode')) {
                const params = [];

                for (let entry of searchParams.entries()) {
                    params.push(entry);
                }
                const param = params.map(([key, value]) => ({ key, value }));
                let status = param.find(({ key, value }) => key === 'vnp_ResponseCode');
                if (status?.value === '00') {
                    swal({
                        title: 'Thành công',
                        text: 'Giao dịch thành công',
                        icon: 'success',
                    });
                } else {
                    swal({
                        title: 'Thất bại',
                        text: 'Giao dịch thất bại, vui lòng kiểm tra lại',
                        icon: 'error',
                    });
                }
                await vnpay.getReturnVNPay(param);
            }
        };
        const clearParamByGoogle = async () => {
            let token = searchParams.get('token');
            localStorage.setItem('token', JSON.stringify(token));
            getUserByToken(dispatch, token)
        }
        if (searchParams) {
            if (searchParams.get('vnp_ResponseCode')) {
                clearParamByVNPay();
            } else if (searchParams.get('token')) {
                clearParamByGoogle();
            }
            navigate('/');
        }
        document.title = title;
    }, []);
    //hieu ung header va nut backtotop
    $('#backtotop').on('click', function () {
        $('html, body').animate({ scrollTop: 0 }, 400);
    });

    $(window).on('scroll', function () {
        if ($('body,html').scrollTop() > 150) {
            $('.navbar').addClass('fixed-top');
        } else {
            $('.navbar').removeClass('fixed-top');
        }
    });

    $(window).on('scroll', function () {
        if ($('body,html').scrollTop() > 500) {
            $('.nutcuonlen').addClass('hienthi');
        } else {
            $('.nutcuonlen').removeClass('hienthi');
        }
    });

    //btn-spin
    $('.btn-inc').on('click', function (e) {
        var strval = $(this).parent().prev().val();
        var val = parseInt(strval) + 1;
        $(this).parent().prev().attr('value', val);
    });
    $('.btn-dec').on('click', function (e) {
        var strval = $(this).parent().next().val();
        var val = parseInt(strval) - 1;
        if (val < 1) {
            $(this).parent().next().attr('value', 1);
        } else {
            $(this).parent().next().attr('value', val);
        }
    });

    // gui danh gia
    $('.formdanhgia').hide(200);
    $('.vietdanhgia').on('click', function (e) {
        $('.formdanhgia').toggle(200);
    });

    $('#form-signin').validate({
        rules: {
            password: {
                required: true,
                minlength: 5,
            },
            email: {
                required: true,
                email: true,
            },
        },
        messages: {
            password: {
                required: 'Vui lòng nhập mật khẩu',
                minlength: 'Vui lòng nhập ít nhất 5 kí tự',
            },
            email: {
                required: 'Vui lòng nhập email',
                minlength: 'Email không hợp lệ',
                email: 'Vui lòng nhập email',
            },
        },
    });

    $('#form-signup-cart').validate({
        rules: {
            name: {
                required: true,
            },
            phone: {
                required: true,
                minlength: 8,
            },
            password: {
                required: true,
                minlength: 6,
            },
            confirm_password: {
                required: true,
                minlength: 6,
                equalTo: '#inputPassword',
            },
            email: {
                required: true,
                email: true,
            },
        },
        messages: {
            name: {
                required: 'Vui lòng nhập họ và tên',
            },
            phone: {
                required: 'Vui lòng nhập số điện thoại',
                minlength: 'Số máy quý khách vừa nhập là số không có thực',
            },
            password: {
                required: 'Vui lòng nhập mật khẩu',
                minlength: 'Vui lòng nhập ít nhất 6 kí tự',
            },
            confirm_password: {
                required: 'Vui lòng nhập lại mật khẩu',
                minlength: 'Vui lòng nhập ít nhất 6 kí tự',
                equalTo: 'Mật khẩu không trùng',
            },
            email: {
                required: 'Vui lòng nhập email',
                minlength: 'Email không hợp lệ',
                email: 'Vui lòng nhập email',
            },
        },
    });

    $('#form-signin-cart').validate({
        rules: {
            password: {
                required: true,
                minlength: 6,
            },
            email: {
                required: true,
                email: true,
            },
        },
        messages: {
            password: {
                required: 'Vui lòng nhập mật khẩu',
                minlength: 'Vui lòng nhập ít nhất 6 kí tự',
            },
            email: {
                required: 'Vui lòng nhập email',
                minlength: 'Email không hợp lệ',
                email: 'Vui lòng nhập email',
            },
        },
    });

    // add to cart

    let carts = document.querySelector('.nutmua');
    if (carts) {
        carts.addEventListener('click', () => {
            cartNumbers(product);
            totalCost(product);
        });
    }

    function cartNumbers(product) {
        let productNumbers = localStorage.getItem('cartNumbers');
        productNumbers = parseInt(productNumbers);

        if (productNumbers) {
            localStorage.setItem('cartNumbers', productNumbers + parseInt($('.soluongsp').val()));
            document.querySelector('.giohang .cart-amount').textContent =
                productNumbers + parseInt($('.soluongsp').val());
        } else {
            localStorage.setItem('cartNumbers', parseInt($('.soluongsp').val()));
            document.querySelector('.giohang .cart-amount').textContent = parseInt($('.soluongsp').val());
        }
        setItem(product);
    }

    function setItem(product) {
        let cartItems = localStorage.getItem('productsInCart');
        cartItems = JSON.parse(cartItems);

        if (cartItems != null) {
            if (cartItems[product.tag] == undefined) {
                cartItems = {
                    ...cartItems,
                    [product.tag]: product,
                };
            }
            cartItems[product.tag].inCart += parseInt($('.soluongsp').val());
        } else {
            product.inCart = parseInt($('.soluongsp').val());
            cartItems = {
                [product.tag]: product,
            };
        }

        localStorage.setItem('productsInCart', JSON.stringify(cartItems));
    }

    function totalCost(product) {
        let cartCost = localStorage.getItem('totalCost');

        if (cartCost != null) {
            cartCost = parseFloat(cartCost);
            localStorage.setItem('totalCost', cartCost + parseInt($('.soluongsp').val()) * product.price);
        } else {
            localStorage.setItem('totalCost', parseInt($('.soluongsp').val()) * product.price);
        }
    }

    $('.btn-checkout').on('click', function (e) {
        localStorage.clear();
        location.reload(true);
        swal({
            title: 'Thành công',
            text: 'Cảm ơn đã bạn mua hàng',
            icon: 'success',
        });
    });

    $('.items .row').isotope({
        itemSelector: '.item',
    });

    $('.tag a').on('click', function (e) {
        var tacgia = $(this).data('tacgia');

        if (tacgia == 'all') {
            $('.items .row').isotope({ filter: '*' });
        } else {
            $('.items .row').isotope({ filter: tacgia });
        }
        return false;
    });

    $('.thay-doi-mk').hide();
    $('#changepass').on('click', function (e) {
        $('.thay-doi-mk').toggle(200);
    });

    return (
        <>
            <ProductBlock homeOption={EHomeOption.NEW} />
            {/* <!-- khoi sach combo hot  --> */}
            <ProductBlock homeOption={EHomeOption.SALE} />
            {/* <!-- khoi sach sap phathanh  --> */}
            <ProductBlock homeOption={EHomeOption.POPULAR} />
            {/* <!-- div _1khoi -- khoi sachnendoc --> */}
            {/* <section className="_1khoi sachnendoc bg-white mt-4">
                <div className="container">
                    <div className="noidung" style={{ width: '100%' }}>
                        <div className="row">
                            <div className="col-12 d-flex justify-content-between align-items-center pb-2 bg-transparent pt-4">
                                <h2 className="header text-uppercase" style={{ fontWeight: '400' }}>
                                    SÁCH HAY NÊN ĐỌC
                                </h2>
                                <a href="#" className="btn btn-warning btn-sm text-white">
                                    Xem tất cả
                                </a>
                            </div>
                            <div className="col-lg col-sm-4">
                                <div className="card">
                                    <div
                                        href="#"
                                        className="motsanpham"
                                        style={{ textDecoration: 'none', color: 'black' }}
                                        datatoggle="tooltip"
                                        dataplacement="bottom"
                                        title="Từng bước chân nở hoa: Khi câu kinh bước tới"
                                    >
                                        <img
                                            className="card-img-top anh"
                                            src="images/tung-buoc-chan-no-hoa.jpg"
                                            alt="tung-buoc-chan-no-hoa"
                                        />
                                        <div className="card-body noidungsp mt-3">
                                            <h3 className="card-title ten">
                                                Từng bước chân nở hoa: Khi câu kinh bước tới
                                            </h3>
                                            <small className="thoigian text-muted">03/04/2020</small>
                                            <div>
                                                <a className="detail" href="#">
                                                    Xem chi tiết
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg col-sm-4">
                                <div className="card">
                                    <div
                                        href="#"
                                        className="motsanpham"
                                        style={{ textDecoration: 'none', color: 'black' }}
                                        datatoggle="tooltip"
                                        dataplacement="bottom"
                                        title="Cảm ơn vì đã được thương"
                                    >
                                        <img
                                            className="card-img-top anh"
                                            src="images/cam-on-vi-da-duoc-thuong.jpg"
                                            alt="cam-on-vi-da-duoc-thuong"
                                        />
                                        <div className="card-body noidungsp mt-3">
                                            <h3 className="card-title ten">Cảm ơn vì đã được thương</h3>
                                            <small className="thoigian text-muted">31/03/2020</small>
                                            <div>
                                                <a className="detail" href="#">
                                                    Xem chi tiết
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg col-sm-4">
                                <div className="card">
                                    <div
                                        href="#"
                                        className="motsanpham"
                                        style={{ textDecoration: 'none', color: 'black' }}
                                        datatoggle="tooltip"
                                        dataplacement="bottom"
                                        title="Hào quang của vua Gia Long trong mắt Michel Gaultier"
                                    >
                                        <img
                                            className="card-img-top anh"
                                            src="images/vua-gia-long.jpg"
                                            alt="vua-gia-long"
                                        />
                                        <div className="card-body noidungsp mt-3">
                                            <h3 className="card-title ten">
                                                Hào quang của vua Gia Long trong mắt Michel Gaultier
                                            </h3>
                                            <small className="thoigian text-muted">21/03/2020</small>
                                            <div>
                                                <a className="detail" href="#">
                                                    Xem chi tiết
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg col-sm-4">
                                <div className="card">
                                    <div
                                        href="#"
                                        className="motsanpham"
                                        style={{ textDecoration: 'none', color: 'black' }}
                                        datatoggle="tooltip"
                                        dataplacement="bottom"
                                        title="Suối nguồn” và cái tôi hiện sinh trong từng cá thể"
                                    >
                                        <img
                                            className="card-img-top anh"
                                            src="images/suoi-nguon-va-cai-toi-trong-tung-ca-the.jpg"
                                            alt="suoi-nguon-va-cai-toi-trong-tung-ca-the"
                                        />
                                        <div className="card-body noidungsp mt-3">
                                            <h3 className="card-title ten">
                                                "Suối nguồn” và cái tôi hiện sinh trong từng cá thể
                                            </h3>
                                            <small className="thoigian text-muted">16/03/2020</small>
                                            <div>
                                                <a className="detail" href="#">
                                                    Xem chi tiết
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg col-sm-4">
                                <div className="card cuoicung">
                                    <div
                                        href="#"
                                        className="motsanpham"
                                        style={{ textDecoration: 'none', color: 'black' }}
                                        datatoggle="tooltip"
                                        dataplacement="bottom"
                                        title="Đại dịch trên những con đường tơ lụa"
                                    >
                                        <img
                                            className="card-img-top anh"
                                            src="images/dai-dich-tren-con-duong-to-lua.jpg"
                                            alt="dai-dich-tren-con-duong-to-lua"
                                        />
                                        <div className="card-body noidungsp mt-3">
                                            <h3 className="card-title ten">Đại dịch trên những con đường tơ lụa</h3>
                                            <small className="thoigian text-muted">16/03/2020</small>
                                            <div>
                                                <a className="detail" href="#">
                                                    Xem chi tiết
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr />
                </div>
            </section> */}
        </>
    );
}
export default Home;
