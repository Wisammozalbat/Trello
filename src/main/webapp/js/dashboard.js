function $(id) {
  return document.getElementById(id);
}
function progressTask(items) {
  let count = 0;
  items.map(i => {
    if (i.status === 3) {
      count++;
    }
  });
  return count;
}

const create = element => {
  return document.createElement(element);
};

let showDetails = id_project => {
  window.location = `../views/items.html?${id_project}`;
};

window.onpageshow = async () => {
  // if (localStorage.length < 1) {
  //   window.location = "./../index.html";
  // }
  let data;
  await fetch("./../projects", {
    method: "GET",
    headers: new Headers({
      "Content-Type": "application/x-www-form-urlencoded"
    })
  })
    .then(res => res.json())
    .then(res => {
      data = res.data;
      data.map(async i => {
        console.log(i);
        let max;
        let progress;
        let msg = "";
        let projectDesc = "";
        let name = "";
        console.log(i.projectId);
        console.log(i.items);
        if (i.items.length > 0) {
          max = i.items.length;
          progress = progressTask(i.items);
          msg = `${progress}/${max}`;
          console.log("cuantity: " + max + ", completed: " + progress);
        } else {
          msg = "NI";
          console.log(`project ${i.projectId} has any items`);
        }
        if (i.projectName.length > 28) {
          for (let j = 0; j < 28; j++) {
            name += i.projectName.charAt(j);
          }
          name += "...";
        } else {
          name = i.projectName;
        }
        if (i.projectDes.length > 37) {
          for (let j = 0; j < 37; j++) {
            projectDesc += i.projectDes.charAt(j);
          }
          projectDesc += "...";
        } else {
          projectDesc = i.projectDes;
        }
        $("projectContent").innerHTML += `

        <div class="col s12 l4 m6">
          <div class="card" id="mdCard">
            <div class="card-content black-text">
              <div class="card-content black-text">
                <div class="row">
                  <div class="col s10 card-title">
                    ${name}
                  </div>
                  <div class="col s2 card-title">
                    ${msg}
                  </div>
                </div>
                <div class="row">
                  <div class="col s12 card-content">
                    <p>${projectDesc}</p>
                  </div>
                </div>
              </div>
              <div class="card-action">
                <a class="waves-effect waves-light btn-flat" onClick="showDetails(${i.projectId})"
                  >Ver mas</a
                >
              </div>
            </div>
          </div>
        </div>

          `;
        console.log(i.user);
      });
    });
};
