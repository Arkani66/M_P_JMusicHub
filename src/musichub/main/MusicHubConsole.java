package musichub.main;

import musichub.business.*;
import musichub.util.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Cette classe effectue toutes les actions disponibles sur la console du MusicHub
 * 
 * @version 2.0
 *
 * @author PROSPA Florence, MICONNET Sandrine, LEOPOLD Arnaud et CHIDIAC Bryan
 */

 public class MusicHubConsole
{

    /**
     * Constructeur de la classe : créer le socket client
     */
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

            System.out.println("\n\n\t\tBienvenue sur le jMusicHub\n");

            String question = "null";
            String reponseC= "null";
            clearScreen();
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
                    while((line = reader.readLine()).compareTo("OK") != 0 ){
                        System.out.println(line);
                        if( line.equals("fin")) break;
                    }
                    if( line.equals("fin")) break;
                    question = scanner.nextLine();
                    writer.println(question);
                    writer.flush();
                }
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

    /**
     * This method clear the current screen
     *
     *
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Méthode qui affiche le menu proposant les choix de fonctionnalité
     * @param
     * @return void
     */
    public void AffichageMenu(){
        System.out.println("\n\n\t\t"+"                                        ");
        System.out.println("\t\t"+" ---------------MENU------------------- ");
        System.out.println("\t\t"+"                                        ");
        System.out.println("Si vous voulez : .... =>tappez ...");
        System.out.println("Rajout d'une nouvelle chanson                                           => c ");
        System.out.println("Rajout d'un nouvel album                                                => a ");
        System.out.println("Rajout d'une chanson existante a un album                               => + ");
        System.out.println("Rajout d'un nouveau livre audio                                         => l ");
        System.out.println("Creation d'une nouvelle playlist a partir de chanson existante          => p ");
        System.out.println("Suppression d'une playlist                                              => - ");
        System.out.println("Sauvegarde des playlists, des albums, des chansons, des livres audios   => s ");
        System.out.println("Aide avec les details des commandes precedentes                         => h ");
        System.out.println("\t\tQuitter le jMusicHub ==> quitter");
        System.out.println("\t\t"+" --------------------------------------- ");

    }

    /**
     * Méthode qui en fonction du choix du client (une saisie au format String) renvoie le choix pour le server
     * @param reponse
     * @return String
     */
    public String ChoixClient( String reponse){
        String retour = "null";

        switch(reponse)
        {
            case "c":
                System.out.println("Vous avez choisi => Rajout d'une nouvelle chanson ");
                retour = "c";
                break;

            case "a":
                System.out.println("Vous avez choisi => Rajout d'un nouvel album ");
                retour = "a";
                break;

            case "+":
                System.out.println("Vous avez choisi => Rajout d'une chanson existante a un album ");
                retour = "+";
                break;

            case "l":
                System.out.println("Vous avez choisi => Rajout d'un nouveau livre audio ");
                retour = "l";
                break;

            case "p":
                System.out.println("Vous avez choisi => Creation d'une nouvelle playlist a partir de chanson existante ");
                retour = "p";
                break;

            case "-":
                System.out.println("Vous avez choisi => Suppression d'une playlist ");
                retour = "-";
                break;

            case "s":
                System.out.println("Vous avez choisi => Sauvegarde des playlists, des albums, des chansons, des livres audios");
                retour = "s";
                break;

            case "h":
                System.out.println("Vous avez choisi => Aide avec les details des commandes precedentes ");
                retour = "h";
                break;
            case "q":
                retour = "q";
                break;

            case "quitter":
                retour = "quitter";
                break;
            default :
                System.out.println("Saisie incorrecte. Veuillez choisir une option proposee");
                break;
        }
        return retour;
    }



    public static void main( String[] args)
    {
        new MusicHubConsole();
    }
}
