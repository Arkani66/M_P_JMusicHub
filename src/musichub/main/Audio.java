package musichub.main;

import javax.sound.sampled.*;

/**
*Une interface est une liste de noms de méthodes qui doivent être implémentées dans les classes
*construites à partir de ce prototype. Ici, on déclare donc 2 méthodes qu'on utilise dans toutes
*les classes qui nécéssite une action audio.
*/

public interface Audio {
    
    public void playSong(Clip clip);	//signature de méthode play()
    public void stopSong(Clip clip);	//signature de méthode stop()
    public void resetSong(Clip clip, int sec);
    public void closeSong(Clip clip);
}
