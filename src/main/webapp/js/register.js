function $(id) {
  return document.getElementById(id);
}

$("loginLink").addEventListener("click", () => {
  window.location = "../views/login.html";
});
