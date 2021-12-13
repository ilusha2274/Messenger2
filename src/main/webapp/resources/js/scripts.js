var stompClient = null;
var arr = null;
var messageId = null;

$(document).ready(function() {
    var chatID = document.querySelector('#chatID');
    if (chatID != null){
        console.log("Index page is ready");
        connect();
    }

    $("#send").click(function() {
        sendMessage();
    });
});

function connect() {
    var socket = new SockJS('/our-websocket');
    stompClient = Stomp.over(socket);
    var chatID = document.querySelector('#chatID').value;
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/messages/chat/' + chatID, function (message) {
            showMessage(JSON.parse(message.body));
        });

    });
    arr = document.querySelectorAll('#messageId');
    messageId = arr[arr.length - 1].value;
    addListenerOnLastElement(arr);
}

function showMessage(message) {
    console.log('Got message: ' + message.content);
    var userId = document.querySelector('#userId');
    if (userId.value == message.userId){
    var date = document.getElementById('date');
    date.innerHTML = message.time;
    }else{
    $("#messages").append(" <div class= \"media w-50 mb-3\"> " +
        " <div class= \"media-body ml-3\"> " +
        " <h6> " + message.nameAuthor + " </h6> "  +
        " <div class= \"bg-light rounded py-2 px-3 mb-2\"> " +
        " <p class= \"text-small mb-0 text-muted\"> " + message.content + " </p> " +
        " </div> " +
        " <p class=\"small text-muted\"> " + message.time + " </p> " +
        " </div> " +
        " </div> ");
    }
}

function sendMessage() {
    var chatID = document.querySelector('#chatID');
    var messageContent = document.querySelector('#message');
    var nameAuthor = document.querySelector('#name');
    var userId = document.querySelector('#userId');
    var chatMessage = {
    nameAuthor: nameAuthor.value,
    userId: userId.value,
    content: messageContent.value,
    idChat: chatID.value
    };
    showMessageAuthor(chatMessage);
    stompClient.send("/ws/chat/" + chatID.value, {}, JSON.stringify(chatMessage));
    console.log('Got post message: ' + messageContent.value );
    messageContent.value = '';
}

function showMessageAuthor(message) {
    console.log('Got message: ' + message.content);
    $("#messages").append(" <div class= \"media w-50 ml-auto col-md-3 offset-md-6\"> " +
    " <div class= \"media-body\"> " +
    " <div class= \"bg-primary rounded py-2 px-3 mb-2\"> " +
    " <p class= \"text-small mb-0 text-white text-right\"> " + message.content + " </p> " +
    " </div> " +
    "<p id=\"date\" class=\"small text-muted\">" + "---" + "</p>" +
    " </div> " +
    " </div> ");
}

function addListenerOnLastElement(array){
    array[array.length -1].parentNode.addEventListener("mouseover", load);
}

function showM(message) {
    $("#messages").append(" <div class= \"media w-50 mb-3\"> " +
        " <div class= \"media-body ml-3\"> " +
        " <h6> " + message.nameAuthor + " </h6> "  +
        " <div class= \"bg-light rounded py-2 px-3 mb-2\"> " +
        " <p class= \"text-small mb-0 text-muted\"> " + message.content + " </p> " +
        " </div> " +
        " <p class=\"small text-muted\"> " + message.time + " </p> " +
        " </div> " +
        " </div> ");
}

function load(evt){
    var xhr = new XMLHttpRequest();
    var chatID = document.querySelector('#chatID').value;
    var url = "http://localhost:8080/chat/" + chatID + "/" + messageId;
    xhr.open("GET", url);
    xhr.setRequestHeader("Context-type", "application/json");

    xhr.onload = function (ev){
        var jsonResponse = JSON.parse(xhr.responseText);
        if(jsonResponse != null && jsonResponse.length > 0){
            for (var i =0;i < jsonResponse.length;i++){
                showM(jsonResponse[i])
            }
            arr = document.getElementsByClassName('messageId');
            messageId = arr[arr.length - 1].value;
            console.log(lastMessageId);
            addListenerOnLastElement(arr);
        }
    };
    xhr.send();
    arr[arr.length -1].removeEventListener("mouseover", load);

};