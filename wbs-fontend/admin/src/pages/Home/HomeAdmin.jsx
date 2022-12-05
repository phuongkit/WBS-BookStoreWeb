import './Home.scss';
import Widget from '../../components/Widget/Widget';
import Chart from '../../components/chart/Chart';
import Featured from '../../components/featured/Featured';
import Table from '../../components/table/Table';
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { getAllOrders } from '../../redux/order/ordersApi';

const HomeAdmin = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const user = useSelector((state) => state?.users.auth.currentUser);
    useEffect(() => {
        if (user === null || user?.role !== 0) {
            // navigate('/admin/login');
        }
        getAllOrders(dispatch, { limit: 5, sortField: 'createdAt', sortDir: 'dec' });
    }, []);
    return (
        <div>
            <div className="widgets">
                <Widget type="order" />
                <Widget type="earning" />
                {/* <Widget type="balance" /> */}
            </div>
            <div className="charts">
                {/* <Featured /> */}
                <Chart title="Last 6 Months (Revenue)" aspect={2 / 1} />
            </div>
            <div className="listContainer">
                <div className="listTitle">Latest Transactions</div>
                <Table />
            </div>
        </div>
    );
};

export default HomeAdmin;
