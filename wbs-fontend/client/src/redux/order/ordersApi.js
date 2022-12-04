import { getPageOrder, postOrder, updateOrder, deleteOrder } from './orderSlice';
import { orderService } from '../../services';
import { MESSAGE } from '../../utils';
import swal from 'sweetalert';

export const getAllOrdersByUserId = async (dispatch, userId) => {
    let res = await orderService.getAllOrdersByUserId(userId);
    dispatch(getPageOrder(res.data));
};

export const getAllOrders = async (dispatch) => {
    let res = await orderService.getAllOrders();
    dispatch(getPageOrder(res.data));
};

export const postOrders = async (dispatch, data, navigate) => {
    let res = await orderService.postOrder(data);
    if (res.status === 'CREATED') {
        swal({
            title: 'Thành công',
            text: 'Tạo đơn hàng thành công',
            icon: 'success',
        });
        dispatch(postOrder(res?.data));
        navigate('/order');
    } else {
        swal({
            title: 'Thất bại',
            text: 'Có lỗi xảy ra! Vui lòng thử lại sau!',
            icon: 'error',
        });
        navigate('/');
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

export const deleteOrdersByIdApi = async (dispatch, id, navigate = null) => {
    try {
        let res = await orderService.deleteOrderById(id);
        dispatch(deleteOrder());
        swal({
            title: 'Thành công',
            text: 'Hủy đơn hàng thành công!',
            icon: 'success',
        });
    } catch (err) {
        swal({
            text: MESSAGE.ERROR_ACTION,
            icon: 'info',
        });
    }
    if (navigate) {
        navigate('/');
    }
};
