package cn.foodslab.common.cache;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by Pengwei Ding on 2016-10-17 14:55.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SessionContext {
    public static final String KEY_USER = "user";

    private static HashMap<String, HttpSession> sessionMap = new HashMap();

    public static synchronized void addSession(HttpSession session) {
        if (session != null) {
            sessionMap.put(session.getId(), session);
            System.out.println("==========ADD SESSION============" + sessionMap.size());
        }
    }

    public static synchronized void delSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
            System.out.println("----------DEL SESSION------------" + sessionMap.size());
        }
    }

    public static synchronized HttpSession getSession(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        return sessionMap.get(sessionId);
    }
}
