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

    public String ChoixClient(MusicHub hub, OutputStream output, InputStream input, String reponse, String serverresponse){
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
        writeTo(output,serverrespond+reponse);
        System.out.println("reponse = "+reponse);
        switch(reponse)
        {
            case "c":
                /*writeTo(writer,serverrespond.concat("Vous avez choisi => Rajout d'une nouvelle chanson "));
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
                hub.ajoutChansons(artiste,titre,genre1,duree);*/
                hub.ajoutChansons(output,input);
                System.out.println("\n\tChansons actuellement dans le Hub");
                writeTo(output,"\n\tChansons actuellement dans le Hub");
                hub.afftitre_genre(output);
                break;

            case "a":
                System.out.println("Vous avez choisi => Rajout d'un nouvel album ");
                writeTo(output,serverask.concat("Vous avez choisi => Rajout d'un nouvel album "));
                boolean sortie = true;
                System.out.print("Donnez le nom de l'artiste : ");
                writeTo(output,serverask.concat("Donnez le nom de l'artiste : "));
                writeTo(output,"OK");
                artiste = readFrom(input);
                if( artiste == null) artiste = readFrom(input);
                System.out.print("Donnez le nom de l'album : ");
                writeTo(output,serverask.concat("Donnez le nom de l'album : "));
                writeTo(output,"OK");
                titre = readFrom(input);
                if( titre == null) titre = readFrom(input);
                do
                {
                    System.out.print("Donnez la date de sortie de l'album : (avec le format : 'yyyy-MM-dd')");
                    writeTo(output,serverask.concat("Donnez la date de sortie de l'album : (avec le format : 'yyyy-MM-dd')"));
                    writeTo(output,"OK");
                    date = readFrom(input);
                    if( date == null) date = readFrom(input);
                }while( sortie != true);
                try{
                    date1 =  new SimpleDateFormat("yyyy-MM-dd").parse(date);
                } catch (Exception e) {
                    System.out.println("Date hasn't working out");
                }
                System.out.print("Donnez la durée de l'album : ");
                writeTo(output,serverask.concat("Donnez la durée de l'album : "));
                writeTo(output,"OK");
                duree = Integer.parseInt(readFrom(input));
                System.out.println("Création de l'album : "+artiste+", "+titre+", "+date);
                System.out.println("\n\tAlbums actuellement dans le Hub");
                writeTo(output,serverrespond.concat("Création de l'album : "+artiste+", "+titre+", "+date));
                writeTo(output,serverrespond.concat("\n\tAlbums actuellement dans le Hub"));
                writeTo(output,"OK");
                hub.ajoutAlbum(artiste,titre,duree,date1,output);
                //hub.ajoutAlbum(output, input);
                hub.afftitre_date(output);
                writeTo(output,"OK");
                retour = "a";
                break;

            case "+":
                System.out.println("Vous avez choisi => Rajout d'une chanson existante à un album ");
                writeTo(output,serverask.concat("Vous avez choisi => Rajout d'une chanson existante à un album "));
                hub.ajoutChansonAlbum(output, input);
                System.out.println("\n\tAffichage des chansons par albums");
                writeTo(output,serverask.concat("\n\tAffichage des chansons par albums"));
                hub.affchanson_album(output);
                writeTo(output,"OK");
                retour = "+";
                break;

            case "l":
                System.out.println("Vous avez choisi => Rajout d'un nouveau livre audio ");
                writeTo(output,serverrespond.concat("Vous avez choisi => Rajout d'un nouveau livre audio "));
                writeTo(output,"OK");
                hub.ajoutLivreAudio(output, input);
                System.out.println("\n\tLivres audios actuellement dans le Hub");
                writeTo(output,serverrespond.concat("\n\tLivres audios actuellement dans le Hub"));
                writeTo(output,"OK");
                hub.afflivre_auteur(output);
                writeTo(output,"OK");
                retour = "l";
                break;

            case "p":
                System.out.println("Vous avez choisi => Création d'une nouvelle playlist à partir de chanson existante ");
                writeTo(output,serverrespond.concat("Vous avez choisi => Création d'une nouvelle playlist à partir de chanson existante "));
                hub.ajoutPlaylist(output, input);
                System.out.println("\nPlaylists actuellement dans le Hub");
                writeTo(output,serverrespond.concat("\nPlaylists actuellement dans le Hub"));
                hub.affplaylist(output);
                writeTo(output,"OK");
                retour = "p";
                break;

            case "-":
                System.out.println("Vous avez choisi => Suppression d'une playlist ");
                writeTo(output,serverrespond.concat("Vous avez choisi => Suppression d'une playlist "));
                writeTo(output,"OK");
                hub.suppressPlaylist(output,input);
                writeTo(output,"OK");
                retour = "-";
                break;

            case "s":
                System.out.println("Vous avez choisi => Sauvegarde des playlists, des albums, des chansons, des livres audios");
                writeTo(output,serverrespond.concat("Vous avez choisi => Sauvegarde des playlists, des albums, des chansons, des livres audios"));
                writeTo(output,"OK");
                hub.sauvegarde(output);
                writeTo(output,"OK");
                retour = "s";
                break;

            case "h":
                writeTo(output,"coucou");
                System.out.println("Vous avez choisi => Aide avec les détails des commandes précédentes ");
                writeTo(output,serverrespond.concat("Vous avez choisi => Aide avec les détails des commandes précédentes "));
                hub.aideCommande(output,input);
                writeTo(output,"OK");
                retour = "h";
                break;
            case "q":
                fin = true;
                retour = "quitter";
                break;

            case "quitter":
                fin = true;
                retour = "quitter";
                break;
            case "":
                retour = ChoixClient(hub,output,input,serverresponse,reponse);
                break;
            default :
                System.out.println("Saisie incorrecte. Veuillez choisir une option proposée");
                writeTo(output,serverrespond.concat("Saisie incorrecte. Veuillez choisir une option proposée"));
                writeTo(output,"OK");
                break;
        }
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
            String serverresponse = null;

            MusicHub hub = new MusicHub();
            Recuperation(hub);
            int cpt=0;
            System.out.println("text =" + text);
            text = readFrom(input);
            cpt++;
            System.out.println("Client ask :" + text);
            String s = "Server respond : ";
            s = s.concat(text);
            writeTo(output, s);
            while( !(text.equals("quitter")) ){

                System.out.println("avant menu");
                AffichageMenu(output);
                writeTo(output, "OK");
                /*while( (text = readFrom(input)).equals(null)  )
                {

                }*/
                cpt=0;

                text = readFrom(input);
                System.out.print("2 text ="+text);
                if( (!(text.equals("\n"))) || (!(text.equals(null)))/* || (!(text.equals("")))*/)
                {
                    System.out.print("3 text ="+text);
                    serverresponse = ChoixClient(hub,output,input,text, serverresponse);
                }
               /*else {
                    do{
                        System.out.println(cpt);
                        text = readFrom(input);
                        writeTo(output, String.valueOf(cpt));
                        cpt++;
                    }while( text.equals(null)  );
                }*/
                //AffichageMenu(output);
                writeTo(output,"whys");
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
