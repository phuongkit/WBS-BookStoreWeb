import { getAllAuthors, getOneAuthor, createAuthor } from './authorsSlice';
import { authorService } from '../../services';

export const getAllAuthorsApi = async (dispatch) => {
    let res = await authorService.getAll();
    dispatch(getAllAuthors(res.data));
};

export const createAuthorApi = async (dispatch, data) => {
    let res = await authorService.create(data);
    dispatch(createAuthor(res.data));
};

export const updateAuthorApi = async (dispatch, id, data) => {
    let res = await authorService.update(id, data);
    dispatch(getOneAuthor(res.data));
};

export const deleteAuthorByIdApi = async (dispatch, id) => {
    let res = await authorService.deleteById(id);
    dispatch(getOneAuthor(res.data));
};