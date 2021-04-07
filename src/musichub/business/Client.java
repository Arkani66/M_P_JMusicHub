package musichub.business;

import musichub.util.SocketServer;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client implements SocketServer {
    private static String ip = "localhost";
    private static int port = 6666;
    private Socket socket;
    private OutputStream output;
    private InputStream input;

    public Client(){
        try{//ouvre le socket
            this.socket = new Socket(this.ip,this.port);
            this.input = socket.getInputStream();//ouvre un flux d'entrée vers le socket
            this.output = socket.getOutputStream();//ouvre un flux de sortie vers le socket
            PrintWriter writer = new PrintWriter(this.output, true);//on écrit vers le flux de sortie, en accord avec le protocole du server
            writer.println("\tClient connected !");
            writer.flush();
        }catch( UnknownHostException uhe){
            System.out.println(uhe.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void writeTo(String s) {
        try {
            this.input = socket.getInputStream();//ouvre un flux d'entrée vers le socket
            this.output = socket.getOutputStream();//ouvre un flux de sortie vers le socket
            //on écrit vers le flux de sortie, en accord avec le protocole du server
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(s);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readFrom(){
        String response = null;
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             response = reader.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }
        return response;
    }

    public void closeOutput(){
        try {
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeInput(){
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
