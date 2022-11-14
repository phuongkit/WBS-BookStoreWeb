import React, {useState} from 'react';
import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useSearchParams } from 'react-router-dom';
import { getResult } from '../../../redux/search/searchApi';
import './Search.scss';
import Paging from '../../../components/Paging/Paging';
import ProductCard from '../../../components/ProductCard/ProductCard';

function Search() {
    const dispatch = useDispatch();
    const [currentPageNumber, setCurrentPageNumber] = useState(1);
    let [searchParams, setSearchParams] = useSearchParams();
    let keySearch = searchParams.get('keyword');
    const { content: products = [], ...page } = useSelector((state) => state.search.pageSearch.data) || { content: [] };
    useEffect(() => {
        if (keySearch) {
            getResult(dispatch, { keyword: keySearch, page: currentPageNumber });
        }
    }, [keySearch, currentPageNumber]);

    return (
        <>
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

                        {page && (
                            <Paging
                                totalPages={page.totalPages}
                                currentPage={page.number}
                                onClick={(e) => setCurrentPageNumber(Number.parseInt(e.target.innerText))}
                            />
                        )}

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

export default Search;
