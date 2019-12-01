let x = location.href.split("?")[1];

window.onpageshow = () => {
  fetch(`./../items?projectId=${x}`, {
    method: "GET",
    headers: new Headers({
      "Content-Type": "application/x-www-form-urlencoded"
    })
  })
    .then(res => res.json())
    .then(res => {
      console.log(res);
    });
};
