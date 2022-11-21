import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import './Account.scss';
import LocationForm from '../../components/LocationForm';
import validate from 'jquery-validation';
import { getUserByToken } from '../../redux/user/userApi';
import { useEffect } from 'react';
import { getPrice, toAddressSlug } from '../../utils/utils';
import Select from 'react-select';
import { ENUM, EOrderStatus } from '../../utils/variableDefault';
import { getAllOrdersByUserId } from '../../redux/order/ordersApi';
import Paging from '../../components/Paging';

var $ = require('jquery');

function Account() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [addressOption, setAddresOption] = useState({
        ward: '',
        district: '',
        city: '',
    });
    const token = localStorage.getItem('token');
    if (!token) {
        navigate('/');
    }
    const user = token
        ? localStorage.getItem('user') !== null
            ? JSON.parse(localStorage.getItem('user'))
            : useSelector((state) => state.users.auth.currentUser)
        : undefined;
    const { content: orders = [], ...page } = useSelector((state) => state.orders.pageOrder.data);
    useEffect(() => {
        if (token && !user) {
            getUserByToken(dispatch);
        }
    }, [token]);
    useEffect(() => {
        if (user && user?.id) {
            getAllOrdersByUserId(dispatch, user.id);
        }
    }, []);
    return (
        <>
            {/* <!-- nội dung của trang  --> */}
            <section className="account-page my-3">
                <div className="container">
                    <div className="page-content bg-white">
                        <div className="account-page-tab-content m-4">
                            {/* <!-- 2 tab: thông tin tài khoản, danh sách đơn hàng  --> */}
                            <nav>
                                <div className="nav nav-tabs" id="nav-tab" role="tablist">
                                    <a
                                        className="nav-item nav-link active"
                                        id="nav-taikhoan-tab"
                                        data-toggle="tab"
                                        href="#nav-taikhoan"
                                        role="tab"
                                        aria-controls="nav-home"
                                        aria-selected="true"
                                    >
                                        Thông tin tài khoản
                                    </a>
                                    <a
                                        className="nav-item nav-link"
                                        id="nav-donhang-tab"
                                        data-toggle="tab"
                                        href="#nav-donhang"
                                        role="tab"
                                        aria-controls="nav-profile"
                                        aria-selected="false"
                                    >
                                        Danh sách đơn hàng
                                    </a>
                                </div>
                            </nav>

                            {/* <!-- nội dung 2 tab --> */}
                            <div className="tab-content">
                                {/* <!-- nội dung tab 1: thông tin tài khoản  --> */}
                                <div
                                    className="tab-pane fade show active pl-4 "
                                    id="nav-taikhoan"
                                    role="tabpanel"
                                    aria-labelledby="nav-taikhoan-tab"
                                >
                                    <div className="offset-md-4 mt-3">
                                        <h3 className="account-header">Thông tin tài khoản</h3>
                                    </div>
                                    <div className="hoten my-3">
                                        <div className="row">
                                            <label className="col-md-2 offset-md-2" htmlFor="account-lastname">
                                                Họ
                                            </label>
                                            <input
                                                className="col-md-4"
                                                type="text"
                                                name="account-lastname"
                                                defaultValue={user?.lastName}
                                            />
                                        </div>
                                    </div>
                                    <div className="hoten my-3">
                                        <div className="row">
                                            <label className="col-md-2 offset-md-2" htmlFor="account-firstname">
                                                Tên
                                            </label>
                                            <input
                                                className="col-md-4"
                                                type="text"
                                                name="account-firstname"
                                                defaultValue={user?.firstName}
                                            />
                                        </div>
                                    </div>
                                    <div className="email my-3">
                                        <div className="row">
                                            <label className="col-md-2 offset-md-2" htmlFor="account-phone">
                                                Só điện thoại
                                            </label>
                                            <input
                                                className="col-md-4"
                                                type="text"
                                                name="account-phone"
                                                disabled="disabled"
                                                defaultValue={user.phone}
                                            />
                                        </div>
                                    </div>
                                    <div className="email my-3">
                                        <div className="row">
                                            <label className="col-md-2 offset-md-2" htmlFor="account-email">
                                                Địa chỉ email
                                            </label>
                                            <input
                                                className="col-md-4"
                                                type="email"
                                                name="account-email"
                                                disabled="disabled"
                                                defaultValue={user.email}
                                            />
                                        </div>
                                    </div>
                                    <div className="email my-3">
                                        <div className="row">
                                            <label className="col-md-2 offset-md-2" htmlFor="account-home-add">
                                                Giới tính
                                            </label>
                                            <div className="select-container flex tw-min-w-[33.333333%]">
                                                <Select
                                                    name="cityId"
                                                    options={[
                                                        {
                                                            value: ENUM.EGender.FEMALE.index,
                                                            label: ENUM.EGender.FEMALE.name,
                                                        },
                                                        {
                                                            value: ENUM.EGender.MALE.index,
                                                            label: ENUM.EGender.MALE.name,
                                                        },
                                                        {
                                                            value: ENUM.EGender.UNKNOWN.index,
                                                            label: ENUM.EGender.UNKNOWN.name,
                                                        },
                                                    ]}
                                                    onChange={(option) => {}}
                                                    placeholder="Giới tính"
                                                    defaultValue={{
                                                        value: user?.gender || ENUM.EGender.UNKNOWN.index,
                                                        label:  ENUM.EGender.getNameFromIndex(user?.gender),
                                                    }}
                                                    className="tw-min-w-[33.333333%]"
                                                    required
                                                />
                                            </div>
                                        </div>
                                    </div>
                                    <div className="email my-3">
                                        <div className="row">
                                            <label className="col-md-2 offset-md-2" htmlFor="account-home-add">
                                                Địa chỉ
                                            </label>
                                            <input
                                                className="col-md-4"
                                                type="text"
                                                name="account-home-add"
                                                defaultValue={user.address?.homeAdd}
                                            />
                                        </div>
                                    </div>
                                    <div className="email my-3">
                                        <div className="row">
                                            <label className="col-md-2 offset-md-2" htmlFor="account-address">
                                                {}
                                            </label>
                                            <LocationForm
                                                name="account-address"
                                                className="tw-mb-4 tw-min-w-[33.333333%]"
                                                onChange={setAddresOption}
                                                address={toAddressSlug(user?.address)}
                                            />
                                        </div>
                                    </div>
                                    <div className="checkbox-change-pass my-3">
                                        <div className="row">
                                            <input
                                                type="checkbox"
                                                name="changepass"
                                                id="changepass"
                                                className="offset-md-4"
                                                style={{ marginTop: '6px', marginRight: '5px' }}
                                            />
                                            <label htmlFor="changepass">Thay đổi mật khẩu</label>
                                        </div>
                                    </div>
                                    <div className="thay-doi-mk">
                                        <div className="mkcu my-3">
                                            <div className="row">
                                                <label className="col-md-2 offset-md-2" htmlFor="account-mkcu">
                                                    Mật khẩu cũ
                                                </label>
                                                <input className="col-md-4" type="text" name="account-mkcu" />
                                            </div>
                                        </div>
                                        <div className="mkmoi my-3">
                                            <div className="row">
                                                <label className="col-md-2 offset-md-2" htmlFor="account-mkmoi">
                                                    Mật khẩu mới
                                                </label>
                                                <input className="col-md-4" type="text" name="account-mkmoi" />
                                            </div>
                                        </div>
                                        <div className="xacnhan-mkmoi my-3">
                                            <div className="row">
                                                <label className="col-md-2 offset-md-2" htmlFor="account-xacnhan-mkmoi">
                                                    Xác nhận mật khẩu
                                                </label>
                                                <input className="col-md-4" type="text" name="account-xacnhan-mkmoi" />
                                            </div>
                                        </div>
                                        <div className="capnhat my-3">
                                            <div className="row">
                                                <button
                                                    type="button"
                                                    className="button-capnhat text-uppercase offset-md-4 btn btn-warning mb-4"
                                                >
                                                    Cập nhật
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                {/* <!-- nội dung tab 2: danh sách đơn hàng --> */}
                                <div
                                    className="tab-pane fade py-3"
                                    id="nav-donhang"
                                    role="tabpanel"
                                    aria-labelledby="nav-donhang-tab"
                                >
                                    <div className="donhang-table table-responsive-xl">
                                        <table className="table table-striped m-auto">
                                            <tr>
                                                <th>Mã đơn hàng</th>
                                                <th>Ngày mua</th>
                                                <th>Sản phẩm</th>
                                                <th>Tổng tiền</th>
                                                <th>Trạng thái đơn hàng</th>
                                                <th>Phương thức thanh toán</th>
                                                <th>Phương thức vận chuyển</th>
                                            </tr>
                                            <tbody>
                                                {orders &&
                                                    orders.map((order, index) => (
                                                        <tr>
                                                            <td>{order.id}</td>
                                                            <td>{order.createdAt}</td>
                                                            <td>
                                                                {order.orderItems &&
                                                                    order.orderItems
                                                                        .map((item) => item.product?.name)
                                                                        .toString()}
                                                            </td>
                                                            <td>{getPrice(order.totalPrice)} đ</td>
                                                            <td>{EOrderStatus.getNameFromIndex(order.status)}</td>
                                                            <td>{ENUM.EPayment.getNameFromIndex(order.payment)}</td>
                                                            <td>
                                                                {ENUM.EShippingMethod.getNameFromIndex(
                                                                    order.shippingMethod,
                                                                )}
                                                            </td>
                                                        </tr>
                                                    ))}
                                            </tbody>
                                        </table>
                                    </div>
                                    <Paging totalPages={page.totalPages} currentPage={page.number} />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </>
    );
}

export default Account;
