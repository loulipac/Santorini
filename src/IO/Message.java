package IO;

import java.io.Serializable;

public class Message implements Serializable {
    static final int DECO = -1;
    static final int MOVE = 1;
    static final int START = 0;
    static final int UNAME = 2;
    static final int RDY = 3;

    private int code;
    private Object contenu;

    public Message(int code, Object contenu) {
        this.code = code;
        this.contenu = contenu;
    }

    public Message(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public Object getContenu() {
        return contenu;
    }
}
