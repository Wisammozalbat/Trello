function $(id) {
  return document.getElementById(id);
}

window.onload = function() {
  if (sessionStorage.getItem("token") == null || undefined) {
    alert("NO HAVE SESSION!");
    location.href = "dashboard.html";
  } else {
    let config = {
      method: "GET",
      withCredentials: true,
      credentials: "same-origin",
      headers: {
        "Content-type": "application/x-www-form-urlencoded",
        Authorization: "Bearer " + sessionStorage.getItem("token")
      }
    };

    fetch("./../projects", config)
      .then(res => res.json())
      .then(data => {
        console.log(data);
        if (data.status == 200 || data.status == 201) {
          data.data.forEach(element => {
            $("row").innerHTML += `
              <div class="card grey darken-1 carousel-item col s4">
              <div class="card-content white-text">
              <span class="card-title center">
              ${element.projectName}
              </span>
              <p class="center">
              ${element.projectDes}
              </p>
              </div>
              <div class="card-action center">
              <br>
              <a href="project.html?project_id='${element.projectId}'
              class="waves-effect waves-light btn lime">Acceder</a>
              </div>
              </div>`;
          });
        } else {
          alert(data.message + " Error:" + data.status);
          location.href = "dashboard.html";
        }
      });
  }
};

function createProject() {
  let body = {
      project_name: $("name_project").value,
      project_des: $("des_project").value
    },
    params = {
      method: "POST",
      headers: new Headers({
        "Content-Type": "application/json",
        Authorization: "Bearer " + sessionStorage.getItem("token")
      }),
      body: JSON.stringify(body)
    };
  fetch("./../project/createProject", params)
    .then(resp => resp.json())
    .then(data => {
      console.log(data);
      if (data.status == 200) {
        location.reload();
      } else {
        alert("Error al iniciar sesion, status:" + data.status);
      }
    });
}

function logout() {
  params = {
    method: "GET",
    headers: new Headers({
      "Content-type": "application/x-www-form-urlencoded"
    })
  };
  fetch("./../logout", params)
    .then(resp => resp.json())
    .then(data => {
      console.log(data);
      if (data.status == 200) {
        window.location = "./../index.html";
      } else {
        alert("Error al cerrar sesion, status:" + data.status);
      }
    });
}
$("modalbtn").addEventListener("click", createProject);
