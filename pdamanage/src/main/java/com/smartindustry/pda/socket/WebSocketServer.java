package com.smartindustry.pda.socket;

import com.alibaba.fastjson.JSON;
import com.smartindustry.pda.vo.WebSocketVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @description: WebSocket
 * @version: 1.0
 */
@ServerEndpoint("/websocket/{imei}")
@Component
public class WebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private static AtomicInteger num = new AtomicInteger();
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("imei") String imei) {
        sessionPools.put(imei, session);
        num.incrementAndGet();
        logger.info(imei);
        sendMsg(imei, "连接成功");
    }

    @OnClose
    public void onClose(@PathParam("imei") String imei) {
        sendMsg(imei, "断开连接成功");
        sessionPools.remove(imei);
        num.decrementAndGet();
    }

    /**
     * 发送所有信息
     *
     * @param vo
     */
    public static void sendAllMsg(WebSocketVO vo) {
        String message = JSON.toJSONString(vo);
        for (Session session : sessionPools.values()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息
     */
    public static void sendMsg(List<String> imeis, WebSocketVO vo) {
        for (String imei : imeis) {
            sendMsg(imei, vo);
        }
    }

    /**
     * 发送消息
     */
    public static void sendMsg(String imei, WebSocketVO vo) {
        sendMsg(imei, JSON.toJSONString(vo));
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
