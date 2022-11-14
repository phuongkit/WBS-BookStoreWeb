import React from 'react';
import { Header } from '../../Header';
import { Footer } from '../../Footer';
import { Outlet } from 'react-router-dom';
import './CommonLayout.scss';

function CommonLayout({ children }) {
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
