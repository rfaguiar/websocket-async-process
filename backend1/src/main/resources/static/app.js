let stompClient = null;
const websocketUrl = '/ws';
const restTokenUrl = '/token';
const websocketTopic = '/users/topic/';

function connect({username, solicitationId}) {
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({username: username}, function (frame) {
        showSolicitations('Connected websocket with username: ' + username + ', url : ' + websocketUrl);
        showSolicitations('subscribe websocket: ' + websocketTopic + solicitationId);
        stompClient.subscribe(websocketTopic + solicitationId, function (message) {
            showSolicitations("received token: " + JSON.parse(message.body).content);
            disconnect();
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    showSolicitations("Disconnected websocket");
}

function sendName() {
    const username = $("#name").val();
    const data = JSON.stringify({ username: username });
    showSolicitations("REST POST to : " + restTokenUrl + ', body: ' + data);

    $.post({
        url: restTokenUrl,
        data: data,
        contentType: 'application/json; charset=utf-8'
    })
    .done(function (data) {
        const solicitationId = data.solicitationId
        showSolicitations("Received solicitationId: " + solicitationId);
        connect({username, solicitationId});
    });
}

function showSolicitations(message) {
    console.log(message);
    $("#solicitations").append("<tr><td class='white-space:nowrap;word-wrap:break-word;'>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

