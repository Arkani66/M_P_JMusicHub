package musichub.main;

import musichub.business.*;
import musichub.util.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Cette classe effectue toutes les actions disponibles sur la console du MusicHub
 * 
 * @version 1.0
 *
 * @see MusicHubConsole
 * @author PROSPA Florence et MICONNET Sandrine
 */

 public class MusicHubConsole
{
    public static final String ANSI_RESET = "\u001B[0m";
    //public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    //public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    //public static final String ANSI_WHITE = "\u001B[37m";
    
    //public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    //public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    //public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    //public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    //public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    //public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    //public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

   /* public MusicHubConsole()
    {
        //Client client = new Client();
        String ip = "localhost";
        int port = 6666;
        OutputStream output;
        InputStream input = null;
        Socket socket;
        try {//ouvre le socket
            socket = new Socket(ip,port);
            output = socket.getOutputStream();//ouvre un flux de sortie vers le socket
            PrintWriter writer = new PrintWriter(output, true);//on écrit vers le flux de sortie, en accord avec le protocole du server
            writer.println("Enter text: ");
            writer.flush();
            input = socket.getInputStream();//ouvre un flux d'entrée vers le socket

            Scanner scanner = new Scanner(System.in);
            String question = "null";



            System.out.println(ANSI_WHITE_BACKGROUND+ANSI_BLUE+"\n\n\t\tBienvenue sur le jMusicHub\n"+ANSI_RESET);
            MusicHub hub = new MusicHub();

            Recuperation(hub);

            System.out.println("\n\tTri des albums par date");
            hub.afftitre_date();
            System.out.println("\n\tTri des chansons par genre");
            hub.afftitre_genre();
            System.out.println("\n\tTri des livres audio par auteur");
            hub.afflivre_auteur();

            AffichageMenu();
            boolean fin = false;
            //MENU
            while(!fin ) {
                fin = ChoixClient(hub, input);
            }
            input.close();
            output.close();
            socket.close();
            System.out.println(ANSI_BLUE+"\n\n---------------EN REVOIR-------------------"+ANSI_RESET);

        }catch( UnknownHostException uhe){
            System.out.println(uhe.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }*/

    /**
     * This method clear the current screen
     *
     *
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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

    public void AffichageMenu(){
        System.out.println("\n\n\t\t"+ANSI_WHITE_BACKGROUND+ANSI_BLUE+"                                        "+ANSI_RESET);
        System.out.println("\t\t"+ANSI_WHITE_BACKGROUND+ANSI_BLUE+" ---------------MENU------------------- "+ANSI_RESET);
        System.out.println("\t\t"+ANSI_WHITE_BACKGROUND+ANSI_BLUE+"                                        "+ANSI_RESET);
        System.out.println(ANSI_GREEN+"Si vous voulez : .... =>tappez ...");
        System.out.println("Rajout d'une nouvelle chanson                                           => c ");
        System.out.println("Rajout d'un nouvel album                                                => a ");
        System.out.println("Rajout d'une chanson existante à un album                               => + ");
        System.out.println("Rajout d'un nouveau livre audio                                         => l ");
        System.out.println("Création d'une nouvelle playlist à partir de chanson existante          => p ");
        System.out.println("Suppression d'une playlist                                              => - ");
        System.out.println("Sauvegarde des playlists, des albums, des chansons, des livres audios   => s ");
        System.out.println("Aide avec les détails des commandes précédentes                         => h ");
        System.out.println("\t\tQuitter le jMusicHub ==> quitter"+ANSI_RESET);
        System.out.println("\t\t"+ANSI_WHITE_BACKGROUND+ANSI_BLUE+" --------------------------------------- "+ANSI_RESET);

    }

    //public void AffichageCouleur(){}

    public String ChoixClient( String reponse){
        String retour = "null";

        switch(reponse)
        {
            case "c":
                System.out.println(ANSI_PURPLE+"Vous avez choisi => Rajout d'une nouvelle chanson "+ANSI_RESET);
                retour = "c";
                break;

            case "a":
                System.out.println(ANSI_PURPLE+"Vous avez choisi => Rajout d'un nouvel album "+ANSI_RESET);
                retour = "a";
                break;

            case "+":
                System.out.println(ANSI_PURPLE+"Vous avez choisi => Rajout d'une chanson existante à un album "+ANSI_RESET);
                retour = "+";
                break;

            case "l":
                System.out.println(ANSI_PURPLE+"Vous avez choisi => Rajout d'un nouveau livre audio "+ANSI_RESET);
                retour = "l";
                break;

            case "p":
                System.out.println(ANSI_PURPLE+"Vous avez choisi => Création d'une nouvelle playlist à partir de chanson existante "+ANSI_RESET);
                retour = "p";
                break;

            case "-":
                System.out.println(ANSI_PURPLE+"Vous avez choisi => Suppression d'une playlist "+ANSI_RESET);
                retour = "-";
                break;

            case "s":
                System.out.println(ANSI_PURPLE+"Vous avez choisi => Sauvegarde des playlists, des albums, des chansons, des livres audios"+ANSI_RESET);
                retour = "s";
                break;

            case "h":
                System.out.println("Vous avez choisi => Aide avec les détails des commandes précédentes ");
                retour = "h";
                break;
            case "q":
                retour = "q";
                break;

            case "quitter":
                retour = "quitter";
                break;
            default :
                System.out.println(ANSI_RED+"Saisie incorrecte. Veuillez choisir une option proposée"+ANSI_RESET);
                break;
        }
        return retour;
    }

    public MusicHubConsole(){
        String ip = "localhost";
        int port = 6666;
        try(Socket socket = new Socket(ip,port) ) {//ouvre le socket

            OutputStream output = socket.getOutputStream();//ouvre un flux de sortie vers le socket
            PrintWriter writer = new PrintWriter(output, true);//on écrit vers le flux de sortie, en accord avec le protocole du server
            writer.println(" hello !");
            writer.flush();
            InputStream input = socket.getInputStream();//ouvre un flux d'entrée vers le socket
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            Scanner scanner = new Scanner(System.in);

            System.out.println(ANSI_WHITE_BACKGROUND+ANSI_BLUE+"\n\n\t\tBienvenue sur le jMusicHub\n"+ANSI_RESET);

            String question = "null";
            String reponseC= "null";
            //Recuperation(hub);
            clearScreen();
            System.out.println("Toutes les récupérations sont finis");
            while( question.compareTo("quitter") != 0)
            {
                String line;
                while((line = reader.readLine()).compareTo("OK") != 0 ){
                    System.out.println(line);
                }
                AffichageMenu();
                question = scanner.nextLine();
                question = ChoixClient(question);
                writer.println(question);
                writer.flush();
                while( true ){
                    System.out.println(ANSI_BLUE+"line = "+line+ANSI_RESET);
                    while((line = reader.readLine()).compareTo("OK") != 0 ){
                        System.out.println(line);
                        if( line.equals("fin")) break;
                    }
                    if( line.equals("fin")) break;
                    System.out.println(ANSI_BLUE+"avant scanner"+ANSI_RESET);
                    question = scanner.nextLine();
                    writer.println(question);
                    writer.flush();
                    System.out.println(ANSI_BLUE+"après write"+ANSI_RESET);
                }
                System.out.println(ANSI_BLUE+"fin boucle"+ANSI_RESET);
            }
            writer.println("quitter");
            input.close();//clôt le InputStream
            output.close();//clôt le OutputStream
            socket.close();//clôt le socket

        }catch( UnknownHostException uhe){
            System.out.println(uhe.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static void main( String[] args)
    {
        new MusicHubConsole();
    }
}
