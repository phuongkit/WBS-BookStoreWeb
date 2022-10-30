import React from 'react';
import $ from 'jquery';
import validate from 'jquery-validation';
import Popper from 'popper.js';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import Slider from 'react-slick';
import '@fortawesome/fontawesome-free/css/all.min.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import { Link } from 'react-router-dom';
import './Home.scss';

var jQueryBridget = require('jquery-bridget');
var Isotope = require('isotope-layout'); 
jQueryBridget( 'isotope', Isotope, $ );

import { Header } from '@Components/Header';

function Home({ title }) {
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

    // thumb-img
    $('.thumb-img.thumb1').addClass('vienvang');
    $('.thumb-img').click(function (e) {
        $('.product-image').attr('src', this.src);
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
    let product = {
        name: $('.khoithongtin .ten').text(),
        tag: $('.product-image').attr('alt'),
        price: parseFloat($('.gia span.giamoi').text()),
        old_price: parseFloat($('.gia span.giacu').text()),
        inCart: 0,
    };

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
            {/* <!-- thanh tieu de "danh muc sach" + hotline + ho tro truc tuyen --> */}
            <section className="duoinavbar">
                <div className="container text-white">
                    <div className="row justify">
                        <div className="col-md-3">
                            <div className="categoryheader">
                                <div className="noidungheader text-white">
                                    <i className="fa fa-bars"></i>
                                    <span className="text-uppercase font-weight-bold ml-1">Danh mục sách</span>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-9">
                            <div className="benphai float-right">
                                <div className="hotline">
                                    <i className="fa fa-phone"></i>
                                    <span>
                                        Hotline:<b>1900 1999</b>{' '}
                                    </span>
                                </div>
                                <i className="fas fa-comments-dollar"></i>
                                <a href="#">Hỗ trợ trực tuyến </a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            {/* <!-- noi dung danh muc sach(categories) + banner slider --> */}
            <section className="header bg-light">
                <div className="container">
                    <div className="row">
                        <div className="col-md-3" style={{ marginRight: '-15px' }}>
                            {/* <!-- CATEGORIES --> */}
                            <div className="categorycontent">
                                <ul>
                                    <li>
                                        {' '}
                                        <a href="sach-kinh-te.html"> Sách Kinh Tế - Kỹ Năng</a>
                                        <i className="fa fa-chevron-right float-right"></i>
                                        <ul>
                                            <li className="liheader">
                                                <a href="#" className="header text-uppercase">
                                                    Sách Kinh Tế - Kỹ Năng
                                                </a>
                                            </li>
                                            <div className="content trai">
                                                <li>
                                                    <a href="#">Kinh Tế - Chính Trị</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Khởi Nghiệp</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Tài Chính, Kế Toán</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Quản Trị Nhân Sự</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Kỹ Năng Làm Việc</a>
                                                </li>
                                            </div>
                                            <div className="content phai">
                                                <li>
                                                    <a href="#">Nhân Vật - Bài Học Kinh Doanh</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Quản Trị - Lãnh Đạo</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Kinh Tế Học</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Chứng Khoán - Bất Động Sản - Đầu Tư</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Marketing - Bán Hàng</a>
                                                </li>
                                            </div>
                                        </ul>
                                    </li>

                                    <li>
                                        <a href="#">Nghệ Thuật Sống - Tâm Lý </a>
                                        <i className="fa fa-chevron-right float-right"></i>
                                        <ul>
                                            <li className="liheader">
                                                <a href="#" className="header text-uppercase">
                                                    Nghệ Thuật Sống - Tâm Lý
                                                </a>
                                            </li>
                                            <div className="content trai">
                                                <li>
                                                    <a href="#">Sách Nghệ Thuật Sống</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Tâm Lý</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Hướng Nghiệp</a>
                                                </li>
                                            </div>
                                            <div className="content phai">
                                                <li>
                                                    <a href="#">Sách Nghệ Thuật Sống Đẹp</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Tư Duy </a>
                                                </li>
                                            </div>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#">Sách Văn Học Việt Nam</a>
                                        <i className="fa fa-chevron-right float-right"></i>
                                        <ul>
                                            <li className="liheader">
                                                <a href="#" className="header text-uppercase">
                                                    Sách Văn Học Việt Nam
                                                </a>
                                            </li>
                                            <div className="content trai">
                                                <li>
                                                    <a href="#">Truyện Ngắn - Tản Văn </a>
                                                </li>
                                                <li>
                                                    <a href="#">Tiểu Thuyết lịch Sử </a>
                                                </li>
                                                <li>
                                                    <a href="#">Phóng Sự - Ký Sự - Du ký - Tùy Bút</a>
                                                </li>
                                                <li>
                                                    <a href="#">Thơ</a>
                                                </li>
                                            </div>
                                            <div className="content phai">
                                                <li>
                                                    <a href="#">Tiểu thuyết</a>
                                                </li>
                                                <li>
                                                    <a href="#">Tiểu sử - Hồi ký</a>
                                                </li>
                                                <li>
                                                    <a href="#">Phê Bình Văn Học</a>
                                                </li>
                                            </div>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#">Sách Văn Học Nước Ngoài</a>
                                        <i className="fa fa-chevron-right float-right"></i>
                                        <ul>
                                            <li className="liheader">
                                                <a href="#" className="header text-uppercase">
                                                    Sách Văn Học Nước Ngoài
                                                </a>
                                            </li>
                                            <div className="content trai">
                                                <li>
                                                    <a href="#">Văn Học Hiện Đại</a>
                                                </li>
                                                <li>
                                                    <a href="#">Tiểu Thuyết </a>
                                                </li>
                                                <li>
                                                    <a href="#">Truyện Trinh Thám</a>
                                                </li>
                                                <li>
                                                    <a href="#">Thần Thoại - Cổ Tích</a>
                                                </li>
                                            </div>
                                            <div className="content phai">
                                                <li>
                                                    <a href="#">Văn Học Kinh Điển</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Giả Tưởng - Kinh Dị</a>
                                                </li>
                                                <li>
                                                    <a href="#">Truyện Kiếm Hiệp</a>
                                                </li>
                                            </div>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#">Sách Thiếu Nhi</a>
                                        <i className="fa fa-chevron-right float-right"></i>
                                        <ul>
                                            <li className="liheader">
                                                <a href="#" className="header text-uppercase">
                                                    Sách Thiếu Nhi
                                                </a>
                                            </li>
                                            <div className="content trai">
                                                <li>
                                                    <a href="#">Mẫu Giáo</a>
                                                </li>
                                                <li>
                                                    <a href="#">Thiếu Niên</a>
                                                </li>
                                                <li>
                                                    <a href="#">Kiến Thức - Bách Khoa</a>
                                                </li>
                                                <li>
                                                    <a href="#">Truyện Cổ Tích</a>
                                                </li>
                                            </div>
                                            <div className="content phai">
                                                <li>
                                                    <a href="#">Nhi Đồng</a>
                                                </li>
                                                <li>
                                                    <a href="#">Văn Học Thiếu Nhi</a>
                                                </li>
                                                <li>
                                                    <a href="#">Kỹ Năng Sống</a>
                                                </li>
                                                <li>
                                                    <a href="#">Truyện Tranh</a>
                                                </li>
                                            </div>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#">Sách Giáo Dục - Gia Đình</a>
                                        <i className="fa fa-chevron-right float-right"></i>
                                        <ul>
                                            <li className="liheader">
                                                <a href="#" className="header text-uppercase">
                                                    Sách Giáo Dục - Gia Đình
                                                </a>
                                            </li>
                                            <div className="content trai">
                                                <li>
                                                    <a href="#">Giáo dục</a>
                                                </li>
                                                <li>
                                                    <a href="#">Thai Giáo</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Dinh Dưỡng - Chăm Sóc Trẻ</a>
                                                </li>
                                                <li>
                                                    <a href="#">Ẩm Thực - Nấu Ăn</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Tham Khảo</a>
                                                </li>
                                            </div>
                                            <div className="content phai">
                                                <li>
                                                    <a href="#">Giới Tính</a>
                                                </li>
                                                <li>
                                                    <a href="#">Sách Làm Cha Mẹ</a>
                                                </li>
                                                <li>
                                                    <a href="#">Kiến Thức - Kỹ Năng Cho Trẻ</a>
                                                </li>
                                                <li>
                                                    <a href="#">Ngoại Ngữ - Từ Điển</a>
                                                </li>
                                            </div>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#">Sách Lịch Sử</a>
                                        <i className="fa fa-chevron-right float-right"></i>
                                        <ul>
                                            <li className="liheader">
                                                <a href="#" className="header text-uppercase">
                                                    Sách Lịch Sử
                                                </a>
                                            </li>
                                            <div className="content trai">
                                                <li>
                                                    <a href="#">Lịch Sử Việt Nam</a>
                                                </li>
                                            </div>
                                            <div className="content phai">
                                                <li>
                                                    <a href="#">Lịch Sử Thế Giới</a>
                                                </li>
                                            </div>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#">Sách Văn Hóa - Nghệ Thuật</a>
                                        <i className="fa fa-chevron-right float-right"></i>
                                        <ul>
                                            <li className="liheader">
                                                <a href="#" className="header text-uppercase">
                                                    Sách Văn Hóa - Nghệ Thuật
                                                </a>
                                            </li>
                                            <div className="content trai">
                                                <li>
                                                    <a href="#">Văn Hóa</a>
                                                </li>
                                                <li>
                                                    <a href="#">Phong Tục Tập Quán</a>
                                                </li>
                                                <li>
                                                    <a href="#">Phong Thủy</a>
                                                </li>
                                            </div>
                                            <div className="content phai">
                                                <li>
                                                    <a href="#">Nghệ Thuật</a>
                                                </li>
                                                <li>
                                                    <a href="#">Kiến Trúc</a>
                                                </li>
                                                <li>
                                                    <a href="#">Du Lịch</a>
                                                </li>
                                            </div>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#">Sách Khoa Học - Triết Học</a>
                                        <i className="fa fa-chevron-right float-right"></i>
                                        <ul>
                                            <li className="liheader">
                                                <a href="#" className="header text-uppercase">
                                                    Sách Khoa Học - Triết Học
                                                </a>
                                            </li>
                                            <div className="content trai">
                                                <li>
                                                    <a href="#">Triết Học Phương Tây</a>
                                                </li>
                                                <li>
                                                    <a href="#">Khoa Học Cơ Bản</a>
                                                </li>
                                            </div>
                                            <div className="content phai">
                                                <li>
                                                    <a href="#">Minh Tiết Phương Đông</a>
                                                </li>
                                            </div>
                                        </ul>
                                    </li>
                                    <li>
                                        <a href="#">Sách Tâm Linh - Tôn Giáo</a>
                                        <i className="fa fa-chevron-right float-right"></i>
                                    </li>
                                    <li>
                                        <a href="#">Sách Y Học - Thực Dưỡng</a>
                                        <i className="fa fa-chevron-right float-right"></i>
                                        <ul>
                                            <li className="liheader">
                                                <a href="#" className="header text-uppercase">
                                                    Sách Y Học - Thực Dưỡng
                                                </a>
                                            </li>
                                            <div className="content trai">
                                                <li>
                                                    <a href="#">Chăm Sóc Sức Khỏe</a>
                                                </li>
                                                <li>
                                                    <a href="#">Y Học</a>
                                                </li>
                                                <li>
                                                    <a href="#">Thiền - Yoga</a>
                                                </li>
                                            </div>
                                            <div className="content phai">
                                                <li>
                                                    <a href="#">Thực Dưỡng</a>
                                                </li>
                                                <li>
                                                    <a href="#">Đông Y - Cổ Truyền</a>
                                                </li>
                                            </div>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        {/* <!-- banner slider  --> */}
                        <div className="col-md-9 px-0">
                            <div id="carouselId" className="carousel slide" data-ride="carousel">
                                <ol className="nutcarousel carousel-indicators rounded-circle">
                                    <li datatarget="#carouselId" data-slide-to="0" className="active"></li>
                                    <li datatarget="#carouselId" data-slide-to="1"></li>
                                    <li datatarget="#carouselId" data-slide-to="2"></li>
                                </ol>
                                <div className="carousel-inner">
                                    <div className="carousel-item active">
                                        <a href="#">
                                            <img
                                                src="images/banner-sach-moi.jpg"
                                                className="img-fluid"
                                                style={{ height: '386px' }}
                                                width="900px"
                                                alt="First slide"
                                            />
                                        </a>
                                    </div>
                                    <div className="carousel-item">
                                        <a href="#">
                                            <img
                                                src="images/banner-beethoven.jpg"
                                                className="img-fluid"
                                                style={{ height: '386px' }}
                                                width="900px"
                                                alt="Second slide"
                                            />
                                        </a>
                                    </div>
                                    <div className="carousel-item">
                                        <a href="#">
                                            <img
                                                src="images/neu-toi-biet-duoc-khi-20-full-banner.jpg"
                                                className="img-fluid"
                                                style={{ height: '386px' }}
                                                alt="Third slide"
                                            />
                                        </a>
                                    </div>
                                </div>
                                <a className="carousel-control-prev" href="#carouselId" data-slide="prev">
                                    <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span className="sr-only">Previous</span>
                                </a>
                                <a className="carousel-control-next" href="#carouselId" data-slide="next">
                                    <span className="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span className="sr-only">Next</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            {/* <!-- khoi sach moi  --> */}
            <section className="_1khoi sachmoi bg-white">
                <div className="container">
                    <div className="noidung" style={{ width: '100%' }}>
                        <div className="row">
                            {/* <!--header--> */}
                            <div className="col-12 d-flex justify-content-between align-items-center pb-2 bg-transparent pt-4">
                                <h1 className="header text-uppercase" style={{ fontWeight: '400' }}>
                                    SÁCH MỚI TUYỂN CHỌN
                                </h1>
                                <a href="sach-moi-tuyen-chon.html" className="btn btn-warning btn-sm text-white">
                                    Xem tất cả
                                </a>
                            </div>
                        </div>
                        <Slider
                            dots={false}
                            speed={300}
                            slidesToShow={5}
                            slidesToScroll={1}
                            responsive={[
                                {
                                    breakpoint: 1400,
                                    settings: {
                                        slidesToShow: 3,
                                        slidesToScroll: 1,
                                        infinite: true,
                                        dots: true,
                                    },
                                },
                                {
                                    breakpoint: 800,
                                    settings: {
                                        slidesToShow: 2,
                                        slidesToScroll: 1,
                                    },
                                },
                                {
                                    breakpoint: 480,
                                    settings: {
                                        slidesToShow: 1,
                                        slidesToScroll: 1,
                                    },
                                },
                            ]}
                            className="khoisanpham"
                            style={{ paddingBottom: '2rem' }}
                        >
                            {/* <!-- 1 san pham --> */}
                            <div className="card">
                                <a
                                    href="Lap-trinh-ke-hoach-kinh-doanh-hieu-qua.html"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Lập Kế Hoạch Kinh Doanh Hiệu Quả"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/lap-ke-hoach-kinh-doanh-hieu-qua.jpg"
                                        alt="lap-ke-hoach-kinh-doanh-hieu-qua"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Lập Kế Hoạch Kinh Doanh Hiệu Quả</h3>
                                        <small className="tacgia text-muted">Brian Finch</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </a>
                            </div>
                            <div className="card">
                                <a
                                    href="Ma-bun-luu-manh-va-nhung-cau-chuyen-khac-cua-nguyen-tri.html"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Ma Bùn Lưu Manh Và Những Câu Chuyện Khác Của Nguyễn
                            Trí"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/ma-bun-luu-manh.jpg"
                                        alt="ma-bun-luu-manh"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">
                                            Ma Bùn Lưu Manh Và Những Câu Chuyện Khác Của Nguyễn Trí
                                        </h3>
                                        <small className="tacgia text-muted">Nguyễn Trí</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">68.000 ₫</div>
                                            <div className="giacu text-muted">85.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </a>
                            </div>
                            <div className="card">
                                <div
                                    link="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Bank 4.0 - Giao dịch mọi nơi, không chỉ là ngân hàng"
                                >
                                    <img className="card-img-top anh" src="images/bank-4.0.jpg" alt="bank-4.0" />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">
                                            Bank 4.0 - Giao dịch mọi nơi, không chỉ là ngân hàng
                                        </h3>
                                        <small className="tacgia text-muted">Brett King</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Bộ Sách 500 Câu Chuyện Đạo Đức - Những Câu Chuyện
                            Tình Thân (Bộ 8 Cuốn)"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/bo-sach-500-cau-chuyen-dao-duc.jpg"
                                        alt="bo-sach-500-cau-chuyen-dao-duc"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">
                                            Bộ Sách 500 Câu Chuyện Đạo Đức - Những Câu Chuyện Tình Thân (Bộ 8 Cuốn)
                                        </h3>
                                        <small className="tacgia text-muted">Nguyễn Hạnh - Trần Thị Thanh Nguyên</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Lịch Sử Ung Thư - Hoàng Đế Của Bách Bệnh"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/ung-thu-hoang-de-cua-bach-benh.jpg"
                                        alt="ung-thu-hoang-de-cua-bach-benh"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Lịch Sử Ung Thư - Hoàng Đế Của Bách Bệnh</h3>
                                        <small className="tacgia text-muted">Siddhartha Mukherjee</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Cuốn Sách Khám Phá: Trời Đêm Huyền Diệu"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/troi-dem-huyen-dieu.jpg"
                                        alt="troi-dem-huyen-dieu"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Cuốn Sách Khám Phá: Trời Đêm Huyền Diệu</h3>
                                        <small className="tacgia text-muted">Disney Learning</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Bộ Sách Những Câu Chuyện Cho Con Thành Người Tử Tế (Bộ 5 Cuốn)"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/bo-sach-nhung-cau-chuyen-cho-con-thanh-nguoi-tu-te.jpg"
                                        alt="bo-sach-nhung-cau-chuyen-cho-con-thanh-nguoi-tu-te"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">
                                            Bộ Sách Những Câu Chuyện Cho Con Thành Người Tử Tế (Bộ 5 Cuốn)
                                        </h3>
                                        <small className="tacgia text-muted">Nhiều Tác Giả</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Lịch Sử Thế Giới"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/lich-su-the-gioi.jpg"
                                        alt="lich-su-the-gioi"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Lịch Sử Thế Giới</h3>
                                        <small className="tacgia text-muted">
                                            Nam Phong tùng thư - Phạm Quỳnh chủ nhiệm
                                        </small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Slider>
                    </div>
                </div>
            </section>

            {/* <!-- khoi sach combo hot  --> */}
            <section className="_1khoi combohot mt-4">
                <div className="container">
                    <div className="noidung bg-white" style={{ width: '100%' }}>
                        <div className="row">
                            {/* <!--header --> */}
                            <div className="col-12 d-flex justify-content-between align-items-center pb-2 bg-light">
                                <h2 className="header text-uppercase" style={{ fontWeight: '400' }}>
                                    COMBO SÁCH HOT - GIẢM ĐẾN 25%
                                </h2>
                                <a href="#" className="btn btn-warning btn-sm text-white">
                                    Xem tất cả
                                </a>
                            </div>
                        </div>
                        <Slider Slider
                            dots={false}
                            speed={300}
                            slidesToShow={5}
                            slidesToScroll={1}
                            responsive={[
                                {
                                    breakpoint: 1400,
                                    settings: {
                                        slidesToShow: 3,
                                        slidesToScroll: 1,
                                        infinite: true,
                                        dots: true,
                                    },
                                },
                                {
                                    breakpoint: 800,
                                    settings: {
                                        slidesToShow: 2,
                                        slidesToScroll: 1,
                                    },
                                },
                                {
                                    breakpoint: 480,
                                    settings: {
                                        slidesToShow: 1,
                                        slidesToScroll: 1,
                                    },
                                },
                            ]} className="khoisanpham">
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Chuyện Nghề Và Chuyện Đời - Bộ 4 Cuốn"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/combo-chuyen-nghe-chuyen-doi.jpg"
                                        alt="combo-chuyen-nghe-chuyen-doi"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Chuyện Nghề Và Chuyện Đời - Bộ 4 Cuốn</h3>
                                        <small className="tacgia text-muted">Nguyễn Hữu Long</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Combo Mẹ Con Sư Tử - Bồ Tát Ngàn Tay Ngàn Mắt"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/combo-me-con-su-tu-bo-tat-ngan-tay-ngan-mat.jpg"
                                        alt="combo-me-con-su-tu-bo-tat-ngan-tay-ngan-mat"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">
                                            Combo Mẹ Con Sư Tử - Bồ Tát Ngàn Tay Ngàn Mắt
                                        </h3>
                                        <small className="tacgia text-muted">Thích Nhất Hạnh</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Combo Osho: Hạnh Phúc Tại Tâm, Can Đảm Biến Thách Thức Thành
                                Sức Mạnh & Sáng Tạo Bừng Cháy Sức Mạnh Bên Trong"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/combo-hanh-phuc-sang-tao.jpg"
                                        alt="combo-hanh-phuc-sang-tao"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">
                                            Combo Osho: Hạnh Phúc Tại Tâm, Can Đảm Biến Thách Thức Thành Sức Mạnh & Sáng
                                            Tạo Bừng Cháy Sức Mạnh Bên Trong
                                        </h3>
                                        <small className="tacgia text-muted">
                                            Gosho Aoyama, Mutsuki Watanabe, Takahisa Taira
                                        </small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Combo Giáo Dục Và Ý Nghĩa Cuộc Sống Và Bạn Đang Nghịch Gì Với Đời Mình?"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/combo-giao-duc-va-y-nghia-cuoc-song.jpg"
                                        alt="combo-giao-duc-va-y-nghia-cuoc-song"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">
                                            Combo Giáo Dục Và Ý Nghĩa Cuộc Sống Và Bạn Đang Nghịch Gì Với Đời Mình?
                                        </h3>
                                        <small className="tacgia text-muted"> J.Krishnamurti</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Combo Dinh Dưỡng Xanh - Thần dược xanh"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/combo-dinh-duong-than-duoc-xanh.jpg"
                                        alt="combo-dinh-duong-than-duoc-xanh"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Combo Dinh Dưỡng Xanh - Thần dược xanh</h3>
                                        <small className="tacgia text-muted">Ryu Seung-SunVictoria Boutenko</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Combo Ăn Xanh Để Khỏe - Sống Lành Để Trẻ"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/combo-an-xanh-song-lanh.jpg"
                                        alt="combo-an-xanh-song-lanh"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Combo Ăn Xanh Để Khỏe - Sống Lành Để Trẻ</h3>
                                        <small className="tacgia text-muted">Norman W. Walker</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Combo Lược Sử Loài Người - Lược Sử Tương Lai - 21 Bài Học Cho Thế Kỷ 21"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/combo-luoc-su-loai-nguoi.jpg"
                                        alt="combo-luoc-su-loai-nguoi"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">
                                            Combo Lược Sử Loài Người - Lược Sử Tương Lai - 21 Bài Học Cho Thế Kỷ 21
                                        </h3>
                                        <small className="tacgia text-muted">Yuval Noah Harari</small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Bộ Sách Phong Cách Sống (Bộ 5 Cuốn)"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/combo-phong-cach-song.jpg"
                                        alt="combo-phong-cach-song"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Bộ Sách Phong Cách Sống (Bộ 5 Cuốn)</h3>
                                        <small className="tacgia text-muted">
                                            Marie Tourell Soderberg, Joanna Nylund, Yukari Mitsuhashi, Margareta
                                            Magnusson, Linnea Dunne
                                        </small>
                                        <div className="gia d-flex align-items-baseline">
                                            <div className="giamoi">111.200 ₫</div>
                                            <div className="giacu text-muted">139.000 ₫</div>
                                            <div className="sale">-20%</div>
                                        </div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Slider>
                    </div>
                </div>
            </section>

            {/* <!-- khoi sach sap phathanh  --> */}
            <section className="_1khoi sapphathanh mt-4">
                <div className="container">
                    <div className="noidung bg-white" style={{ width: '100%' }}>
                        <div className="row">
                            {/* <!--header--> */}
                            <div className="col-12 d-flex justify-content-between align-items-center pb-2 bg-light">
                                <h2 className="header text-uppercase" style={{ fontWeight: '400' }}>
                                    SÁCH SẮP PHÁT HÀNH / ĐẶT TRƯỚC
                                </h2>
                                <a href="#" className="btn btn-warning btn-sm text-white">
                                    Xem tất cả
                                </a>
                            </div>
                        </div>
                        <Slider
                            dots={false}
                            speed={300}
                            slidesToShow={5}
                            slidesToScroll={1}
                            responsive={[
                                {
                                    breakpoint: 1400,
                                    settings: {
                                        slidesToShow: 3,
                                        slidesToScroll: 1,
                                        infinite: true,
                                        dots: true,
                                    },
                                },
                                {
                                    breakpoint: 800,
                                    settings: {
                                        slidesToShow: 2,
                                        slidesToScroll: 1,
                                    },
                                },
                                {
                                    breakpoint: 480,
                                    settings: {
                                        slidesToShow: 1,
                                        slidesToScroll: 1,
                                    },
                                },
                            ]} className="khoisanpham">
                            {/* <!-- 1 san pham --> */}
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Ngoại Giao Của Chính Quyền Sài Gòn"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/ngoai-giao-cua-chinh-quyen-sai-gon.jpg"
                                        alt="ngoai-giao-cua-chinh-quyen-sai-gon"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Ngoại Giao Của Chính Quyền Sài Gòn</h3>
                                        <small className="tacgia text-muted">Brian Finch</small>
                                        <div className="gia d-flex align-items-baseline"></div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Đường Mây Trên Đất Hoa"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/duong-may-tren-dat-hoa.jpg"
                                        alt="duong-may-tren-dat-hoa"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Đường Mây Trên Đất Hoa</h3>
                                        <small className="tacgia text-muted">Brian Finch</small>
                                        <div className="gia d-flex align-items-baseline"></div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Muôn Kiếp Nhân Sinh"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/muon-kiep-nhan-sinh.jpg"
                                        alt="muon-kiep-nhan-sinh"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Muôn Kiếp Nhân Sinh</h3>
                                        <small className="tacgia text-muted">Brian Finch</small>
                                        <div className="gia d-flex align-items-baseline"></div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="card">
                                <div
                                    href="#"
                                    className="motsanpham"
                                    style={{ textDecoration: 'none', color: 'black' }}
                                    datatoggle="tooltip"
                                    dataplacement="bottom"
                                    title="Đường Mây Trong Cõi Mộng"
                                >
                                    <img
                                        className="card-img-top anh"
                                        src="images/duong-may-trong-coi-mong.jpg"
                                        alt="duong-may-trong-coi-mong.jpg"
                                    />
                                    <div className="card-body noidungsp mt-3">
                                        <h3 className="card-title ten">Đường Mây Trong Cõi Mộng</h3>
                                        <small className="tacgia text-muted">Brian Finch</small>
                                        <div className="gia d-flex align-items-baseline"></div>
                                        <div className="danhgia">
                                            <ul className="d-flex" style={{ listStyle: 'none' }}>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li className="active">
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <i className="fa fa-star"></i>
                                                </li>
                                                <li>
                                                    <span className="text-muted">0 nhận xét</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Slider>
                    </div>
                </div>
            </section>

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

            {/* <!-- thanh cac dich vu :mien phi giao hang, qua tang mien phi ........ --> */}
            <section className="abovefooter text-white" style={{ backgroundColor: '#CF111A' }}>
                <div className="container">
                    <div className="row">
                        <div className="col-lg-3 col-sm-6">
                            <div className="dichvu d-flex align-items-center">
                                <img src="images/icon-books.png" alt="icon-books" />
                                <div className="noidung">
                                    <h3 className="tieude font-weight-bold">HƠN 14.000 TỰA SÁCH HAY</h3>
                                    <p className="detail">Tuyển chọn bởi DealBooks</p>
                                </div>
                            </div>
                        </div>
                        <div className="col-lg-3 col-sm-6">
                            <div className="dichvu d-flex align-items-center">
                                <img src="images/icon-ship.png" alt="icon-ship" />
                                <div className="noidung">
                                    <h3 className="tieude font-weight-bold">MIỄN PHÍ GIAO HÀNG</h3>
                                    <p className="detail">Từ 150.000đ ở TP.HCM</p>
                                    <p className="detail">Từ 300.000đ ở tỉnh thành khác</p>
                                </div>
                            </div>
                        </div>
                        <div className="col-lg-3 col-sm-6">
                            <div className="dichvu d-flex align-items-center">
                                <img src="images/icon-gift.png" alt="icon-gift" />
                                <div className="noidung">
                                    <h3 className="tieude font-weight-bold">QUÀ TẶNG MIỄN PHÍ</h3>
                                    <p className="detail">Tặng Bookmark</p>
                                    <p className="detail">Bao sách miễn phí</p>
                                </div>
                            </div>
                        </div>
                        <div className="col-lg-3 col-sm-6">
                            <div className="dichvu d-flex align-items-center">
                                <img src="images/icon-return.png" alt="icon-return" />
                                <div className="noidung">
                                    <h3 className="tieude font-weight-bold">ĐỔI TRẢ NHANH CHÓNG</h3>
                                    <p className="detail">Hàng bị lỗi được đổi trả nhanh chóng</p>
                                </div>
                            </div>
                        </div>
                    </div>
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
