function createSuccessAlert(text) {
    let alert = document.createElement("div");
    alert.className = "alert show";
    alert.innerHTML = text;
    alert.style = 'background-color: green';
    document.getElementById("alerts-container").append(alert);
    setInterval(function() {
        alert.className = "alert hide";
        setInterval(function() {
            alert.remove();
        }, 500);
    }, 3000);
}

function createErrorAlert(text) {
    let alert = document.createElement("div");
    alert.className = "alert show";
    alert.innerHTML = text;
    alert.style = 'background-color: red';
    document.getElementById("alerts-container").append(alert);
    setInterval(function() {
        alert.className = "alert hide";
        setInterval(function() {
            alert.remove();
        }, 500);
    }, 3000);
}

export {
    createSuccessAlert,
    createErrorAlert
};