import React from "react"
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { OderColumns, userColumns } from '../datablesource/datablesource';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { getAllUsersApi } from "../../redux/user/userApi";

const Datatable = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const getUser = JSON.parse(localStorage.getItem('user'));
    useEffect(() => {
        getAllUsersApi(dispatch);
    }, []);

    const { content: userList = [] } = useSelector((state) => state.users.pageUser.data);
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
                        <Link to={`/users/${params.row._id}`}>
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
                Add New User
                <Link to="/users/new" className="link">
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
                rows={userList || []}
                columns={userColumns.concat(actionColumn)}
                pageSize={8}
                rowsPerPageOptions={[8]}
                checkboxSelection
            />
        </div>
    );
};

export default Datatable;