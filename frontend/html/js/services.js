"use strict"
let host = "158.160.159.58";
document.addEventListener("DOMContentLoaded", async function () {
    const url = `http://${host}:8080/service`
    let ans = await fetch(url)
    let services = await ans.json()
    services.forEach(function (service) {
        const name = service.serviceName
        const description = service.description
        const cost = service.cost
        let elem =
            `
            <li class="service">
                <p class="name">${name}</p>
                <p class="description">${description}</p>
                <p class="cost">${cost} руб.</p>
            </li>
            `
        let list = document.querySelector("#service-list")
        list.innerHTML += elem
    })
})