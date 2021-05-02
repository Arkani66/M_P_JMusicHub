package musichub.business;

import musichub.util.socketServer;

import java.io.*;
import java.net.*;

public class Server implements socketServer {
    private static String ip = "localhost";
    private static int port = 6666;
    private ServerSocket ss;
    private Socket socket;
    private OutputStream output;
    private InputStream input;

    public void Server()
    {
        try {
            this.ss = new ServerSocket(port);
            System.out.println("Server waiting for connection...");
            this.socket = ss.accept();//établit la connexion
            System.out.println("Connected as " + ip);
        }catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @Override
    public void writeTo(OutputStream output, String s) {
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(s);
    }

    /**
     * Méthode qui lit depuis l'entrée input une chaine de caractère et la renvoie
     * @see socketServer
     * @param input
     * @return String
     */
    @Override
    public String readFrom(InputStream input) {
        String textread = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            textread = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textread;
    }

    @Override
    public void closeOutput() {
        try {
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeInput() {
        try {
            this.input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeSocket() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
