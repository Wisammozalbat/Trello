let x = location.href.split("?")[1];

function $(id) {
  return document.getElementById(id);
}

const create = element => {
  return document.createElement(element);
};

let getStatus = projectStatus => {
  switch (projectStatus) {
    case "1":
      return "Pendiente";
      break;
    case "2":
      return "En progreso";
      break;
    case "3":
      return "Culminado";
      break;
    default:
      break;
  }
};

let verStatus = () => {
  console.log($("iStatus").options[$("iStatus").selectedIndex].value);
};

let deleteData = () => {
  localStorage.removeItem("itemName");
  localStorage.removeItem("itemDesc");
  localStorage.removeItem("itemStat");
};

let editProject = () => {
  let user = JSON.parse(localStorage.getItem("data"));
  fetch(`./../projects?projectId=${x}`, {
    method: "PUT",
    body: JSON.stringify({
      projectName: $("pName").value,
      projectDes: $("pDesc").value,
      status: $("pStatus").options[$("pStatus").selectedIndex].value,
      projectId: x,
      userId: user.id
    }),
    headers: new Headers({
      "Content-Type": "application/x-www-form-urlencoded"
    })
  })
    .then(res => res.json())
    .then(res => {
      deleteData();
      window.location = `../views/dashboard.html`;
    });
};

window.onpageshow = async () => {
  let status = getStatus(localStorage.getItem("projectStat"));
  $("submit").innerHTML += `
     <input type="submit" class="btn" style="background: #8a9fbf" onClick="editProject()" value="submit" />
  `;
  $("content").innerHTML += `
  <a onClick="deleteData()" href="../views/dashboard.html">ir al dashboard</a>
  `;
  $("pName").value = localStorage.getItem("projectName");
  $("pDesc").value = localStorage.getItem("projectDesc");
  $("dStatus").innerHTML = status;
  $("dStatus").value = localStorage.getItem("projectStat");
};
