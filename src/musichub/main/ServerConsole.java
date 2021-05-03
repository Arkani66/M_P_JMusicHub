package musichub.main;

import musichub.util.EmptyFichException;
import musichub.util.Genre;
import musichub.util.socketServer;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Cette classe effectue toutes les actions disponibles sur la console du MusicHub
 *
 * @version 2.0
 *
 * @see MusicHubConsole
 * @author PROSPA Florence, MICONNET Sandrine, LEOPOLD Arnaud et CHIDIAC Bryan
 */

public class ServerConsole implements socketServer {

    /**
     * Constructeur de la classe : créer le socket server
     */
    public ServerConsole(){
        System.out.println("Waiting to create Server...");
        String ip = "localhost";
        Socket socket;
        InputStream input;
        OutputStream output;
        String text = "null";
        try {
            ServerSocket ss = new ServerSocket(6666);
            System.out.println("Server waiting for connection...");
            socket = ss.accept();//établit la connexion
            System.out.println("Connected as " + ip);
            input = socket.getInputStream();
            output = socket.getOutputStream();

            MusicHub hub = new MusicHub();
            Recuperation(hub);
            writeTo(output,"Toutes les récupérations sont finis");
            while( !(text.equals("quitter")) ){
                text = readFrom(input);
                System.out.println("Client ask :"+text);
                text = ChoixClient(hub,output,input,text);
            }
            output.close();
            input.close();
            socket.close();
        }catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * Méthode qui récupère et affiche les albums, playlists et éléments
     * depuis les fichiers XML
     * @param hub
     * @return void
     */
    public void Recuperation(MusicHub hub){
        try
        {
            System.out.println("\nRécupération Playlists");
            hub.recupPlaylists();
            System.out.println("\nRécupération Albums");
            hub.recupAlbums();
            System.out.println("\nRécupération Eléments");
            hub.recupElements();
        }
        catch (EmptyFichException ex)
        {
            System.err.println(ex.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Méthode qui contient le menu proposant les choix de fonctionnalité
     * en fonction de la réponse envoyé par le client, le server va appeler
     * les fonctions correspondantes de la classe MusicHub
     * @see MusicHub
     * @param hub
     * @param output
     * @param input
     * @param reponse
     * @return String
     */
    public String ChoixClient(MusicHub hub, OutputStream output, InputStream input, String reponse){
        String retour = "null";
        String serverrespond = "Server respond : ";
        String serverask = "Server ask : ";
        boolean fin = false;

        switch(reponse)
        {
            case "c":
                hub.ajoutChansons(output,input);
                System.out.println("\n\tChansons actuellement dans le Hub");
                writeTo(output,"\n\tChansons actuellement dans le Hub");
                hub.afftitre_genre(output,input);
                writeTo(output,"fin");
                writeTo(output,"OK");
                break;

            case "a":
                System.out.println("Vous avez choisi => Rajout d'un nouvel album ");
                writeTo(output,serverask.concat("Vous avez choisi => Rajout d'un nouvel album "));
                hub.ajoutAlbum(output, input);
                hub.afftitre_date(output,input);
                writeTo(output,"fin");
                writeTo(output,"OK");
                break;

            case "+":
                System.out.println("Vous avez choisi => Rajout d'une chanson existante à un album ");
                writeTo(output,serverask.concat("Vous avez choisi => Rajout d'une chanson existante à un album "));
                hub.ajoutChansonAlbum(output, input);
                System.out.println("\n\tAffichage des chansons par albums");
                writeTo(output,serverask.concat("\n\tAffichage des chansons par albums"));
                hub.affchanson_album(output,input);
                writeTo(output,"fin");
                writeTo(output,"OK");
                break;

            case "l":
                System.out.println("Vous avez choisi => Rajout d'un nouveau livre audio ");
                writeTo(output,serverrespond.concat("Vous avez choisi => Rajout d'un nouveau livre audio "));
                hub.ajoutLivreAudio(output, input);
                System.out.println("\n\tLivres audios actuellement dans le Hub");
                writeTo(output,serverrespond.concat("\n\tLivres audios actuellement dans le Hub"));
                hub.afflivre_auteur(output,input);
                writeTo(output,"fin");
                writeTo(output,"OK");
                break;

            case "p":
                System.out.println("Vous avez choisi => Création d'une nouvelle playlist à partir de chanson existante ");
                writeTo(output,serverrespond.concat("Vous avez choisi => Création d'une nouvelle playlist à partir de chanson existante "));
                hub.ajoutPlaylist(output, input);
                System.out.println("\nPlaylists actuellement dans le Hub");
                writeTo(output,serverrespond.concat("\nPlaylists actuellement dans le Hub"));
                hub.affplaylist(output, input);
                writeTo(output,"fin");
                writeTo(output,"OK");
                break;

            case "-":
                System.out.println("Vous avez choisi => Suppression d'une playlist ");
                writeTo(output,serverrespond.concat("Vous avez choisi => Suppression d'une playlist "));
                hub.suppressPlaylist(output,input);
                writeTo(output,"fin");
                writeTo(output,"OK");
                break;

            case "s":
                System.out.println("Vous avez choisi => Sauvegarde des playlists, des albums, des chansons, des livres audios");
                writeTo(output,serverrespond.concat("Vous avez choisi => Sauvegarde des playlists, des albums, des chansons, des livres audios"));
                hub.sauvegarde(output,input);
                writeTo(output,"fin");
                writeTo(output,"OK");
                break;

            case "h":
                System.out.println("Vous avez choisi => Aide avec les détails des commandes précédentes ");
                writeTo(output,serverrespond.concat("Vous avez choisi => Aide avec les détails des commandes précédentes "));
                hub.aideCommande(output,input);
                writeTo(output,"fin");
                writeTo(output,"OK");
                break;
            case "q":
                fin = true;
                writeTo(output,"En revoir !");
                writeTo(output,"OK");
                break;

            case "quitter":
                fin = true;
                writeTo(output,"En revoir !");
                writeTo(output,"OK");
                break;
            default :
                System.out.println("Saisie incorrecte. Veuillez choisir une option proposée");
                writeTo(output,serverrespond.concat("Saisie incorrecte. Veuillez choisir une option proposée"));
                writeTo(output,"OK");
                break;
        }
        if(fin) retour = "quitter";
        return retour;
    }

    public static void main( String[] args)
    {
        ServerConsole serverConsole = new ServerConsole();
    }

    /**
     * Méthode qui affiche sur la sortie output donnée en paramètre un string s
     * @see socketServer
     * @param output
     * @param s
     * @return void
     */
    @Override
    public void writeTo(OutputStream output,String s) {
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

    }

    @Override
    public void closeInput() {

    }

    @Override
    public void closeSocket() {

    }
}
