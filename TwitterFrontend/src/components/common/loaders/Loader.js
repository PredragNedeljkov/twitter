function Loader(props) {
    if (props.isActive) {
        return (
            <div className="text-center w-100 mb-2">
                <div className="spinner-border text-primary" style={{height: "2em", width: "2em"}} role="status">
                    <span className="sr-only">Loading...</span>
                </div>
            </div>
        )
    } else {
        return null;
    }
}

export default Loader;