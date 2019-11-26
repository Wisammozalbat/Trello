function $(id) {
  return document.getElementById(id);
}

$("LoginButton").addEventListener("click", () => {
  window.location = "./views/login.html";
});

$("RegisterButton").addEventListener("click", () => {
  window.location = "./views/register.html";
});
