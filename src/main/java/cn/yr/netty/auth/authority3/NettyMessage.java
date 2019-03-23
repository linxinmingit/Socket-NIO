package cn.yr.netty.auth.authority3;

import java.io.Serializable;

public class NettyMessage implements Serializable{
    private String username;
    private String passwords;
    private String url;
    private String head;
    private byte body;

    public NettyMessage() {
    }

    public NettyMessage(String username, String passwords, String url, String head,byte body) {
        this.username = username;
        this.passwords = passwords;
        this.url = url;
        this.head = head;
        this.body = body;
    }

    public byte getBody() {
        return body;
    }

    public void setBody(byte body) {
        this.body = body;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "username='" + username + '\'' +
                ", passwords='" + passwords + '\'' +
                ", url='" + url + '\'' +
                ", head='" + head + '\'' +
                ", body=" + body +
                '}';
    }
}
