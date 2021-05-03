package musichub.main;

import javax.sound.sampled.*;
import java.io.*;
import musichub.business.*;
import musichub.util.*;
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
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public MusicHubConsole() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        Client client = new Client();
        System.out.println(ANSI_WHITE_BACKGROUND+ANSI_BLUE+"\n\n\t\tBienvenue sur le jMusicHub\n"+ANSI_RESET);
        MusicHub hub = new MusicHub();
        try
        {
            System.out.println("\nRécupération Playlists");
            hub.recupPlaylists();
            System.out.println("\nRécupération Albums");
            hub.recupAlbums();
            System.out.println("\nRécupération Eléments");
            hub.recupElement();


        }
        catch (EmptyFichException ex)
        {
            System.err.println(ex.getMessage());
                System.exit(-1);
        }
        
        System.out.println("\n\tTri des albums par date");
        hub.afftitre_date();
        System.out.println("\n\tTri des chansons par genre");
        hub.afftitre_genre();
        System.out.println("\n\tTri des livres audio par auteur");
        hub.afflivre_auteur();
            
        Scanner scanner = new Scanner(System.in);
        boolean fin = false;
        //MENU
        while(fin == false)
        {
            
//             System.out.println("\n\tAffichage des chansons par albums");
//             hub.affchanson_album();
//             System.out.println("\n\tAffichage des éléments par playlist");
//             hub.affplaylist();
            System.out.println("\n\n\t\t"+ANSI_WHITE_BACKGROUND+ANSI_BLUE+"                                        "+ANSI_RESET);
            System.out.println("\t\t"+ANSI_WHITE_BACKGROUND+ANSI_BLUE+" ---------------MENU------------------- "+ANSI_RESET);
            System.out.println("\t\t"+ANSI_WHITE_BACKGROUND+ANSI_BLUE+"                                        "+ANSI_RESET);
            System.out.println(ANSI_GREEN+"Si vous voulez : .... =>tappez ...");
            System.out.println("Ecouter une chanson                                                     => e ");
            System.out.println("Rajout d'une nouvelle chanson                                           => c ");
            System.out.println("Rajout d'un nouvel album                                                => a ");
            System.out.println("Rajout d'une chanson existante à un album                               => + ");
            System.out.println("Rajout d'un nouveau livre audio                                         => l ");
            System.out.println("Création d'une nouvelle playlist à partir de chanson existante          => p ");
            System.out.println("Suppression d'une playlist                                              => - ");
            System.out.println("Sauvegarde des playlists, des albums, des chansons, des livres audios   => s ");
            System.out.println("Aide avec les détails des commandes précédentes                         => h ");
            System.out.println("\t\tQuitter le jMusicHub ==> q"+ANSI_RESET);
            System.out.println("\t\t"+ANSI_WHITE_BACKGROUND+ANSI_BLUE+" --------------------------------------- "+ANSI_RESET);
            
            String reponse = scanner.nextLine();
//             System.out.println("reponse = "+reponse);
                        
            switch(reponse)
            {
                case "e":
                System.out.println(ANSI_PURPLE+"Vous avez choisi => Ecouter une chanson "+ANSI_RESET);
                hub.playSound();
                break;

                case "c":
                    System.out.println(ANSI_PURPLE+"Vous avez choisi => Rajout d'une nouvelle chanson "+ANSI_RESET);
                    hub.ajoutChansons();
                    System.out.println(ANSI_CYAN+"\n\tChansons actuellement dans le Hub"/*+ANSI_RESET*/);
                    hub.afftitre_genre();
                    break;
                    
                case "a":
                    System.out.println(ANSI_PURPLE+"Vous avez choisi => Rajout d'un nouvel album "+ANSI_RESET);
                    hub.ajoutAlbum();
                    System.out.println(ANSI_CYAN+"\n\tAlbums actuellement dans le Hub");
                    hub.afftitre_date();
                    break;
                    
                case "+":
                    System.out.println(ANSI_PURPLE+"Vous avez choisi => Rajout d'une chanson existante à un album "+ANSI_RESET);
                    hub.ajoutChansonAlbum();
                    System.out.println(ANSI_CYAN+"\n\tAffichage des chansons par albums");
                    hub.affchanson_album();
                    break;
                    
                case "l":
                    System.out.println(ANSI_PURPLE+"Vous avez choisi => Rajout d'un nouveau livre audio "+ANSI_RESET);
                    hub.ajoutLivreAudio();
                    System.out.println(ANSI_CYAN+"\n\tLivres audios actuellement dans le Hub");
                    hub.afflivre_auteur();
                    break;
                    
                case "p":
                    System.out.println(ANSI_PURPLE+"Vous avez choisi => Création d'une nouvelle playlist à partir de chanson existante "+ANSI_RESET);
                    hub.ajoutPlaylist();
                    System.out.println(ANSI_CYAN+"\nPlaylists actuellement dans le Hub"+ANSI_RESET);
                    hub.affplaylist();
                    break;
                    
                case "-":
                    System.out.println(ANSI_PURPLE+"Vous avez choisi => Suppression d'une playlist "+ANSI_RESET);
                    hub.suppressPlaylist();
                    break;
                
                case "s":
                    System.out.println(ANSI_PURPLE+"Vous avez choisi => Sauvegarde des playlists, des albums, des chansons, des livres audios"+ANSI_RESET);
                    hub.sauvegarde();
                    break;
                    
                case "h":
                    System.out.println("Vous avez choisi => Aide avec les détails des commandes précédentes ");
                    hub.aideCommande();
                    break;
                case "q":
                    fin = true;
                    break;
                default :
                    System.out.println(ANSI_RED+"Saisie incorrecte. Veuillez choisir une option proposée"+ANSI_RESET);
                    break;
            }
        }
        System.out.println(ANSI_BLUE+"\n\n---------------EN REVOIR-------------------"+ANSI_RESET);
        
    }
    
    public static void main( String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        new MusicHubConsole();
    }
}
