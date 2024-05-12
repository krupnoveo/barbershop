"use strict"
document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("#profile").addEventListener("click", function (event) {
        event.preventDefault();
        if (localStorage.getItem("jwtToken") === null) {
            // if (window.location.pathname.endsWith("index.html")) {
            //     window.location.href = './html/login.html'
            // } else {
            //     window.location.href = '../html/login.html'
            // }
            window.location.href = '../login.html'
        } else {
            // if (window.location.pathname.endsWith("index.html")) {
            //     window.location.href = './html/account.html'
            // } else {
            //     window.location.href = '../html/account.html'
            // }
            window.location.href = '../account.html'
        }
    })
})