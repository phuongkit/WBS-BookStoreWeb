import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import $ from 'jquery';
import validate from 'jquery-validation';
import './ProductDetail.scss';
import { ProductBlock } from '@Components/ProductBlock';
import { EHomeOption } from '~/utils';
import Parameter from './Parameter';
import { getNameBook } from '../../../utils';
import { EBookLayout } from '../../../utils';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheck } from '@fortawesome/free-solid-svg-icons';
import { addItem } from '../../../redux/shopping-cart/cartItemsSlide';
import { useState } from 'react';

var jQueryBridget = require('jquery-bridget');
var Isotope = require('isotope-layout');
jQueryBridget('isotope', Isotope, $);

function ProductDetail(props) {
    const dispatch = useDispatch();
    const [quantity, setQuantity] = useState(1);
    // thumb-img
    $('.thumb-img.thumb1').addClass('vienvang');
    $('.thumb-img').on("click", function (e) {
        $('.product-image').attr('src', this.src);
    });

    let product = {
        name: $('.khoithongtin .ten').text(),
        tag: $('.product-image').attr('alt'),
        price: parseFloat($('.gia span.giamoi').text()),
        old_price: parseFloat($('.gia span.giacu').text()),
        inCart: 0,
    };

    const handletAddToCart = async () => {
        let result = document.getElementById('result');
        let status = document.getElementById('status');
        result.style.display = 'block';
        status.classList.add('success');
        let quantity = document.getElementById('quantity').value || 0;
        if (!localStorage.getItem('accessToken')) {
            const data = { ...initialProductDetail, quantity: Number.parseInt(quantity) };
            dispatch(addItem(data));
        }
        setTimeout(function () {
            result.style.display = 'none';
        }, 1500);
    };

    const initialProductDetail = useSelector((state) => state.products.productDetail.data);

    const Description = () => {
        return <div dangerouslySetInnerHTML={{ __html: initialProductDetail.description }} />;
    };

    return (
        <>
            {/* <!-- nội dung của trang  --> */}
            <section className="product-page mb-4">
                <div className="container">
                    {/* <!-- chi tiết 1 sản phẩm --> */}
                    <div className="product-detail bg-white p-4">
                        <div className="row">
                            {/* <!-- ảnh  --> */}
                            <div className="col-md-5 khoianh">
                                <div className="anhto mb-4">
                                    <a className="active" href={initialProductDetail.img} data-fancybox="thumb-img">
                                        <img
                                            className="product-image"
                                            src={initialProductDetail.img}
                                            alt={initialProductDetail.img}
                                            style={{ width: '100%' }}
                                        />
                                    </a>

                                    {initialProductDetail.gallery &&
                                        initialProductDetail.gallery.map((item, index) => (
                                            <a key={index} href={item} data-fancybox="thumb-img"></a>
                                        ))}
                                </div>
                                <div className="list-anhchitiet d-flex mb-4" style={{ marginLeft: '2rem' }}>
                                    <img
                                        className="thumb-img thumb1 mr-3 img-fluid"
                                        src={initialProductDetail.img}
                                        alt={initialProductDetail.img}
                                    />
                                    {initialProductDetail.gallery &&
                                        initialProductDetail.gallery.map((item, index) => (
                                            <img
                                                key={index}
                                                className="thumb-img thumb2 img-fluid"
                                                src={item}
                                                alt={item}
                                            />
                                        ))}
                                </div>
                            </div>
                            {/* <!-- thông tin sản phẩm: tên, giá bìa giá bán tiết kiệm, các khuyến mãi, nút chọn mua.... --> */}
                            <div className="col-md-7 khoithongtin">
                                <div className="row">
                                    <div className="col-md-12 header">
                                        <h4 className="ten">
                                            {getNameBook(initialProductDetail.name, initialProductDetail.reprintYear)}
                                        </h4>
                                        <div className="rate">
                                            <i className="fa fa-star active"></i>
                                            <i className="fa fa-star active"></i>
                                            <i className="fa fa-star active"></i>
                                            <i className="fa fa-star active"></i>
                                            <i className="fa fa-star "></i>
                                        </div>
                                        <hr />
                                    </div>
                                    <div className="col-md-7">
                                        <div className="gia">
                                            <div className="giabia">
                                                Giá bìa:
                                                <span className="giacu ml-2">{initialProductDetail.originPrice} ₫</span>
                                            </div>
                                            <div className="giaban">
                                                Giá bán tại DealBooks:{' '}
                                                <span className="giamoi font-weight-bold">
                                                    {initialProductDetail.salePrice}{' '}
                                                </span>
                                                <span className="donvitien">₫</span>
                                            </div>
                                            <div className="tietkiem">
                                                Tiết kiệm:{' '}
                                                <b>
                                                    {initialProductDetail.originPrice - initialProductDetail.salePrice}{' '}
                                                    ₫
                                                </b>{' '}
                                                <span className="sale">-{initialProductDetail?.sale*100 || 0}%</span>
                                            </div>
                                        </div>
                                        <div className="uudai my-3">
                                            <h6 className="header font-weight-bold">
                                                Khuyến mãi & Ưu đãi tại DealBook:
                                            </h6>
                                            <ul>
                                                <li>
                                                    <b>Miễn phí giao hàng </b>cho đơn hàng từ 150.000đ ở TP.HCM và
                                                    300.000đ ở Tỉnh/Thành khác <a href="#">{'>>'} Chi tiết</a>
                                                </li>
                                                <li>
                                                    <b>Combo sách HOT - GIẢM 25% </b>
                                                    <a href="#">{'>>'}Xem ngay</a>
                                                </li>
                                                <li>Tặng Bookmark cho mỗi đơn hàng</li>
                                                <li>Bao sách miễn phí (theo yêu cầu)</li>
                                            </ul>
                                        </div>
                                        <div className="soluong d-flex">
                                            <label className="font-weight-bold">Số lượng: </label>
                                            <div className="input-number input-group mb-3">
                                                <div className="input-group-prepend" onClick={() => setQuantity(prev => prev > 1 ? prev - 1 : 1)}>
                                                    <span className="input-group-text btn-spin btn-dec">-</span>
                                                </div>
                                                <input
                                                    id="quantity"
                                                    type="text"
                                                    value={quantity}
                                                    className="soluongsp text-center"
                                                />
                                                <div className="input-group-append" onClick={() => setQuantity(prev => prev < initialProductDetail.availableQuantity ? prev + 1 : prev)}>
                                                    <span className="input-group-text btn-spin btn-inc">+</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div
                                            className="nutmua btn w-10 text-uppercase tw-mr-[10px]"
                                            onClick={() => handletAddToCart()}
                                        >
                                            Thêm vào giỏ hàng
                                        </div>
                                        <div className="nutmua btn w-10 text-uppercase">Chọn mua</div>
                                        <div
                                            id="result"
                                            style={{
                                                display: 'none',
                                                bottom: '-70px',
                                                width: '340px',
                                                textAlign: 'center',
                                                height: '180px',
                                                backgroundColor: '#000',
                                                opacity: '0.6',
                                                position: 'absolute',
                                                paddingTop: '32px',
                                            }}
                                        >
                                            <a id="status" className="ti-arrow-circle-down"></a>
                                            <FontAwesomeIcon
                                                icon={faCheck}
                                                style={{ color: 'green', fontSize: '30px' }}
                                            />
                                            <h3 style={{ color: '#fff', paddingTop: '8px', fontSize: '20px' }}>
                                                Thêm vào giỏ hàng
                                            </h3>
                                        </div>
                                        <a className="huongdanmuahang text-decoration-none" href="#">
                                            (Vui lòng xem hướng dẫn mua hàng)
                                        </a>
                                        <small className="share">Share: </small>
                                        <div
                                            className="fb-like"
                                            data-href="https://www.facebook.com/DealBook-110745443947730/"
                                            data-width="300px"
                                            data-layout="button"
                                            data-action="like"
                                            data-size="small"
                                            data-share="true"
                                        ></div>
                                    </div>
                                    {/* <!-- thông tin khác của sản phẩm:  tác giả, ngày xuất bản, kích thước ....  --> */}
                                    <div className="col-md-5">
                                        <div className="thongtinsach">
                                            <ul>
                                                {initialProductDetail.authors && (
                                                    <li>
                                                        Tác giả:{' '}
                                                        <a href="#" className="tacgia">
                                                            {initialProductDetail.authors.toString()}
                                                        </a>
                                                    </li>
                                                )}
                                                <li>Nhà xuất bản: {initialProductDetail.publisher}</li>
                                                <li>Nhà cung cấp: {initialProductDetail.supplier}</li>
                                                <li>
                                                    Hình thức bìa:{' '}
                                                    {EBookLayout.getNameFromIndex(initialProductDetail.layout)}
                                                </li>
                                                {initialProductDetail.series && (
                                                    <li>
                                                        Bộ:{' '}
                                                        <a
                                                            href={`series/${initialProductDetail.series.slug}`}
                                                            className="tacgia"
                                                        >
                                                            {initialProductDetail.series.name}
                                                        </a>
                                                    </li>
                                                )}
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            {/* <!-- decripstion của 1 sản phẩm: giới thiệu , đánh giá độc giả  --> */}
                            <div className="product-description col-md-9">
                                {/* <!-- 2 tab ở trên  --> */}
                                <nav>
                                    <div className="nav nav-tabs" id="nav-tab" role="tablist">
                                        <a
                                            className="nav-item nav-link active text-uppercase"
                                            id="nav-gioithieu-tab"
                                            data-toggle="tab"
                                            href="#nav-gioithieu"
                                            role="tab"
                                            aria-controls="nav-gioithieu"
                                            aria-selected="true"
                                        >
                                            Giới thiệu
                                        </a>
                                        <a
                                            className="nav-item nav-link text-uppercase"
                                            id="nav-danhgia-tab"
                                            data-toggle="tab"
                                            href="#nav-danhgia"
                                            role="tab"
                                            aria-controls="nav-danhgia"
                                            aria-selected="false"
                                        >
                                            Đánh giá của độc giả
                                        </a>
                                    </div>
                                </nav>
                                {/* <!-- nội dung của từng tab  --> */}
                                <div className="tab-content" id="nav-tabContent">
                                    <div
                                        className="tab-pane fade show active ml-3"
                                        id="nav-gioithieu"
                                        role="tabpanel"
                                        aria-labelledby="nav-gioithieu-tab"
                                    >
                                        <Parameter />
                                        <h6 className="tieude font-weight-bold">{initialProductDetail.name}</h6>
                                        <Description />
                                    </div>
                                    <div
                                        className="tab-pane fade"
                                        id="nav-danhgia"
                                        role="tabpanel"
                                        aria-labelledby="nav-danhgia-tab"
                                    >
                                        <div className="row">
                                            <div className="col-md-3 text-center">
                                                <p className="tieude">Đánh giá trung bình</p>
                                                <div className="diem">0/5</div>
                                                <div className="sao">
                                                    <i className="fa fa-star"></i>
                                                    <i className="fa fa-star"></i>
                                                    <i className="fa fa-star"></i>
                                                    <i className="fa fa-star"></i>
                                                    <i className="fa fa-star"></i>
                                                </div>
                                                <p className="sonhanxet text-muted">(0 nhận xét)</p>
                                            </div>
                                            <div className="col-md-5">
                                                <div className="tiledanhgia text-center">
                                                    <div className="motthanh d-flex align-items-center">
                                                        5 <i className="fa fa-star"></i>
                                                        <div className="progress mx-2">
                                                            <div
                                                                className="progress-bar"
                                                                role="progressbar"
                                                                aria-valuenow="0"
                                                                aria-valuemin="0"
                                                                aria-valuemax="100"
                                                            ></div>
                                                        </div>{' '}
                                                        0%
                                                    </div>
                                                    <div className="motthanh d-flex align-items-center">
                                                        4 <i className="fa fa-star"></i>
                                                        <div className="progress mx-2">
                                                            <div
                                                                className="progress-bar"
                                                                role="progressbar"
                                                                aria-valuenow="0"
                                                                aria-valuemin="0"
                                                                aria-valuemax="100"
                                                            ></div>
                                                        </div>{' '}
                                                        0%
                                                    </div>
                                                    <div className="motthanh d-flex align-items-center">
                                                        3 <i className="fa fa-star"></i>
                                                        <div className="progress mx-2">
                                                            <div
                                                                className="progress-bar"
                                                                role="progressbar"
                                                                aria-valuenow="0"
                                                                aria-valuemin="0"
                                                                aria-valuemax="100"
                                                            ></div>
                                                        </div>{' '}
                                                        0%
                                                    </div>
                                                    <div className="motthanh d-flex align-items-center">
                                                        2 <i className="fa fa-star"></i>
                                                        <div className="progress mx-2">
                                                            <div
                                                                className="progress-bar"
                                                                role="progressbar"
                                                                aria-valuenow="0"
                                                                aria-valuemin="0"
                                                                aria-valuemax="100"
                                                            ></div>
                                                        </div>{' '}
                                                        0%
                                                    </div>
                                                    <div className="motthanh d-flex align-items-center">
                                                        1 <i className="fa fa-star"></i>
                                                        <div className="progress mx-2">
                                                            <div
                                                                className="progress-bar"
                                                                role="progressbar"
                                                                aria-valuenow="0"
                                                                aria-valuemin="0"
                                                                aria-valuemax="100"
                                                            ></div>
                                                        </div>{' '}
                                                        0%
                                                    </div>
                                                    <div className="btn vietdanhgia mt-3">Viết đánh giá của bạn</div>
                                                </div>
                                                {/* <!-- nội dung của form đánh giá  --> */}
                                                <div className="formdanhgia">
                                                    <h6 className="tieude text-uppercase">GỬI ĐÁNH GIÁ CỦA BẠN</h6>
                                                    <span className="danhgiacuaban">
                                                        Đánh giá của bạn về sản phẩm này:
                                                    </span>
                                                    <div className="rating d-flex flex-row-reverse align-items-center justify-content-end">
                                                        <input type="radio" name="star" id="star1" />
                                                        <label htmlFor="star1"></label>
                                                        <input type="radio" name="star" id="star2" />
                                                        <label htmlFor="star2"></label>
                                                        <input type="radio" name="star" id="star3" />
                                                        <label htmlFor="star3"></label>
                                                        <input type="radio" name="star" id="star4" />
                                                        <label htmlFor="star4"></label>
                                                        <input type="radio" name="star" id="star5" />
                                                        <label htmlFor="star5"></label>
                                                    </div>
                                                    <div className="form-group">
                                                        <input
                                                            type="text"
                                                            className="txtFullname w-100"
                                                            placeholder="Mời bạn nhập tên(Bắt buộc)"
                                                        />
                                                    </div>
                                                    <div className="form-group">
                                                        <input
                                                            type="text"
                                                            className="txtEmail w-100"
                                                            placeholder="Mời bạn nhập email(Bắt buộc)"
                                                        />
                                                    </div>
                                                    <div className="form-group">
                                                        <input
                                                            type="text"
                                                            className="txtComment w-100"
                                                            placeholder="Đánh giá của bạn về sản phẩm này"
                                                        />
                                                    </div>
                                                    <div className="btn nutguibl">Gửi bình luận</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <hr />
                                    {/* <!-- het tab nav-danhgia  --> */}
                                </div>
                                {/* <!-- het tab-content  --> */}
                            </div>
                            {/* <!-- het product-description --> */}
                        </div>
                        {/* <!-- het row  --> */}
                    </div>
                    {/* <!-- het product-detail --> */}
                </div>
                {/* <!-- het container  --> */}
            </section>
            {/* <!-- het product-page --> */}

            {/* <!-- khối sản phẩm tương tự --> */}
            <ProductBlock homeOption={EHomeOption.SIMILAR} />

            {/* <!-- khối sản phẩm đã xem  --> */}

            <ProductBlock homeOption={EHomeOption.HISTORY} />
        </>
    );
}

export default ProductDetail;
