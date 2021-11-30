var stompClient = null;

$(document).ready(function() {
    var chatID = document.querySelector('#chatID');
    if (chatID != null){
        console.log("Index page is ready");
        connect();
    }

//    $("#send").click(function() {
//        sendMessage();
//    });
});

function connect() {
    var socket = new SockJS('/our-websocket');
    stompClient = Stomp.over(socket);
    var chatID = document.querySelector('#chatID').value;
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages/' + chatID, function (message) {
            showMessage(JSON.parse(message.body));
        });

    });
}

function showMessage(message) {
    console.log('Got message: ' + message.content);
    $("#messages").append(" <div class= \"media w-50 mb-3\"> " +
    " <div class= \"media-body ml-3\"> " +
    " <div class= \"bg-light rounded py-2 px-3 mb-2\"> " +
    " <p class= \"text-small mb-0 text-muted\"> " + message.content + " </p> " +
    " </div> " +
    " <p class= \"small text-muted\"> " + message.time + " </p> " +
    " </div> " +
    " </div> ");
}

//function sendMessage() {
//    stompClient.send("/ws/chat/{id}", {}, JSON.stringify({'messageContent': $("#message").val()}));
//}