import axios from 'axios'
import Cookies from "js-cookie";

const request = axios.create({
    baseURL:"http://localhost:9090/api",
    timeout:5000
});

request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    const authInfoJson = Cookies.get("authInfo")
    if(authInfoJson){
        // 设置请求头
        config.headers['token'] = JSON.parse(authInfoJson).token;
    }
    return config
},error => {
    return Promise.reject(error)
});

request.interceptors.response.use(
    response => {
        let res = response.data;
        if(typeof res === 'string'){
            res = res ? JSON.parse(res):res
        }
        return res;
    },
    error => {
        console.log('err'+error)
        return Promise.reject(error)
    }
)

export default request