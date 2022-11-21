import { getAllCategories, getAllCategoriesHierarchy, getOneCategory } from './categoriesSlice';
import { categoryService } from '../../services';
import { getAllProductByCategoryId } from '../product/productsApi';

export const getAllCategoriesApi = async (dispatch) => {
    let res = await categoryService.getAllCategories();
    dispatch(getAllCategories(res.data));
};
export const getAllCategoriesHierarchyApi = async (dispatch) => {
    let res = await categoryService.getAllCategoriesWithHierarchy();
    dispatch(getAllCategoriesHierarchy(res));
};
export const getOneCategoryBySlugApi = async (dispatch, slug) => {
    let res =  await categoryService.getCategoryBySlug(slug);
    dispatch(getOneCategory(res.data));
};