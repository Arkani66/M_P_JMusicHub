package musichub.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface socketServer {

    public void writeTo(String s); // ouvre la connection server avec un socket

    public String readFrom();

    public void closeOutput();

    public void closeInput();

    public void closeSocket();
}
