package musichub.business;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    String ip = "localhost";
    int port = 6666;
        try(
        Socket socket = new Socket(ip,port) ) {//ouvre le socket

        OutputStream output = socket.getOutputStream();//ouvre un flux de sortie vers le socket
        PrintWriter writer = new PrintWriter(output, true);//on écrit vers le flux de sortie, en accord avec le protocole du server
        writer.println("Enter text: ");
        writer.flush();
        InputStream input = socket.getInputStream();//ouvre un flux d'entrée vers le socket

        Scanner scanner = new Scanner(System.in);
        String question = "null";
        while( question.compareTo("quitter") != 0)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();//lit le flux d'entrée, en accord avec le protocole du serveur!
            System.out.println(line);
            writer.println("Client ask: " + question);
            writer.flush();
            question = scanner.nextLine();
        }

        input.close();//clôt le InputStream
        output.close();//clôt le OutputStream
        socket.close();//clôt le socket

    }catch( UnknownHostException uhe){
        System.out.println(uhe.getMessage());
    } catch (IOException ioe) {
        System.out.println(ioe.getMessage());
    }
}
