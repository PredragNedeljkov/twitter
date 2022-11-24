import axios from "../../api/axios";


function getProfileAndPosts(userId, token) {
    return axios.get('posts/user/' + userId, {headers: {Authorization: token}});
}

function saveNewComment(requestBody, token) {
    return axios.post('comments', requestBody, {headers: {Authorization: token}});
}

function likePost(requestBody, token) {
    return axios.post('posts/like', requestBody, {headers: {Authorization: token}});
}

function dislikePost(requestBody, token) {
    return axios.post('posts/dislike', requestBody, {headers: {Authorization: token}});
}

function getImagesURL() {
    return axios.defaults.baseURL + "upload/";
}

function followUser(requestBody, token) {
    return axios.post('following', requestBody, {headers: {Authorization: token}});
}

function acceptFollowingRequest(requestBody, token) {
    return axios.post('following/request/accept', requestBody, {headers: {Authorization: token}});
}

function declineFollowingRequest(requestBody, token) {
    return axios.post('following/request/decline', requestBody, {headers: {Authorization: token}});
}

export {
    getProfileAndPosts,
    saveNewComment,
    likePost,
    dislikePost,
    getImagesURL,
    followUser,
    acceptFollowingRequest,
    declineFollowingRequest
};