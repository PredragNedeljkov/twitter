import {useEffect, useState} from 'react';
import {Redirect, useHistory, useParams} from "react-router-dom";
import Loader from "../common/loaders/Loader";
import ModalLoader from "../common/loaders/ModalLoader";
import { passwordForgotten } from '../users/UserService';

function PasswordForgotten(props) {
    const [formFields, setFormFields] = useState({
        'email': "",
    });
    const [serverError, setServerError] = useState(false);
    const [isLoaderShown, setLoaderShown] = useState(false);
    const [isSmallLoaderShown, setSmallLoaderShown] = useState(false);
    const [isUserFetched, setUserFetched] = useState(false);
    const history = useHistory();

    function handleChange(event) {
        setFormFields({
            ...formFields,
            [event.target.name]: event.target.value
        })
    }

    function handleChangePassword(event) {
        event.preventDefault();
        setSmallLoaderShown(true);
        passwordForgotten({
            email: formFields.email
        }, props.token).then(response => {
            if (response.status === 200) {
                history.push('/login');
            }
        }).catch(error => {
            setSmallLoaderShown(false);
            setServerError(true);
        })
    }


        
    return (
        <div className="form-signin-container text-center h-75">
                <form className="form-signin mt-5" onSubmit={event => handleChangePassword(event)}>
                    <img src='https://g.foolcdn.com/editorial/images/451536/twitter-bird-on-blue-background.png' style={{width: '100%'}} alt="header" />
                    <label htmlFor="inputPassword" className="sr-only">Lozinka</label>
                    <input type="email" id="email" className="form-control" placeholder="Email"
                            required name="email" onChange={(event => handleChange(event))} />
                    <Loader isActive={isSmallLoaderShown} />
                    <button className="btn btn-lg btn-primary btn-block" type="submit">Pošaljite zahtev</button>
                </form>
            <ModalLoader isActive={isLoaderShown} />
        </div>
    )
}


export default PasswordForgotten;