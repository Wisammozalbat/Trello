function $(id) {
  return document.getElementById(id);
}

function logout() {
  let params = {
    method: "GET",
    headers: {
      "Content-type": "application/x-www-form-urlencoded"
    }
  };
  fetch("./../logout", params)
    .then(res => res.json())
    .then(data => {
      console.log(data);
      localStorage.clear();
      window.location = "./../";
    });
}

$("logout").addEventListener("click", logout);
