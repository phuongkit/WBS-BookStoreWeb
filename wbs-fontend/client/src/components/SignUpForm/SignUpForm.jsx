import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import './SignUpForm.scss';
import validate from 'jquery-validation';
import { postRegister } from '../../redux/user/userApi';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faGoogle } from '@fortawesome/free-brands-svg-icons';
import { GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL } from '../../utils/constants';

var $ = require( "jquery" );
function SignUpForm() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [data, setData] = useState({ phone: '', email: '', password: '' });
    const handleSubmit = async(e) => {
        e.preventDefault();
        postRegister(dispatch, data);
        navigate('/');
    }
    // validate
    $('#form-signup').validate({
        rules: {
            name: {
                required: true,
            },
            phone: {
                required: true,
                minlength: 8,
            },
            password: {
                required: true,
                minlength: 5,
            },
            confirm_password: {
                required: true,
                minlength: 5,
                equalTo: '#inputPassword',
            },
            email: {
                required: true,
                email: true,
            },
        },
        messages: {
            name: {
                required: 'Vui lòng nhập họ và tên',
            },
            phone: {
                required: 'Vui lòng nhập số điện thoại',
                minlength: 'Số máy quý khách vừa nhập là số không có thực',
            },
            password: {
                required: 'Vui lòng nhập mật khẩu',
                minlength: 'Vui lòng nhập ít nhất 5 kí tự',
            },
            confirm_password: {
                required: 'Vui lòng nhập lại mật khẩu',
                minlength: 'Vui lòng nhập ít nhất 5 kí tự',
                equalTo: 'Mật khẩu không trùng',
            },
            email: {
                required: 'Vui lòng nhập email',
                minlength: 'Email không hợp lệ',
                email: 'Vui lòng nhập email',
            },
        },
    });
    return (
        <>
            {/* <!-- form dang ky khi click vao button tren header--> */}
            <div
                className="modal fade mt-5"
                id="formdangky"
                data-backdrop="static"
                tabIndex="-1"
                aria-labelledby="dangky_tieude"
                aria-hidden="true"
            >
                <div className="modal-dialog ">
                    <div className="modal-content">
                        <div className="modal-header">
                            <ul className="tabs d-flex justify-content-around list-unstyled mb-0">
                                <li className="tab tab-dangnhap text-center">
                                    <a className=" text-decoration-none">Đăng nhập</a>
                                    <hr />
                                </li>
                                <li className="tab tab-dangky text-center">
                                    <a className="text-decoration-none">Đăng ký</a>
                                    <hr />
                                </li>
                            </ul>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <form id="form-signup" className="form-signin mt-2" onSubmit={handleSubmit}>
                                <div className="form-label-group">
                                    <input
                                        type="text"
                                        className="form-control"
                                        placeholder="Nhập họ và tên"
                                        name="name"
                                        required
                                        autoFocus
                                    />
                                </div>
                                <div className="form-label-group">
                                    <input
                                        type="text"
                                        className="form-control"
                                        placeholder="Nhập số điện thoại"
                                        name="phone"
                                        onChange={(e) => setData({ ...data, phone: e.target.value })}
                                        required
                                    />
                                </div>
                                <div className="form-label-group">
                                    <input
                                        type="email"
                                        className="form-control"
                                        placeholder="Nhập địa chỉ email"
                                        name="email"
                                        onChange={(e) => setData({ ...data, email: e.target.value })}
                                        required
                                    />
                                </div>
                                <div className="form-label-group">
                                    <input
                                        id="inputPassword"
                                        type="password"
                                        className="form-control"
                                        placeholder="Nhập mật khẩu"
                                        name="password"
                                        onChange={(e) => setData({ ...data, password: e.target.value })}
                                        required
                                    />
                                </div>
                                <div className="form-label-group mb-4">
                                    <input
                                        type="password"
                                        className="form-control"
                                        name="confirm_password"
                                        placeholder="Nhập lại mật khẩu"
                                        required
                                    />
                                </div>
                                <button
                                    className="btn btn-lg btn-block btn-signin text-uppercase text-white mt-3"
                                    type="submit"
                                    style={{ background: '#F5A623' }}
                                >
                                    Đăng ký
                                </button>
                                <hr className="mt-3 mb-2" />
                                <a href={GOOGLE_AUTH_URL} className="btn btn-lg btn-google btn-block text-uppercase" type="submit">
                                    <FontAwesomeIcon icon={faGoogle} className="mr-2" />
                                    Đăng nhập bằng Google
                                </a>
                                <a href={FACEBOOK_AUTH_URL} className="btn btn-lg btn-facebook btn-block text-uppercase" type="submit">
                                    <FontAwesomeIcon icon={faFacebook} className="mr-2" /> Đăng nhập bằng Facebook
                                </a>
                                <hr className="mt-3 mb-2" />                                
                                <div className="custom-control custom-checkbox">
                                    <p className="text-center">Bằng việc đăng ký, bạn đã đồng ý với DealBook về</p>
                                    <a
                                        href="#"
                                        className="text-decoration-none text-center"
                                        style={{ color: '#F5A623' }}
                                    >
                                        Điều khoản dịch vụ & Chính sách bảo mật
                                    </a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default SignUpForm;
