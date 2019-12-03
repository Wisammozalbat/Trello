let x = location.href.split("?")[1];

function $(id) {
  return document.getElementById(id);
}

let createProject = () => {
  fetch(`./../projects`, {
    method: "POST",
    body: JSON.stringify({
      projectName: $("pName").value,
      projectDes: $("pDesc").value
    }),
    headers: new Headers({
      "Content-Type": "application/x-www-form-urlencoded"
    })
  })
    .then(res => res.json())
    .then(res => {
      window.location = `../views/dashboard.html`;
    });
};

window.onpageshow = async () => {
  if (localStorage.length < 1) {
    window.location = "./../index.html";
  }
  $("submit").innerHTML += `
    <input type="submit" class="btn" style="background: #8a9fbf" onClick="createProject()" value="submit" />
 `;
  $("content").innerHTML += `
    <a href="../views/dashboard.html">ir al dashboard</a>
  `;
};
