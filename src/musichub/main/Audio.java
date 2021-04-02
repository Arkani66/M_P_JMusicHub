package musichub.main;
/**
*Une interface est une liste de noms de méthodes qui doivent être implémentées dans les classes
*construites à partir de ce prototype. Ici, on déclare donc 2 méthodes qu'on utilise dans toutes
*les classes qui nécéssite une action audio.
*/

public interface Audio {
    
    public void play();	//signature de méthode play()
    public void stop();	//signature de méthode stop()
}
