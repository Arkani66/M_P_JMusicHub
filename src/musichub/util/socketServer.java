package musichub.util;

import musichub.main.MusicHubConsole;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Cette classe effectue toutes les actions disponibles sur la console du MusicHub
 *
 * @version 1.0
 *
 * @see MusicHubConsole
 * @author PROSPA Florence, MICONNET Sandrine, LEOPOLD Arnaud et CHIDIAC Bryan
 */

public interface socketServer {

    /**
     * Méthode qui affiche sur la sortie output donnée en paramètre un string s
     * @see socketServer
     * @param output
     * @param s
     * @return void
     */
    public void writeTo(OutputStream output,String s); // ouvre la connection server avec un socket

    /**
     * Méthode qui lit depuis l'entrée input une chaine de caractère et la renvoie
     * @see socketServer
     * @param input
     * @return String
     */
    public String readFrom(InputStream input);

    public void closeOutput(); //unused

    public void closeInput();   //unused

    public void closeSocket();  //unused
}
