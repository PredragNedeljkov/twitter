import { useState} from "react";
import { Link, useHistory } from "react-router-dom";
import ModalLoader from "../common/loaders/ModalLoader";
import {createErrorAlert, createSuccessAlert} from "../../alertHelper";
import { register } from "./RegisterService";

function Register(props) {
    const newUserInitialState = {
        name: '',
        surname: '',
        email: '',
        userName: '',
        password: '',
        repeatedPassword: '',
    };
    const [isLoaderActive, setLoaderActive] = useState(false);
    const [newUser, setNewUser] = useState(newUserInitialState);
    const [formError, setFormError] = useState(null);
    const [buttonDisabled, setButtonDisabled] = useState(false);
    const history = useHistory();

    function handleChange(event) {
        setNewUser({
           ...newUser,
            [event.target.name]: event.target.value
        });
    }

    function handleFormSubmit(event) {
        event.preventDefault();
        if (newUser.password !== newUser.repeatedPassword) {
            setFormError("Unete lozinke nisu iste");
            return;
        }
        setLoaderActive(true);
        setButtonDisabled(true);
        setFormError(null);
        register({
            name: newUser.name,
            lastName: newUser.surname,
            email: newUser.email,
            username: newUser.userName,
            password: newUser.password,
        }, props.token).then(response => {
            createSuccessAlert("Korisnik sačuvan");
            history.push('/login')
        }).catch(error => {
            if (error.response.status === 400) {
                setFormError("Korisničko ime već postoji");
                return;
            }
            createErrorAlert("Korisnik nije sačuvan")
        }).finally(() => {
            setLoaderActive(false);
            setButtonDisabled(false);
        });
    }

    
    return (
        <div className="form-signin-container text-center h-75">
            <ModalLoader isActive={isLoaderActive} />
            <form className="form-signin mt-5" onSubmit={event => handleFormSubmit(event)}>
            <img src='https://g.foolcdn.com/editorial/images/451536/twitter-bird-on-blue-background.png' style={{width: '100%'}} alt="header" />
                <input type="text" id="inputName" className="form-control" placeholder="Ime" required
                        autoFocus name="name" value={newUser.name} onChange={event => handleChange(event)}/>
                <input type="text" id="inputSurname" className="form-control" placeholder="Prezime" required
                        name="surname" value={newUser.surname} onChange={event => handleChange(event)} />
                <input type="text" id="inputEmail" className="form-control" placeholder="Email" required
                        name="email" value={newUser.email} onChange={event => handleChange(event)} />
                <input type="text" id="inputUsername" className="form-control" placeholder="Korisničko ime" required
                        name="userName" value={newUser.userName} onChange={event => handleChange(event)} />
                <input type="password" id="inputPassword" className="form-control m-0" placeholder="Lozinka"
                        required value={newUser.password} name="password" onChange={event => handleChange(event)} />
                <input type="password" id="inputRepeatPassword" className="form-control m-0" placeholder="Ponovite lozinku"
                        required name="repeatedPassword" value={newUser.repeatedPassword} onChange={event => handleChange(event)} />
                {formError?<label className="text-danger">{formError}</label>:null}
                <button className="btn btn-lg btn-primary btn-block" type="submit" disabled={buttonDisabled}>Registrujte se</button>

                <div className="text-center text-white mt-2">
                    Vodite biznis? Registrujte se kao bizis korisnik <Link to={"/register-business"}>Registracija</Link>
                </div>
            </form>

        </div>
    );
}

export default Register;