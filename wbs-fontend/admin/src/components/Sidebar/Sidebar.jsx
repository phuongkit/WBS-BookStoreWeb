import React from 'react';
import './Sidebar.scss';
import DashboardIcon from '@mui/icons-material/Dashboard';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import StoreIcon from '@mui/icons-material/Store';
import SettingsApplicationsIcon from '@mui/icons-material/SettingsApplications';
import ExitToAppIcon from '@mui/icons-material/ExitToApp';
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';

//import { useContext } from "react";
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { postLogout } from '../../redux/user/userApi';
import { useTheme } from '@emotion/react';

const Sidebar = ({onChangeMode}) => {
    // const user = useSelector((state)=> state.auth?.login?.currentUser)
    // const accessToken = user?.accessToken;
    // const id = user?._id;
    const theme = useTheme();
    const dispatch = useDispatch();
    const handleLogout = () => {
        postLogout(dispatch);
    };
    return (
        <div className="admin-sidebar" style={{ backgroundColor: theme.palette.background.default }}>
            <div className="top">
                <Link to="" style={{ textDecoration: 'none' }}>
                    <span className="logo">DealBook</span>
                </Link>
            </div>
            <hr />
            <div className="center">
                <ul>
                    <p className="title">MAIN</p>
                    <Link to="" style={{ textDecoration: 'none' }}>
                        <li>
                            <DashboardIcon className="icon" />
                            <span>Dashboard</span>
                        </li>
                    </Link>
                    <p className="title">LISTS</p>
                    <Link to="/users" style={{ textDecoration: 'none' }}></Link>

                    <Link to="/authors" style={{ textDecoration: 'none' }}>
                        <li>
                            <CreditCardIcon className="icon" />
                            <span>Authors</span>
                        </li>
                    </Link>

                    <Link to="/genres" style={{ textDecoration: 'none' }}>
                        <li>
                            <CreditCardIcon className="icon" />
                            <span>Genres</span>
                        </li>
                    </Link>

                    <Link to="/categories" style={{ textDecoration: 'none' }}>
                        <li>
                            <CreditCardIcon className="icon" />
                            <span>Categories</span>
                        </li>
                    </Link>

                    <Link to="/publishers" style={{ textDecoration: 'none' }}>
                        <li>
                            <CreditCardIcon className="icon" />
                            <span>Publishers</span>
                        </li>
                    </Link>

                    <Link to="/suppliers" style={{ textDecoration: 'none' }}>
                        <li>
                            <CreditCardIcon className="icon" />
                            <span>Suppliers</span>
                        </li>
                    </Link>

                    <Link to="/translators" style={{ textDecoration: 'none' }}>
                        <li>
                            <CreditCardIcon className="icon" />
                            <span>Translators</span>
                        </li>
                    </Link>

                    <Link to="/products" style={{ textDecoration: 'none' }}>
                        <li>
                            <StoreIcon className="icon" />
                            <span>Products</span>
                        </li>
                    </Link>
                    <Link to="/orders" style={{ textDecoration: 'none' }}>
                        <li>
                            <CreditCardIcon className="icon" />
                            <span>Orders</span>
                        </li>
                    </Link>

                    <Link to="/users" style={{ textDecoration: 'none' }}>
                        <li>
                            <CreditCardIcon className="icon" />
                            <span>Users</span>
                        </li>
                    </Link>

                    <p className="title">USEFUL</p>

                    <li>
                        <SettingsApplicationsIcon className="icon" />
                        <span>Settings</span>
                    </li>
                    <p className="title">USER</p>
                    <li>
                        <AccountCircleOutlinedIcon className="icon" />
                        <span>Profile</span>
                    </li>

                    <Link to="/login" onClick={handleLogout} style={{ textDecoration: 'none' }}>
                        <li onClick={handleLogout}>
                            <ExitToAppIcon className="icon" />
                            <span>Logout</span>
                        </li>
                    </Link>
                </ul>
            </div>
            <div className="bottom">
                <div className="colorOption" onClick={() => onChangeMode('light')}></div>
                <div className="colorOption" onClick={() => onChangeMode('dark')}></div>
            </div>
        </div>
    );
};

export default Sidebar;
