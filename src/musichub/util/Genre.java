package musichub.util;

/**
*Une énumération est un type de données particulier, dans lequel une variable ne peut prendre qu'un 
*nombre restreint de valeurs. Ces valeurs sont des constantes qui sont ici des noms communs
*désignant un genre de musique.
*/

public enum Genre{
    JAZZ("Jazz"), CLASSIQUE("Classique"), HIPHOP("Hip-Hop"), ROCK("Rock"), POP("Pop"), RAP("Rap");
    
    private String genre;
    
    /**
    *On a voulu associer à chacune de nos valeurs énumérée une autre forme d'affichage. Ainsi, on a
    *créé un constructeur privé pour chaque instance où on l'initialise avec notre affichage 
    */
    private Genre (String genre){
        this.genre = genre;
    }

    public String toString(){
        return this.genre;	//on créé une méthode publique pour récupérer la valeur instanciée
    }
}
