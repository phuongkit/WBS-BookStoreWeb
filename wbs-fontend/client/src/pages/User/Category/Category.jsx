import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useLocation, useParams } from 'react-router-dom';
import ProductCard from '../../../components/ProductCard/ProductCard';
import { getOneCategoryBySlugApi } from '../../../redux/category/categoriesApi';
import { getAllProductByCategoryIdApi } from '../../../redux/product/productsApi';
import './Category.scss';
import Paging from '../../../components/Paging/Paging';
import { useState } from 'react';

function Category() {
    const location = useLocation();
    const dispatch = useDispatch();
    const { categorySlug } = useParams();
    const category = useSelector((state) => state.categories.oneCategory.data);
    const { content: products = [], ...page } = useSelector((state) => state.products.pageProduct.data) || {content: []};
    const [currentPageNumber, setCurrentPageNumber] = useState(1);
    useEffect(() => {
        if (categorySlug) {
            getOneCategoryBySlugApi(dispatch, categorySlug);
        }
    }, [categorySlug]);
    useEffect(() => {
        if (category?.id) {
            getAllProductByCategoryIdApi(dispatch, category.id, {page: currentPageNumber});
        }
    }, [category?.id, currentPageNumber]);
    console.log(location.pathname)
    return (
        <>
            {/* <!-- breadcrumb  --> */}
            <section className="breadcrumbbar">
                <div className="container">
                    <ol className="breadcrumb mb-0 p-0 bg-transparent">
                        <li className="breadcrumb-item">
                            <Link to="/">Trang chủ</Link>
                        </li>
                        <li className="breadcrumb-item active">
                            <Link to={location.pathname}>{category?.name}</Link>
                        </li>
                    </ol>
                </div>
            </section>

            {/* <!-- ảnh banner  --> */}
            <section className="banner">
                <div className="container">
                    <a href="sach-moi-tuyen-chon.html">
                        <img src="images/banner-sach-ktkn.jpg" alt="banner-sach-ktkn" className="img-fluid" />
                    </a>
                </div>
            </section>

            {/* <!-- thể loại sách: kinh tế chính trị nhân vật bài học kinh doanh ( từng ô vuông) --> */}
            <section className="page-content my-3">
                <div className="container">
                    <div>
                        <h1 className="header text-uppercase">{category?.name}</h1>
                    </div>
                    <div className="the-loai-sach">
                        <ul className="list-unstyled d-flex">
                            <li>
                                <a href="#" className="danh-muc text-decoration-none">
                                    <div className="img text-center">
                                        <img src="images/tls-kinh-te-chinh-tri.png" alt="tls-kinh-te-chinh-tri" />
                                    </div>
                                    <div className="ten">Kinh tế - chính trị</div>
                                </a>
                            </li>
                            <li>
                                <a href="#" className="danh-muc text-decoration-none">
                                    <div className="img text-center">
                                        <img
                                            src="images/tls-nhan-vat-bai-hoc-kinh-doanh.png"
                                            alt="tls-kinh-te-chinh-tri"
                                        />
                                    </div>
                                    <div className="ten">Nhân vật - Bài học kinh doanh</div>
                                </a>
                            </li>
                            <li>
                                <a href="#" className="danh-muc text-decoration-none">
                                    <div className="img text-center">
                                        <img src="images/tls-sach-khoi-nghiep.png" alt="tls-kinh-te-chinh-tri" />
                                    </div>
                                    <div className="ten">Sách Khởi Nghiệp</div>
                                </a>
                            </li>
                            <li>
                                <a href="#" className="danh-muc text-decoration-none">
                                    <div className="img text-center">
                                        <img src="images/tls-sach-quan-tri-lanh-dao.png" alt="tls-kinh-te-chinh-tri" />
                                    </div>
                                    <div className="ten">Sách Quản trị - Lãnh đạo</div>
                                </a>
                            </li>
                            <li>
                                <a href="#" className="danh-muc text-decoration-none">
                                    <div className="img text-center">
                                        <img src="images/tls-sach-tai-chinh-ke-toan.png" alt="tls-kinh-te-chinh-tri" />
                                    </div>
                                    <div className="ten">Sách tài chính - kế toán</div>
                                </a>
                            </li>
                            <li>
                                <a href="#" className="danh-muc text-decoration-none">
                                    <div className="img text-center">
                                        <img src="images/tls-sach-kinh-te-hoc.png" alt="tls-kinh-te-chinh-tri" />
                                    </div>
                                    <div className="ten">Sách Kinh tế học</div>
                                </a>
                            </li>
                            <li>
                                <a href="#" className="danh-muc text-decoration-none">
                                    <div className="img text-center">
                                        <img src="images/tls-sach-quan-tri-nhan-su.png" alt="tls-kinh-te-chinh-tri" />
                                    </div>
                                    <div className="ten">Sách quản trị nhân sự</div>
                                </a>
                            </li>
                            <li>
                                <a href="#" className="danh-muc text-decoration-none">
                                    <div className="img text-center">
                                        <img
                                            src="images/tls-sach-chung-khoan-bat-dong-san-dau-tu.png"
                                            alt="tls-kinh-te-chinh-tri"
                                        />
                                    </div>
                                    <div className="ten">Sách chứng khoán - bất động sản</div>
                                </a>
                            </li>
                            <li>
                                <a href="#" className="danh-muc text-decoration-none">
                                    <div className="img text-center">
                                        <img src="images/tls-sach-ky-nang-lam-viec.png" alt="tls-kinh-te-chinh-tri" />
                                    </div>
                                    <div className="ten">Sách kỹ năng làm việc</div>
                                </a>
                            </li>
                            <li>
                                <a href="#" className="danh-muc text-decoration-none">
                                    <div className="img text-center">
                                        <img src="images/tls-sach-marketing-ban-hang.png" alt="tls-kinh-te-chinh-tri" />
                                    </div>
                                    <div className="ten">Sách marketing - bán hàng</div>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </section>

            {/* <!-- khối sản phẩm  --> */}
            <section className="content my-4">
                <div className="container">
                    <div className="noidung bg-white" style={{ width: '100%' }}>
                        {/* <!-- header của khối sản phẩm : tag(tác giả), bộ lọc và sắp xếp  --> */}
                        <div className="header-khoi-sp d-flex">
                            <div className="tag">
                                <label>Tác giả nổi bật:</label>
                                <a href="#">Tất cả</a>
                                <a href="#" data-tacgia=".MarieForleo">
                                    Marie Forleo
                                </a>
                                <a href="#" data-tacgia=".DeanGraziosi">
                                    Dean Graziosi
                                </a>
                                <a href="#" data-tacgia=".DavikClark">
                                    Davik Clark
                                </a>
                                <a href="#" data-tacgia=".TSLêThẩmDương">
                                    TS Lê Thẩm Dương
                                </a>
                                <a href="#" data-tacgia=".SimonSinek">
                                    Simon Sinek
                                </a>
                            </div>
                            <div className="sort d-flex ml-auto">
                                {/* <div className="hien-thi">
                                    <label htmlFor="hienthi-select" className="label-select">
                                        Hiển thị
                                    </label>
                                    <select className="hienthi-select">
                                        <option value="30">30</option>
                                        <option value="60">60</option>
                                    </select>
                                </div> */}
                                <div className="sap-xep">
                                    <label htmlFor="sapxep-select" className="label-select">
                                        Sắp xếp
                                    </label>
                                    <select className="sapxep-select">
                                        <option value="moinhat">Mới nhất</option>
                                        <option value="thap-cao">Giá: Thấp - Cao</option>
                                        <option value="cao-thap">Giá: Cao - Thấp</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        {/* <!-- các sản phẩm  --> */}
                        <div className="items">
                            <div className="row">
                                {products &&
                                    products.map((product, index) => (
                                        <div className="col-lg-3 col-md-4 col-xs-6 item SimonSinek">
                                            <ProductCard key={index} product={product} />
                                        </div>
                                    ))}
                            </div>
                        </div>
                        
                        {page && <Paging totalPages={page.totalPages} currentPage={page.number} onClick={(e) => setCurrentPageNumber(Number.parseInt(e.target.innerText))}/> }

                        {/* <!-- pagination bar --> */}
                        {/* <div className="pagination-bar my-3">
                            <div className="row">
                                <div className="col-12">
                                    <nav>
                                        <ul className="pagination justify-content-center">
                                            <li className="page-item disabled">
                                        <a className="page-link" href="#" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                            <span className="sr-only">Previous</span>
                                        </a>
                                    </li>
                                            <li className="page-item active">
                                                <a className="page-link" href="#">
                                                    1
                                                </a>
                                            </li>
                                            <li className="page-item">
                                                <a className="page-link" href="#">
                                                    2
                                                </a>
                                            </li>
                                            <li className="page-item">
                                                <a className="page-link" href="#" aria-label="Next">
                                                    <span aria-hidden="true">&rsaquo;</span>
                                                    <span className="sr-only">Next</span>
                                                </a>
                                            </li>
                                            <li className="page-item">
                                                <a className="page-link" href="#" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                    <span className="sr-only">Next</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div> */}

                        {/* <!--het khoi san pham--> */}
                    </div>
                    {/* <!--het div noidung--> */}
                </div>
                {/* <!--het container--> */}
            </section>
            {/* <!--het _1khoi--> */}
        </>
    );
}

export default Category;
