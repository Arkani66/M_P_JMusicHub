package musichub.main;

import musichub.business.*;
import musichub.util.*;

import java.io.*;
import java.util.*;

import java.util.Scanner;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;  

/**
 * Cette classe effectue toutes les actions disponibles sur la console du MusicHub
 * 
 * @version 2.0
 *
 * @see MusicHubConsole
 * @author PROSPA Florence et MICONNET Sandrine
 */


public class MusicHub implements socketServer
{
    Scanner scanner = new Scanner(System.in);
    LinkedList<Album> albums;
    LinkedList<Playlist> playlists;
    LinkedList<Chanson> chansons;
    LinkedList<LivreAudio> livres;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";

    /**
     * Constructeur du MusicHub
     */
    public MusicHub(){
        
        this.albums = new LinkedList<Album>();
        this.playlists = new LinkedList<Playlist>();
        this.chansons = new LinkedList<Chanson>();
        this.livres = new LinkedList<LivreAudio>();
    }

//////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////RÉCUPÉRATION///////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////

    
/**
* Méthode qui récupère les éléments du fichier playlists.xml et les ajoutent à la liste playlist
* Une exception se déclenche si le fichier est vide
*/
    public void recupPlaylists() throws EmptyFichException{
        FileXML recup = new FileXML();
        playlists = recup.readPlaylists(playlists);
        if (playlists.isEmpty()) throw new EmptyFichException ("Le fichier 'playlists.xml' demandé est vide.");
    }

/**
* Méthode qui récupère les éléments du fichier elements.xml et les ajoutent aux listes chansons et livres qui contiennent la liste des chansons et des livres du MusicHub
* Une exception se déclenche si le fichier est vide
*/
    public void recupElements() throws EmptyFichException{
        FileXML recup = new FileXML();
        chansons = recup.readElementsChansons(chansons);
        livres = recup.readElementsLivres(livres);
        if (chansons.isEmpty()) throw new EmptyFichException ("Le fichier 'elements.xml' demandé est vide pour les chansons.");
        if (livres.isEmpty()) throw new EmptyFichException ("Le fichier 'elements.xml' demandé est vide pour les livres audio.");
        System.out.println("\t ->Fini");
    }

/**
* Méthode qui récupère les éléments du fichier albums.xml et les ajoutent à la liste albums qui contient la liste des albums du MusicHub
* Une exception se déclenche si le fichier est vide
*/
    public void recupAlbums() throws EmptyFichException{
        FileXML recup = new FileXML();
        albums = recup.readAlbums(albums);
        if (albums.isEmpty()) throw new EmptyFichException ("Le fichier 'albums.xml' demandé est vide.");
    }

//////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////MANIPULATION///////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////

/**
* Méthode qui vérifie si les saisie sont null ou pas
* (NOTE : ne marche pas pour l'instant)
* @param question la question posée
* @param reponse la réponse saisie par l'utilisateur
* @return reponse la réponse correctement saisie en fonction de la question en paramètre
*/
    public String verifString(String question,String reponse)
    {
        int continu = 1;
        do
        {
            if( reponse == null)
            {
                System.out.println("\n"+ANSI_RED+"Votre saisie est null, voulez vous recommencer votre saisie ? OUI(0) et NON(1)"+ANSI_RESET);
                continu = scanner.nextInt();
            }
            if( continu == 0)
            {
                System.out.println(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+question+ANSI_RESET);
                reponse = scanner.nextLine();
            }
        }while(continu == 1 || reponse == null);
        return reponse;
    }

/**
* Méthode qui créer l'ID adapté à un élément
* si nous créons un ID pour une playlist, nous devons uniquement créer et vérifier les trois premiers chiffres
*                  pour un album, nous devons uniquement créer et vérifier les trois chiffres de la deuxième partie
*                  pour un élément, nous devons uniquement créer et vérifier les trois chiffres de la dernière partie
* @param cas quel est le type de l'ID à créer
* @return String
*/
    public String creaID(int cas)
    {
        String id = null;
        int cmpid=001;
        switch(cas)
        {
            case 0:
                
                for(Playlist courant : playlists)
                {
                    String idStr = courant.getPlaylist();
                    int IDcourant = Integer.parseInt(idStr);
                    if( cmpid == IDcourant) cmpid++;
                }
                StringBuffer bufferid = new StringBuffer();
                if(cmpid/10 > 0)
                {
                    bufferid = bufferid.append("0").append(String.valueOf(cmpid)).append("-000-000");
                }
                else if(cmpid/100 > 0)
                {
                    bufferid = bufferid.append(String.valueOf(cmpid)).append("-000-000");
                }
                else if(cmpid/1 > 0)
                {
                    bufferid = bufferid.append("00").append(String.valueOf(cmpid)).append("-000-000");
                }
                id = bufferid.toString();
                break;
                
            case 1:
                for(Album courant : albums)
                {
                    String idStr = courant.getAlbum();
                    int IDcourant = Integer.parseInt(idStr);
                    if( cmpid == IDcourant) cmpid++;
                }
                StringBuffer bufferid1 = new StringBuffer();
                if(cmpid/10 > 0) bufferid1 = bufferid1.append("000-").append("0").append(String.valueOf(cmpid)).append("-000");
                else if(cmpid/100 > 0) bufferid1 = bufferid1.append("000-").append(String.valueOf(cmpid)).append("-000");
                else if(cmpid/1 > 0) bufferid1 = bufferid1.append("000-").append("00").append(String.valueOf(cmpid)).append("-000");
                id = bufferid1.toString();
                break;
                
            case 2:
                for(Chanson courant : chansons)
                {
                    String idStr = courant.getPartID();
                    int IDcourant = Integer.parseInt(idStr);
                    if( cmpid == IDcourant) cmpid++;
                }
                for(LivreAudio courant : livres)
                {
                    String idStr = courant.getPartID();
                    int IDcourant = Integer.parseInt(idStr);
                    if( cmpid == IDcourant) cmpid++;
                }
                StringBuffer bufferid2 = new StringBuffer();
                if(cmpid/10 > 0) bufferid2 = bufferid2.append("000-000-").append("0").append(String.valueOf(cmpid));
                else if(cmpid/100 > 0) bufferid2 = bufferid2.append("000-000-").append(String.valueOf(cmpid));
                else if(cmpid/1 > 0) bufferid2 = bufferid2.append("000-000-").append("00").append(String.valueOf(cmpid));
                id = bufferid2.toString();
                break;
        }
        return id;
    }

/**
* Méthode qui vérifie que la date entrée est valide selon nos critères
* (NOTE : ne marche pas pour l'instant)
* @see public void ajoutAlbum()
* @param date_a_verif
* @return boolean true si la date est valide, false si la date n'est pas valide
*/
    public boolean verifDate(String date_a_verif){      
        String[] parties = date_a_verif.split("-",3);
        int nb=0, i=0, mois=0;
        boolean retour = true;
        for (String a : parties){ 
            nb = Integer.parseInt(a);
            if( nb < 0)
            {
                System.out.println("nombre incorrect ! => "+i);
                return false;
            }
            else if( nb > 12 && i == 1)
            {
                System.out.println("Mois incorrect ! "+nb);
                mois = nb;
                retour = false;
            }
            else if( nb > 30 && i == 2)
            {
                if( (mois%2 == 0 && mois < 8) &&  nb == 30 ) retour = true;
                else if( (mois%2 == 0 && mois >= 8 ) && nb == 31 ) retour = true;
                else 
                {
                    System.out.println("jour incorrect ! "+nb+"//"+mois);
                    retour = false;
                }
            }
            i++;
        }
        return retour;
    }

/**
* Méthode qui ajoute une chanson à la liste de chansons
* @param output
 * @param input
 * @return void
*/
    public void ajoutChansons(OutputStream output, InputStream input){
        String artiste = null;
        String titre = null;
        String genre = null;
        String id = null;
        Genre genre1=null;
        int duree;
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de l'artiste : "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de l'artiste : "+ANSI_RESET+" ");
        writeTo(output,"OK");
        artiste = readFrom(input);
        if( artiste == null) artiste = readFrom(input);
//         artiste = verifString("Donnez le nom de l'artiste : ",artiste);
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la chanson : "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la chanson : "+ANSI_RESET+" ");
        writeTo(output,"OK");
        titre = readFrom(input);
        if( titre == null) titre = readFrom(input);
//         titre = verifString("Donnez le nom de la chanson : ",titre);
        id = creaID(2);
        while( genre1 == null)
        {
            System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Choisissez le genre de la chanson : Jazz(J), Classique(C), Hip-Hop(H), Rock(R), Pop(P), Rap(A)"+ANSI_RESET+" ");
            writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Choisissez le genre de la chanson : Jazz(J), Classique(C), Hip-Hop(H), Rock(R), Pop(P), Rap(A)"+ANSI_RESET+" ");
            writeTo(output,"OK");
            genre = readFrom(input);
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
                    System.out.println(ANSI_RED+"Veuillez choisir un genre existant !"+ANSI_RESET);
                    writeTo(output,ANSI_RED+"Veuillez choisir un genre existant !"+ANSI_RESET);
                    break;
            }
        }
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez la durée de la chanson en secondes (Attention uniquement un nombre est attendu): "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez la durée de la chanson en secondes (Attention uniquement un nombre est attendu): "+ANSI_RESET+" ");
        writeTo(output,"OK");
        duree = Integer.parseInt(readFrom(input));
        System.out.println("Création de la Chanson : "+artiste+", "+titre+", "+id);
        writeTo(output,"Création de la Chanson : "+artiste+", "+titre+", "+id);
        Chanson crea_song = new Chanson(titre,artiste,duree,id,"MP3",genre1);
        System.out.println(ANSI_BLACK_BACKGROUND+ANSI_CYAN+" Vérification, vous avez créé = "+ANSI_RESET+ANSI_BLACK_BACKGROUND+ANSI_CYAN+crea_song.toString()+" !"+ANSI_RESET);
        writeTo(output,ANSI_BLACK_BACKGROUND+ANSI_CYAN+" Vérification, vous avez créé = "+ANSI_RESET+ANSI_BLACK_BACKGROUND+ANSI_CYAN+crea_song.toString()+" !"+ANSI_RESET);
        chansons.add(crea_song);
        writeTo(output,ANSI_BLUE+"\t Appuyez sur Entrée "+ANSI_RESET+" ");
        writeTo(output,"OK");
        readFrom(input);
    }

