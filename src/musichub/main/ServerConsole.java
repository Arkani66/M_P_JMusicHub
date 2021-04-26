package musichub.main;

import musichub.util.EmptyFichException;
import musichub.util.Genre;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerConsole {

    public void writeTo(PrintWriter writer, String s){
        writer.println(s);
        writer.flush();
    }

    public String readFrom(BufferedReader reader){
        String textread = null;
        try {
             textread = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textread;
    }

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

    public void AffichageMenu(PrintWriter writer){
        writeTo(writer,"\t\t"+" ---------------MENU------------------- ");
        writeTo(writer,"\t\t"+"                                        ");
        writeTo(writer,"Si vous voulez : .... =>tappez ...");
        writeTo(writer,"Rajout d'une nouvelle chanson                                           => c ");
        writeTo(writer,"Rajout d'un nouvel album                                                => a ");
        writeTo(writer,"Rajout d'une chanson existante à un album                               => + ");
        writeTo(writer,"Rajout d'un nouveau livre audio                                         => l ");
        writeTo(writer,"Création d'une nouvelle playlist à partir de chanson existante          => p ");
        writeTo(writer,"Suppression d'une playlist                                              => - ");
        writeTo(writer,"Sauvegarde des playlists, des albums, des chansons, des livres audios   => s ");
        writeTo(writer,"Aide avec les détails des commandes précédentes                         => h ");
        writeTo(writer,"\t\tQuitter le jMusicHub ==> quitter");
        writeTo(writer,"\t\t"+" --------------------------------------- ");

    }

    public String ChoixClient(MusicHub hub, PrintWriter writer, BufferedReader reader, String reponse){
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
                writeTo(writer,serverrespond.concat("Vous avez choisi => Rajout d'une nouvelle chanson "));
                System.out.println("Vous avez choisi => Rajout d'une nouvelle chanson ");
                System.out.print("Donnez le nom de l'artiste : ");
                writeTo(writer,serverask.concat("Donnez le nom de l'artiste : "));
                writeTo(writer,"OK");
                artiste = readFrom(reader);
                System.out.print("artiste="+artiste);
                if( artiste == null) artiste = readFrom(reader);
                System.out.print("Donnez le nom de la chanson : ");
                writeTo(writer,serverask.concat("Donnez le nom de la chanson : "));
                writeTo(writer,"OK");
                titre = readFrom(reader);
                System.out.print("titre : "+titre);
                if( titre == null) titre = readFrom(reader);
                while( genre1 == null)
                {
                    System.out.print("Choisissez le genre de la chanson : Jazz(J), Classique(C), Hip-Hop(H), Rock(R), Pop(P), Rap(A)");
                    writeTo(writer,serverask.concat("Choisissez le genre de la chanson : Jazz(J), Classique(C), Hip-Hop(H), Rock(R), Pop(P), Rap(A)"));
                    writeTo(writer,"OK");
                    genre = readFrom(reader);
                    System.out.print("genre : "+genre);
                    switch(genre)
                    {
                        case "J" :
                            genre1 = Genre.JAZZ;
                            break;

                        case "C":
                            genre1 = Genre.CLASSIQUE;
                            break;

                        case "H":
                            genre1 = Genre.HIPHOP;
                            break;

                        case "R":
                            genre1 = Genre.ROCK;
                            break;

                        case "P":
                            genre1 = Genre.POP;
                            break;

                        case "A":
                            genre1 = Genre.RAP;
                            break;
                        default :
                            System.out.println("Veuillez choisir un genre existant !");
                            writeTo(writer,serverask.concat("Veuillez choisir un genre existant !"));
                            break;
                    }
                }
                System.out.print("Donnez la durée de la chanson en secondes (Attention uniquement un nombre est attendu): ");
                writeTo(writer,serverask.concat("Donnez la durée de la chanson en secondes (Attention uniquement un nombre est attendu): "));
                writeTo(writer,"OK");
                duree = Integer.parseInt(readFrom(reader));
                System.out.println("Création de la Chanson : "+artiste+", "+titre+", "+duree);
                writeTo(writer,"Création de la Chanson : "+artiste+", "+titre+", "+duree);
                hub.ajoutChansons(artiste,titre,genre1,duree);
                System.out.println("\n\tChansons actuellement dans le Hub");
                writeTo(writer,"\n\tChansons actuellement dans le Hub");
                //hub.afftitre_genre();
                break;

            case "a":
                System.out.println("Vous avez choisi => Rajout d'un nouvel album ");
                writeTo(writer,serverask.concat("Vous avez choisi => Rajout d'un nouvel album "));
                boolean sortie = true;
                System.out.print("Donnez le nom de l'artiste : ");
                writeTo(writer,serverask.concat("Donnez le nom de l'artiste : "));
                writeTo(writer,"OK");
                artiste = readFrom(reader);
                if( artiste == null) artiste = readFrom(reader);
                System.out.print("Donnez le nom de l'album : ");
                writeTo(writer,serverask.concat("Donnez le nom de l'album : "));
                writeTo(writer,"OK");
                titre = readFrom(reader);
                if( titre == null) titre = readFrom(reader);
                do
                {
                    System.out.print("Donnez la date de sortie de l'album : (avec le format : 'yyyy-MM-dd')");
                    writeTo(writer,serverask.concat("Donnez la date de sortie de l'album : (avec le format : 'yyyy-MM-dd')"));
                    writeTo(writer,"OK");
                    date = readFrom(reader);
                    if( date == null) date = readFrom(reader);
                }while( sortie != true);
                try{
                    date1 =  new SimpleDateFormat("yyyy-MM-dd").parse(date);
                } catch (Exception e) {
                    System.out.println("Date hasn't working out");
                }
                System.out.print("Donnez la durée de l'album : ");
                writeTo(writer,serverask.concat("Donnez la durée de l'album : "));
                writeTo(writer,"OK");
                duree = Integer.parseInt(readFrom(reader));
                System.out.println("Création de l'album : "+artiste+", "+titre+", "+date);
                System.out.println("\n\tAlbums actuellement dans le Hub");
                writeTo(writer,serverrespond.concat("Création de l'album : "+artiste+", "+titre+", "+date));
                writeTo(writer,serverrespond.concat("\n\tAlbums actuellement dans le Hub"));
                writeTo(writer,"OK");
                hub.ajoutAlbum(artiste,titre,duree,date1);
                hub.afftitre_date();
                break;

            case "+":
                System.out.println("Vous avez choisi => Rajout d'une chanson existante à un album ");
                writeTo(writer,serverask.concat("Vous avez choisi => Rajout d'une chanson existante à un album "));
                //hub.ajoutChansonAlbum();
                System.out.println("\n\tAffichage des chansons par albums");
                writeTo(writer,serverask.concat("\n\tAffichage des chansons par albums"));
                hub.affchanson_album();
                break;

            case "l":
                System.out.println("Vous avez choisi => Rajout d'un nouveau livre audio ");
                writeTo(writer,serverrespond.concat("Vous avez choisi => Rajout d'un nouveau livre audio "));
                //hub.ajoutLivreAudio();
                System.out.println("\n\tLivres audios actuellement dans le Hub");
                writeTo(writer,serverrespond.concat("\n\tLivres audios actuellement dans le Hub"));
                //hub.afflivre_auteur();
                break;

            case "p":
                System.out.println("Vous avez choisi => Création d'une nouvelle playlist à partir de chanson existante ");
                writeTo(writer,serverrespond.concat("Vous avez choisi => Création d'une nouvelle playlist à partir de chanson existante "));
                //hub.ajoutPlaylist();
                System.out.println("\nPlaylists actuellement dans le Hub");
                writeTo(writer,serverrespond.concat("\nPlaylists actuellement dans le Hub"));
                //hub.affplaylist();
                break;

            case "-":
                System.out.println("Vous avez choisi => Suppression d'une playlist ");
                writeTo(writer,serverrespond.concat("Vous avez choisi => Suppression d'une playlist "));
                //hub.suppressPlaylist();
                break;

            case "s":
                System.out.println("Vous avez choisi => Sauvegarde des playlists, des albums, des chansons, des livres audios");
                writeTo(writer,serverrespond.concat("Vous avez choisi => Sauvegarde des playlists, des albums, des chansons, des livres audios"));
                //hub.sauvegarde();
                break;

            case "h":
                System.out.println("Vous avez choisi => Aide avec les détails des commandes précédentes ");
                writeTo(writer,serverrespond.concat("Vous avez choisi => Aide avec les détails des commandes précédentes "));
                //hub.aideCommande();
                break;
            case "q":
                fin = true;
                break;

            case "quitter":
                fin = true;
                break;
            default :
                System.out.println("Saisie incorrecte. Veuillez choisir une option proposée");
                writeTo(writer,serverrespond.concat("Saisie incorrecte. Veuillez choisir une option proposée"));
                writeTo(writer,"OK");
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

            while( !(text.equals("quitter")) ){
                AffichageMenu(writer);
                //while( !(text.equals("null")) )
                //{
                    System.out.println("text ="+text);
                    text = readFrom(reader);
                    System.out.println("Client ask :"+text);
                    String s = "Server respond : ";
                    s = s.concat(text);
                    writeTo(writer,s);
                    writeTo(writer,"OK");
                    // if(!(text.equals("null")) ) text = ChoixClient(hub,writer,reader,text);
                //}
                System.out.println("2 text ="+text);
                text = readFrom(reader);
                text = ChoixClient(hub,writer,reader,text);
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
}
