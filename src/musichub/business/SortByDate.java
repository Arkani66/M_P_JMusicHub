package musichub.business;

import java.util.*;
import musichub.util.*;
/**
* Cette classe permet de trier nos objets. Pour pouvoir les trier, on les compare grâce à 
* l'interface 'Comparator' qui est ici de type Album. 
* On la retrouve dans le paquet java.util.* et ressemble à ça: 
*
*	interface Comparator { 
*
*	public int   compare(Object o1, Object o2); 
*	public boolean equals(Object obj); 
*
*	} 
*/

public class SortByDate implements Comparator<Album> {
	/*compare les differents albums et tri la date de sortie selon l'ordre numérique*/
	
        public int compare(Album a, Album b) {
        /*nos objets sont les albums de notre liste chainée*/
            return a.getDate().compareTo(b.getDate());
            /**
	    *On retourne un entier qui indique si cet objet précède, suit ou apparait à la même
	    *position dans l'ordre de tri (numérique) du calendrier. On récupère cette date donnée 
	    * par 'getDate()'
	    */
        }
}
