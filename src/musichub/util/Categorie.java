package musichub.util;

/**
*Une énumération est un type de données particulier, dans lequel une variable ne peut prendre qu'un 
*nombre restreint de valeurs. Ces valeurs sont des constantes qui sont ici des noms communs
*désignant une catégorie de livre-audio.
*/

public enum Categorie{
    JEUNESSE("Jeunesse"), ROMAN("Roman"), THEATRE("Théatre"), DISCOURS("Discours"), DOCUMENTAIRE("Documentaire");
    
    private String categorie;
    
    /**
    *On a voulu associer à chacune de nos valeurs énumérée une autre forme d'affichage. Ainsi, on a
    *créé un constructeur privé pour chaque instance où on l'initialise avec notre affichage 
    */
    private Categorie(String categorie){
    	this.categorie = categorie;
    }
    	
    public String toString(){
        return this.categorie; //on a créé une méthode publique pour récupérer la valeur instanciée
    }

}
