package musichub.business;

import java.io.*;
import java.util.*;
import musichub.util.*;

/**
*	Cette classe utilise la classe abstraite nommée Gestion et implémente ses méthodes.
*	On utilise alors le mot clé extends car Gestion est en réalité la classe mère de ce menu
*	d'options.
 *	@version 2.0
 *
 * @author PROSPA Florence, MICONNET Sandrine, LEOPOLD Arnaud et CHIDIAC Bryan
*/

public class Options extends Gestion implements socketServer{
	int vitesse;	//attribut de valeur de vitesse
	String quality;	//attribut de chaine permettant d'avoir un niveau de qualité.
	OutputStream output;
	InputStream input;

	/*Constructeur du menu*/
	public Options(OutputStream output, InputStream input){
		vitesse = 1;
		quality = "480p";
		this.output = output;
		this.input = input;
	}

	public void menu(){
        /*Menu d'options*/
            System.out.println("---------------MENU OPTIONS-------------------");
            System.out.println("Si vous voulez : .... =>tappez ...");
            System.out.println("Augmenter la qualité de lecture.                                           => a ");
            System.out.println("Diminuer la qualité de lecture.                                           => d ");
            System.out.println("Augmenter la vitesse de lecture.                                           => p ");
            System.out.println("Diminuer la vitesse de lecture.                                           => m ");
            writeTo(output,"---------------MENU OPTIONS-------------------");
			writeTo(output,"Si vous voulez : .... =>tappez ...");
			writeTo(output,"Augmenter la qualité de lecture.                                           => a ");
			writeTo(output,"Diminuer la qualité de lecture.                                           => d ");
			writeTo(output,"Augmenter la vitesse de lecture.                                           => p ");
			writeTo(output,"Diminuer la vitesse de lecture.                                           => m ");
			writeTo(output,"OK");
        /*récupération du choix*/
        String touche = readFrom(input);

        switch(touche)	//boucle de choix en fonction de la touche récupérée
        {
            case "a":
                bestQuality();	//on augmente la qualité
				writeTo(output,"Vous avez choisi augmenter la qualité");
                break;			//on revient au menu = 'casse' la boucle

                case "d":
                lessQuality();	//baisse la qualité
				writeTo(output,"Vous avez choisi baisser la qualité");
				break;

                case "p":
                moreSpeed();		//augmente la vitesse de lecture
				writeTo(output,"Vous avez choisi augmenter la vitesse de lecture");
				break;

                case "m":
                lessSpeed();		//diminue la vitesse de lecture
				writeTo(output,"Vous avez choisi diminuer la vitesse de lecture");
				break;
        }

    	}
	
	/*methodes utilisées pour changer les options*/
	public void bestQuality(){
		this.quality = "720p";
		/**
		*	On change le niveau de qualité en changeant la valeur de l'attribut
		*	'qualité'.
		*	Le mot-clé this est utilisé ici pour faire référence à l'attribut de sa
		*	qualité. C'est la même méthode que l'on utilise pour vitesse.
		*/
		System.out.println("Vous avez augmenté la qualité à "+this.quality);
		/*On affiche à la console la nouvelle qualité d'écoute*/
	}
	public void lessQuality(){
		this.quality = "360p";
		System.out.println("Vous avez diminué la qualité à "+this.quality);
		/*On affiche à la console la nouvelle qualité d'écoute*/
	}
	public void moreSpeed(){
		this.vitesse = vitesse ++;  
		/* On augmente la qualité en incrémentant de 1 la vitesse possible d'écoute*/
		System.out.println("Vous avez augmenté la vitesse à "+this.vitesse);
		/*On affiche à la console la nouvelle vitesse d'écoute*/
	}
	public void lessSpeed(){
		this.vitesse = vitesse --;
		System.out.println("Vous avez diminué la vitesse à "+this.vitesse);
		/*On affiche à la console la nouvelle vitesse d'écoute*/
	}

	/**
	 * Méthode qui affiche sur la sortie output donnée en paramètre un string s
	 * @see socketServer
	 * @param output
	 * @param s
	 * @return void
	 */
	@Override
	public void writeTo(OutputStream output, String s) {
		PrintWriter writer = new PrintWriter(output, true);
		writer.println(s);
	}

	/**
	 * Méthode qui lit depuis l'entrée input une chaine de caractère et la renvoie
	 * @see socketServer
	 * @param input
	 * @return String
	 */
	@Override
	public String readFrom(InputStream input) {
		String textread = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			textread = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return textread;
	}

	@Override
	public void closeOutput() {

	}

	@Override
	public void closeInput() {

	}

	@Override
	public void closeSocket() {

	}
}
