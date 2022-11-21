import { getAllGenres, getOneGenre } from './genresSlice';
import { genreService } from '../../services';

export const getAllGenresApi = async (dispatch) => {
    let res = await genreService.getAll();
    dispatch(getAllgenres(res.data));
};

export const creategenreApi = async (dispatch, id) => {
    let res = await genreService.getById(id);
    dispatch(getOneGenre(res.data));
};

export const updategenreApi = async (dispatch, id, data) => {
    let res = await genreService.update(id, data);
    dispatch(getOneGenre(res.data));
};

export const deletegenreByIdApi = async (dispatch, id) => {
    let res = await genreService.deleteById(id);
    dispatch(getOneGenre(res.data));
};