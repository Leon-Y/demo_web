package com.example.web.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: Xiao
 * @Date: 2019/8/14 :14:11
 * @Description:
 */
@ServerEndpoint(value = "/websocket")
@Component
public class Websocket {
    //记录在线数量
    private static int onlineCount = 0;
    //线程安全的set
    private static CopyOnWriteArraySet<Websocket> websocketSet = new CopyOnWriteArraySet<Websocket>();
    //会话
    private Session session;

    /**
     * 连接成功建立的方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        websocketSet.add(this);
        addOnlineCount();
        System.out.println("有新的连接加入");
        try {
            sendMessage(CommonConstant.CURRENT_WARING_NUMBER.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage()+"发送消息异常");
        }
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(){
        websocketSet.remove(this);
        subOnlineCount();
        System.out.println("连接关闭");
    }

    /**
     * 收到消息进行调用
     */
    @OnMessage
    public void onMessage(String message){
        System.out.println("来自客户端的消息："+message);
        for (Websocket websocket:websocketSet){
            try {
                websocket.sendMessage(message);
            } catch (IOException e) {
                System.out.println(e.getMessage()+"io异常导致消息失败");
            }
        }
    }

    /**
     * 错误调用
     */
    @OnError
    public void onError(Session session,Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }
    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        subOnlineCount();
    }


    /**
     * 计数-增
     */
    public static synchronized void addOnlineCount(){
        onlineCount++;
    }

    /**
     * 计数-减
     */
    public static synchronized void subOnlineCount(){
        onlineCount--;
    }

    /**
     * 获取计数
     */
    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    /**
     * 群发消息
     */
    public void sendInfo(String message){
        for (Websocket websocket:websocketSet){
            try {
                websocket.sendMessage(message);
            } catch (IOException e) {
                System.out.println(e.getMessage()+"io异常导致消息失败");
            }
        }
    }
}
