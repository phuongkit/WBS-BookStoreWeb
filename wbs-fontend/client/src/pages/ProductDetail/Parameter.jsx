import React from 'react';
import clsx from 'clsx';
import { useSelector } from 'react-redux';
import { EBookLayout } from '../../utils';
function Parameter() {
    const initProductDetail = useSelector((state) => state.products.productDetail.data);
    const {
        supplier,
        publisher,
        authors,
        translators,
        series,
        language,
        publishYear,
        reprintYear,
        minAge,
        maxAge,
        weight,
        packagingLength,
        packagingWidth,
        packagingHeight,
        numPages,
        layout,
        genres,
    } = initProductDetail;
    const parameters = [
        {
            label: 'Tên Nhà Cung Cấp',
            value: supplier,
        },
        {
            label: 'Tác giả',
            value: authors,
        },
        {
            label: 'Người Dịch',
            value: translators,
        },
        {
            label: 'NXB',
            value: publisher,
        },
        {
            label: 'Năm XB',
            value: reprintYear || publishYear,
        },
        {
            label: 'Ngôn ngữ',
            value: language,
        },
        {
            label: 'Trọng lượng (gr)',
            value: weight,
        },
        {
            label: 'Kích Thước Bao Bì',
            value: packagingHeight
                ? `${packagingLength} x ${packagingWidth} x ${packagingHeight} cm`
                : `${packagingLength} x ${packagingWidth} cm`,
        },
        {
            label: 'Số trang',
            value: numPages,
        },
        {
            label: 'Hình thức',
            value: EBookLayout.getNameFromIndex(layout),
        },
        // {
        //     label: 'Tên Nhà Cung Cấp',
        //     value: ,
        // },
        // {
        //     label: 'Tên Nhà Cung Cấp',
        //     value: ,
        // },
        // {
        //     label: 'Tên Nhà Cung Cấp',
        //     value: ,
        // },
    ];
    return (
        <div className="my-8">
            <p className="tieude font-weight-bold">Thông tin sản phẩm</p>
            <table className="w-full">
                <tbody>
                    {parameters.map((item, index) => 
                        { return item.value && (
                        <tr className="bg-gray-100" key={index}>
                            <td colSpan="4">{item.label}</td>
                            <td colSpan="6">{item.value}</td>
                        </tr>)
                        }
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default Parameter;
