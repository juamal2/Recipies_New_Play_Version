
let button = document.getElementById("button1");
let content = document.getElementById("inputf1");

button.onclick = (event) =>{
    console.log("hello")
    location.href="/?text=" + content.value;
}
