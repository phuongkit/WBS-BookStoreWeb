import React from 'react';
import { useRoutes } from 'react-router-dom';
import { CommonLayout, AdminLayout } from '../components/Layout';
import NotFound from '../pages/NotFound';
import { publishRoutes } from './PublishRoutes';

export default function Routes() {
    const routes = [
        {
            path: '',
            element: <CommonLayout />,
            children: [
                ...publishRoutes,
                { path: '*', element: <NotFound title="Not found" /> },
            ],
        },
    ];
    return useRoutes(routes);
}
