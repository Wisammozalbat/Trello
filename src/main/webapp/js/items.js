function $(id) {
    return document.getElementById(id);
}

const project_id = getIdProject();

function getIdProject() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var id = url.searchParams.get("project_id");
    if (id === null || undefined) {
        location.href = "dashboard.html";
    } else {
        return id;
    }
}

/*'<img src="images/1.png" class="responsive-img">'+
                    '<a href="#modal1" class="modal-trigger waves-effect waves-light btn-large white black-text" id="btn-new">Add content</a>'+
                    '<a href="#modal1" class="modal-trigger waves-effect waves-light btn-large white black-text" id="btn-new">Finish</a>'+
                    */

window.onload = function() {
    if (sessionStorage.getItem("token") == null || undefined) {
        alert("NO TIENES SESSION!");
        location.href = "dashboard.html";
    } else {
        let config = {
            method: "GET",
            withCredentials: true,
            credentials: 'same-origin',
            headers: {
                "Content-type": "application/x-www-form-urlencoded",
                "Authorization": "Bearer " + sessionStorage.getItem("token")
            }
        };

        let params = location.href.split('?')[1];
        fetch('./../item/getListItem?' + params, config)
            .then(res => res.json())
            .then(data => {
                console.log(data)
                if (data.status == 200 || data.status == 201) {
                    
                    if((data.data[0].users_id != JSON.parse(sessionStorage.getItem("user")).id)){
                        if(JSON.parse(sessionStorage.getItem("user")).type != 1){
                            alert('Aqui no chiamo');
                            location.href = 'dashboard.html';
                        }
                    }
                    $('name-project').innerHTML = data.data[0].project_name;
                    var statusProject = 1;
                    data.data.forEach(element => {
                        var status = getDataStatus(element.status_id);
                        if(element.status_id!=1){
                            statusProject = 2;
                        }
                        $('collapsible').innerHTML += "" +
                            '<li id=' + element.project_item_id + ' onclick="getContent(' + element.project_item_id + ')">' +
                            '<div class="collapsible-header">' + element.project_item_name + '<span class="new badge ' + status.color + '" data-badge-caption="' + status.msg + '"></span></div>' +
                            '<div class="collapsible-body white-text" id="item-' + element.project_item_id + '">' +
                            '<span>' + element.project_item_description + '</span><br>' +
                            '</div>' +
                            '</li>';
                    });
                        
                    console.log(statusProject);
                    if(statusProject === 1){
                        $('finishbtn').hidden = false;
                        $('finishbtn1').addEventListener('click',changeStatusProject);
                    }

                } else {
                    alert(data.message + " Error:" + data.status);
                    location.href = "dashboard.html";
                }
            });

        //Cambiar para el admin
        $("project_id_delegado").value = getIdProject();
    }
}

function changeStatusProject(){
    let body = {
        project_id: getIdProject(),
        status: 1
    },
    config = {
        method: "PUT",
        headers: new Headers({
            'Content-Type': 'application/json',
            "Authorization": "Bearer " + sessionStorage.getItem("token")
        }),
        body: JSON.stringify(body)
    };

    fetch('./../project/editProject', config)
        .then(res => res.json())
        .then(data => {
        if (data.status == 200 || data.status == 201) {
            console.log(data);
            location.reload();
        } else {
            alert(data.message + " Error:" + data.status);
            location.href = "dashboard.html";
        }
    });
}

function getDataStatus(status) {

    switch (status) {
        case 1:
            return {
                msg: "Completed",
                color: "green"
            }
        case 2:
            return {
                msg: "Progess",
                color: "yellow"
            }
        case 3:
            return {
                msg: "Started",
                color: "red"
            }
    }
}

function getContent(id) {
    console.log(id);
    $(id).onclick = null;

    let config = {
        method: "GET",
        withCredentials: true,
        credentials: 'same-origin',
        headers: {
            "Content-type": "application/x-www-form-urlencoded",
            "Authorization": "Bearer " + sessionStorage.getItem("token")
        }
    };
    
    fetch('./../content/getContent?item_id='+id, config)
    .then(res => res.json())
    .then(data => {
        console.log(data);
        if(data.status == 200 || data.status == 201){
            data.data.forEach(element => {
                $('item-'+id).innerHTML += ""+
                '<span>'+element.item_content_description+'</span>'+
                '<img src="'+element.item_content_url+'" class="responsive-img">';    
            });

            $('item-'+id).innerHTML += "<br><br>" +
            '<a href="#modal-content" class="modal-trigger waves-effect waves-light btn white black-text" id="btn-new" onclick="assignIdAdd('+id+')">Add content</a>'+
            '<a class="waves-effect waves-light btn white black-text" onclick="changeStatus('+id+','+1+')">Finish</a>'+
            '<a href="#modal-edititem" class="modal-trigger waves-effect waves-light btn white black-text" onclick="assignIdEdit('+id+')">Edit</a>'+
            '<a class="waves-effect waves-light btn white black-text" onclick="deleteItem('+id+')">Delete</a>';
        } else{
            alert(data.message+" Error:"+data.status);
            location.href = "dashboard.html";
        }
    });
}

function assignIdAdd(id){
    $('id_item_modal').value = id;    
}

function assignIdEdit(id){
    $('id_item_edit').value = id;
}

