function ModalLoader(props) {
    if (props.isActive) {
        return (
            <div className="modal-dialog-centered position-fixed d-flex justify-content-center align-items-center w-100"
            style={{zIndex: 2000}}>
                <div className="spinner-border text-primary" style={{height: "4em", width: "4em"}} role="status">
                    <span className="sr-only">Loading...</span>
                </div>
            </div>
        )
    } else {
        return null;
    }
}

export default ModalLoader;