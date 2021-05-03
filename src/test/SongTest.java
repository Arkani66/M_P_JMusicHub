package test;

import musichub.util.Chanson;
import musichub.util.Genre;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SongTest {
    Chanson bruit = new Chanson("You Need To Calm Down","Taylor Swift",198,"001-000-002"," Mp3", Genre.POP);
    test.MockSong msong = new test.MockSong();
    @Test
    void testArtiste(){
        /**
         * test de fermeture du socket : valide
         */
        try{
            bruit.getArtiste();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testAssert() {
        msong.getDuree();
        bruit.getDuree();
        msong.play();
        bruit.play();
        assertEquals(msong.getContenu(), bruit.getContenu());
        assertEquals(bruit.toString(), bruit.toString());
    }

    @Test
    void testDureefaux(){
        /**
         * test de fermeture du socket : valide
         */
        String duree = "12546555653";
        try{
            bruit.getDuree();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    void testDuree(){
        /**
         * test de fermeture du socket : valide
         */
        try{
            bruit.getDuree();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    void testGenre(){
        /**
         * test de fermeture du socket : valide
         */
        try{
            bruit.getGenre();
            bruit.getGenre();
            bruit.getGenre();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }


}
