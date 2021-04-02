package musichub.main;

/**
*Il s'agit de la classe mère de l'ensemble des éléments audio de notre hub.
*/

public class Son {

    protected String titre;	//attribut commun à tout nos éléments audio de type String
    protected String ID;	//attribut commun à tout nos éléments audio de type String
    
    
    public String getID()	//permet de récuperer partout l'ID de l'élément traité
    {
        return this.ID;
    }
    
    public void setID(String id)	//permet de déclarer un possible nouvel ID pour l'élément traité
    {   
        this.ID = id;
    }

    public String getTitre()	//permet de récuperer partout le titre de l'élément traité
    {
        return this.titre;
    }
    
    /**
    *Permet de récupérer dans un tableau de caractère l'ID découpée en 3 parties. Il le découpe à
    *chaque fois qu'il rencontre le caractère '-' grâce à la méthode split() qui divise les types
    *String en plusieures sous-chaines de caractères.
    */
    public String getAlbum(){	

    	String[] parties = ID.split("-",3);
    	String numalbum = null;
    	int i=0;
        for (String a : parties){ 
            if(i==1) numalbum = a;
            /**
            *La partie correspondante à l'album est à l'indice 1, donc pour la récupérer on
            *cherche tous les Strings dont les indices sont à 1 pour les elements récupérés
            */
            i++; //sinon on ne fait rien
        }
        return numalbum;	//on retourne l'élément récupéré
    }
    
    public String getPlaylist(){
    	String[] parties = ID.split("-",3);
    	String numalbum = null;
    	int i=0;
        for (String a : parties){ 
            if(i==0) numalbum = a;
             /**
            *La partie correspondante à la playlist est à l'indice 0, donc pour la récupérer on
            *cherche tous les Strings dont les indices sont à 0 pour les elements récupérés
            */
            i++;	//sinon on ne fait rien
        }
        return numalbum; //on retourne l'élément récupéré
    }
    
    public String getPartID(){
    	String[] parties = ID.split("-",3);
    	String numalbum = null;
    	int i=0;
        for (String a : parties){ 
            if(i==2) numalbum = a;
             /**
            *La partie correspondante à l'élément en lui-même est à l'indice 2, donc pour la
            *récupérer on cherche tous les Strings dont les indices sont à 2 pour les elements
            *récupérés. Il s'agit de la derniere partie de notre ID.
            */
            i++;	//sinon on ne fait rien
        }
        return numalbum; //on retourne l'élément récupéré
    }
}
