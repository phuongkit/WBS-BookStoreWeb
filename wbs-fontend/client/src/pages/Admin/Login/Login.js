import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Login.scss';
import { postLogin } from '../../../redux/user/userApi';
import { useDispatch } from 'react-redux';
import { checkRegex, MESSAGE } from '../../../utils';

const Login = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [data, setData] = useState({
        loginUserName: '',
        password: '',
        otp: false,
    });
    const handleLogin = async () => {
        console.log(data);
        let regexType = checkRegex(data.loginUserName);
        let auth = {
            otp: data.otp,
            password: data.password,
        };
        if (regexType === 0) {
            auth.phone = data.loginUserName;
        } else if (regexType === 1) {
            auth.email = data.loginUserName;
        } else {
            alert(MESSAGE.LOGIN_USER_NAME_NOT_MATCH);
            return;
        }
        postLogin(dispatch, auth, navigate);
    }
    return (
        <section className="tw-h-screen gradient-custom">
            <div className="container py-5 h-100">
                <div className="row d-flex justify-content-center align-items-center h-100">
                    <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                        <div className="card bg-dark text-white" style={{ borderRadius: '1rem' }}>
                            <div className="card-body p-5 text-center">
                                <div className="md-5 mt-md-4 pb-5">
                                    <h2 className="fw-bold mb-2 text-uppercase">Login Admin</h2>
                                    <p className="text-white-50 mb-5">Please enter your login and password!</p>

                                    <div className="form-outline form-white mb-4">
                                        <input
                                            placeholder="Email"
                                            type="email"
                                            id="typeEmailX"
                                            className="form-control form-control-lg tw-rounded"
                                            onChange={e => setData({...data, loginUserName: e.target.value})}
                                        />
                                    </div>

                                    <div className="form-outline form-white mb-4">
                                        <input
                                            placeholder="Password"
                                            type="password"
                                            id="typePasswordX"
                                            className="form-control form-control-lg tw-rounded"
                                            onChange={e => setData({...data, password: e.target.value})}
                                        />
                                    </div>

                                    <p className="small mb-5 pb-lg-2">
                                        <a className="text-white-50" href="/">
                                            Forgot password?
                                        </a>
                                    </p>

                                    <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleLogin}>
                                        Login
                                    </button>

                                    <div className="d-flex justify-content-center text-center mt-4 pt-1">
                                        <a href="" className="text-white">
                                            <i className="fab fa-facebook-f fa-lg"></i>
                                        </a>
                                        <a href="" className="text-white">
                                            <i className="fab fa-twitter fa-lg mx-4 px-2"></i>
                                        </a>
                                        <a href="" className="text-white">
                                            <i className="fab fa-google fa-lg"></i>
                                        </a>
                                    </div>
                                </div>

                                <div>
                                    <p className="mb-0">
                                        Don't have an account?{' '}
                                        {/* <a href="#!" className="text-white-50 fw-bold">
                                            Sign Up
                                        </a> */}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default Login;
