import React from 'react';
import './TitleBar.scss';
import Menu from '../Menu';
import Banner from '../Banner';

function TitleBar(props) {
    const { isShowBanner } = props;
    console.log(isShowBanner);
    return (
        <>
            {isShowBanner ? (
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
                                    <Menu/>
                                </div>
                                <Banner />
                            </div>
                        </div>
                    </section>
                </>
            ) : (
                <>
                    <section className="duoinavbar">
                        <div className="container text-white">
                            <div className="row justify">
                                <div className="col-lg-3 col-md-5">
                                    <div className="categoryheader">
                                        <div className="noidungheader text-white">
                                            <i className="fa fa-bars"></i>
                                            <span className="text-uppercase font-weight-bold ml-1">Danh mục sách</span>
                                        </div>
                                        <Menu/>
                                    </div>
                                </div>
                                <div className="col-md-5 ml-auto contact d-none d-md-block">
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
                </>
            )}
        </>
    );
}

export default TitleBar;
