
package musichub.business;

import musichub.util.*;
import musichub.main.*;
import java.util.*;

/**
 * Cette classe crée et gére un objet de type Playlist
 * 
 * @version 1.0
 *
 * @author PROSPA Florence et MICONNET Sandrine
 */

public class Playlist extends Son /*implements Audio*/
{
    public Playlist(String nom, String ID/*, LivreAudio livre*/ )
    {
        this.titre = nom;
        this.ID = ID;
    }
    
/**
*   Renvoie en format String les attributs privés de la classe : titre et ID
*/
    public String toString()
    {
        return "\n"+this.titre+" - "+this.ID;
    }
/**
*   Renvoie l'attribut privé : nom, dans son format d'origine (String)
*/
    public String getNom()
    {
    	return this.titre;
    }
}

