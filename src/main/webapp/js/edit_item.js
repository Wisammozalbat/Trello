let x = location.href.split("?")[1];
x = x.split("&");

function $(id) {
  return document.getElementById(id);
}

const create = element => {
  return document.createElement(element);
};

let getStatus = itemStatus => {
  switch (itemStatus) {
    case "1":
      console.log("uno");
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

let editItem = () => {
  fetch(`./../items?projectId=${x[0]}`, {
    method: "PUT",
    body: JSON.stringify({
      itemId: x[1],
      status: $("iStatus").options[$("iStatus").selectedIndex].value,
      itemName: $("iName").value,
      itemDes: $("iDesc").value
    }),
    headers: new Headers({
      "Content-Type": "application/x-www-form-urlencoded"
    })
  })
    .then(res => res.json())
    .then(res => {
      deleteData();
      window.location = `../views/items.html?${x[0]}`;
    });
};

window.onpageshow = async () => {
  let status = getStatus(localStorage.getItem("itemStat"));
  $("submit").innerHTML += `
     <input type="submit" class="btn" style="background: #8a9fbf" onClick="editItem()" value="submit" />
  `;
  $("content").innerHTML += `
  <a onClick="deleteData()" href="../views/items.html?${x[0]}">ir al items</a>
  `;
  $("iName").value = localStorage.getItem("itemName");
  $("iDesc").value = localStorage.getItem("itemDesc");
  $("dStatus").innerHTML = status;
  $("dStatus").value = localStorage.getItem("itemStat");
};