/**
* Méthode qui ajoute un album à la liste des albums
* Demande : titre, artiste, date, duree
 * @param output
 * @param input
 * @return void
*/
    public void ajoutAlbum(OutputStream output,InputStream input){
        String artiste = null;
        String titre = null;
        String id = null;
        String date = null;
        Date date1 = null;
        int duree;
        boolean sortie = true;
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de l'artiste : "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de l'artiste : "+ANSI_RESET+" ");
        writeTo(output,"OK");
        artiste = readFrom(input);
        if( artiste == null) artiste = readFrom(input);
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de l'album : "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de l'album : "+ANSI_RESET+" ");
        writeTo(output,"OK");
        titre = readFrom(input);
        if( titre == null) titre = readFrom(input);
        id = creaID(1);
        do
        {
            System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez la date de sortie de l'album : (avec le format : 'yyyy-MM-dd')"+ANSI_RESET+" ");
            writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez la date de sortie de l'album : (avec le format : 'yyyy-MM-dd')"+ANSI_RESET+" ");
            writeTo(output,"OK");
            date = readFrom(input);
            if( date == null) date = readFrom(input);
            sortie = this.verifDate(date);
        }while( sortie != true);
        try{
            date1 =  new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            System.out.println(ANSI_RED+"Date hasn't working out"+ANSI_RESET);
            writeTo(output,ANSI_RED+"Date hasn't working out"+ANSI_RESET);
        }
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez la durée de l'album : "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez la durée de l'album : "+ANSI_RESET+" ");
        writeTo(output,"OK");
        duree = Integer.parseInt(readFrom(input));
        System.out.println("Création de l'album : "+artiste+", "+titre+", "+id);
        Album crea_album = new Album(titre,artiste,duree,date1,id);
        System.out.println(ANSI_BLACK_BACKGROUND+ANSI_CYAN+" Vérification, vous avez créé = "+ANSI_RESET+ANSI_BLACK_BACKGROUND+ANSI_CYAN+crea_album.toString()+" !"+ANSI_RESET);
        writeTo(output,ANSI_BLACK_BACKGROUND+ANSI_CYAN+" Vérification, vous avez créé = "+ANSI_RESET+ANSI_BLACK_BACKGROUND+ANSI_CYAN+crea_album.toString()+" !"+ANSI_RESET);
        albums.add(crea_album);
        writeTo(output,ANSI_BLUE+"\t Appuyez sur Entrée "+ANSI_RESET+" ");
        writeTo(output,"OK");
        readFrom(input);
    }
    
