import React from 'react';
import './datatable.scss';
import { DataGrid } from '@mui/x-data-grid';
import { OderColumns, userColumns } from '../datablesource/datablesource';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { getAllUsersApi, updateEnableUserApi } from '../../redux/user/userApi';
import { FormControl, FormControlLabel, FormGroup, FormLabel, styled, Switch, SwitchProps } from '@mui/material';
import swal from 'sweetalert';

const Datatable = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const getUser = JSON.parse(localStorage.getItem('user'));
    useEffect(() => {
        getAllUsersApi(dispatch);
    }, []);

    const { content: userList = [] } = useSelector((state) => state.users.pageUser.data);

    const handleChangeStatus = async (e, user) => {
        if (user.role === 0) {
            swal({
                text: 'Bạn không thể tắt hoạt động của một admin khác',
                icon: 'error',
                buttons: {
                    cancel: true,
                    confirm: true,
                },
            });
            e.preventDefault();
        } else {
            updateEnableUserApi(dispatch, user.id, e.target.checked);
        }
    };

    const IOSSwitch = styled((props) => <Switch focusVisibleClassName=".Mui-focusVisible" disableRipple {...props} />)(
        ({ theme }) => ({
            width: 42,
            height: 26,
            padding: 0,
            '& .MuiSwitch-switchBase': {
                padding: 0,
                margin: 2,
                transitionDuration: '300ms',
                '&.Mui-checked': {
                    transform: 'translateX(16px)',
                    color: '#fff',
                    '& + .MuiSwitch-track': {
                        backgroundColor: theme.palette.mode === 'dark' ? '#2ECA45' : '#65C466',
                        opacity: 1,
                        border: 0,
                    },
                    '&.Mui-disabled + .MuiSwitch-track': {
                        opacity: 0.5,
                    },
                },
                '&.Mui-focusVisible .MuiSwitch-thumb': {
                    color: '#33cf4d',
                    border: '6px solid #fff',
                },
                '&.Mui-disabled .MuiSwitch-thumb': {
                    color: theme.palette.mode === 'light' ? theme.palette.grey[100] : theme.palette.grey[600],
                },
                '&.Mui-disabled + .MuiSwitch-track': {
                    opacity: theme.palette.mode === 'light' ? 0.7 : 0.3,
                },
            },
            '& .MuiSwitch-thumb': {
                boxSizing: 'border-box',
                width: 22,
                height: 22,
            },
            '& .MuiSwitch-track': {
                borderRadius: 26 / 2,
                backgroundColor: theme.palette.mode === 'light' ? '#E9E9EA' : '#39393D',
                opacity: 1,
                transition: theme.transitions.create(['background-color'], {
                    duration: 500,
                }),
            },
        }),
    );

    const actionColumn = [
        {
            field: 'status',
            headerName: 'Status',
            width: 200,
            renderCell: (params) => {
                return (
                    <>
                        <div className="cellAction text-[12px]">
                            <FormControl component="fieldset">
                                <FormControlLabel
                                    control={
                                        <IOSSwitch
                                            sx={{ m: 1 }}
                                            defaultChecked={params.row.enabled}
                                            onClick={(e) =>  {if (params.row.role === 0) {
                                              e.preventDefault();
                                              swal({
                                                  text: 'Bạn không thể tắt hoạt động của một admin khác',
                                                  icon: 'error',
                                                  buttons: {
                                                      cancel: true,
                                                      confirm: true,
                                                  },
                                              });               
                                          }}}
                                            onChange={(e) => handleChangeStatus(e, params.row)}
                                        />
                                    }
                                    label="Hoạt động"
                                    labelPlacement="start"
                                    // onChange={e=>handleChangeStatus(e, params.row)}
                                />
                            </FormControl>
                        </div>
                    </>
                );
            },
        },
        {
            field: 'action',
            headerName: 'Action',
            width: 200,
            renderCell: (params) => {
                return (
                    <>
                        <div className="cellAction text-[12px]">
                            <Link to={`/users/${params.row._id}`}>
                                <div className="updateButton">Detail</div>
                            </Link>
                        </div>
                    </>
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
