import axios from "../../api/axios";

function getUsers(token) {
    return axios.get('/users', {headers: {Authorization: token}})
}

function getUserById(userId, token) {
    return axios.get('/users/' + userId , {headers: {Authorization: token}})
}

function saveUser(requestBody, token) {
    return axios.post('/users/register', requestBody, {headers: {Authorization: token}})
}

function passwordForgotten(requestBody) {
    return axios.post('/api/auth/forgotten-password/', requestBody)
}

function changePassword(requestBody) {
    return axios.post('/api/auth/change-password/', requestBody)
}

function fetchUserData(userId, token) {
    return axios.get('/users/' + userId, {headers: {Authorization: token}});
}


export {
    getUsers,
    getUserById,
    saveUser,
    passwordForgotten,
    fetchUserData, 
    changePassword,
}