import { getAllTranslators, getOneTranslator } from './tranlatorsSlice';
import { translatorService } from '../../services';

export const getAllTranslatorsApi = async (dispatch) => {
    let res = await translatorService.getAll();
    dispatch(getAllTranslators(res.data));
};

export const createTranlatorApi = async (dispatch, id) => {
    let res = await translatorService.getById(id);
    dispatch(getOneTranslator(res.data));
};

export const updateTranlatorApi = async (dispatch, id, data) => {
    let res = await tranlatorService.update(id, data);
    dispatch(getOneTranslator(res.data));
};

export const deleteTranlatorByIdApi = async (dispatch, id) => {
    let res = await translatorService.deleteById(id);
    dispatch(getOneTranslator(res.data));
};