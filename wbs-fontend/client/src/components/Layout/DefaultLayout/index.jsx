import React from 'react';
import {Header} from '@Components/Header';
import {Footer} from '@Components/Footer';
import { Outlet } from 'react-router-dom';
function DefaultLayout({ children }) {
    return (
        <>
        
            <Header />
            <main role="main" className="wrapper">
                <div className="content">{children}</div>
            </main>
            <Footer />
        </>
    );
}

export default DefaultLayout;
