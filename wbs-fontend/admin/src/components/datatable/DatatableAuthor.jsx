import React from "react"
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { authorColumns } from '../datablesource/datablesource';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { getAllAuthorsApi } from '../../redux/author/authorsApi';

const Datatable = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    useEffect(() => {
        getAllAuthorsApi(dispatch);
    }, []);

    const authorList = useSelector((state) => state.authors.allAuthor.data);

    const actionColumn = [
        {
            field: 'action',
            headerName: 'Action',
            width: 200,
            renderCell: (params) => {
                return (
                    <div className="cellAction text-[12px]">
                        <Link to={`/Authors/${params.row._id}`}>
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
                Add New Author
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
                rows={authorList || []}
                columns={authorColumns.concat(actionColumn)}
                pageSize={8}
                rowsPerPageOptions={[8]}
                checkboxSelection
            />
        </div>
    );
};

export default Datatable;