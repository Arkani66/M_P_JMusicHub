package musichub.business;

import java.util.*;
import musichub.main.*;
import musichub.util.*;

/**
 *  Cette classe crée et gére un objet de type Album
 * 
 * @version 1.0
 *
 * @see MusicHubConsole
 * @author PROSPA Florence et MICONNET Sandrine
 */

public class Album extends Son
{
    private LinkedList<Chanson> chanson;
    private String artiste;
    private int duree;
    private Date date;
    
    //Constructeur
    public Album(String titre, String artiste, int duree, Date date, String ID)
    {
        this.titre = titre;
        this.artiste = artiste;
        this.duree = duree;
        this.date = date;
        this.ID = ID;
        this.chanson = new LinkedList<Chanson>();
    }

/**
*  Renvoie en format String les attributs privés de la classe : artiste et titre
*/
    public String toString()
    {
        return "\n"+this.artiste+" - "+this.titre;
    }

/**
*  Renvoie l'attribut privé : artiste, dans son format d'origine (String) 
*/
    public String getArtiste()
    {
        return this.artiste;
    }
    
/**
*   Renvoie l'attribut privé : artiste, dans son format d'origine (String)
*/
    public int getDuree()
    {
        return this.duree;
    }
    
/**
*   Renvoie l'attribut privé : date, dans son format d'origine (Date)
*/
    public Date getDate()
    {
        return this.date;
    }

 }
