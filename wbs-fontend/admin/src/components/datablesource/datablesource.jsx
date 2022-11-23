import React from "react"
import { ENUM, EOrderStatus } from '../../utils';

export const authorColumns = [
    {
        field: 'id',
        headerName: 'ID',
        width: 50,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.id}</div>;
        },
    },
    {
        field: 'fullName',
        headerName: 'Full Name',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.fullName}</div>;
        },
    },
    {
        field: 'gender',
        headerName: 'gender',
        width: 150,
        renderCell: (params) => {
            return (
                <div className={`cellWithStatus `}>{ENUM.EGender.getNameFromIndex(params.row.gender)}</div>
            );
        },
    },
]

export const categoryColumns = [
    {
        field: 'id',
        headerName: 'ID',
        width: 50,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.id}</div>;
        },
    },
    {
        field: 'name',
        headerName: 'Name',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.name}</div>;
        },
    },
    {
        field: 'description',
        headerName: 'Description',
        width: 300,
        renderCell: (params) => {
            return (
                <div className={`cellWithStatus `}>{params.row.description}</div>
            );
        },
    },
]

export const genreColumns = [
    {
        field: 'id',
        headerName: 'ID',
        width: 50,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.id}</div>;
        },
    },
    {
        field: 'name',
        headerName: 'Name',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.name}</div>;
        },
    },
    {
        field: 'description',
        headerName: 'Description',
        width: 300,
        renderCell: (params) => {
            return (
                <div className={`cellWithStatus `}>{params.row.description}</div>
            );
        },
    },
]

export const publisherColumns = [
    {
        field: 'id',
        headerName: 'ID',
        width: 50,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.id}</div>;
        },
    },
    {
        field: 'name',
        headerName: 'Name',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.name}</div>;
        },
    },
    {
        field: 'description',
        headerName: 'Description',
        width: 300,
        renderCell: (params) => {
            return (
                <div className={`cellWithStatus `}>{params.row.description}</div>
            );
        },
    },
]

export const supplierColumns = [
    {
        field: 'id',
        headerName: 'ID',
        width: 50,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.id}</div>;
        },
    },
    {
        field: 'name',
        headerName: 'Name',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.name}</div>;
        },
    },
    {
        field: 'description',
        headerName: 'Description',
        width: 300,
        renderCell: (params) => {
            return (
                <div className={`cellWithStatus `}>{params.row.description}</div>
            );
        },
    },
]

export const translatorColumns = [
    {
        field: 'id',
        headerName: 'ID',
        width: 50,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.id}</div>;
        },
    },
    {
        field: 'fullName',
        headerName: 'Full Name',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.fullName}</div>;
        },
    },
    {
        field: 'gender',
        headerName: 'gender',
        width: 150,
        renderCell: (params) => {
            return (
                <div className={`cellWithStatus `}>{ENUM.EGender.getNameFromIndex(params.row.gender)}</div>
            );
        },
    },
]

