package net.aas.unomi.points.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Resp implements Serializable {
    private String msg;
    private int code;
    private Object payload;

    public Resp(String msg, int code, Object payload) {
        this.msg = msg;
        this.code = code;
        this.payload = payload;
    }

}
