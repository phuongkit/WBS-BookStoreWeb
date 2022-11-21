import React from 'react';
import Spinner from '../Spinner'
function Loading() {
    return (
        <div className="h-screen bg-yellow-100">
            <Spinner size={60} />
        </div>
    );
}

export default Loading;
