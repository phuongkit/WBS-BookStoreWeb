import React from 'react';
import './SignInForm.scss';

function SignInForm() {
    return (
        <>
                {/* <!-- form dang nhap khi click vao button tren header--> */}
                <div className="modal fade mt-5" id="formdangnhap" data-backdrop="static" tabIndex="-1"
            aria-labelledby="dangnhap_tieude" aria-hidden="true">
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
                        <form id="form-signin" className="form-signin mt-2">
                            <div className="form-label-group">
                                <input type="email" className="form-control" placeholder="Nhập địa chỉ email" name="email"
                                    required autoFocus/>
                            </div>
    
                            <div className="form-label-group">
                                <input type="password" className="form-control" placeholder="Mật khẩu" name="password" required/>
                            </div>
    
                            <div className="custom-control custom-checkbox mb-3">
                                <input type="checkbox" className="custom-control-input" id="customCheck1"/>
                                <label className="custom-control-label" htmlFor="customCheck1">Nhớ mật khẩu</label>
                                <a href="#" className="float-right text-decoration-none" style={{color: "#F5A623"}}>Quên mật
                                    khẩu</a>
                            </div>
    
                            <button className="btn btn-lg btn-block btn-signin text-uppercase text-white" type="submit"
                                style={{background: "#F5A623"}}>Đăng nhập</button>
                            <hr className="my-4"/>
                            <button className="btn btn-lg btn-google btn-block text-uppercase" type="submit"><i
                                    className="fab fa-google mr-2"></i> Đăng nhập bằng Google</button>
                            <button className="btn btn-lg btn-facebook btn-block text-uppercase" type="submit"><i
                                    className="fab fa-facebook-f mr-2"></i> Đăng nhập bằng Facebook</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        </>
    );
}

export default SignInForm;