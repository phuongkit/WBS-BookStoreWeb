import { getPageOrder, postOrder, updateOrder, deleteOrder } from './orderSlice';
import { orderService } from '../../services';
import swal from 'sweetalert';

export const getAllOrdersByUserId = async (dispatch, userId) => {
    let res = await orderService.getAllOrdersByUserId(userId);
    dispatch(getPageOrder(res.data));
};

export const getAllOrders = async (dispatch, param) => {
    let res = await orderService.getAllOrders(param);
    dispatch(getPageOrder(res.data?.content || []));
};

export const postOrders = async (dispatch, data) => {
    let res = await orderService.postOrder(data);
    if (res.status === 'CREATED') {
        swal({
            title: 'Thành công',
            text: 'Tạo đơn hàng thành công',
            icon: 'success',
        });
        dispatch(postOrder(res?.data));
    } else {
        swal({
            title: 'Thất bại',
            text: 'Có lỗi xảy ra! Vui lòng thử lại sau!',
            icon: 'error',
        });
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
    let res = await orderService.deleteOrderById(id.data);
    dispatch(deleteOrder(id));
};
