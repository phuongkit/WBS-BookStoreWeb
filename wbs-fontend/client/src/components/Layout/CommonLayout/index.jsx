import React from 'react';
import { Header } from '@Components/Header';
import { Footer } from '@Components/Footer';
import { Outlet } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import './header.css';
function CommonLayout({ children }) {
    const user = useSelector((state) => state.user?.user);
    const getAccess = JSON.parse(localStorage.getItem('access'));
    const hanleLogout = () => {
        localStorage.removeItem('access');
    };
    return (
        <>
            <Header />
            <main role="main" className="wrapper">
                <div className="content">
                    <Outlet />
                </div>
            </main>
            <Footer />
        </>
    );
}

export default CommonLayout;
