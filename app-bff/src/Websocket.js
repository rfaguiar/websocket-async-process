import SockJS from "sockjs-client";
import Stomp from "stompjs";

let stompClient;
const websocketUrl = '/websocket';
const websocketTopic = '/users/topic/';

function connect({username, id}, messageCallback) {
    let socket = new SockJS(websocketUrl);
    stompClient = Stomp.over(socket);
    stompClient.connect({username: username}, function () {
        messageCallback('Connected websocket with username: ' + username + ', url : ' + websocketUrl);
        messageCallback('subscribe websocket: ' + websocketTopic + id);
        stompClient.subscribe(websocketTopic + id, function (message) {
            messageCallback("received token: " + JSON.parse(message.body).content);
            disconnect(messageCallback);
        });
    });
}

export function disconnect(messageCallback) {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    messageCallback("Disconnected websocket");
}

export default connect;