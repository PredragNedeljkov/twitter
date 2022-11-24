import axios from "../../api/axios";


function saveNewPost(requestBody, token) {
    return axios.post('posts/', requestBody, {headers: {Authorization: token}});
}

export {
    saveNewPost
};