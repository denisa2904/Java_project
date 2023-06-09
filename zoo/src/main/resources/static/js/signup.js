const inputFields = document.querySelectorAll(".input-field");
const loginButton = document.querySelector(".btn");

const signupBtnEvent =  async() => {
    console.log("loginBtnEvent");
    const requestBody = {};
    let passwordConfirm = "";
    inputFields.forEach((input) => {
        if(input.name === "confpass"){
            passwordConfirm = input.value;
        } else {
            requestBody[input.name] = input.value;
        }
    });
    if(!requestBody.username || !requestBody.password || !requestBody.email){
        alert("Please enter username, email and password!");
        return;
    }
    if(requestBody.password !== passwordConfirm){
        alert("Passwords do not match!");
        return;
    }
    const response = await fetch('http://localhost:3000/api/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    });
    const result = await response.json();
    // if(result.status === 'fail' || result.status === 'error') {
    if(response.status !== 200) {
        alert(result.message);
    }
    // } else if(result.status === 'success') {
    else {
        window.location.href = "/login";
    }
};


loginButton.addEventListener("click", async function (e) {
    e.preventDefault();
    await signupBtnEvent();
});