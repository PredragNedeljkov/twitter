import axios from "../../api/axios";

function login(requestBody) {
    return axios.post('api/auth/signin', requestBody);
}

export {
    login
};