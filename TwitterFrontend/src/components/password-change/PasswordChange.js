import {useEffect, useState} from 'react';
import {Redirect, useHistory, useParams, useLocation} from "react-router-dom";
import RetryError from '../common/errors/RetryError';
import Loader from "../common/loaders/Loader";
import ModalLoader from "../common/loaders/ModalLoader";
import { changePassword } from '../users/UserService';

function PasswordChange(props) {
    const search = useLocation().search;
    const searchParams = new URLSearchParams(search);

    const [formFields, setFormFields] = useState({
        'password': "",
        'token': searchParams.get('token')
    });
    const [serverError, setServerError] = useState(false);
    const [showError, setShowError] = useState(false);
    const [isLoaderShown, setLoaderShown] = useState(false);
    const [isSmallLoaderShown, setSmallLoaderShown] = useState(false);
    const history = useHistory();

    function handleChange(event) {
        setFormFields({
            ...formFields,
            [event.target.name]: event.target.value
        })
    }

    function handleChangePassword(event) {
        event.preventDefault();
        if (formFields.password !== formFields.repeatPassword) {
            setShowError(true);
            return;
        }
        setSmallLoaderShown(true);
        changePassword({
            password: formFields.password,
            token: formFields.token
        }).then(response => {
            if (response.status === 200) {
                history.push('/login');
            }
        }).catch(error => {
            setSmallLoaderShown(false);
            setServerError(true);
        })
    }

    function tryAgain() {
        setServerError(false);
    }
        
    if (serverError){
        return <RetryError isActive={true} retry={() => tryAgain()} />
    } else {
        return (
            <div className="form-signin-container text-center h-75 mt-5">
                    <form className="form-signin" onSubmit={event => handleChangePassword(event)}>
                        <img src='https://g.foolcdn.com/editorial/images/451536/twitter-bird-on-blue-background.png' style={{width: '100%'}} alt="header" />
                        <label htmlFor="inputPassword" className="sr-only">Lozinka</label>
                        <input type="password" id="inputPassword" className="form-control mb-0" placeholder="Lozinka"
                               required name="password" onChange={(event => handleChange(event))} />
                        <label htmlFor="inputRepeatPassword" className="sr-only">Ponovljena lozinka</label>
                        <input type="password" id="inputRepeatPassword" className="form-control mb-0" placeholder="Ponovite lozinku"
                               required name="repeatPassword" onChange={(event => handleChange(event))} />
                        <label htmlFor="token" className="sr-only">Token</label>
                        <input type="text" id="token" className="form-control" value={searchParams.get('token')} readOnly
                                name="token" />
                        {showError?<label className="text-danger">Lozinke se ne poklapaju!</label>:null}
                        <Loader isActive={isSmallLoaderShown} />
                        <button className="btn btn-lg btn-primary btn-block" type="submit">Promenite lozinku</button>
                    </form>
                <ModalLoader isActive={isLoaderShown} />
            </div>
    )}
}


export default PasswordChange;