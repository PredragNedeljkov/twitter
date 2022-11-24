import {Link} from "react-router-dom";
import React from "react";
import {faSignInAlt, faSignOutAlt, faUserPlus, faUser} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function NavbarAuth(props) {

    if (props.userName) {
        return (
            <ul className="navbar-nav">
                <li className="nav-item">
                    <Link to="/my-profile" className="nav-link"><FontAwesomeIcon className="h5 mr-2 mb-0" icon={faUser} />Moj profil</Link>
                </li>
                <li className="nav-link" style={{cursor: 'pointer'}} onClick={() => props.handleLogout()}>
                    <FontAwesomeIcon className="h5 mr-2 mb-0" icon={faSignOutAlt} />Izlogujte se
                </li>
            </ul>
        )
    } else {
        return (
            <ul className="navbar-nav">
                <li className="nav-item">
                    <Link to="/register" className="nav-link"><FontAwesomeIcon className="h5 mr-2 mb-0" icon={faUserPlus} />Registrujte se</Link>
                </li>
                <li className="nav-item">
                    <Link to="/login" className="nav-link"><FontAwesomeIcon className="h5 mr-2 mb-0" icon={faSignInAlt} />Prijavite se</Link>
                </li>
            </ul>
        )
    }
}

export default NavbarAuth;