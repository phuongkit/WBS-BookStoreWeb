import { getStatisticOrder } from './statisticsSlice';
import { statisticService } from '../../services';
import swal from 'sweetalert';

export const getStatisticOrdersApi = async (dispatch, param) => {
    let res = await statisticService.getStatisticOrders(param);
    dispatch(getStatisticOrder(res?.data))
};