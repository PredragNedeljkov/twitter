import React from 'react';
import NotFoundPage from "./NotFoundPage";
import Navbar from "./components/common/navbar/Navbar";
import { Route, Switch } from "react-router-dom";
import Login from "./components/login/Login";
import Register from  "./components/register/Register";
import {connect} from "react-redux";
import AlertsContainer from "./components/alerts/AlertsContainer";
import MyProfile from './components/profile/MyProfile';
import UserProfile from './components/profile/UserProfile';
import RegisterBusiness from './components/register/RegisterBusiness';
import PasswordForgotten from './components/password-forgotten/PasswordForgotten';
import PasswordChange from './components/password-change/PasswordChange';


function App(props) {
    props.setAuthData({
        userId: props.userId,
        role: props.role,
        userName: props.userName,
        userLastName: props.userLastName,
        token: props.token
    });

    return (
        <>
        <Navbar />
        <Switch>
            <Route path="/login" exact component={Login} />
            <Route path="/register" exact component={Register} />
            <Route path="/forgotten-password" exact component={PasswordForgotten} />
            <Route path="/password/change" exact component={PasswordChange} />
            <Route path="/register-business" exact component={RegisterBusiness} />
            <Route path="/my-profile" exact component={MyProfile} />
            <Route path="/profiles/:userId" exact component={UserProfile} />
            <Route path="/" exact component={MyProfile} />
            <Route component={NotFoundPage} />
        </Switch>
        <AlertsContainer />
    </>
    )
}

function mapDispatchToProps(dispatch) {
    return {
        setAuthData: (user) => {
            dispatch({
                type: 'SET_AUTH_FIELDS', token: user.token,
                userName: user.userName, role: user.role,
                userLastName: user.userLastName, userId: user.userId
            })
        }
    }
}

export default connect(null, mapDispatchToProps)(App);