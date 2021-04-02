package musichub.business;

import java.util.*;
import musichub.util.*;
/**
* Cette classe permet de trier nos objets. Pour pouvoir les trier, on les compare grâce à 
* l'interface 'Comparator' qui est ici de type LivreAudio. 
* On la retrouve dans le paquet java.util.* et ressemble à ça: 
*
*	interface Comparator { 
*
*	public int   compare(Object o1, Object o2); 
*	public boolean equals(Object obj); 
*
*	} 
*/
public class SortByAuthor implements Comparator<LivreAudio> {
	/*compare les differents livres-audio et tri le nom de l'auteur selon l'ordre alphabétique*/
	
        public int compare(LivreAudio a, LivreAudio b) {	
        /*nos objets sont les livres de notre liste chainée*/
            return a.getAuteur().compareTo(b.getAuteur());
	    /**
	    *On retourne un entier qui indique si cet objet précède, suit ou apparait à la même
	    *position dans l'ordre de tri alphabétique du nom des auteurs donné par 'getAuteur()'
	    */
        }
}
