package musichub.main;

import musichub.business.Server;
import musichub.util.socketServer;

import java.io.*;
import java.net.*;

public class ServerConsole {
    public static void main( String[] args)
    {
        System.out.println("Waiting to create Server...");
        String ip = "localhost";
        Socket socket;
        InputStream input;
        OutputStream output;
        try {
            ServerSocket ss = new ServerSocket(6666);
            System.out.println("Server waiting for connection...");
            socket = ss.accept();//établit la connexion
            System.out.println("Connected as " + ip);
            input = socket.getInputStream();
            output = socket.getOutputStream();

            PrintWriter writer = new PrintWriter(output, true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String text = "null";
            String serverresponse;
            //text = reader.readLine();
            while(text.compareTo("quitter") != 0 )
            {
                System.out.println("text ="+text);
                text = reader.readLine();
                System.out.println("Client ask :"+text);
                writer.println("Server response: " + text);
                writer.flush();
            }
            output.close();
            input.close();
            socket.close();
        }catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        /*int port = 6666;
        ServerSocket mserverSocket;
        Socket socket;
        String texte= "null";
        BufferedReader entree;
        PrintStream sortie;
        int i =0;

        try{
            mserverSocket = new ServerSocket(port);
            System.out.println("Server waiting for connection...");
            socket = mserverSocket.accept();//établit la connexion
            entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sortie = new PrintStream(socket.getOutputStream());
            while(true){
                socket = mserverSocket.accept();//établit la connexion
                if(i==0) System.out.println("Connected as " + ip);
                i = 1;
                if( (texte = entree.readLine()).compareTo("coucou") == 0 ) sortie.println("coucou");
            }
        }catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }*/

        /*Socket socket = new Socket();
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Server waiting for connection...");
             socket = ss.accept();//établit la connexion
            System.out.println("Connected as " + ip);
        }catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }*/
        /*Server server = new Server();
        server.writeTo("coucou");



        String fin = "quitter";
        while( server.readFrom().compareTo(fin) == 0 true)
        {
            server.readFrom();
        }*/

    }
}
