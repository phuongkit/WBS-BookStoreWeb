import { getAllAuthors, getOneAuthor } from './authorsSlice';
import { authorService } from '../../services';

export const getAllAuthorsApi = async (dispatch) => {
    let res = await authorService.getAll();
    dispatch(getAllAuthors(res.data));
};

export const createAuthorApi = async (dispatch, id) => {
    let res = await authorService.getById(id);
    dispatch(getOneAuthor(res.data));
};

export const updateAuthorApi = async (dispatch, id, data) => {
    let res = await authorService.update(id, data);
    dispatch(getOneAuthor(res.data));
};

export const deleteAuthorByIdApi = async (dispatch, id) => {
    let res = await authorService.deleteById(id);
    dispatch(getOneAuthor(res.data));
};