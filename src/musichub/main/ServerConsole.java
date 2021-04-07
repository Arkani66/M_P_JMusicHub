package musichub.main;

import musichub.business.Server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerConsole {
    public static void main( String[] args)
    {
        System.out.println("Waiting to create Server...");
        Server server = new Server();

        String fin = "quitter";
        while( /*server.readFrom().compareTo(fin) == 0*/ true)
        {

        }
    }
}
