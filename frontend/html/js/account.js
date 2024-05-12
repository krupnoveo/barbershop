"use strict"
let host = "158.160.159.58";
document.addEventListener("DOMContentLoaded", async function () {
    let logout = document.querySelector("#logout")
    logout.onclick = (e) => {
        localStorage.clear()
        window.location.href = "../index.html"
    }
    const url = `http://${host}:8080/me`
    let response = await axios.get(url, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("jwtToken")}`
        }
    })
    const records = response.data.clients_records
    records.forEach(function (record) {
        let myRecords = document.querySelector("#my-records-list")
        const id = record.recordId
        const barberName = record.barberName
        const serviceName = record.serviceName
        const barbershopAddress = record.barbershopAddress
        const recordTime = new Date(record.recordTime)
        const cost = record.cost
        let elem = `
            <li class="record">
                <div>
                  <p class="time">Время записи: ${recordTime}</p>
                  <p class="barbershop-address">Адрес: ${barbershopAddress}</p>
                  <p class="barber-name">Барбер: ${barberName}</p>
                  <p class="service-name">Услуга: ${serviceName}</p>
                  <p class="service-cost">Стоимость: ${cost} руб.</p>
                  <p class="record-uuid">${id}</p>
                </div>
                <div>
                    <button class="delete">X</button>
                </div>
            </li>
        `
        myRecords.innerHTML += elem
    })
    const deleteButtons = document.querySelectorAll(".delete")
    for (var btn of deleteButtons) {
        btn.onclick = async (e) => {
            const target = e.target.closest(".record")
            const id = target.querySelector(".record-uuid").innerHTML
            await axios.delete(`http://${host}:8080/record/${id}`, {
                headers: {
                    "Authorization": `Bearer ${localStorage.getItem("jwtToken")}`
                }
            })
            target.parentNode.removeChild(target)
        }
    }
    const name = response.data.full_name
    const email = response.data.email
    const phone = response.data.phone_number
    const inputName = document.querySelector(".name")
    const inputEmail = document.querySelector(".email")
    const inputPhone = document.querySelector(".phone-number")
    inputName.value = name
    inputEmail.value = email
    inputPhone.value = phone

    const redactBut = document.querySelector("#redact-button")
    const save = document.querySelector("#profile-save-data")
    redactBut.onclick = (e) => {
        inputName.disabled = false
        inputPhone.disabled = false
        save.disabled = false
    }

    save.onclick = async (e) => {
        const urlName = `http://${host}:8080/me/name`
        const urlPhone = `http://${host}:8080/me/phoneNumber`

        let newName = {
            name: inputName.value
        }
        let newPhone = {
            phone_number: inputPhone.value
        }
        let nameResponse = await axios.post(urlName, newName, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("jwtToken")}`
            }
        })
        let phoneResponse = await axios.post(urlPhone, newPhone, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("jwtToken")}`
            }
        })

        const name = nameResponse.data.full_name
        const phone = phoneResponse.data.phone_number
        inputName.value = name
        inputPhone.value = phone
        inputName.disabled = true
        inputPhone.disabled = true
        save.disabled = true
    }

    const bookBut = document.querySelector("#book-new-record")
    bookBut.onclick = (e) => {
        window.location.href = "../bookRecord.html"
    }


})

