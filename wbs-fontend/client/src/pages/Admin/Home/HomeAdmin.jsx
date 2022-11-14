import './Home.scss';
import Widget from '../../../components/Admin/Widget/Widget';

import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';

const HomeAdmin = () => {
    const navigate = useNavigate();
    const user = useSelector((state) => state?.users.auth.currentUser);
    useEffect(() => {
        if (user === null || user?.role !== 0) {
            // navigate('/admin/login');
        }
    });

    return (
        <div className="widgets">
            <Widget type="order" />
            <Widget type="earning" />
            <Widget type="balance" />
        </div>
    );
};

export default HomeAdmin;
