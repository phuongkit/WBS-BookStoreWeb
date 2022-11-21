import { getHistoryOrder } from './historyOrdersSlice';
import { historyService } from '../../services';

export const getHistoryOrders = async (dispatch, phone) => {
    let res = await historyService.getHistoryOrderByPhone(phone);
    dispatch(getHistoryOrder(res));
};
export const updateHistoryOrders = async (dispatch, data) => {
    let res = await historyService.updateHistoryOrder(data);
    // dispatch(getHistoryOrder(res));
};
