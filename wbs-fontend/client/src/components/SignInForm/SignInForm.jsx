import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faGoogle } from '@fortawesome/free-brands-svg-icons';
import { brands } from '@fortawesome/fontawesome-svg-core/import.macro';
import './SignInForm.scss';
import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';


function SignInForm({ handleSubmit }) {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [data, setData] = useState({
        loginUserName: '',
        password: '',
        otp: false,
    });

    return (
        <>
            {/* <!-- form dang nhap khi click vao button tren header--> */}
            <div
                className="modal fade mt-5"
                id="formdangnhap"
                data-backdrop="static"
                tabIndex="-1"
                aria-labelledby="dangnhap_tieude"
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
                            <form
                                id="form-signin"
                                className="form-signin mt-2"
                                onSubmit={(e) => {
                                    e.preventDefault();
                                    handleSubmit(data);
                                }}
                            >
                                <div className="form-label-group">
                                    <input
                                        type="text"
                                        className="form-control"
                                        placeholder="Nhập số điện thoại hoặc email"
                                        name="loginUserName"
                                        required
                                        autoComplete="on"
                                        maxLength={128}
                                        autoFocus
                                        onChange={(e) => setData({ ...data, loginUserName: e.target.value })}
                                    />
                                </div>

                                <div className="form-label-group">
                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="Mật khẩu"
                                        name="password"
                                        required
                                        maxLength={32}
                                        onChange={(e) => setData({ ...data, password: e.target.value })}
                                    />
                                </div>

                                <div className="custom-control custom-checkbox mb-3">
                                    <input type="checkbox" className="custom-control-input" id="customCheck1" />
                                    <label className="custom-control-label" htmlFor="customCheck1">
                                        Nhớ mật khẩu
                                    </label>
                                    <a
                                        href="#"
                                        className="float-right text-decoration-none"
                                        style={{ color: '#F5A623' }}
                                    >
                                        Quên mật khẩu
                                    </a>
                                </div>

                                <button
                                    className="btn btn-lg btn-block btn-signin text-uppercase text-white"
                                    type="submit"
                                    style={{ background: '#F5A623' }}
                                >
                                    Đăng nhập
                                </button>
                                <hr className="my-4" />
                                <button className="btn btn-lg btn-google btn-block text-uppercase" type="submit">
                                    <FontAwesomeIcon icon={faGoogle} className="mr-2" />
                                    Đăng nhập bằng Google
                                </button>
                                <button className="btn btn-lg btn-facebook btn-block text-uppercase" type="submit">
                                    <FontAwesomeIcon icon={faFacebook} className="mr-2" /> Đăng nhập bằng Facebook
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default SignInForm;