/**
* Méthode qui ajoute une chanson à la liste des chansons et à un album
 * @param output
 * @param input
 * @return void
*/
    public void ajoutChansonAlbum(OutputStream output, InputStream input){
    /*on affiche les chansons existantes et les albums existants*/
        System.out.println(ANSI_CYAN+"Les Chansons existantes :\n"+chansons.toString()+ANSI_RESET);
        writeTo(output,ANSI_CYAN+"Les Chansons existantes :\n"+chansons.toString()+ANSI_RESET);
        System.out.println(ANSI_CYAN+"Les Albums existants :\n"+albums.toString()+ANSI_RESET);
        writeTo(output,ANSI_CYAN+"Les Albums existants :\n"+albums.toString()+ANSI_RESET);
    /*choix de la chanson à ajouter à un album*/
        String nomchanson = null;
        String nomalbum = null;
        int index = -1,verif=-1,cmpt = 0;
        System.out.print("\n"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre de la chanson à déplacer: "+ANSI_RESET+" ");
        writeTo(output,"\n"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre de la chanson à déplacer: "+ANSI_RESET+" ");
        writeTo(output,"OK");
        nomchanson = readFrom(input);
        if( nomchanson == null) readFrom(input);
    /*recherche de la chanson dans la liste chansons : /!\ si elle n'est pas trouvé demander de vérifier l'ortographe et de réécrire */
        for(Chanson courant : chansons){
            if(nomchanson.equals(courant.getTitre())==true) 
            {
                index = cmpt;
            }
            cmpt++;
        }
        if( index == -1 )
        {
            int continu = 1;
            System.out.println(ANSI_RED+"Cette chanson n'existe pas. Voulez vous recommencer ? OUI(0) ou NON(1)"+ANSI_RESET+" ");
            writeTo(output,ANSI_RED+"Cette chanson n'existe pas. Voulez vous recommencer ? OUI(0) ou NON(1)"+ANSI_RESET+" ");
            writeTo(output,"OK");
            continu = Integer.parseInt(readFrom(input));
            while( continu == 0)
            {
                System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre de la chanson à déplacer: "+ANSI_RESET+" ");
                writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre de la chanson à déplacer: "+ANSI_RESET+" ");
                writeTo(output,"OK");
                nomchanson = readFrom(input);
                cmpt = 0;
                for(Chanson courant : chansons){
                    if(nomchanson.equals(courant.getTitre())==true)
                    {
                        index = cmpt;
                        continu = 1;
                    }
                    cmpt++; 
                }
            }
            if(continu == 1) return;
        }
        Chanson chanson = chansons.get(index);
    /** demander dans quel album il faut mettre la chanson
    * recherche de l'album : /!\ si il n'est pas trouvé demander de vérifier l'ortographe et de réécrire
    */
        while( verif == -1)
        {
            System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la playlist dans laquelle vous voulez mettre cette chanson: "+ANSI_RESET+" ");
            writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la playlist dans laquelle vous voulez mettre cette chanson: "+ANSI_RESET+" ");
            writeTo(output,"OK");
            nomalbum = readFrom(input);
            if( nomalbum == null) nomalbum = readFrom(input);
            cmpt = 0;
            for(Album courant : albums){
                if(nomalbum.equals(courant.getTitre())==true)
                {
                    verif = cmpt;
                }
                cmpt++; 
            }
            if( verif == -1){
                System.out.println(ANSI_RED+"Cet album n'existe pas. Vérifiez l'ortographe et recommencez."+ANSI_RESET);
                writeTo(output,ANSI_RED+"Cet album n'existe pas. Vérifiez l'ortographe et recommencez."+ANSI_RESET);
            }
        }
    /* split l'ID de l'album et isoler la partie album (1)*/
        Album album = albums.get(verif);
        String idsplitalbum = album.getAlbum();
    /* split l'ID de la chanson et isoler la partie album (1)*/
        String idchanson = chanson.getID();
        String[] parties = idchanson.split("-",3);
        String numalbum = null;
    /* rendre la partie album de la chanson égale à celle de l'album*/
        parties[1] = idsplitalbum;
    /* recoller les ID et update la valeur des ID des DEUX*/
        StringBuffer buffer = new StringBuffer();
        int cpt=0;
        for(String a : parties){        //pour reconstituer l'ID
            buffer = buffer.append(a);
            if(cpt!=2) buffer = buffer.append("-");
            cpt++;	
        }
        chanson.setID(buffer.toString());
    }
    
/**
* Méthode qui ajoute un livre à la liste des livres audios
 * @param output
 * @param input
 * @return void
*/
    public void ajoutLivreAudio(OutputStream output, InputStream input){
        String auteur = null;
        String titre = null;
        String id = null;
        String langue = null;
        String categorie = null;
        Langue  langue1 = null;
        Categorie categorie1 = null;
        int duree;
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de l'auteur : "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de l'auteur : "+ANSI_RESET+" ");
        writeTo(output,"OK");
        auteur = readFrom(input);
        if( auteur == null) auteur = readFrom(input);
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la chanson : "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom du livre : "+ANSI_RESET+" ");
        writeTo(output,"OK");
        titre = readFrom(input);
        if( titre == null) titre = scanner.nextLine();
        id = creaID(2);
        if( id == null) id = readFrom(input);
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez la durée du livre : "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez la durée du livre : "+ANSI_RESET+" ");
        writeTo(output,"OK");
        duree = Integer.parseInt(readFrom(input));
        while( langue1 == null)
        {
            System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Choisissez la langue de la chanson : FRANCAIS(F), ANGLAIS(A), ITALIEN(I), ESPAGNOL(E), ALLEMAND(D)"+ANSI_RESET+" ");
            writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Choisissez la langue du livre : FRANCAIS(F), ANGLAIS(A), ITALIEN(I), ESPAGNOL(E), ALLEMAND(D)"+ANSI_RESET+" ");
            writeTo(output,"OK");
            langue = readFrom(input);
            switch(langue)
            {
                case "F" :
                    langue1 = Langue.FRANCAIS;
                    break;
                
                case "A":
                    langue1 = Langue.ANGLAIS;
                    break;
                
                case "I":
                    langue1 = Langue.ITALIEN;
                    break;
                
                case "E":
                    langue1 = Langue.ESPAGNOL;
                    break;
                
                case "D":
                    langue1 = Langue.ALLEMAND;
                    break;
                
                default :
                    System.out.println(ANSI_RED+"Veuillez choisir une langue existante !"+ANSI_RESET);
                    writeTo(output,ANSI_RED+"Veuillez choisir une langue existante !"+ANSI_RESET);
                    writeTo(output,"OK");
                    break;
            }
        }
        while( categorie1 == null)
        {
            System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Choisissez le genre de la chanson : JEUNESSE(J), ROMAN(R), THEATRE(T), DISCOURS(S), DOCUMENTAIRE(D);"+ANSI_RESET+" ");
            writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Choisissez le genre du livre : JEUNESSE(J), ROMAN(R), THEATRE(T), DISCOURS(S), DOCUMENTAIRE(D);"+ANSI_RESET+" ");
            writeTo(output,"OK");
            categorie = readFrom(input);
            switch(categorie)
            {
                case "J" :
                    categorie1 = Categorie.JEUNESSE;
                    break;
                
                case "R":
                    categorie1 = Categorie.ROMAN;
                    break;
                
                case "T":
                    categorie1 = Categorie.THEATRE;
                    break;
                
                case "S":
                    categorie1 = Categorie.DISCOURS;
                    break;
                
                case "D":
                    categorie1 = Categorie.DOCUMENTAIRE;
                    break;

                default :
                    System.out.println(ANSI_RED+"Veuillez choisir une catégorie existante !"+ANSI_RESET);
                    writeTo(output,ANSI_RED+"Veuillez choisir une catégorie existante !"+ANSI_RESET);
                    writeTo(output,"OK");
                    break;
            }
        }
        System.out.println("Création du livre audio : "+auteur+", "+titre+", "+id);
        writeTo(output,"Création du livre audio : "+auteur+", "+titre+", "+id);
        LivreAudio crea_livre = new LivreAudio(titre,auteur,duree,id,"MP4",langue1,categorie1);
        System.out.println(ANSI_BLACK_BACKGROUND+ANSI_CYAN+" Vérification, vous avez créé = "+crea_livre.toString()+" !"+ANSI_RESET);
        writeTo(output,ANSI_BLACK_BACKGROUND+ANSI_CYAN+" Vérification, vous avez créé = "+crea_livre.toString()+" !"+ANSI_RESET);
        livres.add(crea_livre);
        System.out.println(livres);
        writeTo(output,livres.toString());
        writeTo(output,ANSI_BLUE+"\t Appuyez sur Entrée "+ANSI_RESET+" ");
        writeTo(output,"OK");
        readFrom(input);
    }

