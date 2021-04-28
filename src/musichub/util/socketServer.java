package musichub.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public interface socketServer {

    public void writeTo(PrintWriter writer,String s); // ouvre la connection server avec un socket

    public String readFrom(BufferedReader reader);

    public void closeOutput(); //unused

    public void closeInput();   //unused

    public void closeSocket();  //unused
}
