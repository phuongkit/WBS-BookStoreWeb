import React from 'react';

function NextArrow(props) {
    const { className, style, onClick } = props;
    return (
        <button
            className={className}
            onClick={onClick}
            style={{
                fontSize: '40px',
                display: 'block',
                zIndex: '2',
                right: 0,
                height: '40px',
                width: '40px',
                opacity: '0.7',
                borderRadius: '50%',
                color: 'yellow',
                backgroundColor: '#F5A623',
            }}
        >
        </button>
    );
}

export default NextArrow;
