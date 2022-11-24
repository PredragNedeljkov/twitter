const initState = {
    token: null,
    role: null,
    userName: null,
    userId: null
};

const rootReducer = (state = initState, action) => {
    if (action.type === 'SET_AUTH_FIELDS') {
        return {
            ...state,
            token: 'Bearer ' + action.token,
            userName: action.userName,
            userLastName: action.userLastName,
            role: action.role,
            userId: action.userId
        }
    }
    return state;
};

export default rootReducer