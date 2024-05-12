"use strict"
let host = "158.160.159.58";
document.addEventListener("DOMContentLoaded", async function () {
    const url = `http://${host}:8080/barbershop`
    let ans = await fetch(url)
    let barbershops = await ans.json()
    barbershops.forEach(function (barbershop) {
        const address = barbershop.address
        let elem =
            `
            <li class="office">
                <p class="address">${address}</p>
            </li>
            `
        let list = document.querySelector("#office-list")
        list.innerHTML += elem
    })
})