import React from 'react';
import './SignUpForm.scss';

function SignUpForm() {
    return (
        <>
                {/* <!-- form dang ky khi click vao button tren header--> */}
                <div className="modal fade mt-5" id="formdangky" data-backdrop="static" tabIndex="-1" aria-labelledby="dangky_tieude"
            aria-hidden="true">
            <div className="modal-dialog ">
                <div className="modal-content">
                    <div className="modal-header">
                        <ul className="tabs d-flex justify-content-around list-unstyled mb-0">
                            <li className="tab tab-dangnhap text-center">
                                <a className=" text-decoration-none">Đăng nhập</a>
                                <hr/>
                            </li>
                            <li className="tab tab-dangky text-center">
                                <a className="text-decoration-none">Đăng ký</a>
                                <hr/>
                            </li>
                        </ul>
                        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        <form id="form-signup" className="form-signin mt-2">
                            <div className="form-label-group">
                                <input type="text" className="form-control" placeholder="Nhập họ và tên" name="name" required
                                    autoFocus/>
                            </div>
                            <div className="form-label-group">
                                <input type="text" className="form-control" placeholder="Nhập số điện thoại" name="phone"
                                    required/>
                            </div>
                            <div className="form-label-group">
                                <input type="email" className="form-control" placeholder="Nhập địa chỉ email" name="email"
                                    required/>
                            </div>
                            <div className="form-label-group">
                                <input type="password" className="form-control" placeholder="Nhập mật khẩu" name="password"
                                    required/>
                            </div>
                            <div className="form-label-group mb-4">
                                <input type="password" className="form-control" name="confirm_password"
                                    placeholder="Nhập lại mật khẩu" required/>
                            </div>
                            <button className="btn btn-lg btn-block btn-signin text-uppercase text-white mt-3" type="submit"
                                style={{background: "#F5A623"}}>Đăng ký</button>
                            <hr className="mt-3 mb-2"/>
                            <div className="custom-control custom-checkbox">
                                <p className="text-center">Bằng việc đăng ký, bạn đã đồng ý với DealBook về</p>
                                <a href="#" className="text-decoration-none text-center" style={{color: "#F5A623"}}>Điều khoản dịch
                                    vụ & Chính sách bảo mật</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        </>
    )
}

export default SignUpForm;