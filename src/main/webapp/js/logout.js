function $(id){
    return document.getElementById(id);
}

function logout() {
    let params = {
        method: "GET",
        withCredentials: true,
        credentials: 'same-origin',
        headers: {
            "Content-type": "application/x-www-form-urlencoded"
        }
    }
    fetch('./../session/logout', params)
    .then(res => res.json())
    .then(data => {
        sessionStorage.clear();
        location.href = "dashboard.html";
    })
}