$('#deleteModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget)
    var userId = button.data('id')
    var userFirstName = button.data('firstname')
    var userAge = button.data('age')
    var userLastName = button.data('lastname')
    var userEmail = button.data('email')
    var userUsername = button.data('username')

    var modal = $(this)
    modal.find('#idDeleteTextInput').val(userId)
    modal.find('#firstNameDeleteInput').val(userFirstName)
    modal.find('#ageDeleteInput').val(userAge)
    modal.find('#lastNameDeleteInput').val(userLastName)
    modal.find('#emailDeleteInput').val(userEmail)
    modal.find('#usernameDeleteInput').val(userUsername)
    modal.find('#deleteForm').attr("action", "/?id=" + userId)
})

$('#editModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget)
    var userId = button.data('id')
    var userAge = button.data('age')
    var userFirstName = button.data('firstname')
    var userLastName = button.data('lastname')
    var userEmail = button.data('email')
    var userUsername = button.data('username')

    var modal = $(this)
    modal.find('#idDisabledTextInput').val(userId)
    modal.find('#firstNameEditInput').val(userFirstName)
    modal.find('#ageEditInput').val(userAge)
    modal.find('#lastNameEditInput').val(userLastName)
    modal.find('#emailEditInput').val(userEmail)
    modal.find('#usernameEditInput').val(userUsername)
    modal.find('#updateForm').attr("action", "/?id=" + userId)
})
$(document).ready(function () {
    $("#nav-create-text").hide();
    $("#nav-table-tab").click(function () {
        $("#nav-create-text").hide();
        $("#nav-list-text").show()
    });
    $("#nav-create-tab").click(function () {
        $("#nav-list-text").hide();
        $("#nav-create-text").show()
    });
});
const getData = async (url) => {
    const response = await fetch(url)
    if (!response.ok) {
        throw new Error(`Ошибка по адресу ${url}`)
    }
    return await response.json()
}
const sendDataJSON = async (url, data, method) => {
    const response = await fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json', // отправляемые данные
        },
        body: JSON.stringify(data)
    })
        .catch(() => console.log("Ошибка"))

    return await response.json()
}
const sendData = async (url, data, method) => {
    const response = await fetch(url, {
        method: method,
        body: data
    }).catch(() => console.log("Ошибка"))

    return await response.json()
}
const sendFormCreate = async () => {
    const userForm = document.querySelector('#nav-create-form')
    userForm.addEventListener('submit', e => {
        e.preventDefault()
        let formData = new FormData(userForm)
        formData = Object.fromEntries(formData)
        getData(`api/roles`).then((response) => {
            for (let i = 0; i < 2; i++) {
                if (formData.roles === response[i].role) {
                    formData.roles = [response[i]]
                }
            }
            sendDataJSON(`/api/users/`, formData, 'POST')
                .then((response) => {
                    console.log(response);
                    userForm.reset()
                    $('#nav-tab a[href="#nav-table"]').tab('show')
                })
                .then(() => {
                    getData(`api/users/`)
                        .then((response) => {
                            fillTable(response, '#show-users-table-body')
                        })
                })
                .catch((err) => {
                    console.log(err)
                })
        })
    })
}
const sendFormUpdate = async () => {
    const userForm = document.querySelector('#updateForm')
    const userInfoTable = document.querySelector('#info-table')
    userForm.addEventListener('submit', e => {
        e.preventDefault()
        let formData = new FormData(userForm)
        formData = Object.fromEntries(formData)
        getData(`/api/roles`).then((response) => {
            for (let i = 0; i < 2; i++) {
                if (formData.roles === response[i].role) {
                    formData.roles = [response[i]]
                }
            }
            sendDataJSON(`/api/users`, formData, 'PUT')
                .then((response) => {
                    if(response.id + "" === userInfoTable.getAttribute("data-id")){
                        fillInfoTable(response, userInfoTable)
                    }
                    $('#editModal').modal('hide')
                })
                .then(() => {
                    getData(`/api/users/`).then((response) => {
                        fillTable(response, '#show-users-table-body')
                    })
                })
                .catch((err) => {
                    console.log(err)
                })
        })
    })
}
const sendDelete = async () => {
    const userForm = document.querySelector('#deleteForm')
    userForm.addEventListener('submit', e => {
        e.preventDefault()
        let formData = new FormData(userForm)
        sendData(`/api/users/` + formData.get('id'), null, 'DELETE')
            .then((response) => {
                console.log(response)
                $('#deleteModal').modal('hide')
                getData(`/api/users/`)
                    .then((response) => {
                        fillTable(response, '#show-users-table-body')
                    })
            })
            .catch((err) => {
                console.log(err)
            })
    })
}
const fillTable = async (arrData, selector) => {
    let table = document.querySelector(selector);
    while (table.firstChild) {
        table.firstChild.remove()
    }
    for (let i = 0; i < arrData.length; i++) {
        const tr = document.createElement('tr');
        const tdId = document.createElement('td');
        const tdFirstName = document.createElement('td');
        const tdLastName = document.createElement('td');
        const tdEmail = document.createElement('td');
        const tdAge = document.createElement('td');
        const tdUsername = document.createElement('td');
        const tdRole = document.createElement('td');
        const tdEditButton = document.createElement('td')
        const editButton = document.createElement('button')
        const tdDeleteButton = document.createElement('td')
        const deleteButton = document.createElement('button')

        const attributesSet = {
            'type': 'button',
            'class': 'btn btn-info',
            'data-toggle': 'modal',
            'data-target': '#editModal',
            'data-id': arrData[i].id,
            'data-email': arrData[i].email,
            'data-firstname': arrData[i].firstName,
            'data-age': arrData[i].age,
            'data-username': arrData[i].username,
            'data-lastname': arrData[i].lastName
        }

        editButton.append('Редактировать')
        for (let key in attributesSet) {
            editButton.setAttribute(key, attributesSet[key]);
        }
        tdEditButton.appendChild(editButton)

        attributesSet.class = 'btn btn-danger'
        attributesSet["data-target"] = '#deleteModal'
        deleteButton.append('Удалить')
        for (let key in attributesSet) {
            deleteButton.setAttribute(key, attributesSet[key]);
        }
        tdDeleteButton.appendChild(deleteButton)

        tdId.append(arrData[i].id)
        tr.appendChild(tdId);
        tdFirstName.append(arrData[i].firstName)
        tr.appendChild(tdFirstName);
        tdLastName.append(arrData[i].lastName)
        tr.appendChild(tdLastName);
        tdAge.append(arrData[i].age)
        tr.appendChild(tdAge);
        tdUsername.append(arrData[i].username)
        tr.appendChild(tdUsername);
        tdEmail.append(arrData[i].email)
        tr.appendChild(tdEmail);
        for (let j = 0; j < arrData[i].roles.length; j++) {
            if (arrData[i].roles[j].role === 'ROLE_ADMIN') {
                tdRole.append('ADMIN')
            } else if (arrData[i].roles[j].role === 'ROLE_USER') {
                tdRole.append('USER')
            }
        }
        tr.appendChild(tdRole);
        tr.appendChild(tdEditButton)
        tr.appendChild(tdDeleteButton)
        table.appendChild(tr);
        console.log(arrData)
    }
}
const fillInfoTable = async (data, selector) => {
    let table = selector;
    while (table.firstChild) {
        table.firstChild.remove()
    }
    const tr = document.createElement('tr');
    const tdId = document.createElement('td');
    const tdFirstName = document.createElement('td');
    const tdLastName = document.createElement('td');
    const tdEmail = document.createElement('td');
    const tdAge = document.createElement('td');
    const tdUsername = document.createElement('td');
    const tdRole = document.createElement('td');

    tdId.append(data.id)
    tr.appendChild(tdId);
    tdFirstName.append(data.firstName)
    tr.appendChild(tdFirstName);
    tdLastName.append(data.lastName)
    tr.appendChild(tdLastName);
    tdAge.append(data.age)
    tr.appendChild(tdAge);
    tdUsername.append(data.username)
    tr.appendChild(tdUsername);
    tdEmail.append(data.email)
    tr.appendChild(tdEmail);
    for (let j = 0; j < data.roles.length; j++) {
        if (data.roles[j].role === 'ROLE_ADMIN') {
            tdRole.append('ADMIN')
        } else if (data.roles[j].role === 'ROLE_USER') {
            tdRole.append('USER')
        }
    }
    tr.appendChild(tdRole);
    table.appendChild(tr);
    console.log(data)
}
sendFormCreate()
sendFormUpdate()
sendDelete()