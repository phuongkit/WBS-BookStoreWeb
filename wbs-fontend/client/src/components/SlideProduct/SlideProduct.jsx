import ProductCard from '../ProductCard';
import NextArrow from '../Slick/NextArrow';
import PrevArrow from '../Slick/PrevArrow';
import React from 'react';
import ReactDOM from 'react-dom';
import Slider from 'react-slick';
import Slick from '../Slick';
import './slideproduct.scss';

const SlideProduct = ({ index, products }) => {
    return (
        <div className="slidebar">
            <div className="w-full">
                <Slider
                    slidesToShow={(products && 4 < products?.length ? 5 : products?.length) || 5}
                    slidesToScroll={5}
                    nextArrow={<NextArrow />}
                    prevArrow={<PrevArrow />}
                    dots={false}
                    speed={300}
                    className="khoisanpham"
                    style={{ paddingBottom: '2rem' }}
                >
                    {products &&
                        products.map((product) => (
                            <div className="w-full" key={product.id}>
                                <div className="mx-4">
                                    <ProductCard key={product.id} product={product} />
                                </div>
                            </div>
                        ))}
                </Slider>
            </div>
        </div>
    );
};
export default SlideProduct;
