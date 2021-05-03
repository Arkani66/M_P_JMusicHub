package test;

import musichub.main.Audio;
import musichub.main.Son;
import musichub.util.Genre;

public class MockSong implements Audio {
    private String artiste;
    private String contenu;
    private Genre genre;
    private int duree;
    private String titre ;
    private String ID;


    //Constructeur
    public MockSong()
    {
        titre = "Toxic";
        artiste = "Britney Spears";
        duree = 256;
        ID = "000.000.500";
        contenu =" Mp3";
        genre  = Genre.POP;

    }

    /**
     *   Renvoie en format String les attributs privés de la classe : artiste et titre
     */
    public String toString()
    {
        return "\n" + this.artiste + " - "+this.titre;
    }

    /**
     *   Renvoie l'attribut privé : artiste, dans son format d'origine (String)
     * @return artiste
     */
    public String getArtiste()
    {
        return this.artiste;
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
     *   Renvoie l'attribut privé : genre, dans son format d'origine (Genre)
     * @return genre
     */
    public Genre getGenre()
    {
        return this.genre;
    }

    /**
     *   Renvoie l'attribut privé : genre, en format String
     * @return genre.toString()
     */
    public String getChansonGenre(){
        return genre.toString();
    }

    /**
     *   Méthode de lecture d'un album associée à l'interface Audio
     */
    public void play(){
        System.out.println ("La chanson " +titre+ " est en écoute.");
    }

    /**
     *   Méthode pour stopper la lecture d'un album associée à l'interface Audio
     */
    public void stop(){
        System.out.println ("Vous venez de stopper l'écoute !");
    }
}
