import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { solid } from '@fortawesome/fontawesome-svg-core/import.macro';
import { SignInForm } from '../SignInForm';
import { SignUpForm } from '../SignUpForm';
import './Header.scss';
import TitleBar from '../TitleBar';
import { useEffect } from 'react';
import { getUserByToken, postLogout } from '../../redux/user/userApi';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { checkRegex, getName, MESSAGE } from '../../utils';
import { postLogin } from '../../redux/user/userApi';
import { getAllCategoriesHierarchyApi } from '../../redux/category/categoriesApi';

var $ = require('jquery');

function Header() {
    const location = useLocation();
    const isShowBanner = location.pathname === '/';
    const navigate = useNavigate();
    const dispatch = useDispatch();
    getAllCategoriesHierarchyApi(dispatch);
    const token = localStorage.getItem('token');
    const cartAmount =
        // token ? 0 :
        localStorage.getItem('cartItems') ? JSON.parse(localStorage.getItem('cartItems'))?.length : 0;
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

    const handleLogout = async () => {
        postLogout(dispatch, navigate);
    };

    const handleLogin = (data) => {
        let regexType = checkRegex(data.loginUserName);
        let auth = {
            otp: data.otp,
            password: data.password,
        };
        if (regexType === 0) {
            auth.phone = data.loginUserName;
        } else if (regexType === 1) {
            auth.email = data.loginUserName;
        } else {
            alert(MESSAGE.LOGIN_USER_NAME_NOT_MATCH);
            return;
        }
        postLogin(dispatch, auth, navigate);
        $('.modal').addClass('fade');
        $('#formdangnhap').modal('hide');
        $('#formdangky').modal('hide');
        $('ul.tabs .tab-dangnhap').removeClass('active');
        $('ul.tabs .tab-dangky').removeClass('active');
    };

    // header form dangnhap dangky
    $('.nutdangnhap').on('click', function (e) {
        $('ul.tabs .tab-dangnhap').addClass('active');
    });
    $('.nutdangky').on('click', function (e) {
        $('ul.tabs .tab-dangky').addClass('active');
    });

    $('ul.tabs .tab-dangnhap').on('click', function (e) {
        $('ul.tabs .tab-dangnhap').addClass('active');
        $('ul.tabs .tab-dangky').removeClass('active');
    });

    $('ul.tabs .tab-dangky').on('click', function (e) {
        $('ul.tabs .tab-dangky').addClass('active');
        $('ul.tabs .tab-dangnhap').removeClass('active');
    });

    // form dangnhap dangky
    $('.tab-dangky,.nutdangky').on('click', function (e) {
        $('#formdangnhap').removeClass('fade');
        $('#formdangky').removeClass('fade');
        $('#formdangnhap').modal('hide');
        $('#formdangky').modal('show');
        $('.dropdown-menu').removeClass('show');
    });
    $('.tab-dangnhap,.nutdangnhap').on('click', function (e) {
        $('#formdangnhap').removeClass('fade');
        $('#formdangky').removeClass('fade');
        $('#formdangky').modal('hide');
        $('#formdangnhap').modal('show');
        $('.dropdown-menu').removeClass('show');
    });
    $('.close').on('click', function (e) {
        $('.modal').addClass('fade');
        $('ul.tabs .tab-dangnhap').removeClass('active');
        $('ul.tabs .tab-dangky').removeClass('active');
    });

    $('.thumb-img').on('click', function (e) {
        $('.thumb-img:not(:hover)').removeClass('vienvang');
        $(this).addClass('vienvang');
    });
    return (
        <>
            {/* <!-- code cho nut like share facebook  --> */}
            <div id="fb-root"></div>
            {/* <script async defer crossorigin="anonymous"
            src="https://connect.facebook.net/vi_VN/sdk.js#xfbml=1&version=v6.0"></script> */}

            {/* <!-- header --> */}
            <nav className="navbar navbar-expand-md bg-white navbar-light">
                <div className="container">
                    {/* <!-- logo  --> */}
                    <Link className="navbar-brand" to="" style={{ color: '#CF111A' }}>
                        <b>DealBook</b>.xyz
                    </Link>

                    {/* <!-- navbar-toggler  --> */}
                    <button
                        className="navbar-toggler d-lg-none"
                        type="button"
                        datatoggle="collapse"
                        datatarget="#collapsibleNavId"
                        aria-controls="collapsibleNavId"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                    >
                        <span className="navbar-toggler-icon"></span>
                    </button>

                    <div className="collapse navbar-collapse" id="collapsibleNavId">
                        {/* <!-- form tìm kiếm  --> */}
                        <form className="form-inline ml-auto my-2 my-lg-0 mr-3">
                            <div className="input-group" style={{ width: '520px' }}>
                                <input
                                id="keyword"
                                    type="text"
                                    className="form-control"
                                    aria-label="Small"
                                    placeholder="Nhập sách cần tìm kiếm..."
                                    onKeyDown={(e) => {
                                        e.preventDefault;
                                        if (e.key === 'Enter') {
                                            let keyword = e.target.value;
                                            navigate(`/search?keyword=${keyword}`);
                                        }
                                    }}
                                />
                                <div className="input-group-append">
                                    <button
                                        type="button"
                                        className="btn"
                                        style={{ backgroundColor: '#CF111A', color: 'white' }}
                                        onClick={() => {
                                            let keyword = $('#keyword').val();
                                            navigate(`/search?keyword=${keyword}`);
                                        }}
                                    >
                                        <FontAwesomeIcon icon={solid('search')} />
                                    </button>
                                </div>
                            </div>
                        </form>

                        {/* <!-- ô đăng nhập đăng ký giỏ hàng trên header  --> */}
                        <ul className="navbar-nav mb-1 ml-auto">
                            <div className="dropdown">
                                {user ? (
                                    <>
                                        <li
                                            className="nav-item account d-flex btn dropdown"
                                            type="button"
                                            // data-toggle="dropdown"
                                        >
                                            <a href="/account" className="btn btn-secondary rounded-circle">
                                                <FontAwesomeIcon icon={solid('user')} />
                                            </a>
                                            <div className="info-logout">
                                                <a
                                                    className="nav-link text-dark text-uppercase username"
                                                    href="/account"
                                                >
                                                    {getName(user?.username)}
                                                </a>
                                                {/* <a className="nav-link text-dark logout" href="#">
                                                    Thoát
                                                    <FontAwesomeIcon icon={solid('sign-out-alt')} />
                                                </a> */}
                                            </div>
                                            <div className="dropdown-menu">
                                                <Link className="dropdown-item text-center mb-2" to="/account">
                                                    Tài khoản của tôi
                                                </Link>
                                                <Link className="dropdown-item text-center mb-2" to="/purchase">
                                                    Đơn mua
                                                </Link>
                                                <Link className="dropdown-item text-center" onClick={handleLogout}>
                                                    Đăng xuất
                                                </Link>
                                            </div>
                                        </li>
                                    </>
                                ) : (
                                    <>
                                        <li
                                            className="nav-item account btn dropdown"
                                            type="button"
                                            onClick={() => {
                                                let dropdownMenu = document.getElementsByClassName('dropdown-menu')[0];
                                                dropdownMenu.classList.toggle('show');
                                            }}
                                        >
                                            <a href="#" className="btn btn-secondary rounded-circle">
                                                <FontAwesomeIcon icon={solid('user')} />
                                            </a>
                                            <div
                                                className="nav-link text-dark text-uppercase"
                                                style={{ display: 'inline-block', marginLeft: '1px' }}
                                            >
                                                Tài khoản
                                            </div>
                                        </li>
                                        <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                            <a
                                                className="dropdown-item nutdangky text-center mb-2"
                                                href="#"
                                                datatoggle="modal"
                                                datatarget="#formdangky"
                                            >
                                                Đăng ký
                                            </a>
                                            <a
                                                className="dropdown-item nutdangnhap text-center"
                                                href="#"
                                                datatoggle="modal"
                                                datatarget="#formdangnhap"
                                            >
                                                Đăng nhập
                                            </a>
                                        </div>
                                    </>
                                )}
                            </div>
                            <li className="nav-item giohang tw-mt-[7px]">
                                <Link to={'/cart'} className="btn btn-secondary rounded-circle">
                                    {/* <i className="fa fa-shopping-cart"></i> */}
                                    <FontAwesomeIcon icon={solid('shopping-cart')} className="fa fa-shopping-cart" />
                                    <div className="cart-amount">{cartAmount || 0}</div>
                                </Link>
                                <Link
                                    to={'/cart'}
                                    className="nav-link text-dark giohang text-uppercase"
                                    style={{ display: 'inline-block', marginLeft: '1px' }}
                                >
                                    Giỏ Hàng
                                </Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <SignUpForm />
            <SignInForm handleSubmit={handleLogin} />
            <TitleBar isShowBanner={isShowBanner} />
        </>
    );
}

export default Header;
