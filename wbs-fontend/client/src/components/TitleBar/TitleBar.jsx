import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import './TitleBar.scss';
import { Menu } from '@Components/Menu';
import { Banner } from '@Components/Banner';

function TitleBar(props) {
    const { isShowBanner } = props;
    return (
        <>
            {isShowBanner && (
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
                                    <Menu />
                                </div>
                                <Banner />
                            </div>
                        </div>
                    </section>
                </>
            )}
            {!isShowBanner && (
                <>
                    <section class="duoinavbar">
                        <div class="container text-white">
                            <div class="row justify">
                                <div class="col-lg-3 col-md-5">
                                    <div class="categoryheader">
                                        <div class="noidungheader text-white">
                                            <i class="fa fa-bars"></i>
                                            <span class="text-uppercase font-weight-bold ml-1">Danh mục sách</span>
                                        </div>
                                        <Menu />
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-5 ml-auto contact d-none d-md-block">
                                <div class="benphai float-right">
                                    <div class="hotline">
                                        <i class="fa fa-phone"></i>
                                        <span>
                                            Hotline:<b>1900 1999</b>{' '}
                                        </span>
                                    </div>
                                    <i class="fas fa-comments-dollar"></i>
                                    <a href="#">Hỗ trợ trực tuyến </a>
                                </div>
                            </div>
                        </div>
                    </section>
                </>
            )}
        </>
    );
}

export default TitleBar;
