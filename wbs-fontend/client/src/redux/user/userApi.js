import { authService } from '../../services/auth.service';
import { login,logout } from './userSlice';
export const PostLogin = async (dispatch, data, Navigate) => {

    try {
        let res = await authService.postLogin(data);
        localStorage.setItem('access',JSON.stringify(res.data.accessToken))
        if(res.data.role===2){
            Navigate('/')
        }else if(res.data.role===0){
            Navigate('/Admin')
        }else{
            Navigate('/Seller')
        }
        dispatch(login(res.data));
    } catch (error) {
        console.error(error)
    }
};
export const PostRegister = async (dispatch, data, Navigate) => {
    try {
        let res = await authService.postRegister(data);
        console.log(res.data)
        localStorage.setItem('access',JSON.stringify(res.data.accessToken))
        if(res.data.role===2){
            Navigate('/')
        }else if(res.data.role===0){
            Navigate('/Admin')
        }else{
            Navigate('/Seller')
        }
        dispatch(login(res.data));
    } catch (error) {
        console.error(error)
    }
    
};
