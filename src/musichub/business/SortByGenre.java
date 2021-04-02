package musichub.business;

import java.util.*;
import musichub.util.*;
/**
* Cette classe permet de trier nos objets. Pour pouvoir les trier, on les compare grâce à 
* l'interface 'Comparator' qui est ici de type Chanson. 
* On la retrouve dans le paquet java.util.* et ressemble à ça: 
*
*	interface Comparator { 
*
*	public int   compare(Object o1, Object o2); 
*	public boolean equals(Object obj); 
*
*	} 
*/

public class SortByGenre implements Comparator<Chanson> {
	/*compare les differentes chansons et tri le genre selon l'ordre alphabétique*/
	
        public int compare(Chanson a, Chanson b) {
        /*nos objets sont les chansons de notre liste chainée*/
            return a.getChansonGenre().compareTo(b.getChansonGenre());
            /**
	    *On retourne un entier qui indique si cet objet précède, suit ou apparait à la même
	    *position dans l'ordre de tri alphabétique du nom de genre donné par 'getChansonGenre()'
	    */
        }
}
