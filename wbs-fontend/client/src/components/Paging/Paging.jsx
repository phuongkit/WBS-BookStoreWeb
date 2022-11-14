import React, { useState, useEffect } from 'react';
import { PAGE } from '../../utils';

function Paging(props) {
    const {totalPages, currentPage, onClick} = props;
    const [pageIndexs, setPageIndexs] = useState([]);

    useEffect(() => {
        if (currentPage !== undefined) {
            setPageIndexs(getAllIndexPages());
        }
    }, [currentPage]);

    const getAllIndexPages = () => {
        var pageIndexs = [];
        if (totalPages < PAGE.CENTER + PAGE.LEFT + PAGE.RIGHT) {
            for (let i = 1; i <= totalPages; i++) {
                pageIndexs.push(i);
            }
        } else if (currentPage < PAGE.LEFT + (PAGE.CENTER + 1) / 2 + 1) {
            for (let i = 1; i <= PAGE.LEFT + (PAGE.CENTER + 1) / 2 + 1; i++) {
                pageIndexs.push(i);
            }
            pageIndexs.push(-1);
            pageIndexs.push(totalPages);
        } else if (
            currentPage >= PAGE.LEFT + (PAGE.CENTER + 1) / 2 + 1 &&
            currentPage <= totalPages - (PAGE.CENTER + 1) / 2 - PAGE.RIGHT
        ) {
            pageIndexs.push(1);
            pageIndexs.push(-1);
            for (
                let i = currentPage - (PAGE.CENTER - 1) / 2;
                i <= currentPage + (PAGE.CENTER - 1) / 2;
                i++
            ) {
                pageIndexs.push(i);
            }
            pageIndexs.push(-1);
            pageIndexs.push(totalPages);
        } else {
            pageIndexs.push(1);
            pageIndexs.push(-1);
            for (
                let i = currentPage - (PAGE.CENTER - 1) / 2;
                i <= currentPage + (PAGE.CENTER - 1) / 2;
                i++
            ) {
                pageIndexs.push(i);
            }
        }
        return pageIndexs;
    };
    return (
        <>
        <div className="tw-mt-4 tw-flex tw-justify-center tw-items-center tw-items-center">
        {pageIndexs && pageIndexs.map((item, index) => {
            if (item === currentPage + 1) {
                return (
                    <button className="tw-bg-gray-300 tw-px-2 tw-py-3 tw-rounded tw-mr-[12px]" key={index} onClick={onClick}>{item}</button>
                )
            } else if (item === -1) {
                return (
                    <button className="tw-bg-gray-100 tw-px-2 tw-py-3 tw-rounded tw-mr-[12px]" key={index}>...</button>
                )
            } else {
                return (
                    <button className="tw-bg-gray-100 tw-px-2 tw-py-3 tw-rounded tw-mr-[12px]" key={index} onClick={onClick}>{item}</button>
                )
            }
        })}
        </div>
        </>
    )
}

export default Paging;
