package musichub.util;

/**
*Une énumération est un type de données particulier, dans lequel une variable ne peut prendre qu'un 
*nombre restreint de valeurs. Ces valeurs sont des constantes qui sont ici des noms communs
*désignant une langue parlée dans un livre-audio en particulier.
*/

public enum Langue{
    FRANCAIS("Francais"), ANGLAIS("Anglais"), ITALIEN("Italien"), ESPAGNOL("Espagnol"), ALLEMAND("Allemand");
    
    private String langue;
    /**
    *On a voulu associer à chacune de nos valeurs énumérée une autre forme d'affichage. Ainsi, on a
    *créé un constructeur privé pour chaque instance où on l'initialise avec notre affichage 
    */
    
    private Langue (String langue){
        this.langue = langue;
    }

    public String toString(){
        return this.langue; //on créé une méthode publique pour récupérer la valeur instanciée
    }
}
