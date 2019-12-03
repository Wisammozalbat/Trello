function $(id) {
  return document.getElementById(id);
}

let register = () => {
  body = {
    username: $("username").value,
    password: $("password").value,
    name: $("name").value
  };
  console.log(body);
  fetch("./../register", {
    method: "POST",
    body: JSON.stringify(body),
    headers: new Headers({ "Content-Type": "application/json" })
  })
    .then(res => res.json())
    .then(res => {
      console.log(res);
      if (res.status == 200) {
        alert("Usuario creado satisfactoriamente");
        window.location = "../views/login.html";
      }
    });
};

$("register").addEventListener("click", register);

$("loginLink").addEventListener("click", () => {
  window.location = "../views/login.html";
});
