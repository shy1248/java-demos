package me.shy.demo.netty.serializationAndZip;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class MessageBean implements Serializable {
    private long id;
    private String message;
    private byte[] attachement;

    public MessageBean(long id, String message, byte[] attachement) {
        this.id = id;
        this.message = message;
        this.attachement = attachement;
    }

    @Override public String toString() {
        return "RequestBean{" + "id=" + id + ", message='" + message + '\'' + ", attachement=" + Arrays
            .toString(attachement) + '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getAttachement() {
        return attachement;
    }

    public void setAttachement(byte[] attachement) {
        this.attachement = attachement;
    }
}
