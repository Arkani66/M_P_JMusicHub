package musichub.util;

import musichub.main.*;

/**
 * Cette classe effectue toutes les actions disponibles sur la console du MusicHub
 * 
 * @version 1.0
 *
 * @author PROSPA Florence et MICONNET Sandrine
 */

public class LivreAudio extends Son implements Audio
{
    private String auteur;
    private String contenu; // (fichier audio)
    private Langue langue;
    private Categorie categorie;
    private int duree;

    //Constructeur
    public LivreAudio(String titre, String auteur, int duree, String ID, String contenu, Langue langue, Categorie categorie)
    {
        this.titre = titre;
        this.auteur = auteur;
        this.duree = duree;
        this.ID = ID;
        this.contenu = contenu;
        this.langue = langue;
        this.categorie = categorie;
    }

/**
*   Renvoie en format String les attributs privés de la classe : auteur et titre
*/
    public String toString()
    {
        return "\n" + this.auteur + " - "+this.titre;
    }
    
/**
*   Renvoie l'attribut privé : auteur, dans son format d'origine (String)
* @return auteur 
*/
    public String getAuteur()
    {
        return this.auteur;
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
*   Renvoie l'attribut privé : langue, en format String
* @return langue.toString()
*/
    public String getLivreLangue(){
        return langue.toString();
    }
    
/**
*   Renvoie l'attribut privé : langue, dans son format d'origine (Langue)
* @return langue
*/
    public Langue getLangue(){
        return this.langue;
    }
    
/**
*   Renvoie l'attribut privé : categorie, dans son format d'origine (Categorie)
* @return categorie
*/
    public Categorie getCategorie(){
    	return this.categorie;
    }

/**
*   Méthode de lecture d'un album associée à l'interface Audio
*/
    public void play(){
        System.out.println ("Vous écoutez en ce moment le livre: " +titre);
    }
    
/**
*   Méthode pour stopper la lecture d'un album associée à l'interface Audio
*/
    public void stop(){
        System.out.println ("Vous venez de stopper l'écoute !");
    }
}
