import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import $ from 'jquery';
import validate from 'jquery-validation';
import Popper from 'popper.js';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import '@fortawesome/fontawesome-free/css/all.min.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import { Link } from 'react-router-dom';
import './Home.scss';
import { TitleBar } from '@Components/TitleBar';
import { getAllCategoriesHierarchyApi } from '~/redux/category/categoriesApi';
import { ProductBlock} from '@Components/ProductBlock';
import { EHomeOption } from '~/utils';

var jQueryBridget = require('jquery-bridget');
var Isotope = require('isotope-layout'); 
jQueryBridget( 'isotope', Isotope, $ );

function Home({ title }) {
    const dispatch = useDispatch();
    getAllCategoriesHierarchyApi(dispatch);
    //hieu ung header va nut backtotop
    $('#backtotop').click(function () {
        $('html, body').animate({ scrollTop: 0 }, 400);
    });

    $(window).scroll(function () {
        if ($('body,html').scrollTop() > 150) {
            $('.navbar').addClass('fixed-top');
        } else {
            $('.navbar').removeClass('fixed-top');
        }
    });

    $(window).scroll(function () {
        if ($('body,html').scrollTop() > 500) {
            $('.nutcuonlen').addClass('hienthi');
        } else {
            $('.nutcuonlen').removeClass('hienthi');
        }
    });

    // header form dangnhap dangky
    $('.nutdangnhap').click(function (e) {
        $('ul.tabs .tab-dangnhap').addClass('active');
    });
    $('.nutdangky').click(function (e) {
        $('ul.tabs .tab-dangky').addClass('active');
    });

    $('ul.tabs .tab-dangnhap').click(function (e) {
        $('ul.tabs .tab-dangnhap').addClass('active');
        $('ul.tabs .tab-dangky').removeClass('active');
    });

    $('ul.tabs .tab-dangky').click(function (e) {
        $('ul.tabs .tab-dangky').addClass('active');
        $('ul.tabs .tab-dangnhap').removeClass('active');
    });

    // form dangnhap dangky
    $('.tab-dangky').click(function (e) {
        $('#formdangnhap').removeClass('fade');
        $('#formdangky').removeClass('fade');
        $('#formdangnhap').modal('hide');
        $('#formdangky').modal('show');
    });
    $('.tab-dangnhap').click(function (e) {
        $('#formdangnhap').removeClass('fade');
        $('#formdangky').removeClass('fade');
        $('#formdangky').modal('hide');
        $('#formdangnhap').modal('show');
    });
    $('.close').click(function (e) {
        $('.modal').addClass('fade');
        $('ul.tabs .tab-dangnhap').removeClass('active');
        $('ul.tabs .tab-dangky').removeClass('active');
    });

    $('.thumb-img').click(function (e) {
        $('.thumb-img:not(:hover)').removeClass('vienvang');
        $(this).addClass('vienvang');
    });

    //btn-spin
    $('.btn-inc').click(function (e) {
        var strval = $(this).parent().prev().val();
        var val = parseInt(strval) + 1;
        $(this).parent().prev().attr('value', val);
    });
    $('.btn-dec').click(function (e) {
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
    $('.vietdanhgia').click(function (e) {
        $('.formdanhgia').toggle(200);
    });

    //rotate chevron
    $('#step1contentid').on('show.bs.collapse', function () {
        $(this).prev().addClass('active');
    });
    $('#step1contentid').on('hide.bs.collapse', function () {
        $(this).prev().removeClass('active');
    });
    $('#step2contentid').on('show.bs.collapse', function () {
        $(this).prev().addClass('active');
    });
    $('#step2contentid').on('hide.bs.collapse', function () {
        $(this).prev().removeClass('active');
    });
    $('#step3contentid').on('show.bs.collapse', function () {
        $(this).prev().addClass('active');
    });
    $('#step3contentid').on('hide.bs.collapse', function () {
        $(this).prev().removeClass('active');
    });

    // nut btn-shopping-without-signup
    $('#step1contentid').toggle('show');
    $('.btn-shopping-without-signup').click(function (e) {
        $('#step1contentid').toggle('hide');
        $('#step2contentid').toggle ('show');
    });

    // validate
    $('#form-signup').validate({
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

    $('#form-signin').validate({
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

    function onLoadCartNumbers() {
        let productNumbers = localStorage.getItem('cartNumbers');
        if (productNumbers) {
            document.querySelector('.giohang .cart-amount').textContent = productNumbers;
        }
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

    function displayCart() {
        let cartItems = localStorage.getItem('productsInCart');
        cartItems = JSON.parse(cartItems);
        let cartContent = document.querySelector('.cart-content');
        let cartCost = localStorage.getItem('totalCost');
        let productNumbers = localStorage.getItem('cartNumbers');

        if (cartItems == null) {
            $('.cart-empty').removeClass('d-none');
            $('.cart').addClass('d-none');
            $('.cart-steps').addClass('d-none');
        }
        if (cartItems && cartContent) {
            $('.cart-empty').addClass('d-none');
            $('.cart').removeClass('d-none');
            $('.cart-steps').removeClass('d-none');

            cartContent.innerHTML = '';

            cartContent.innerHTML += `
            <h6 class="header-gio-hang">GIỎ HÀNG CỦA BẠN <span>(${productNumbers} sản phẩm)</span></h6>
            <div class="cart-list-items">
            `;
            Object.values(cartItems).map((item) => {
                cartContent.innerHTML += `
                    <div class="cart-item d-flex">
                        <a href="product-item.html" class="img">
                            <img src="images/${item.tag}.jpg" class="img-fluid" alt="${item.tag}">
                        </a>
                        <div class="item-caption d-flex w-100">
                            <div class="item-info ml-3">
                                <a href="product-item.html" class="ten">${item.name}</a>
                                <div class="soluong d-flex">
                                    <div class="input-number input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text btn-spin btn-dec">-</span>
                                        </div>
                                        <input type="text" value="${item.inCart}" class="soluongsp  text-center">
                                        <div class="input-group-append">
                                            <span class="input-group-text btn-spin btn-inc">+</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="item-price ml-auto d-flex flex-column align-items-end">
                                <div class="giamoi">${parseFloat(item.price).toFixed(3)} ₫</div>
                                <div class="giacu">${parseFloat(item.old_price).toFixed(3)} ₫</div>
                                <span class="remove mt-auto"><i class="far fa-trash-alt"></i></span>
                            </div>
                        </div>
                    </div>
                    <hr>
                `;
            });

            cartContent.innerHTML += `
            </div>

            <div class="row">
                <div class="col-md-3">
                    <a href="index.html" class="btn nutmuathem mb-3">Mua thêm</a>
                </div>
                <div class="col-md-5 offset-md-4">
                    <div class="tonggiatien">
                        <div class="group d-flex justify-content-between">
                            <p class="label">Tạm tính:</p>
                            <p class="tamtinh">${parseFloat(cartCost).toFixed(3)} ₫</p>
                        </div>
                        <div class="group d-flex justify-content-between">
                            <p class="label">Giảm giá:</p>
                            <p class="giamgia">0 ₫</p>
                        </div>
                        <div class="group d-flex justify-content-between">
                            <p class="label">Phí vận chuyển:</p>
                            <p class="phivanchuyen">0 ₫</p>
                        </div>
                        <div class="group d-flex justify-content-between">
                            <p class="label">Phí dịch vụ:</p>
                            <p class="phidicvu">0 ₫</p>
                        </div>
                        <div class="group d-flex justify-content-between align-items-center">
                            <strong class="text-uppercase">Tổng cộng:</strong>
                            <p class="tongcong">${parseFloat(cartCost).toFixed(3)} ₫</p>
                        </div>
                        <small class="note d-flex justify-content-end text-muted">
                            (Giá đã bao gồm VAT)
                        </small>
                    </div>
                </div>
            </div>
            `;
        }
    }

    $('.btn-checkout').click(function (e) {
        localStorage.clear();
        location.reload(true);
        alert('cảm ơn đã mua hàng');
    });

    onLoadCartNumbers();
    displayCart();

    $('.items .row').isotope({
        itemSelector: '.item',
    });

    $('.tag a').click(function (e) {
        var tacgia = $(this).data('tacgia');

        if (tacgia == 'all') {
            $('.items .row').isotope({ filter: '*' });
        } else {
            $('.items .row').isotope({ filter: tacgia });
        }
        return false;
    });

    $('.thay-doi-mk').hide();
    $('#changepass').click(function (e) {
        $('.thay-doi-mk').toggle(200);
    });

    return (
        <>
            <TitleBar isShowBanner/>
            <ProductBlock homeOption={EHomeOption.NEW}/>
            {/* <!-- khoi sach combo hot  --> */}
            <ProductBlock homeOption={EHomeOption.SALE}/>
            {/* <!-- khoi sach sap phathanh  --> */}
            <ProductBlock homeOption={EHomeOption.POPULAR}/>
            {/* <!-- div _1khoi -- khoi sachnendoc --> */}
            <section className="_1khoi sachnendoc bg-white mt-4">
                <div className="container">
                    <div className="noidung" style={{ width: '100%' }}>
                        <div className="row">
                            {/* <!--header--> */}
                            <div className="col-12 d-flex justify-content-between align-items-center pb-2 bg-transparent pt-4">
                                <h2 className="header text-uppercase" style={{ fontWeight: '400' }}>
                                    SÁCH HAY NÊN ĐỌC
                                </h2>
                                <a href="#" className="btn btn-warning btn-sm text-white">
                                    Xem tất cả
                                </a>
                            </div>
                            {/* <!-- 1 san pham --> */}
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
            </section>

            

            {/* <!-- nut cuon len dau trang --> */}
            <div className="fixed-bottom">
                <div
                    className="btn btn-warning float-right rounded-circle nutcuonlen"
                    id="backtotop"
                    href="#"
                    style={{ background: '#CF111A' }}
                >
                    <i className="fa fa-chevron-up text-white"></i>
                </div>
            </div>
        </>
    );
}
export default Home;
