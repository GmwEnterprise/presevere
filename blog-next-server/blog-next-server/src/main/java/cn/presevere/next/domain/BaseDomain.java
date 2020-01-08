package cn.presevere.next.domain;

import java.io.*;

public abstract class BaseDomain implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public <R extends BaseDomain> R deepCopy(R source) {
        try {
            var byteOut = new ByteArrayOutputStream();
            var output = new ObjectOutputStream(byteOut);
            output.writeObject(source);

            var byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            var input = new ObjectInputStream(byteIn);
            return ((R) input.readObject());
        } catch (Exception e) {
            return null;
        }
    }
}
