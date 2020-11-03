package com.smartindustry.pda.socket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: xiahui
 * @date: Created in 2020/10/30 10:33
 * @description: TODO
 * @version: 1.0
 */
@ServerEndpoint("/websocket/{imei}")
@Component
public class WebSocketServer {
    private static AtomicInteger num = new AtomicInteger();
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("imei") String imei) {
        sessionPools.put(imei, session);
        num.incrementAndGet();
    }

    @OnClose
    public void onClose(@PathParam("imei") String imei) {
        sessionPools.remove(imei);
        num.decrementAndGet();
    }

    /**
     * 批量发送
     *
     * @param imeis
     * @param message
     */
    public static void sendMsg(List<String> imeis, String message) {
        for (String imei : imeis) {
            sendMsg(imei, message);
        }
    }

    /**
     * 发送消息
     *
     * @param imei
     * @param message
     * @throws IOException
     */
    public static void sendMsg(String imei, String message) {
        Session session = sessionPools.get(imei);
        if (null != session) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
