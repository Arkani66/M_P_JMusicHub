package musichub.main;

import musichub.business.*;
import musichub.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import java.util.Scanner;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;  

/**
 * Cette classe effectue toutes les actions disponibles sur la console du MusicHub
 * 
 * @version 1.0
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

    //Constructeur
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
* @return 
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
* @return retour true si la date est valide, false si la date n'est pas valide
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
* Demande : titre, artiste, genre, duree
 * @param artiste
 * @param titre
 * @param genre1
 * @param duree
*/
    public void ajoutChansons(String artiste, String titre, Genre genre1, int duree){
        String id = null;
        id = creaID(2);
        Chanson crea_song = new Chanson(titre,artiste,duree,id,"MP3",genre1);
        System.out.println(ANSI_BLACK_BACKGROUND+ANSI_CYAN+" Vérification, vous avez créé = "+ANSI_RESET+ANSI_BLACK_BACKGROUND+ANSI_CYAN+crea_song.toString()+id+" !"+ANSI_RESET);
        chansons.add(crea_song);
    }

/**
* Méthode qui ajoute un album à la liste des albums
* Demande : titre, artiste, date, duree
 * @param
 * @return void
*/
    public void ajoutAlbum(String artiste, String titre, int duree, Date date1,PrintWriter writer){
        String id = null;
        id = creaID(1);
        Album crea_album = new Album(titre,artiste,duree,date1,id);
        System.out.println(ANSI_BLACK_BACKGROUND+ANSI_CYAN+" Vérification, vous avez créé = "+ANSI_RESET+ANSI_BLACK_BACKGROUND+ANSI_CYAN+crea_album.toString()+" !"+ANSI_RESET);
       // writer.println(ANSI_BLACK_BACKGROUND+ANSI_CYAN+" Vérification, vous avez créé = "+ANSI_RESET+ANSI_BLACK_BACKGROUND+ANSI_CYAN+crea_album.toString()+" !"+ANSI_RESET);
        albums.add(crea_album);
    }
    
