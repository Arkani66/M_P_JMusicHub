package musichub.business;

import musichub.util.socketServer;

import java.io.*;
import java.net.*;

public class Client implements socketServer {
    private static String ip = "localhost";
    private static int port = 6666;
    private Socket socket;
    private OutputStream output;
    private InputStream input;

    public Client(){
        try{//ouvre le socket
            this.socket = new Socket(this.ip,this.port);
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
    public void closeOutput(){
        try {
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
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
