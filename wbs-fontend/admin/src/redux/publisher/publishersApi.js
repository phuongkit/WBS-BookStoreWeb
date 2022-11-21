import { getAllPublishers, getOnePublisher } from './publishersSlice';
import { publisherService } from '../../services';

export const getAllPublishersApi = async (dispatch) => {
    let res = await publisherService.getAll();
    dispatch(getAllPublishers(res.data));
};

export const createpublisherApi = async (dispatch, id) => {
    let res = await publisherService.getById(id);
    dispatch(getOnePublisher(res.data));
};

export const updatepublisherApi = async (dispatch, id, data) => {
    let res = await publisherService.update(id, data);
    dispatch(getOnePublisher(res.data));
};

export const deletepublisherByIdApi = async (dispatch, id) => {
    let res = await publisherService.deleteById(id);
    dispatch(getOnePublisher(res.data));
};