package musichub.util;

/**
*On a créé ici, notre propre exception afin d'avoir des exceptions personnalisées pour notre Hub.
*Elle descend des classes Exception d'où le mot-clé 'extends'. Cette classe sera utilisée dans une
*autre classe qui possèdera la 'java.lang.*' ce qui permettra de détecter et de traiter ces
*erreurs (try, catch et finally ) mais aussi de les lever ou les propager avec throw et throws.
*/
public class EmptyFichException extends Exception
{
    public EmptyFichException(String s)
    {
        super(s);
        /**
        *Le mot-clé super est utilisé afin d'appeler ou d'accéder à des fonctions définies sur 
        *l'objet parent. Ici, on utilise son constructeur parent.
        */
    }
}
