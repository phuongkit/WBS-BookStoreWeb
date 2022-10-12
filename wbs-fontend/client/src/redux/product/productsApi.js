import { commentService, productService, ratingService } from '~/services';
import { getAllProducts, getOneProduct, handleFilter,getLocationProduct, getProductDetail,updateAllProduct} from './productsSlice'

// export const getProducts = async(dispatch,id)=>{
//     let res = await commentService.getCommentByProductId(id)
//     dispatch(getAllProducts(res))
// }

export const HandleFilter = async (dispatch, data) => {
    dispatch(handleFilter(data));
};

export const updateAllProducts= async(dispatch,data)=>{
        dispatch(updateAllProduct(data)) 
}

export const getAllProductByCategory = async (dispatch, category) => {
    let res = await productService.getProductByCategory(category);
    dispatch(getAllProducts(res));
};
export const getAllProductByCategoryId = async (dispatch, categoryId) => {
    let res = await productService.getProductByCategoryId(categoryId);
    dispatch(getAllProducts(res));
};
export const getAllProductByBrandId = async (dispatch, brandId) => {
    let res = await productService.getProductByBrandId(brandId);
    dispatch(getAllProducts(res));
};
export const getAllProductByCategoryIdAndBrandId = async (dispatch, categoryId, brandId) => {
    let res = await productService.getProductByCategoryIdAndBrandId(categoryId, brandId);
    dispatch(getAllProducts(res));
};
//get by location and page and limit
export const getLocation = async (dispatch, location) => {
    let res = await productService.getProductByLocation(location);
    dispatch(getLocationProduct(res));
};
export const getAllProductApi = async (dispatch) => {
    let res = await productService.getAllProducts();
    dispatch(getLocationProduct(res));
};

export const getProductDetailApi = async (dispatch, slug) => {
    let res = await productService.getProductBySlug(slug);
    let resRating = await ratingService.getRating(res.id);
    dispatch(getProductDetail({...res, rating: resRating}));
};
