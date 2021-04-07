package musichub.business;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    private static String ip = "localhost";

    public Server(){

    }

        try {
        ServerSocket ss = new ServerSocket(6666);
        System.out.println("Server waiting for connection...");
        Socket socket = ss.accept();//établit la connexion
        System.out.println("Connected as " + ip);
        InputStream input = socket.getInputStream();

        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        Scanner scanner = new Scanner(System.in);
        String text = "null";
        String serverresponse;
        while( text.compareTo("quitter") != 0 )
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            text = reader.readLine();
            System.out.println(text);
            //String reverseText =  new StringBuilder(text).reverse().toString();
            writer.println("Server response: " + text);
            writer.flush();
            serverresponse = scanner.nextLine();

        }
        output.close();
        input.close();
        socket.close();
    }catch (IOException ioe) {
        System.out.println(ioe.getMessage());
    }

}
