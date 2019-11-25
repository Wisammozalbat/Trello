function $(id){
    return document.getElementById(id);
}

function register(){
    let body={
        name:$('name').value,
        username:$('uname').value,
        password:$('pword').value
    },
        params={
                method: "POST", 
                headers: new Headers({'Content-Type': 'application/json'}), 
                body:JSON.stringify(body) 
    };
    fetch("./../register/createUser", params)
    .then(resp => resp.json())
    .then(data => {
        console.log(data);
        if (data.status==200){
            location.href = "login.html";
        }else{
            alert("Error al iniciar sesion, status:"+data.status);
        }
    });
}

$('regbtn').addEventListener('click',register);