/**
* Méthode qui créer d'une playlist à partir de chansons existantes
 * @param output
 * @param input
 * @return void
*/
    public void ajoutPlaylist(OutputStream output, InputStream input){
        String name,idd;
        int ans,inde=-1,cptt=0;
        affplaylist(output, input);
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la playlist à créer : "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la playlist à créer : "+ANSI_RESET+" ");
        writeTo(output,"OK");
        name = readFrom(input);
        idd = creaID(0);
        for(Playlist play : playlists){
        	if(name.equals(play.getNom())==true){
        		inde=-1;
        	}
        	inde=cptt;
        	cptt ++;
        }
        if(inde == -1){ 
        	System.out.print(ANSI_RED+"La playlist :"+name+" existe déjà !"+ANSI_RESET);
            writeTo(output,ANSI_RED+"La playlist :"+name+" existe déjà !"+ANSI_RESET);
        	return;
        }
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Etes-vous sûr de vouloir créer la playlist : "+name+","+idd+"? "+ANSI_RESET);
        System.out.print("\t"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Tapez '0' pour oui et '1' pour non."+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Etes-vous sûr de vouloir créer la playlist : "+name+","+idd+"? "+ANSI_RESET);
        writeTo(output,"\t"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Tapez '0' pour oui et '1' pour non."+ANSI_RESET+" ");
        writeTo(output,"OK");
        ans = Integer.parseInt(readFrom(input));
        switch(ans)
        {
        	case 0 :
        	Playlist crea_play = new Playlist(name,idd);
        	playlists.add(crea_play);
        	String[] playid = idd.split("-");
        	int boucle = 0;
        	/* Affichage des chansons et livres audios existants*/
        	System.out.println(ANSI_CYAN+"Les Chansons existantes :\n"+chansons.toString());
        	System.out.println("Les Livres-Audios existants :\n"+livres.toString()+ANSI_RESET);
        	System.out.println(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Veuillez choisir une chanson ou un livre à ajouter à votre playlist."+ANSI_RESET);
        	System.out.println("\t"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+" Tapez 'C' pour ajouter une chanson et 'L' pour un livre"+ANSI_RESET);
            System.out.println("\t"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+" Pour arreter les ajouts, tapez 'S'"+ANSI_RESET+" ");
        	do{
                writeTo(output,ANSI_CYAN+"Les Chansons existantes :\n"+chansons.toString());
                writeTo(output,"Les Livres-Audios existants :\n"+livres.toString()+ANSI_RESET);
                writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Veuillez choisir une chanson ou un livre à ajouter à votre playlist."+ANSI_RESET);
                writeTo(output,"\t"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+" Tapez 'C' pour ajouter une chanson et 'L' pour un livre"+ANSI_RESET);
                writeTo(output,"\t"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+" Pour arreter les ajouts, tapez 'S'"+ANSI_RESET+" ");
                writeTo(output,"OK");
                String choix;
                choix = readFrom(input);
                System.out.println("après read");
                switch(choix)
                {
                    case "C":
                        String nomchanson;
                        int index = -1,cmpt = 0;
                        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre de la chanson à déplacer: "+ANSI_RESET+" ");
                        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre de la chanson à déplacer: "+ANSI_RESET+" ");
                        writeTo(output,"OK");
                        nomchanson = readFrom(input);
                        //nomchanson = "Hey Jude";
                        /* recherche de la chanson dans la liste chansons : /!\ si elle n'est pas trouvé demander de vérifier l'ortographe et de réécrire */
                        for(Chanson courant : chansons)
                        {
                            if(nomchanson.equals(courant.getTitre())==true) 
                            {
                                index = cmpt;
                            }
                            cmpt++;
                        }
                        if( index == -1 ) 
                        {
                            System.out.println(ANSI_RED+"Cette chanson n'existe pas."+ANSI_RESET);
                            writeTo(output,ANSI_RED+"Cette chanson n'existe pas."+ANSI_RESET);
                            break;
                        }
                        Chanson chanson = chansons.get(index);
                        //changement de l'ID de playlist
                        String num = chanson.getID();
                        String[] buffer1 = num.split("-");
                        buffer1[0] = playid[0];
                        StringBuffer buffer = new StringBuffer();
                        int compt=0;
                        for(String a : buffer1){
                            buffer = buffer.append(a);
                            if(compt!=2) buffer = buffer.append("-");
                                compt++;	
                        }
                        num = buffer.toString();
                        chanson.setID(num);
                        break;
                    
                    case "L" :
                        String nomlivre;
                        int i = -1,ite = 0;
                        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre du livre à déplacer: "+ANSI_RESET+" ");
                        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre du livre à déplacer: "+ANSI_RESET+" ");
                        writeTo(output,"OK");
                        nomlivre = readFrom(input);
                        System.out.println("nomlivre = " + nomlivre);
                        /*recherche de la chanson dans la liste chansons : /!\ si elle n'est pas trouvé demander de vérifier l'ortographe et de réécrire */
                        for(LivreAudio courant : livres)
                        {
                            if(nomlivre.equals(courant.getTitre())==true) 
                            {
                                i = ite;
                            }
                            ite++;
                        }
                        System.out.println("nomlivre = " + nomlivre);
                        if( i == -1 )
                        {
                            System.out.println(ANSI_RED+"Ce livre n'existe pas."+ANSI_RESET);
                            writeTo(output,ANSI_RED+"Ce livre n'existe pas."+ANSI_RESET);
                            break;
                        }
                        System.out.println("nomlivre = " + nomlivre);
                        LivreAudio livre = livres.get(i);
                        
                        /* changement de l'ID de playlist*/
                        String nume = livre.getID();
                        String[] buffer2 = nume.split("-");
                        buffer2[0] = playid[0];
                        StringBuffer buffer3 = new StringBuffer();
                        int compteur=0;
                        for(String a : buffer2){
                            buffer3 = buffer3.append(a);
                            if(compteur!=2) buffer3 = buffer3.append("-");
                                compteur++;	
                        }
                        nume = buffer3.toString();
                        livre.setID(nume);
                        System.out.println("nomlivre = " + nomlivre);
                        break;
                    
                    case "S":
                        boucle = 1;
                        break;
                }
                
            }while(boucle == 0);
        	System.out.println("nomlivre = ");
            System.out.println(ANSI_CYAN+"Voici vos nouvelles playlists disponibles: "+playlists+ANSI_RESET);
            writeTo(output,ANSI_CYAN+"Voici vos nouvelles playlists disponibles: "+playlists+ANSI_RESET);
            break;
            
            case 1:
            System.out.println(ANSI_CYAN+"Vous n'avez pas ajouté de playlist ! "+ANSI_RESET);
            writeTo(output,ANSI_CYAN+"Vous n'avez pas ajouté de playlist ! "+ANSI_RESET);
            break;
        }
        writeTo(output,ANSI_BLUE+"\t Appuyez sur Entrée"+ANSI_RESET+" ");
        writeTo(output,"OK");
        name = readFrom(input);
    }
    
/**
* Méthode qui supprime une playlist choisie
 * @param output
 * @param input
 * @return void
*/
    public void suppressPlaylist(OutputStream output,InputStream input){
        String nom,id;
        int answer,index=-1,compt=0;
        affplaylist(output, input);
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la playlist à supprimer : "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la playlist à supprimer : "+ANSI_RESET+" ");
        writeTo(output,"OK");
        nom = readFrom(input);
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez l'ID de la playlist à supprimer : "+ANSI_RESET+" ");
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez l'ID de la playlist à supprimer (de la forme XXX-XXX-XXX): "+ANSI_RESET+" ");
        writeTo(output,"OK");
        id = readFrom(input);
        for(Playlist play : playlists){
        	if(nom.equals(play.getNom())==true){
        		index=compt;
        	}
        	compt++;
        }
        if(index == -1){ 
        	System.out.print(ANSI_RED+"La playlist :"+nom+" n'existe pas !"+ANSI_RESET);
            writeTo(output,ANSI_RED+"La playlist :"+nom+" n'existe pas !"+ANSI_RESET);
            writeTo(output,"OK");
        	return;
        }
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Etes-vous sûr de vouloir suprimer la playlist : "+nom+","+id+"? "+ANSI_RESET);
        writeTo(output,ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Etes-vous sûr de vouloir suprimer la playlist : "+nom+","+id+"? "+ANSI_RESET);
        System.out.print("\t"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Tapez '0' pour oui et '1' pour non."+ANSI_RESET+" ");
        writeTo(output,"\t"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Tapez '0' pour oui et '1' pour non."+ANSI_RESET+" ");
        writeTo(output,"OK");
        answer = Integer.parseInt(readFrom(input));
        switch(answer)
        {
        	case 0 :
        	    String[] playid = id.split("-");
                for(Chanson supp : chansons ){
                    String num = supp.getID();
                    String[] buffer1 = num.split("-");
                    if(buffer1[0].equals(playid[0])  == true){
                        buffer1[0] = "000";
                        StringBuffer buffer = new StringBuffer();
                        int cpt=0;
                        for(String a : buffer1){
                            buffer = buffer.append(a);
                            if(cpt!=2) buffer = buffer.append("-");
                            cpt++;
                        }
                        num = buffer.toString();
                    }

                }
                for(LivreAudio non : livres ){
                    String num = non.getID();
                    String[] buffer1 = num.split("-");
                    if(buffer1[0].equals(playid[0])  == true){
                        buffer1[0] = "000";
                        StringBuffer buffer = new StringBuffer();
                        int cpt=0;
                        for(String a : buffer1){
                            buffer = buffer.append(a);
                            if(cpt!=2) buffer = buffer.append("-");
                            cpt++;
                        }
                        num = buffer.toString();
                    }
                }
                playlists.remove(index);
                System.out.println(ANSI_CYAN+"Voici vos nouvelles playlists disponibles: "+playlists+ANSI_RESET);
                writeTo(output,ANSI_CYAN+"Voici vos nouvelles playlists disponibles: "+playlists+ANSI_RESET);
                break;
            
            	case 1:
                System.out.println(ANSI_CYAN+"Vous n'avez rien supprimé ! "+ANSI_RESET);
                writeTo(output,ANSI_CYAN+"Vous n'avez rien supprimé ! "+ANSI_RESET);
                break;
        }
        writeTo(output,"\t"+ANSI_BLUE+"Appuyez sur Entrée"+ANSI_RESET+" ");
        writeTo(output,"OK");
        id = readFrom(input);
        
    }
    
/**
* Méthode pour sauvegarder les playlists, les albums, les chansons, les livres audios actuellement dans le MusicHubs
 * @param output
 * @param input
 * @return void
*/
    public void sauvegarde(OutputStream output, InputStream input){
        FileXML ecriture = new FileXML();
        System.out.println("\t\t"+ANSI_CYAN+"Sauvegarde en cours ..."+ANSI_RESET);
        writeTo(output,"\t\t"+ANSI_CYAN+"Sauvegarde en cours ..."+ANSI_RESET);
        ecriture.writePlaylists(this.playlists);
        ecriture.writeAlbums(this.albums);
        ecriture.writeElements(this.chansons,this.livres);
        writeTo(output,ANSI_BLUE+"\t Appuyez sur Entrée "+ANSI_RESET+" ");
        writeTo(output,"OK");
        readFrom(input);
    }
    
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////AFFICHAGE///////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////

/**
* Méthode qui affiche les chanson des albums
 * @param output
 * @param input
 * @return void
*/
    public void affchanson_album(OutputStream output, InputStream input){
        System.out.println("\t\t"+ANSI_CYAN +"Affichage en cours ..."+ANSI_RESET);
        writeTo(output,"\t\t"+ANSI_CYAN +"Affichage en cours ..."+ANSI_RESET);
        int i=1;
        for( Album ici : albums )
        {
            String num = ici.getAlbum();        //IDalbum de l'album
            System.out.println(ANSI_CYAN+"ALBUM "+i+": "+ici.toString()+ANSI_RESET);
            writeTo(output,ANSI_CYAN+"ALBUM "+i+": "+ici.toString()+ANSI_RESET);
        //on tri les chansons en fonction de leur album
            for(Chanson actuel : chansons)
            {
        //on récupère l'id => on le casse en trois (le séparateur c'est un "-") => le deuxième nombre indique dans quel album elle est
                String numalbum = actuel.getAlbum();//IDalbum de la chanson
                if( num.equals(numalbum)  == true){
                    System.out.println("\t"+ANSI_CYAN+"=> "+actuel.getArtiste()+" - "+actuel.getTitre()+ANSI_RESET);
                    writeTo(output,"\t"+ANSI_CYAN+"=> "+actuel.getArtiste()+" - "+actuel.getTitre()+ANSI_RESET);
                }
            }
            i++;
        }
        writeTo(output,ANSI_BLUE+"\t Appuyez sur Entrée "+ANSI_RESET+" ");
        writeTo(output,"OK");
        readFrom(input);
    }

/**
* Méthode qui affiche sur la console les titres des albums triés par date de sortie
 * @param output
 * @param input
 * @return void
*/
    public void afftitre_date(OutputStream output, InputStream input){
        Collections.sort(albums, new SortByDate());
        System.out.println(albums);
        writeTo(output,albums.toString());
        writeTo(output,ANSI_BLUE+"\t Appuyez sur Entrée "+ANSI_RESET+" ");
        writeTo(output,"OK");
        readFrom(input);
    }

/**
* Méthode qui affiche sur la console les titres des chansons triés par genre
 * @param output
 * @param input
 * @return void
*/
    public void afftitre_genre(OutputStream output, InputStream input){
        Collections.sort(chansons, new SortByGenre());
        System.out.println(chansons);
        writeTo(output,chansons.toString());
        writeTo(output,ANSI_BLUE+"\t Appuyez sur Entrée "+ANSI_RESET+" ");
        writeTo(output,"OK");
        readFrom(input);
    }

/**
* Méthode qui affiche sur la console les playlists
 * @param output
 * @param input
 * @return void
*/
    public void affplaylist(OutputStream output, InputStream input){
        System.out.println("\t\tAffichage en cours ...");
        writeTo(output,"\t\tAffichage en cours ...");
        for( Playlist ici : playlists )
        {
            String num = ici.getPlaylist();        //IDalbum de l'album
            System.out.println("PLAYLIST "+num+": "+ici.toString());
            writeTo(output,"PLAYLIST "+num+": "+ici.toString());
    //on tri les chansons en fonction de leur playlist
            for(Chanson actuel : chansons)
            {
        //on récupère l'id => on le casse en trois (le séparateur c'est un "-") => le premier nombre indique dans quelle playlist elle est
                String numalbum = actuel.getPlaylist();//IDPlaylist de la chanson
                if( num.equals(numalbum)  == true){
                    System.out.println("\t=> "+actuel.getArtiste()+" - "+actuel.getTitre());
                    writeTo(output,"\t=> "+actuel.getArtiste()+" - "+actuel.getTitre());
                }
            }
    //on tri les livreaudios en fonction de leur playlist
            for(LivreAudio actuel : livres)
            {
        //on récupère l'id => on le casse en trois (le séparateur c'est un "-") => le premier nombre indique dans quelle playlist il est
                String numalbum = actuel.getPlaylist();//IDPlaylist du livre audio
                if( num.equals(numalbum)  == true){
                    System.out.println("\t=> "+actuel.getAuteur()+" - "+actuel.getTitre());
                    writeTo(output,"\t=> "+actuel.getAuteur()+" - "+actuel.getTitre());
                }
            }
        }
        writeTo(output,ANSI_BLUE+"\t Appuyez sur Entrée "+ANSI_RESET+" ");
        writeTo(output,"OK");
        readFrom(input);
    }

/**
* Méthode qui affiche sur la console les livres audios triés par auteur
 * @param output
 * @param input
 * @return void
*/
    public void afflivre_auteur(OutputStream output, InputStream input){
        Collections.sort(livres, new SortByAuthor());
        System.out.println(livres);
        writeTo(output,livres.toString());
        writeTo(output,ANSI_BLUE+"\t Appuyez sur Entrée "+ANSI_RESET+" ");
        writeTo(output,"OK");
        readFrom(input);
    }
    
/**
* Méthode qui affiche sur la console les détails des commandes
 * @param output
 * @param input
 * @return void
*/
    public void aideCommande(OutputStream output, InputStream input){
        
        System.out.println("\n");
        System.out.println(ANSI_BLUE+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++    Menu d'aide aux commandes du MusicHub      +++++++++++++++++++++++"+ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND+ANSI_BLACK+"Veuillez entrer le caractère dont vous voulez connaitre l'aide: "+ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tRajout : Chanson (c) ; Album (a) ; Chanson à un Album (+) Livre-Audio (l). "+ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tCréation : Playlist à partir element existant(p).                          "+ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tSuppression : Playlist (-).                                                "+ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tSauvegarde : Nouveaux elements pour utilisation ultérieure (s).            "+ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tAide : Commandes (h) ; Options (o) ; Quitter (q).                          "+ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tVérification (v) : Affiche les vérifications de récupération des fichiers. "+ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND+ANSI_BLACK+"Pour sortir de l'aide tappez 'z'. "+ANSI_RESET);
        System.out.println(ANSI_BLUE+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+ANSI_RESET);
        String menu;
        int fin = 1;
        while( fin == 1)
        {
            writeTo(output,"\n");
            writeTo(output,ANSI_BLUE+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            writeTo(output,"++++++++++++++++++++++++    Menu d'aide aux commandes du MusicHub      +++++++++++++++++++++++"+ANSI_RESET);
            writeTo(output,ANSI_GREEN_BACKGROUND+ANSI_BLACK+"Veuillez entrer le caractère dont vous voulez connaitre l'aide: "+ANSI_RESET);
            writeTo(output,ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tRajout : Chanson (c) ; Album (a) ; Chanson à un Album (+) Livre-Audio (l). "+ANSI_RESET);
            writeTo(output,ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tCréation : Playlist à partir element existant(p).                          "+ANSI_RESET);
            writeTo(output,ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tSuppression : Playlist (-).                                                "+ANSI_RESET);
            writeTo(output,ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tSauvegarde : Nouveaux elements pour utilisation ultérieure (s).            "+ANSI_RESET);
            writeTo(output,ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tAide : Commandes (h) ; Options (o) ; Quitter (q).                          "+ANSI_RESET);
            writeTo(output,ANSI_GREEN_BACKGROUND+ANSI_BLACK+"\tVérification (v) : Affiche les vérifications de récupération des fichiers. "+ANSI_RESET);
            writeTo(output,ANSI_GREEN_BACKGROUND+ANSI_BLACK+"Pour sortir de l'aide tappez 'z'. "+ANSI_RESET);
            writeTo(output,ANSI_BLUE+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+ANSI_RESET);
            writeTo(output,"OK");
            menu = readFrom(input);
            
            switch(menu)
            {
                case "c":
                    System.out.println("+ Pour ajouter une nouvelle chanson au catalogue du Hub, il vous suffit de tapez c sur votre clavier.\nIl vous sera ensuite demandé de rentrer à l'aide de votre clavier les différents paramètres de la nouvelle chanson.\nCette chanson sera automatiquement ajoutée à votre liste de chansons par défaut.\t+");
                    writeTo(output,"+ Pour ajouter une nouvelle chanson au catalogue du Hub, il vous suffit de tapez c sur votre clavier.\nIl vous sera ensuite demandé de rentrer à l'aide de votre clavier les différents paramètres de la nouvelle chanson.\nCette chanson sera automatiquement ajoutée à votre liste de chansons par défaut.\t+");
                    break;
                    
                case "a":
                    System.out.println("+ Pour ajouter un nouvel album au catalogue des albums de votre Hub, il vous suffit de tapez a sur votre clavier.\nIl vous sera demandé de remplir les paramètres affichés à l'écran à l'aide de votre clavier.\nUne vérification sera réalisée en fin d'action afin d'être sur de votre choix.\nCe choix sera enregistré dans votre liste d'albums.\t+");
                    writeTo(output,"+ Pour ajouter un nouvel album au catalogue des albums de votre Hub, il vous suffit de tapez a sur votre clavier.\nIl vous sera demandé de remplir les paramètres affichés à l'écran à l'aide de votre clavier.\nUne vérification sera réalisée en fin d'action afin d'être sur de votre choix.\nCe choix sera enregistré dans votre liste d'albums.\t+");
                    break;
                    
                case "+":
                    System.out.println("+ Pour rajouter une chanson déjà présente dans votre catalogue à un album créé, il vous sera demandé d'entrer + sur votre clavier.\nRemplissez ensuite les champs demandé par votre MusicHub à l'aide de votre clavier afin d'ajouter votre chanson à l'album désiré.\nEn cas d'oubli, une liste des chansons et des albums disponibles vous sera rappelée.\nUne vérification sera faite en fin d'ajout pour vous montrer l'action que vous venez de réalisé.\nLa chanson sera automatiquement transféré dans cet album grâce au changement de son ID.\t+");
                    writeTo(output,"+ Pour rajouter une chanson déjà présente dans votre catalogue à un album créé, il vous sera demandé d'entrer + sur votre clavier.\\nRemplissez ensuite les champs demandé par votre MusicHub à l'aide de votre clavier afin d'ajouter votre chanson à l'album désiré.\\nEn cas d'oubli, une liste des chansons et des albums disponibles vous sera rappelée.\\nUne vérification sera faite en fin d'ajout pour vous montrer l'action que vous venez de réalisé.\\nLa chanson sera automatiquement transféré dans cet album grâce au changement de son ID.\\t+");
                    break;
                    
                case "l":
                    System.out.println("+ Pour ajouter une nouveau livre-audio au catalogue du Hub, il vous suffit de tapez l sur votre clavier.\nIl vous sera ensuite demandé de rentrer à l'aide de votre clavier les différents paramètres du nouveau livre-audio à ajouter.\nCe livre audio sera automatiquement ajouté à votre liste de chansons par défaut mais sera bien de type 'livre audio.\t+");
                    writeTo(output,"+ Pour ajouter une nouveau livre-audio au catalogue du Hub, il vous suffit de tapez l sur votre clavier.\nIl vous sera ensuite demandé de rentrer à l'aide de votre clavier les différents paramètres du nouveau livre-audio à ajouter.\nCe livre audio sera automatiquement ajouté à votre liste de chansons par défaut mais sera bien de type 'livre audio.\t+");
                    break;
                    
                case "p":
                    System.out.println("+ Afin de créer une nouvelle playlist à votre catalogue de MusicHub, il vous suffit d'entrer au clavier la lettre p.\nAttention, cette fonctionnalité ne s'applique qu'à des chansons ou livres-audios déjà présent dans votre Hub.\nIl vous sera demandé de donner le nom de votre playlist puis de rajouter les éléments voulu au sein de celle-ci.\nVous devrez bien choisir si vous voulez ajouter des chansons ou des livres-audios car il vous sera impossible d'ajouter des chansons si vous avez précedemment ajouté un livre.\nUn système de vérification vous laissera le choix de ne pas créer la playlist en cas d'erreur.\t+");
                    writeTo(output,"+ Afin de créer une nouvelle playlist à votre catalogue de MusicHub, il vous suffit d'entrer au clavier la lettre p.\nAttention, cette fonctionnalité ne s'applique qu'à des chansons ou livres-audios déjà présent dans votre Hub.\nIl vous sera demandé de donner le nom de votre playlist puis de rajouter les éléments voulu au sein de celle-ci.\nVous devrez bien choisir si vous voulez ajouter des chansons ou des livres-audios car il vous sera impossible d'ajouter des chansons si vous avez précedemment ajouté un livre.\nUn système de vérification vous laissera le choix de ne pas créer la playlist en cas d'erreur.\t+");
                    break;
                    
                case "-":
                    System.out.println("+ Pour effacer une playlist, il vous sera demandé de taper sur votre clavier le caractère -.\nCeci réalisé, il vous sera demandé de rentrer manuellement le nom de la playlist à supprimer.\nEn aucune façon il vous sera possible de retirer une chanson de la playlist ou d'entrer un element d'une playlist pour l'effacer complètement.\nSeul le nom de celle-ci compte.\nUn système de vérification vous permettra de valider vos choix.\t+");
                    writeTo(output,"+ Pour effacer une playlist, il vous sera demandé de taper sur votre clavier le caractère -.\nCeci réalisé, il vous sera demandé de rentrer manuellement le nom de la playlist à supprimer.\nEn aucune façon il vous sera possible de retirer une chanson de la playlist ou d'entrer un element d'une playlist pour l'effacer complètement.\nSeul le nom de celle-ci compte.\nUn système de vérification vous permettra de valider vos choix.\t+");
                    break;
                    
                case "s":
                    System.out.println("+ Afin de sauvegarder vos choix réalisé précédemment lors d'une prochaine utilisation de votre MusicHub,\nil vous sera demandé de sauvegarder vos choix actuel via l'entrée du caractère s sur votre menu d'accueil.\nLa sauvegarde de l'ensemble des éléments est automatique, il ne vous sera nullement demandé d'ajouter au clavier une information.\t+");
                    writeTo(output,"+ Afin de sauvegarder vos choix réalisé précédemment lors d'une prochaine utilisation de votre MusicHub,\nil vous sera demandé de sauvegarder vos choix actuel via l'entrée du caractère s sur votre menu d'accueil.\nLa sauvegarde de l'ensemble des éléments est automatique, il ne vous sera nullement demandé d'ajouter au clavier une information.\t+");
                    break;
                    
                case "h":
                    System.out.println("+\tLa saisie de 'h' vous permet d'acceder à ce menu d'aide.\nIl est seulement disponible via le menu d'accueil.\t+");
                    writeTo(output,"+\tLa saisie de 'h' vous permet d'acceder à ce menu d'aide.\nIl est seulement disponible via le menu d'accueil.\t+");
                    break;
                    
                case "q":
                    System.out.println("+\tLa saisie de 'q' vous permet de quitter le menu d'accueil.\nIl est seulement disponible via le menu d'accueil.\t+");
                    writeTo(output,"+\tLa saisie de 'q' vous permet de quitter le menu d'accueil.\nIl est seulement disponible via le menu d'accueil.\t+");
                    break;
                    
                case "o":
                    System.out.println("+\tCette option est seulement disponible dans ce menu d'aide. Elle vous permettra de changer les réglages de lecture des éléments de votre MusicHub.\t+");
                    writeTo(output,"+\tCette option est seulement disponible dans ce menu d'aide. Elle vous permettra de changer les réglages de lecture des éléments de votre MusicHub.\t+");
                    Options op = new Options();
                    op.menu();
                    break;
                    
                case "v" :
                    System.out.println("\t\tVérification de la liste :");
                    System.out.println(playlists.toString());
                    System.out.println("\t\tVérification de la liste CHANSONS:");
                    System.out.println(chansons.toString());
                    System.out.println("\t\tVérification de la liste LIVRESAUDIOS:");
                    System.out.println(livres.toString());
                    System.out.println("\t\tVérification de la liste :");
                    System.out.println(albums.toString());
                    writeTo(output,"\t\tVérification de la liste :");
                    writeTo(output,playlists.toString());
                    writeTo(output,"\t\tVérification de la liste CHANSONS:");
                    writeTo(output,chansons.toString());
                    writeTo(output,"\t\tVérification de la liste LIVRESAUDIOS:");
                    writeTo(output,livres.toString());
                    writeTo(output,"\t\tVérification de la liste :");
                    writeTo(output,albums.toString());
                    break;
                    
                case "z" :
                    System.out.println("Vous sortez de l'aide !");
                    writeTo(output,"Vous sortez de l'aide !");
                    fin = 0;
                    break;
                    
                default :
                    System.out.println(ANSI_RED+"Saisie incorrecte. Veuillez choisir une option proposée"+ANSI_RESET);
                    writeTo(output,ANSI_RED+"Saisie incorrecte. Veuillez choisir une option proposée"+ANSI_RESET);
                    break;
            }
            writeTo(output,ANSI_BLUE+"\t(Appuyez sur Entrée)"+ANSI_RESET);
            writeTo(output,"OK");
            menu = readFrom(input);
        }
        
    }

    /**
     * Méthode qui affiche sur la sortie output donnée en paramètre un string s
     * @see socketServer
     * @param output
     * @param s
     * @return void
     */
    @Override
    public void writeTo(OutputStream output, String s) {
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

}
