package musichub.util;

import musichub.main.*;

/**
 * Cette classe effectue toutes les actions disponibles sur la console du MusicHub
 * 
 * @version 1.0
 *
 * @see MusicHubConsole
 * @author PROSPA Florence et MICONNET Sandrine
 */

public class Chanson extends Son implements Audio
{
    private String artiste;
    private String contenu; // (fichier audio) => trouver le type
    private Genre genre;
    private int duree;
    
    //Constructeur
    public Chanson(String titre,String artiste,int duree, String ID, String contenu, Genre genre)
    {
        this.titre = titre;
        this.artiste = artiste;
        this.duree = duree;
        this.ID = ID;
        this.contenu = contenu;
        this.genre = genre;
        
    }
    
/**
*   Renvoie en format String les attributs privés de la classe : artiste et titre
*/
    public String toString()
    {
        return "\n" + this.artiste + " - "+this.titre;
    }
    
/**
*   Renvoie l'attribut privé : artiste, dans son format d'origine (String)
* @return artiste 
*/
    public String getArtiste()
    {
        return this.artiste;
    }

/**
*   Renvoie l'attribut privé : duree, dans son format d'origine (int)
* @return duree
*/
    public int getDuree()
    {
        return this.duree;
    }
    
/**
*   Renvoie l'attribut privé : contenu, dans son format d'origine (String)
* @return contenu
*/
    public String getContenu()
    {
        return this.contenu;
    }
    
/**
*   Renvoie l'attribut privé : genre, dans son format d'origine (Genre)
* @return genre
*/
    public Genre getGenre()
    {
        return this.genre;
    }
    
/**
*   Renvoie l'attribut privé : genre, en format String
* @return genre.toString()
*/
    public String getChansonGenre(){
        return genre.toString();
    }

/**
*   Méthode de lecture d'un album associée à l'interface Audio
*/
    public void play(){
        System.out.println ("La chanson " +titre+ " est en écoute.");
    }
    
/**
*   Méthode pour stopper la lecture d'un album associée à l'interface Audio
*/
    public void stop(){
        System.out.println ("Vous venez de stopper l'écoute !");
    }
}

