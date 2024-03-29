import axios from "axios";

const instance = axios.create({
        baseURL: 'https://localhost:8080/',
        timeout: 5000
    }
);
instance.interceptors.response.use(response => {
    return response;
}, error => {
    console.log(error.response)
    if (error.response.status === 401 && error.response.config.url !== 'api/auth/signin') {
        window.location.href = '/login';
    } else if (error.response.status === 404) {
        window.location.href = '/error';
    }
    return Promise.reject(error);
});

export default instance;