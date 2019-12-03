let x = location.href.split("?")[1];

function $(id) {
  return document.getElementById(id);
}

const create = element => {
  return document.createElement(element);
};

let redirect = (pId, iId) => {
  window.location = `../views/edit_item.html?${pId}&${iId}`;
};

let setData = (iName, iDesc, IStatus, iId, pId) => {
  localStorage.setItem("itemName", iName);
  localStorage.setItem("itemDesc", iDesc);
  localStorage.setItem("itemStat", IStatus);
  redirect(pId, iId);
};

let deleteItem = (projectId, itemId) => {
  let opc = prompt("Esta seguro que desea borrar el item? 1=Si 2=NO");
  if (opc == 1) {
    fetch(`./../items?projectId=${projectId}&itemId=${itemId}`, {
      method: "DELETE",
      headers: new Headers({
        "Content-Type": "application/x-www-form-urlencoded"
      })
    })
      .then(res => res.json())
      .then(res => {
        window.location = `../views/items.html?${projectId}`;
      });
    console.log("se borro el projecto " + projectId);
  } else if (opc == 2) {
  }
};

let getStatus = itemStatus => {
  switch (itemStatus) {
    case 1:
      return "Pendiente";
      break;
    case 2:
      return "En progreso";
      break;
    case 3:
      return "Culminado";
      break;
    default:
      break;
  }
};

window.onpageshow = () => {
  if (localStorage.length < 1) {
    window.location = "./../index.html";
  }
  $("newItem").addEventListener("click", () => {
    window.location = `../views/new_item.html?${x}`;
  });
  fetch(`./../items?projectId=${x}`, {
    method: "GET",
    headers: new Headers({
      "Content-Type": "application/x-www-form-urlencoded"
    })
  })
    .then(res => res.json())
    .then(res => {
      data = res.data;
      $("projectName").innerHTML = data.projectName;
      $("projectDesc").innerHTML = data.projectDes;
      if (data.items.length > 0) {
        addToFront(data);
      } else {
        $("projectContent").innerHTML =
          "No se poseen tasks aun para este projecto";
      }
      console.log(data);
    });
};

let addToFront = data => {
  data.items.map(i => {
    let statusDesc = getStatus(i.status);
    $("projectContent").innerHTML += `
      <div class="col l6 offset-l3 col m8 offset-m2 s12">
        <div class="card blue-gray darken-1" id="projectContainer">
          <div class="card-content black-text">
            <span class="card-title" align="center">${i.itemName}</span>

            <div class="itemContainer row center">
              <div class="itemDesc col s12">
                ${i.itemDes}
              </div>
            </div>

            <div class="card-action row">
              <div class="col l8 offset-l2 col s12 valign-wrapper">
                <h5 class="col s7">${statusDesc}</h5>
                <button onClick="setData('${i.itemName}','${i.itemDes}',${i.status}, ${i.itemId}, ${data.projectId})" class="col s2 waves-effect waves-light btn modal-trigger" data-target="idModal" href="#idModal" id="edit"><i class="large material-icons">edit</i></button>
                <button onClick="deleteItem(${data.projectId}, ${i.itemId})" class="col s2 waves-effect waves-light offset-s1 btn" id="delete"><i class="large material-icons">delete</i></button>
              </div>
            </div>
          </div>
        </div>
      </div>  
    `;
  });
};
