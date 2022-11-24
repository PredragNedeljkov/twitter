import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { BrowserRouter as Router } from 'react-router-dom';
import {Provider} from "react-redux";
import {createStore} from "redux";
import rootReducer from "./reducers/rootReducer";

let token = localStorage.getItem('Token');
let userName = localStorage.getItem('userName');
let userLastName = localStorage.getItem('userLastName');
let userId = localStorage.getItem('userId');

const store = createStore(rootReducer);
ReactDOM.render(
    <React.StrictMode>
        <Router>
            <Provider store={store}><App token={token} userName={userName} userId={userId} userLastName={userLastName} /></Provider>
        </Router>
    </React.StrictMode>,
    document.getElementById('root')
);