/**
* Méthode qui ajoute une chanson à la liste des chansons et à un album
*/
    public void ajoutChansonAlbum(PrintWriter writer, BufferedReader reader){
    /*on affiche les chansons existantes et les albums existants*/
        System.out.println(ANSI_CYAN+"Les Chansons existantes :\n"+chansons.toString()+ANSI_RESET);
        writer.println(ANSI_CYAN+"Les Chansons existantes :\n"+chansons.toString()+ANSI_RESET);
        System.out.println(ANSI_CYAN+"Les Albums existants :\n"+albums.toString()+ANSI_RESET);
        writer.println(ANSI_CYAN+"Les Albums existants :\n"+albums.toString()+ANSI_RESET);
    /*choix de la chanson à ajouter à un album*/
        String nomchanson = null;
        String nomalbum = null;
        int index = -1,verif=-1,cmpt = 0;
        System.out.print("\n"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre de la chanson à déplacer: "+ANSI_RESET+" ");
        writer.println("\n"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre de la chanson à déplacer: "+ANSI_RESET+" ");
        nomchanson = readFrom(reader);
        if( nomchanson == null) readFrom(reader);
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
            continu = Integer.parseInt(readFrom(reader));
            while( continu == 0)
            {
                System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre de la chanson à déplacer: "+ANSI_RESET+" ");
                writer.println(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre de la chanson à déplacer: "+ANSI_RESET+" ");
                nomchanson = readFrom(reader);
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
            writer.println(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la playlist dans laquelle vous voulez mettre cette chanson: "+ANSI_RESET+" ");
            nomalbum = readFrom(reader);
            if( nomalbum == null) nomalbum = readFrom(reader);
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
                writer.println(ANSI_RED+"Cet album n'existe pas. Vérifiez l'ortographe et recommencez."+ANSI_RESET);
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
*/
    public void ajoutLivreAudio(){
        String auteur = null;
        String titre = null;
        String genre = null;
        String id = null;
        String langue = null;
        String categorie = null;
        Langue  langue1 = null;
        Categorie categorie1 = null;
        int duree;
        auteur = scanner.nextLine();
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de l'auteur : "+ANSI_RESET+" ");
        auteur = scanner.nextLine();
        if( auteur == null) auteur = scanner.nextLine();
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la chanson : "+ANSI_RESET+" ");
        titre = scanner.nextLine();
        if( titre == null) titre = scanner.nextLine();
        id = creaID(2);
        if( id == null) id = scanner.nextLine();
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez la durée de la chanson : "+ANSI_RESET+" ");
        duree = scanner.nextInt();
        while( langue1 == null)
        {
            System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Choisissez la langue de la chanson : FRANCAIS(F), ANGLAIS(A), ITALIEN(I), ESPAGNOL(E), ALLEMAND(D)"+ANSI_RESET+" ");
            langue = scanner.nextLine();
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
                    break;
            }
        }
        while( categorie1 == null)
        {
            System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Choisissez le genre de la chanson : JEUNESSE(J), ROMAN(R), THEATRE(T), DISCOURS(S), DOCUMENTAIRE(D);"+ANSI_RESET+" ");
            categorie = scanner.nextLine();
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
                    break;
            }
        }
        System.out.println("Création du livre audio : "+auteur+", "+titre+", "+id);
        LivreAudio crea_livre = new LivreAudio(titre,auteur,duree,id,"MP4",langue1,categorie1);
        System.out.println(ANSI_BLACK_BACKGROUND+ANSI_CYAN+" Vérification, vous avez créé = "+crea_livre.toString()+" !"+ANSI_RESET);
        livres.add(crea_livre);
        System.out.println(livres.toString());
    }
    
/**
* Méthode qui créer d'une playlist à partir de chansons existantes
*/
    public void ajoutPlaylist(){
        String name,idd;
        int ans,inde=-1,cptt=0;
        affplaylist();
        name = scanner.nextLine();
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la playlist à créer : "+ANSI_RESET+" ");
        name = scanner.nextLine();
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
        	return;
        }
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Etes-vous sûr de vouloir créer la playlist : "+name+","+idd+"? "+ANSI_RESET);
        System.out.print("\t"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Tapez '0' pour oui et '1' pour non."+ANSI_RESET+" ");
        ans = scanner.nextInt();
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
                String choix;
                choix = scanner.nextLine();
                switch(choix)
                {
                    case "C":
                        String nomchanson;
                        int index = -1,cmpt = 0;
                        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le titre de la chanson à déplacer: "+ANSI_RESET+" ");
                        nomchanson = scanner.nextLine();
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
                        nomlivre = scanner.nextLine();
                        /*recherche de la chanson dans la liste chansons : /!\ si elle n'est pas trouvé demander de vérifier l'ortographe et de réécrire */
                        for(LivreAudio courant : livres)
                        {
                            if(nomlivre.equals(courant.getTitre())==true) 
                            {
                                i = ite;
                            }
                            ite++;
                        }
                        if( i == -1 )
                        {
                            System.out.println(ANSI_RED+"Ce livre n'existe pas."+ANSI_RESET);
                            break;
                        }
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
                        break;
                    
                    case "S":
                        boucle = 1;
                        break;
                }
                
            }while(boucle == 0);
            System.out.println(ANSI_CYAN+"Voici vos nouvelles playlists disponibles: "+playlists+ANSI_RESET);
            break;
            
            case 1:
            System.out.println(ANSI_CYAN+"Vous n'avez pas ajouté de playlist ! "+ANSI_RESET);
            break;
        }
    }
    
/**
* Méthode qui supprime une playlist choisie
*/
    public void suppressPlaylist(){
        String nom,id;
        int answer,index=-1,compt=0;
        affplaylist();
        nom = scanner.nextLine();
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez le nom de la playlist à supprimer : "+ANSI_RESET+" ");
        nom = scanner.nextLine();
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Donnez l'ID de la playlist à supprimer : "+ANSI_RESET+" ");
        id = scanner.nextLine();
        for(Playlist play : playlists){
        	if(nom.equals(play.getNom())==true){
        		index=compt;
        	}
        	compt++;
        }
        if(index == -1){ 
        	System.out.print(ANSI_RED+"La playlist :"+nom+" n'existe pas !"+ANSI_RESET);
        	return;
        }
        System.out.print(ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Etes-vous sûr de vouloir suprimer la playlist : "+nom+","+id+"? "+ANSI_RESET);
        System.out.print("\t"+ANSI_PURPLE_BACKGROUND+ANSI_BLACK+"Tapez '0' pour oui et '1' pour non."+ANSI_RESET+" ");
        answer = scanner.nextInt();
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
                break;
            
            	case 1:
                System.out.println(ANSI_CYAN+"Vous n'avez rien supprimé ! "+ANSI_RESET);
                break;
        }
        
    }
    
/**
* Méthode pour sauvegarder les playlists, les albums, les chansons, les livres audios actuellement dans le MusicHubs
*/
    public void sauvegarde(){
        FileXML ecriture = new FileXML();
        System.out.println("\t\t"+ANSI_CYAN+"Sauvegarde en cours ..."+ANSI_RESET);
        ecriture.writePlaylists(this.playlists);
        ecriture.writeAlbums(this.albums);
        ecriture.writeElements(this.chansons,this.livres);
    }
    
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////AFFICHAGE///////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////

/**
* Méthode qui affiche les chanson des albums
*/
    public void affchanson_album(){
        System.out.println("\t\t"+ANSI_CYAN +"Affichage en cours ..."+ANSI_RESET);
        int i=1;
        for( Album ici : albums )
        {
            String num = ici.getAlbum();        //IDalbum de l'album
            System.out.println(ANSI_CYAN+"ALBUM "+i+": "+ici.toString()+ANSI_RESET);
        //on tri les chansons en fonction de leur album
            for(Chanson actuel : chansons)
            {
        //on récupère l'id => on le casse en trois (le séparateur c'est un "-") => le deuxième nombre indique dans quel album elle est
                String numalbum = actuel.getAlbum();//IDalbum de la chanson
                if( num.equals(numalbum)  == true){
                    System.out.println("\t"+ANSI_CYAN+"=> "+actuel.getArtiste()+" - "+actuel.getTitre()+ANSI_RESET);
                }
            }
            i++;
        }
    }

/**
* Méthode qui affiche sur la console les titres des albums triés par date de sortie
*/
    public void afftitre_date(){
        Collections.sort(albums, new SortByDate());
        System.out.println(albums);
    }

/**
* Méthode qui affiche sur la console les titres des chansons triés par genre
*/
    public void afftitre_genre(){
        Collections.sort(chansons, new SortByGenre());
        System.out.println(chansons);
    }

/**
* Méthode qui affiche sur la console les playlists
*/
    public void affplaylist(){
        System.out.println("\t\tAffichage en cours ...");
        for( Playlist ici : playlists )
        {
            String num = ici.getPlaylist();        //IDalbum de l'album
            System.out.println("PLAYLIST "+num+": "+ici.toString());
    //on tri les chansons en fonction de leur playlist
            for(Chanson actuel : chansons)
            {
        //on récupère l'id => on le casse en trois (le séparateur c'est un "-") => le premier nombre indique dans quelle playlist elle est
                String numalbum = actuel.getPlaylist();//IDPlaylist de la chanson
                if( num.equals(numalbum)  == true){
                    System.out.println("\t=> "+actuel.getArtiste()+" - "+actuel.getTitre());
                }
            }
    //on tri les livreaudios en fonction de leur playlist
            for(LivreAudio actuel : livres)
            {
        //on récupère l'id => on le casse en trois (le séparateur c'est un "-") => le premier nombre indique dans quelle playlist il est
                String numalbum = actuel.getPlaylist();//IDPlaylist du livre audio
                if( num.equals(numalbum)  == true){
                    System.out.println("\t=> "+actuel.getAuteur()+" - "+actuel.getTitre());
                }
            }
        }
    }

/**
* Méthode qui affiche sur la console les livres audios triés par auteur
*/
    public void afflivre_auteur(){
        Collections.sort(livres, new SortByAuthor());
        System.out.println(livres);
    }
    
/**
* Méthode qui affiche sur la console les détails des commandes
*/
    public void aideCommande(){
        
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
            menu = scanner.nextLine();
            
            switch(menu)
            {
                case "c":
                    System.out.println("+ Pour ajouter une nouvelle chanson au catalogue du Hub, il vous suffit de tapez c sur votre clavier.\nIl vous sera ensuite demandé de rentrer à l'aide de votre clavier les différents paramètres de la nouvelle chanson.\nCette chanson sera automatiquement ajoutée à votre liste de chansons par défaut.\t+");
                    break;
                    
                case "a":
                    System.out.println("+ Pour ajouter un nouvel album au catalogue des albums de votre Hub, il vous suffit de tapez a sur votre clavier.\nIl vous sera demandé de remplir les paramètres affichés à l'écran à l'aide de votre clavier.\nUne vérification sera réalisée en fin d'action afin d'être sur de votre choix.\nCe choix sera enregistré dans votre liste d'albums.\t+");
                    break;
                    
                case "+":
                    System.out.println("+ Pour rajouter une chanson déjà présente dans votre catalogue à un album créé, il vous sera demandé d'entrer + sur votre clavier.\nRemplissez ensuite les champs demandé par votre MusicHub à l'aide de votre clavier afin d'ajouter votre chanson à l'album désiré.\nEn cas d'oubli, une liste des chansons et des albums disponibles vous sera rappelée.\nUne vérification sera faite en fin d'ajout pour vous montrer l'action que vous venez de réalisé.\nLa chanson sera automatiquement transféré dans cet album grâce au changement de son ID.\t+");
                    break;
                    
                case "l":
                    System.out.println("+ Pour ajouter une nouveau livre-audio au catalogue du Hub, il vous suffit de tapez l sur votre clavier.\nIl vous sera ensuite demandé de rentrer à l'aide de votre clavier les différents paramètres du nouveau livre-audio à ajouter.\nCe livre audio sera automatiquement ajouté à votre liste de chansons par défaut mais sera bien de type 'livre audio.\t+");
                    break;
                    
                case "p":
                    System.out.println("+ Afin de créer une nouvelle playlist à votre catalogue de MusicHub, il vous suffit d'entrer au clavier la lettre p.\nAttention, cette fonctionnalité ne s'applique qu'à des chansons ou livres-audios déjà présent dans votre Hub.\nIl vous sera demandé de donner le nom de votre playlist puis de rajouter les éléments voulu au sein de celle-ci.\nVous devrez bien choisir si vous voulez ajouter des chansons ou des livres-audios car il vous sera impossible d'ajouter des chansons si vous avez précedemment ajouté un livre.\nUn système de vérification vous laissera le choix de ne pas créer la playlist en cas d'erreur.\t+");
                    break;
                    
                case "-":
                    System.out.println("+ Pour effacer une playlist, il vous sera demandé de taper sur votre clavier le caractère -.\nCeci réalisé, il vous sera demandé de rentrer manuellement le nom de la playlist à supprimer.\nEn aucune façon il vous sera possible de retirer une chanson de la playlist ou d'entrer un element d'une playlist pour l'effacer complètement.\nSeul le nom de celle-ci compte.\nUn système de vérification vous permettra de valider vos choix.\t+");
                    break;
                    
                case "s":
                    System.out.println("+ Afin de sauvegarder vos choix réalisé précédemment lors d'une prochaine utilisation de votre MusicHub,\nil vous sera demandé de sauvegarder vos choix actuel via l'entrée du caractère s sur votre menu d'accueil.\nLa sauvegarde de l'ensemble des éléments est automatique, il ne vous sera nullement demandé d'ajouter au clavier une information.\t+");
                    break;
                    
                case "h":
                    System.out.println("+\tLa saisie de 'h' vous permet d'acceder à ce menu d'aide.\nIl est seulement disponible via le menu d'accueil.\t+");
                    break;
                    
                case "q":
                    System.out.println("+\tLa saisie de 'q' vous permet de quitter le menu d'accueil.\nIl est seulement disponible via le menu d'accueil.\t+");
                    break;
                    
                case "o":
                    System.out.println("+\tCette option est seulement disponible dans ce menu d'aide. Elle vous permettra de changer les réglages de lecture des éléments de votre MusicHub.\t+");
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
                    break;
                    
                case "z" :
                    System.out.println("Vous sortez de l'aide !");
                    fin = 0;
                    break;
                    
                default :
                    System.out.println(ANSI_RED+"Saisie incorrecte. Veuillez choisir une option proposée"+ANSI_RESET);
                    break;
            }
        
        }
        
    }

    @Override
    public void writeTo(PrintWriter writer, String s) {
        writer.println(s);
        writer.flush();
    }

    @Override
    public String readFrom(BufferedReader reader) {
        String textread = null;
        try {
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
