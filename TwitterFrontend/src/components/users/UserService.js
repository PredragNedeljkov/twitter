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

function changePassword(userId, requestBody, token) {
    return axios.patch('/auth/change-password/' + userId, requestBody, {headers: {Authorization: token}})
}

function fetchUserData(userId, token) {
    return axios.get('/users/' + userId, {headers: {Authorization: token}});
}

function saveUserProfile(requestBody, userId, token) {
    return axios.put('/users/' + userId, requestBody, {headers: {Authorization: token}});
}

export {
    getUsers,
    getUserById,
    saveUser,
    changePassword,
    fetchUserData, 
    saveUserProfile
}