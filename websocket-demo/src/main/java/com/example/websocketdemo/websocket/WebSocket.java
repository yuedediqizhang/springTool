package com.example.websocketdemo.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ServerEndpoint(value = "/websocket/{checkInfoPath}", configurator = WebSocketServerConfigurator.class)
// 优先级靠后
@Order(10)
public class WebSocket {

    private final static Map<String, WebSocket> webSocketMap = new ConcurrentHashMap<>();
    

    private static final Logger log = LoggerFactory.getLogger(WebSocket.class);

    private String checkinfoPath;

    private Session session;

    private String clientIP;

    @OnOpen
    public void onOpen(Session session, @PathParam("checkInfoPath") String checkinfoPath) {
        this.checkinfoPath = checkinfoPath;
        this.session = session;
        HttpSession httpSession = (HttpSession) this.session.getUserProperties().get(HttpSession.class.getName());
        this.clientIP = (String) httpSession.getAttribute("ClientIP");
        webSocketMap.put(this.session.getId(), this);
        log.info("websocket 有新的衔接,总数:{}", webSocketMap.size());
    }

    @OnClose
    public void onClose() {
        final String id = this.session.getId();
        if (id != null) {
            webSocketMap.remove(id);
            log.info("websocket 连接断开:总数:{}", webSocketMap.size());
        }
    }

    @OnMessage
    public void onMessage(String message) {
        // 获得前端发过来的消息
        log.info("websocket 收到消息:{}", message);

        // Send message to client
        sendToWeb("Hello, " + message + " !");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket 发生错误:{}", error.getMessage());
        error.printStackTrace();
    }

    /**
     * 发送消息给客户端
     *
     * @param message
     */
    private void sendToWeb(String message) {
        try {
            webSocketMap.get(this.session.getId()).session.getAsyncRemote().sendText(message);
        } catch (Exception e) {
            log.error("websocket 发送消息异常:{}", e.getMessage());
            e.printStackTrace();
        }
    }
    
}
