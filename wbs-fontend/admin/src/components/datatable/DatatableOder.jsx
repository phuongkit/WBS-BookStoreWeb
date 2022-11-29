import React from 'react';
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { orderColumns } from '../datablesource/datablesource';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { getAllOrders, getAllOrdersByShopApi, updateStatusOrderApi } from '../../redux/order/ordersApi';
import { ENUM, EOrderStatusGHN, MESSAGE } from '../../utils';
import { ghn } from '../../services/shipping';
import swal from 'sweetalert';

const Datatable = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const getUser = JSON.parse(localStorage.getItem('customerInfo'));
    useEffect(() => {
        getAllOrders(dispatch, { limit: 9999 });
    }, []);

    const orderList = useSelector((state) => state.orders.pageOrder.data);

    const handleAccept = async (order) => {
        swal({
            text: 'Bạn có xác nhận đơn hàng và tạo đơn hàng shipper không',
            icon: 'info',
            buttons: {
                cancel: true,
                confirm: true,
            },
        }).then(async (isOK) => {
            if (isOK) {
                let res = await ghn.createOrderGHN(order);
                console.log('res', res?.data?.data);
                let data = res.data?.data;

                let resDetails = await ghn.getOrderDetailGHN(data.order_code);

                const dataResponse = {
                    status: EOrderStatusGHN.getIndexFromName(resDetails?.data?.data.status),
                    shipOrderCode: data?.order_code,
                    expectedDeliveryTime: data?.expected_delivery_time,
                    transportFee: data?.total_fee,
                };
                updateStatusOrderApi(dispatch, order.id, dataResponse);

                swal({
                    title: 'Thành công',
                    text: 'Xác nhận đơn hàng thành công',
                    icon: 'success',
                });
            }
        });
    };

    const handleCancel = async (order) => {
        swal({
            text: 'Bạn có muốn hủy đơn này không',
            icon: 'info',
            buttons: {
                cancel: true,
                confirm: true,
            },
            dangerMode: true,
        }).then(async (isOK) => {
            if (isOK) {
                //let reason = prompt('Nhập lý do hủy đơn hàng này', 'Không đủ hàng');
                swal({
                    text: 'Nhập lý do hủy đơn hàng',
                    content: {
                        element: 'input',
                        attributes: {
                            defaultValue: 'Không đủ hàng',
                        },
                    },
                }).then(async (reason) => {
                    if (reason !== null) {
                        let data = {
                            status: ENUM.EOrderStatus.ORDER_CANCELLED.index,
                            log: reason,
                            shipOrderCode: null,
                            expectedDeliveryTime: null,
                        };
                        if (order?.shipOrderCode) {
                            let res = await ghn.cancelOrderGHN(order.shipOrderCode);
                            // if (res.data?.data?.result) {
                            updateStatusOrderApi(dispatch, order.id, data);
                            swal({
                                title: 'Thành công',
                                text: 'Hủy đơn hàng thành công',
                                icon: 'success',
                            });
                        } else {
                            updateStatusOrderApi(dispatch, order.id, data);
                            swal({
                                title: 'Thành công',
                                text: 'Hủy đơn hàng thành công',
                                icon: 'success',
                            });
                        }
                    }
                });
            }
        });
    };

    const actionColumn = [
        {
            field: 'actionStatus',
            headerName: 'Action Update Status',
            width: 200,
            renderCell: (params) => {
                return (
                    <div className="cellAction text-[12px]">
                        {params.row.status === ENUM.EOrderStatus.ORDER_PENDING.index && (
                            <div className="updateButton" onClick={() => handleAccept(params.row)}>
                                Accept
                            </div>
                        )}
                        {params.row.status !== ENUM.EOrderStatus.ORDER_CANCELLED.index && (
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
                columns={orderColumns.concat(actionColumn)}
                pageSize={8}
                rowsPerPageOptions={[8]}
                checkboxSelection
            />
        </div>
    );
};

export default Datatable;
