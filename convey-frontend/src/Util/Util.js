const API_SERVICE = "http://localhost:8080";

const request = (options) => {

    const headers = new Headers();

    if(options.setContentType !== false) {
        headers.append("Content-Type", "application/json");
    }

    if(localStorage.getItem("accessToken")) {
        headers.append(
            "Authorization",
            "Bearer " + localStorage.getItem("accessToken")
        );
    }

    const defaults = {headers: headers}
    
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options).then((response) => response.json()
        .then((json) => {
            if (!response.ok) {
                return Promise.reject(response);
            }

            return json;
        })
  );
}

const betterRequest = (options) => {
    const headers = new Headers();

    if(options.setContentType !== false) {
        headers.append("Content-Type", "application/json");
    }

    if(localStorage.getItem("accessToken")) {
        headers.append(
            "Authorization",
            "Bearer " + localStorage.getItem("accessToken")
        );
    }

    const defaults = {headers: headers}
    
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options);
}

export function isJWTValidatedBetter() {
    return betterRequest({
            url: API_SERVICE + "/protected",
            method: "POST"
        });
}

export function isJWTValidated() {
    console.log("wykonuje sie");

    request({
            url: API_SERVICE + "/protected",
            method: "POST"
        }).then((response => {
            console.log(response);
            if(response.status === 403) {
                console.log(response.status);
                return false;
            }

            console.log("tak");

            return true;
        })).catch((error) => {
            console.log("error");
            return false;
        });
}

export function signUp(signUpRequest) {
    return request({
        url: API_SERVICE + "/users",
        method: "POST",
        body: JSON.stringify(signUpRequest)
    });
}

export function signIn(signInRequest) {
    return request({
        url: API_SERVICE + "/session",
        method: "POST",
        body: JSON.stringify(signInRequest)
    });
}

export function getCurrentUser() {
    return request({
        url: API_SERVICE + "/user/me",
        method: "GET"
    });
}

export function getUsers() {
    return request({
        url: API_SERVICE + "/user/contacts",
        method: "GET"
    })
}

export function countNewMessages(senderId, recipientId) {
    return request({
        url: API_SERVICE + "/messages/" + senderId + "/" + recipientId + "/count",
        method: "GET"
    })
}

export function findChatMessages(senderId, recipientId) {
    return request({
        url: API_SERVICE + "/messages/" + senderId + "/" + recipientId,
        method: "GET"
    })
}

export function findChatMessage(id) {
    return request({
        url: API_SERVICE + "/messages/" + id,
        method: "GET"
    })
}