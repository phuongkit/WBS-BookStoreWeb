import { getAllCategories, getOneCategory } from './categoriesSlice';
import { categoryService } from '~/services';

export const getAllCategoriesApi = async (dispatch) => {
    let res = await categoryService.getAllCategories();
    dispatch(getAllCategories(res));
};
export const getOneCategoryBySlugApi = async (dispatch, slug) => {
    let res =  await categoryService.getCategoryBySlug(slug);
    dispatch(getOneCategory(res));
};