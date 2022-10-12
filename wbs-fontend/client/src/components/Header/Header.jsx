import React from 'react';
import './Header.scss'

import {SignInForm} from '../SignInForm';
import {SignUpForm} from '../SignUpForm';

function Header() {
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
                <a className="navbar-brand" href="" style={{color: "#CF111A"}}><b>DealBook</b>.xyz</a>
    
                {/* <!-- navbar-toggler  --> */}
                <button className="navbar-toggler d-lg-none" type="button" datatoggle="collapse"
                    datatarget="#collapsibleNavId" aria-controls="collapsibleNavId" aria-expanded="false"
                    aria-label="Toggle navigation"><span className="navbar-toggler-icon"></span></button>
    
                <div className="collapse navbar-collapse" id="collapsibleNavId">
                    {/* <!-- form tìm kiếm  --> */}
                    <form className="form-inline ml-auto my-2 my-lg-0 mr-3">
                        <div className="input-group" style={{width: "520px"}}>
                            <input type="text" className="form-control" aria-label="Small"
                                placeholder="Nhập sách cần tìm kiếm..."/>
                            <div className="input-group-append">
                                <button type="button" className="btn" style={{backgroundColor: "#CF111A", color: "white"}}>
                                    <i className="fa fa-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
    
                    {/* <!-- ô đăng nhập đăng ký giỏ hàng trên header  --> */}
                    <ul className="navbar-nav mb-1 ml-auto">
                        <div className="dropdown">
                            <li className="nav-item account btn dropdown" type="button" datatoggle="dropdown">
                                <a href="#" className="btn btn-secondary rounded-circle">
                                    <i className="fa fa-user"></i>
                                </a>
                                <a className="nav-link text-dark text-uppercase" href="#" style={{display:"inline-block"}}>Tài
                                    khoản</a>
                            </li>
                            <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <a className="dropdown-item nutdangky text-center mb-2" href="#" datatoggle="modal"
                                    datatarget="#formdangky">Đăng ký</a>
                                <a className="dropdown-item nutdangnhap text-center" href="#" datatoggle="modal"
                                    datatarget="#formdangnhap">Đăng nhập</a>
                            </div>
                        </div>
                        <li className="nav-item giohang">
                            <a href="gio-hang.html" className="btn btn-secondary rounded-circle">
                                <i className="fa fa-shopping-cart"></i>
                                <div className="cart-amount">0</div>
                            </a>
                            <a className="nav-link text-dark giohang text-uppercase" href="gio-hang.html"
                                style={{display:"inline-block"}}>Giỏ
                                Hàng</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <SignUpForm />
        <SignInForm />
        </>
    )
}

export default Header;