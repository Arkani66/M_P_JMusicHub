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
    private PrintStream sortie;
    private InputStream input;
    private BufferedReader entree;

    public Server(Socket m_socket)
    {
        System.out.println("Server ");
        this.socket = m_socket;
        try{
            entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sortie = new PrintStream(socket.getOutputStream());
        }catch (IOException ioe){
            System.out.println(ioe.getMessage());
            try{
                socket.close();
            }catch (IOException ioe2){
                System.out.println(ioe2.getMessage());
            }
        }
       /* try{
            this.ss = new ServerSocket(6666);
            System.out.println("Server waiting for connection...");
            this.socket = ss.accept();//établit la connexion
            System.out.println("Connected as " + ip);
            this.input = socket.getInputStream();//ouvre un flux d'entrée vers le socket
            this.output = socket.getOutputStream();
        }catch( UnknownHostException uhe){
            System.out.println(uhe.getMessage());
        }catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }*/

    }

    @Override
    public void writeTo(PrintWriter writer,String s) {
        //on écrit vers le flux de sortie, en accord avec le protocole du server
        try {
            output = socket.getOutputStream();
            writer.println(s);
            closeOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public String readFrom(BufferedReader reader) {
        String response = null;
        try{
            response = reader.readLine();
            System.out.println(response);
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