function editItem(){
    let body={
        items_id: $('id_item_edit').value,
        items_name: $('name_item_edit').value,
        items_des: $('des_item_edit').value
    },
        config={
                method: "PUT", 
                headers: new Headers({
                    'Content-Type': 'application/json',
                    "Authorization":"Bearer "+sessionStorage.getItem("token")
                }), 
                body:JSON.stringify(body) 
    };
    
    fetch('./../item/editItem', config)
    .then(res => res.json())
    .then(data => {
        if(data.status == 200 || data.status == 201){
            console.log(data);
            /*data.data.forEach(element => {
                $('collapsible').innerHTML += ""+
                '<li id='+element.project_item_id+' onclick="getContent('+element.project_item_id+')">'+
                '<div class="collapsible-header">'+element.project_item_name+'</div>'+
                '<div class="collapsible-body white-text" id="item-'+element.project_item_id+'">'+
                '<span>'+element.project_item_description+'</span>'+
                '</div>'+
                '</li>';    
            });*/
            location.reload();
        } else{
            alert(data.message+" Error:"+data.status);
            location.href = "dashboard.html";
        }
    });
}

function deleteItem(id){
    let body={
        items_id: id,
    },
        params={
                method: "DELETE", 
                headers: new Headers({
                    'Content-Type': 'application/json',
                    "Authorization":"Bearer "+sessionStorage.getItem("token")
                }), 
                body:JSON.stringify(body)
    };
    fetch("./../item/deleteItem", params)
        .then(resp => resp.json())
        .then(data => {
            console.log(data);
            if (data.status == 200) {
                location.reload();
            } else {
                alert("Error al iniciar sesion, status:" + data.status);
            }
        });
}

function addContent() {
    var url = "./../content/upload/" + $('id_item_modal').value;
    var formData = new FormData();
    formData.append('file', $("file").files[0]);
    formData.append('contentDesc', $('des_cont').value);
    fetch(url, {
            body: formData,
            method: 'POST',
            headers: new Headers({
                "Authorization": "Bearer " + sessionStorage.getItem("token")
            })
        })
        .then(resp => resp.json())
        .then(data => {
            console.log(data);
        });

    changeStatus($('id_item_modal').value,2);
}

function changeStatus(id, status) {
    let body = {
            items_id: id,
            status: status
        },
        config = {
            method: "PUT",
            headers: new Headers({
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + sessionStorage.getItem("token")
            }),
            body: JSON.stringify(body)
        };

    fetch('./../item/editItem', config)
        .then(res => res.json())
        .then(data => {
            if (data.status == 200 || data.status == 201) {
                console.log(data);
                /*data.data.forEach(element => {
                    $('collapsible').innerHTML += ""+
                    '<li id='+element.project_item_id+' onclick="getContent('+element.project_item_id+')">'+
                    '<div class="collapsible-header">'+element.project_item_name+'</div>'+
                    '<div class="collapsible-body white-text" id="item-'+element.project_item_id+'">'+
                    '<span>'+element.project_item_description+'</span>'+
                    '</div>'+
                    '</li>';    
                });*/
                location.reload();
            } else {
                alert(data.message + " Error:" + data.status);
                location.href = "dashboard.html";
            }
        });
}

function createItem() {
    let body = {
            project_id: project_id,
            items_name: $('name_item').value,
            items_des: $('des_item').value
        },
        params = {
            method: "POST",
            headers: new Headers({
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + sessionStorage.getItem("token")
            }),
            body: JSON.stringify(body)
        };
    fetch("./../item/createItem", params)
        .then(resp => resp.json())
        .then(data => {
            console.log(data);
            if (data.status == 200) {
                location.reload();
            } else {
                alert("Error al iniciar sesion, status:" + data.status);
            }
        });
}

function delegation() {
    let slaveUser = $('user_delegado').value;
    let id = $('project_id_delegado').value;
    console.log(slaveUser);
    console.log(id);
    let json = {
        slaveUser: slaveUser,
        projectId: id
    };
    let body = JSON.stringify(json);
    fetch('./../delegate/give', {
            body: body,
            method: 'PUT',
            headers: new Headers({
                "Content-Type": 'application/json',
                "Authorization": "Bearer " + sessionStorage.getItem("token")
            }),
        }).then(resp => resp.json())
        .then((data) => {
            if (data.status === 200) {
                location.href = "dashboard.html";
            } else {
                location.reload();
            }
        })
}

function editProject(){
    let body={
        project_id: getIdProject(),
        project_name: $('name_project_edit').value,
        project_des: $('des_project_edit').value
    },
        config={
                method: "PUT", 
                headers: new Headers({
                    'Content-Type': 'application/json',
                    "Authorization":"Bearer "+sessionStorage.getItem("token")
                }), 
                body:JSON.stringify(body) 
    };
    
    fetch('./../project/editProject', config)
    .then(res => res.json())
    .then(data => {
        if(data.status == 200 || data.status == 201){
            console.log(data);
            location.reload();
        } else{
            alert(data.message+" Error:"+data.status);
            location.href = "dashboard.html";
        }
    });
}

function deleteProject(){
    let body={
        project_id: getIdProject()
    },
        params={
                method: "DELETE", 
                headers: new Headers({
                    'Content-Type': 'application/json',
                    "Authorization":"Bearer "+sessionStorage.getItem("token")
                }), 
                body:JSON.stringify(body)
    };
    fetch("./../project/deleteProject", params)
        .then(resp => resp.json())
        .then(data => {
            console.log(data);
            if (data.status == 200) {
                location.href = 'dashboard.html';
            } else {
                alert("Status:" + data.status);
            }
        });
}

$('deletebtn').addEventListener('click',deleteProject);
$('modaleditprojectbtn').addEventListener('click',editProject);
$('modaledititembtn').addEventListener('click',editItem);
$('modaldelegation').addEventListener('click',delegation);
$('modalbtn').addEventListener('click',createItem);
$('modalcontent').addEventListener('click',addContent);
