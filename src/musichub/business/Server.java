package musichub.business;

import musichub.util.SocketServer;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server implements SocketServer {
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
    public void writeTo(String s) {
        //on écrit vers le flux de sortie, en accord avec le protocole du server
        PrintWriter writer = new PrintWriter(output, true);
    }
    @Override
    public String readFrom() {
        String response = null;
        try{
            this.input = socket.getInputStream();//ouvre un flux d'entrée vers le socket
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            response = reader.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }
        return response;
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
