import React from 'react';
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { oderColumns } from '../datablesource/datablesource';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { getAllOrders, getAllOrdersByShopApi, updateStatusOrderApi } from '../../redux/order/ordersApi';
import { ENUM, MESSAGE } from '../../utils';
import { ghn } from '../../services/shipping';

const Datatable = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const getUser = JSON.parse(localStorage.getItem('customerInfo'));
    useEffect(() => {
        getAllOrders(dispatch, { limit: 9999 });
    }, []);

    const orderList = useSelector((state) => state.orders.pageOrder.data);

    const handleAccept = async (order) => {
        let result = confirm('Bạn có xác nhận đơn hàng và tạo đơn hàng shipper không');
        if (result) {
            // let reason = prompt('Nhập lý do hủy đơn hàng này', 'Không đủ hàng');
            // if (reason !== null) {
            //     let data = {
            //         status: ENUM.EOrderStatus.ORDER_CANCELLED.name,
            //         log: reason,
            //         shipOrderCode: null,
            //         expectedDeliveryTime: null,
            //     }
            //     updateStatusOrderApi(dispatch, id, data);
            // }
            let res = await ghn.createOrderGHN(order);
            console.log('res', res?.data?.data);
            let data = res.data?.data;

            let resDetails = await ghn.getOrderDetailGHN(data.order_code);
            console.log('resDetails', resDetails);

            // let resCancel = await ghn.cancelOrderGHN(data.order_code);
            // console.log('resCancel', resCancel?.data?.data);

            const dataResponse = {
                status: resDetails?.data?.data.status,
                shipOrderCode: data?.order_code,
                expectedDeliveryTime: data?.expected_delivery_time,
                totalFee: data?.total_fee,
            };
            updateStatusOrderApi(dispatch, order.id, dataResponse);
            alert('Xác nhận đơn hàng thành công');
        }
    };

    const handleCancel = async (order) => {
        let result = confirm('Bạn có muốn hủy đơn này không');
        if (result) {
            let reason = prompt('Nhập lý do hủy đơn hàng này', 'Không đủ hàng');
            if (reason !== null) {
                let data = {
                    status: ENUM.EOrderStatus.ORDER_CANCELLED.name,
                    log: reason,
                    shipOrderCode: null,
                    expectedDeliveryTime: null,
                };
                if (order?.shipOrderCode) {
                    let res = await ghn.cancelOrderGHN(order.shipOrderCode);
                    if (res.data?.data?.result) {
                        updateStatusOrderApi(dispatch, order.id, data);
                        alert('Hủy đơn hàng thành công!');
                    } else {
                        alert(MESSAGE.ERROR_ACTION);
                    }
                } else {
                    updateStatusOrderApi(dispatch, order.id, data);
                    alert('Hủy đơn hàng thành công!');
                }
            }
        }
    };

    const actionColumn = [
        {
            field: 'actionStatus',
            headerName: 'Action Update Status',
            width: 200,
            renderCell: (params) => {
                return (
                    <div className="cellAction text-[12px]">
                        {params.row.status === ENUM.EOrderStatus.ORDER_PENDING.name && (
                            <div className="updateButton" onClick={() => handleAccept(params.row)}>
                                Accept
                            </div>
                        )}
                        {params.row.status !== ENUM.EOrderStatus.ORDER_CANCELLED.name && (
                            <div className="deleteButton" onClick={() => handleCancel(params.row)}>
                                Cancel
                            </div>
                        )}
                    </div>
                );
            },
        },
        {
            field: 'action',
            headerName: 'Action',
            width: 200,
            renderCell: (params) => {
                return (
                    <div className="cellAction text-[12px]">
                        <Link to={`/orders/${params.row._id}`}>
                            <div className="updateButton">Detail</div>
                        </Link>
                    </div>
                );
            },
        },
    ];
    return (
        <div className="datatable">
            <div className="datatableTitle">
                Add New Order
                <Link to="/products/new" className="link">
                    Add New
                </Link>
            </div>
            <DataGrid
                sx={{
                    typography: {
                        fontSize: 12,
                        '& .MuiTablePagination-displayedRows': {
                            fontSize: 12,
                        },
                    },
                }}
                getRowId={(row) => row.id}
                className="datagrid "
                rows={orderList || []}
                columns={oderColumns.concat(actionColumn)}
                pageSize={8}
                rowsPerPageOptions={[8]}
                checkboxSelection
            />
        </div>
    );
};

export default Datatable;
