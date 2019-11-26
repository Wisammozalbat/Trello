function $(id) {
  return document.getElementById(id);
}

let login = () => {
  body = {
    username: $("username").value,
    password: $("password").value
  };
  console.log(body);
  fetch("./../login", {
    method: "POST",
    body: JSON.stringify(body),
    headers: new Headers({ "Content-Type": "application/json" })
  })
    .then(res => res.json())
    .then(res => {
      console.log(res);
      let newData = {
        id: res.data.id,
        username: res.data.username
      };
      localStorage.setItem("data", JSON.stringify(newData));
      if (res.status == 200) {
        localStorage.setItem("data", JSON.stringify(newData));
        console.log(localStorage);
        window.location = "../views/dashboard.html";
      }
    });
};

$("login").addEventListener("click", login);

$("registerLink").addEventListener("click", () => {
  window.location = "../views/register.html";
});
