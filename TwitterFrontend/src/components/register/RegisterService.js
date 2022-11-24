import axios from "../../api/axios";


function register(requestBody) {
    return axios.post('api/auth/signup', requestBody);
}

function registerBusinessUser(requestBody) {
    return axios.post('api/auth/signup-business', requestBody);
}

export {
    register,
    registerBusinessUser
};