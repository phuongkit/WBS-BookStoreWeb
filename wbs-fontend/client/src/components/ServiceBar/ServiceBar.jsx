import React from 'react';
import './ServiceBar.scss';

function ServiceBar(props) {
    return (
        <>
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
        </>
    );
}

export default ServiceBar;
