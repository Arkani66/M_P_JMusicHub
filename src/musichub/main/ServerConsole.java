package musichub.main;

import musichub.util.EmptyFichException;
import musichub.util.Genre;
import musichub.util.socketServer;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerConsole implements socketServer {

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

    public void AffichageMenu(OutputStream output){
        writeTo(output,"\t\t"+" ---------------MENU------------------- ");
        writeTo(output,"\t\t"+"                                        ");
        writeTo(output,"Si vous voulez : .... =>tappez ...");
        writeTo(output,"Rajout d'une nouvelle chanson                                           => c ");
        writeTo(output,"Rajout d'un nouvel album                                                => a ");
        writeTo(output,"Rajout d'une chanson existante à un album                               => + ");
        writeTo(output,"Rajout d'un nouveau livre audio                                         => l ");
        writeTo(output,"Création d'une nouvelle playlist à partir de chanson existante          => p ");
        writeTo(output,"Suppression d'une playlist                                              => - ");
        writeTo(output,"Sauvegarde des playlists, des albums, des chansons, des livres audios   => s ");
        writeTo(output,"Aide avec les détails des commandes précédentes                         => h ");
        writeTo(output,"\t\tQuitter le jMusicHub ==> quitter");
        writeTo(output,"\t\t"+" --------------------------------------- ");

    }

    public String ChoixClient(MusicHub hub, OutputStream output, InputStream input, String reponse){
        String retour = "null";
        String serverrespond = "Server respond : ";
        String serverask = "Server ask : ";
        boolean fin = false;
        String artiste = null;
        String titre = null;
        String genre = null;
        Genre genre1=null;
        int duree;
        String date = null;
        Date date1 = null;

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
                break;

            case "quitter":
                fin = true;
                writeTo(output,"fin");
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

            PrintWriter writer = new PrintWriter(output, true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String serverresponse;

            MusicHub hub = new MusicHub();
            Recuperation(hub);
            String s = "Server respond : ";

            while( !(text.equals("quitter")) ){
                text = readFrom(input);
                System.out.println("text ="+text);
                System.out.println("Client ask :"+text);
                System.out.println("2 text ="+text);
                text = ChoixClient(hub,output,input,text);
            }
            output.close();
            input.close();
            socket.close();
        }catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static void main( String[] args)
    {
        ServerConsole serverConsole = new ServerConsole();
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

    @Override
    public void writeTo(OutputStream output,String s) {
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(s);
        //writer.flush();
    }

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
