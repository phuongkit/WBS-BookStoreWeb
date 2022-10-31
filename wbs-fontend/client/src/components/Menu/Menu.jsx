import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
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
                                <a href={item.slug}>{item.name}</a>
                            </li>
                        ))}
                    </div>
                    <div className="content phai">
                        {childRight.map((item) => (
                            <li key={item.id}>
                                <a href={item.slug}>{item.name}</a>
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
                            <a href={parent.slug}>{parent.name}</a>
                            <i className="fa fa-chevron-right float-right"></i>
                            <ul>
                                <li className="liheader">
                                    <a href={parent.slug} className="header text-uppercase">
                                        {parent.name}
                                    </a>
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
