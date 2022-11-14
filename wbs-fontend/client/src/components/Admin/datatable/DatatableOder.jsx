import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { OderColumns } from '../datablesource/datablesource';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { getAllOrdersByShopApi } from '../../../redux/order/ordersApi';

const Datatable = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const getUser = JSON.parse(localStorage.getItem('customerInfo'));
    useEffect(() => {
        getAllOrdersByShopApi(dispatch, getUser.shopId);
    }, []);

    const { content: orderList = [] } = useSelector((state) => state.orders.pageOrder.data);
    // const [data, setData] = useState(oderList);

    // useEffect(()=>{

    //   if(user?.accessToken){
    //     getBySeller(user?.accessToken,dispatch,user?._id)
    //   }

    // // eslint-disable-next-line react-hooks/exhaustive-deps
    // },[]

    const actionColumn = [
        {
            field: 'action',
            headerName: 'Action',
            width: 200,
            renderCell: (params) => {
                return (
                    <div className="cellAction text-[12px]">
                        <Link to={`/seller/orders/${params.row._id}`}>
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
                <Link to="/admin/products/new" className="link">
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
                columns={OderColumns.concat(actionColumn)}
                pageSize={8}
                rowsPerPageOptions={[8]}
                checkboxSelection
            />
        </div>
    );
};

export default Datatable;
