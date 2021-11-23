var stompClient = null;

$(document).ready(function() {
    console.log("Index page is ready");
    connect();

    $("#send").click(function() {
        sendMessage();
    });
});

export function connect() {
    var socket = new SockJS('/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        updateNotificationDisplay();
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });

    });
}

export function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

export function sendMessage() {
    stompClient.send("/ws/chat/{id}", {}, JSON.stringify({'messageContent': $("#message").val()}));
}