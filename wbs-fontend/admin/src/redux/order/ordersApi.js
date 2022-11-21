import { getPageOrder, postOrder, updateOrder, deleteOrder } from './orderSlice';
import { orderService } from '../../services';

export const getAllOrdersByUserId = async(dispatch, userId) => {
    let res = await orderService.getAllOrdersByUserId(userId);
    dispatch(getPageOrder(res.data));
} 

export const getAllOrders = async(dispatch, param) => {
    let res = await orderService.getAllOrders(param);
    dispatch(getPageOrder(res.data?.content || []));
} 

export const postOrders = async (dispatch, data) => {
    let res = await orderService.postOrder(data);
    if (res.status === 'CREATED') {
        alert('Tạo đơn hàng thành công');
        dispatch(postOrder(res?.data));
    } else {
        alert('Có lỗi xảy ra! Vui lòng thử lại sau!');
    }
};

export const updateOrders = async (dispatch, data, id) => {
    let res = await orderService.updateOrder(data, id);
    dispatch(postOrder(res?.data));
};

export const updatePaymentOrders = async (dispatch, data, id) => {
    let res = await orderService.updatePaymentOrder(data, id);
    dispatch(postOrder(res?.data));
};

export const updateStatusOrderApi = async (dispatch, id, data) => {
    let res = await orderService.updateStatus(id, data);
    dispatch(updateOrder(res?.data));
};

export const deleteOrderApi = async (dispatch, id) => {
    let res = await orderService.deleteOrderById(id. data);
    dispatch(deleteOrder(id));
}
