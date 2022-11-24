function RetryError(props) {
    if (props.isActive) {
        return(
            <div className="modal-dialog-centered flex-column position-fixed d-flex justify-content-center align-items-center w-100">
                <h2><span className="text-danger">Ups...</span><span>Greška se dogodila</span></h2>
                <div>
                    <button className="btn btn-primary" onClick={() => props.retry()}>Pokušajte ponovo</button>
                </div>
            </div>
        )} else {
            return null
        }
}

export default RetryError;