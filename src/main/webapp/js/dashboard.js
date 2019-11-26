function $(id) {
  return document.getElementById(id);
}

window.onpageshow = () => {
  if (localStorage.length < 1) {
    window.location = "./../";
  }
  fetch("./../projects", {
    method: "GET",
    headers: new Headers({
      "Content-Type": "application/x-www-form-urlencoded"
    })
  })
    .then(res => res.json())
    .then(res => {
      data = res.data;
      data.map(i => {
        console.log(i);
      });
    });
};
