import { commentService, productService, ratingService } from '../../services';
import { EHomeOption, ENUM } from '../../utils';
import {
    getAllProducts,
    getAllNewProducts,
    getAllSaleProducts,
    getAllPopularProducts,
    getPageProduct,
    getOneProduct,
    handleFilter,
    getLocationProduct,
    getProductDetail,
    updateAllProduct,
} from './productsSlice';

// export const getProducts = async(dispatch,id)=>{
//     let res = await commentService.getCommentByProductId(id)
//     dispatch(getAllProducts(res))
// }

export const HandleFilter = async (dispatch, data) => {
    dispatch(handleFilter(data));
};

export const updateAllProducts = async (dispatch, data) => {
    dispatch(updateAllProduct(data));
};

export const getAllProductByCategory = async (dispatch, category) => {
    let res = await productService.getProductByCategory(category);
    dispatch(getAllProducts(res));
};
export const getAllProductByCategoryIdApi = async (dispatch, categoryId, params = {}) => {
    let res = await productService.getProductByCategoryId(categoryId, params);
    dispatch(getPageProduct(res.data));
};
export const getAllProductByOptionApi = async (dispatch, homeOptionId) => {
    console.log('homeOptionId', homeOptionId);
    let res = await productService.getAllProductsByOption(homeOptionId);
    if (homeOptionId === EHomeOption.NEW.index) {
        dispatch(getAllNewProducts(res.data));
    } else if (homeOptionId === EHomeOption.SALE.index) {
        dispatch(getAllSaleProducts(res.data));
    } else if (homeOptionId === EHomeOption.POPULAR.index) {
        dispatch(getAllPopularProducts(res.data));
    } else {
        dispatch(getAllProducts(res.data));
    }
};
//get by location and page and limit
export const getLocation = async (dispatch, location) => {
    let res = await productService.getProductByLocation(location);
    dispatch(getLocationProduct(res));
};
export const getAllProductApi = async (dispatch, params) => {
    let res = await productService.getAllProducts(params);
    dispatch(getPageProduct(res.data));
};

export const getProductDetailApi = async (dispatch, slug) => {
    let res = await productService.getProductBySlug(slug);
    // let resRating = await ratingService.getRating(res.id);
    // console.log(resRating.data);
    let resRating = {};
    dispatch(getProductDetail({ ...res.data, rating: resRating.data }));
};

export const getProductByIdApi = async (dispatch, id) => {
    let res = await productService.getProductById(id);
    dispatch(getOneProduct(res.data));
};

export const createProduct = async (product, dispatch, navigate, productList) => {
    try {
        const res = await productService.createProduct(product);
        let products = { ...productList };
        products.content = products?.content || [];
        products.content = Array.from(products.content).push(res.data);
        dispatch(getPageProduct(products));
        navigate('/products');
    } catch (err) {
        console.error(err?.message);
    }
};

export const updateProduct = async (id, product, dispatch, navigate, productList) => {
    try {
        const res = await productService.updateProductById(id, product);
        let products = { ...productList };
        products.content = products?.content || [];
        products.content = Array.from(products.content).map((item) => (item.id === res.data.id ? res.data : item));
        dispatch(getPageProduct(products));
        navigate('/products');
    } catch (err) {
        console.error(err?.message);
    }
};

export const deleteProduct = async (id, dispatch, navigate, productList) => {
    try {
        const res = await productService.deleteProductById(id);
        let products = { ...productList };
        products.content = products?.content || [];
        products.content = Array.from(products.content).filter((item) => item.id !== id);
        dispatch(getPageProduct(products));
        navigate('/products');
    } catch (err) {
        console.error(err?.message);
    }
};
