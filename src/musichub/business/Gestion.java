package musichub.business;

import musichub.util.*;

abstract class Gestion{
	
	/**
	*	Méthodes abstraites nécessaires pour la réalisation du menu d'options.
	*	Il s'agit bien d'une classe abstraite car on n'implémente pas les méthodes qui sont
	*	présentes ici mais on les implémentes dans une autre classe.
	*/
	public abstract void bestQuality();	//permet d'augmenter la qualité de lecture
	public abstract void lessQuality();	//permet de baisser la qualité de lecture
	public abstract void moreSpeed();	//permet d'augmenter la vitesse de lecture
	public abstract void lessSpeed();	//permet de baisser la vitesse de lecture
}

