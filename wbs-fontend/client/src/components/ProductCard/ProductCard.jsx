import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Link } from 'react-router-dom';
import './ProductCard.scss';
import { numberWithCommas } from '../../utils';

function ProductCard({ product }) {
    return (
        <div className="card">
            <Link to={`/${product.categorySlug}/${product.slug}`} state={product}>
            {/* <a
                href='{product.categoryName}/{product.slug}'
                className="motsanpham"
                style={{ textDecoration: 'none', color: 'black' }}
                datatoggle="tooltip"
                dataplacement="bottom"
                title={product.name}
            > */}
                <img className="card-img-top anh" src={product.img} alt={product.slug} />
                <div className="card-body noidungsp mt-3">
                    <h3 className="card-title ten">{product.name}</h3>
                    <small className="tacgia text-muted">{product.authors.toString()}</small>
                    <div className="gia d-flex align-items-baseline">
                        {(product.sale && (
                            <>
                                <div className="giamoi">{ numberWithCommas(product.salePrice)} ₫</div>
                                <div className="giacu text-muted">{numberWithCommas(product.originPrice)} ₫</div>
                                <div className="sale">-{product.sale ? product.sale * 100 : 0}%</div>
                            </>
                        )) || <div className="giacu text-muted">{product.originPrice} ₫</div>}
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
                                <span className="text-muted">{product.totalVote} nhận xét</span>
                            </li>
                        </ul>
                    </div>
                </div>
            {/* </a> */}
            </Link>
        </div>
    );
}

export default ProductCard;
