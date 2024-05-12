"use strict"
let host = "158.160.159.58";
document.addEventListener("DOMContentLoaded", function () {
    let but = document.querySelector("#enter")
    let email = document.querySelector(".email")
    let password = document.querySelector(".password")
    but.onclick = async (e) => {
        let emailValue = email.value.replaceAll(" ", "")
        let passwordValue = password.value.replaceAll(" ", "")
        if (emailValue === "" || passwordValue === "") {
            alert("Поля \"Почта\" и \"Пароль\" являются обязательными")
            email.value = ""
            password.value = ""
            return
        }
        let user = {
            email: email.value,
            password: password.value
        }
        const url = `http://${host}:8080/auth/login`
        const otherParam = {
            headers: {
                "content-type": "application/json; charset=UTF-8",
            },
            body: JSON.stringify(user),
            method: "POST",
        };
        let response = await fetch(url, otherParam)
        let jsonUser = await response.json()
        let jwtToken = jsonUser.jwt_token
        if (jwtToken === null || jwtToken === "" || jwtToken === undefined) {
            alert("Неправильный логин или пароль")
            return
        }
        localStorage.setItem("jwtToken", jwtToken)
        window.location.href = "../index.html"
    }
})