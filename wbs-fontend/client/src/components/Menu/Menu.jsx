import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Link } from 'react-router-dom';
import './Menu.scss';

function Menu() {
    const categories = useSelector((state) => state.categories.allCategoryHierarchy.data);

    const createChild = (childCategories) => {
        if (childCategories) {
            let haftIndex = (childCategories.length + 1) / 2;
            const childLeft = childCategories.slice(0, haftIndex);
            const childRight = childCategories.slice(haftIndex, childCategories.length);
            return (
                <>
                    <div className="content trai">
                        {childLeft.map((item) => (
                            <li key={item.id}>
                                <Link to={item.slug}>{item.name}</Link>
                            </li>
                        ))}
                    </div>
                    <div className="content phai">
                        {childRight.map((item) => (
                            <li key={item.id}>
                                <Link to={item.slug}>{item.name}</Link>
                            </li>
                        ))}
                    </div>
                </>
            );
        }
    };

    return (
        <>
            {/* <!-- CATEGORIES --> */}
            <div className="categorycontent">
                <ul>
                    {categories.map((parent) => (
                        <li key={parent.id}>
                            {' '}
                            <Link to={parent.slug}>{parent.name}</Link>
                            <i className="fa fa-chevron-right float-right"></i>
                            <ul>
                                <li className="liheader">
                                    <Link to={parent.slug} className="header text-uppercase">
                                        {parent.name}
                                    </Link>
                                </li>
                                {parent.childCategories && createChild(parent.childCategories)}
                            </ul>
                        </li>
                    ))}
                </ul>
            </div>
        </>
    );
}

export default Menu;