export const oderColumns = [
    {
        field: 'id',
        headerName: 'ID',
        width: 50,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.id}</div>;
        },
    },
    // {

    //   width: 215  ,
    //   renderCell: (params) => {
    //     return (
    //       <div className="cellWithImg">
    //         {params.row.buy_date}
    //       </div>
    //     );
    //   },
    // },

    {
        field: 'customer',
        headerName: 'Customer',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.user.username}</div>;
        },
    },
    {
        field: 'gender',
        headerName: 'gender',
        width: 150,
        renderCell: (params) => {
            return (
                <div className={`cellWithStatus `}>{ENUM.EGender.getNameFromIndex(params.row.gender)}</div>
            );
        },
    },
    {
        field: 'phone',
        headerName: 'phone',
        width: 100,
        renderCell: (params) => {
            return <div className={`cellWithStatus`}>{params.row.phone}</div>;
        },
    },
    {
        field: 'address',
        headerName: 'Address',
        width: 450,
        renderCell: (params) => {
            return (
                <div className={`cellWithStatus`}>
                    {`${params.row.address.homeAdd}, ${params.row.address.ward}, ${params.row.address.district}, ${params.row.address.city}`}
                </div>
            );
        },
    },

    {
        field: 'totalPriceProduct',
        headerName: 'total price product',
        width: 120,
        renderCell: (params) => {
            return <div className={`cellWithStatus`}>{params.row.totalPriceProduct}</div>;
        },
    },

    {
        field: 'totalFee',
        headerName: 'transportFee',
        width: 120,
        renderCell: (params) => {
            return <div className={`cellWithStatus`}>{params.row.transportFee || 'Chưa có thông tin'}</div>;
        },
    },

    {
        field: 'totalPrice',
        headerName: 'totalPrice',
        width: 120,
        renderCell: (params) => {
            return <div className={`cellWithStatus`}>{params.row.totalPrice}</div>;
        },
    },

    {
        field: 'payment',
        headerName: 'Payment',
        width: 130,
        renderCell: (params) => {
            return (
                <div className={`cellWithStatus`}>{ENUM.EPayment.getNameFromIndex(params.row.payment)}</div>
            );
        },
    },
    {
      field: 'shippingMethod',
      headerName: 'shipping method',
      width: 130,
      renderCell: (params) => {
          return (
              <div className={`cellWithStatus`}>{ENUM.EShippingMethod.getNameFromIndex(params.row.shippingMethod)}</div>
          );
      },
  },
    {
        field: 'status',
        headerName: 'Status',
        width: 160,
        renderCell: (params) => {
            return <div className={`cellWithStatus`}>{params.row.status}</div>;
        },
    },
];
export const productColumns = [
    {
        field: 'id',
        headerName: 'ID',
        width: 50,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.id}</div>;
        },
    },
    {
        field: 'product',
        headerName: 'Product',
        width: 250,
        renderCell: (params) => {
            return (
                <div className="cellWithImg">
                    <img className="cellImg2" src={params.row.img} alt="avatar" />
                    {params.row.title}
                </div>
            );
        },
    },
    {
        field: 'originPrice',
        headerName: 'Origin Price',
        width: 100,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.originPrice}</div>;
        },
    },

    {
        field: 'category',
        headerName: 'Category',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.categoryName}</div>;
        },
    },
    {
        field: 'authors',
        headerName: 'Authors',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.authors.join(', ')}</div>;
        },
    },
    {
        field: 'supplier',
        headerName: 'Supplier',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.supplier}</div>;
        },
    },
    {
        field: 'publisher',
        headerName: 'Publisher',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.publisher}</div>;
        },
    },
    {
        field: 'translators',
        headerName: 'Translators',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.translators && params.row.translators.join(', ')}</div>;
        },
    },
    {
        field: 'amount',
        headerName: 'Amount',
        width: 80,
        renderCell: (params) => {
            return <div className={`cellWithStatus`}>{params.row.availableQuantity}</div>;
        },
    },
    {
        field: 'status',
        headerName: 'Status',
        width: 120,
        renderCell: (params) => {
            return (
                <div className={`cellWithStatus`}>
                    {ENUM.EProductStatus.getNameFromIndex(params.row?.status) ||
                        ENUM.EProductStatus.PRODUCT_TRADING.name}
                </div>
            );
        },
    },
];

export const userColumns = [
    {
        field: 'id',
        headerName: 'ID',
        width: 50,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.id}</div>;
        },
    },
    {
        field: 'image',
        headerName: 'Image',
        width: 200,
        renderCell: (params) => {
            return (
                <div className="cellWithImg">
                    <img className="cellImg2" src={params.row.avatar} alt="avatar" />
                    {params.row.title}
                </div>
            );
        },
    },
    {
        field: 'username',
        headerName: 'Username',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.username}</div>;
        },
    },

    {
        field: 'fullName',
        headerName: 'FullName',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.fullName}</div>;
        },
    },
    {
        field: 'email',
        headerName: 'Email',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.email}</div>;
        },
    },
    {
        field: 'phone',
        headerName: 'Phone',
        width: 150,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.phone}</div>;
        },
    },
    {
        field: 'birthDate',
        headerName: 'birthDate',
        width: 200,
        renderCell: (params) => {
            return <div className="cellWithImg">{params.row.birthDate}</div>;
        },
    },
    {
        field: 'gender',
        headerName: 'gender',
        width: 120,
        renderCell: (params) => {
            return <div className="cellWithImg">{ENUM.EGender.getNameFromIndex(params.row.gender)}</div>;
        },
    },
    {
        field: 'address',
        headerName: 'Address',
        width: 700,
        renderCell: (params) => {
            return <div className={`cellWithStatus`}>{JSON.stringify(params.row.address)}</div>;
        },
    },
    {
        field: 'status',
        headerName: 'Status',
        width: 120,
        renderCell: (params) => {
            return (
                <div className={`cellWithStatus`}>
                    {params.row.enabled ? "Hoạt động" : "Khóa"}
                </div>
            );
        },
    },
];

