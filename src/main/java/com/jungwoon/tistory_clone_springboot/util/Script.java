package com.jungwoon.tistory_clone_springboot.util;

public class Script {

    // 경고창을 띄우고 뒤로 가는 기능
    public static String back(String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('"+msg+"');");
        sb.append("history.back();");
        sb.append("</script>");

        return sb.toString();
    }
}
