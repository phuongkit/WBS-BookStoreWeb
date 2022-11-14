import React from 'react';
import './Banner.scss';

function Banner() {
    return (
        <>
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
                                    src="/images/banner-sach-moi.jpg"
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
                                    src="/images/banner-beethoven.jpg"
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
                                    src="/images/neu-toi-biet-duoc-khi-20-full-banner.jpg"
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
        </>
    );
}

export default Banner;
