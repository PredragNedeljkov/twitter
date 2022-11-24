import React from 'react';
import { Link } from "react-router-dom";

function NotFoundPage() {
    return (
        <div className="text-center h-75 d-flex flex-column justify-content-center">
            <h1 className="display-3 font-weight-bold text-white">Stranica nije pronađena</h1>
            <p>
                <Link to="/" type="button" className="btn btn-lg btn-primary mt-5">Nazad na početnu stranu</Link>
            </p>
        </div>
    )
}

export default NotFoundPage;
