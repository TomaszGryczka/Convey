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

    return fetch(options.url, options);
}

export function signUp(signUpRequest) {
    return request({
        url: API_SERVICE + "/users",
        method: "POST",
        body: JSON.stringify(signUpRequest)
    });
}