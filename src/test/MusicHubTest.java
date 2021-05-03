package test;

import static org.junit.jupiter.api.Assertions.*;

import musichub.business.Playlist;
import musichub.main.MusicHub;
import musichub.util.Chanson;
import musichub.util.Genre;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;

class MusicHubTest{
	MusicHub hub = new MusicHub();
	LinkedList<Chanson> chansons;
	LinkedList<Playlist> playlists;
	private Socket socket;
	private OutputStream output  ;
	private InputStream input ;


	@Test
	void testDateAlbumFaux(){
		/**
		 * test de validité des dates des albums
		 */
		try{
			hub.verifDate("16554-56-23");
			hub.verifDate("201i-12-23");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@Test
	void testAddChanson() {
		/**
		 * test de d'ajout manuel d'une chanson au repertoire du musicHub
		 */
		try{
			String id = hub.creaID(2);
			System.out.println("Création de la Chanson :");
			Chanson crea_song = new Chanson("Mr. Brightside","The Killers" ,123,id,"MP3", Genre.POP);
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@Test
	void testAddChansonFaux() {
		/**
		 * test de d'ajout d'une chanson échouant car valeur nulle et ID errone
		 */
		try{
			System.out.println("Création de la Chanson :");
			Chanson crea_song = new Chanson(null,"The Killers" ,123,"000.000.01256","MP3", Genre.POP);
			chansons.add(crea_song);
		} catch (Exception e) {
			assertFalse(e instanceof IllegalArgumentException);
		}
	}

	@Test
	void testDateAlbum() {
		/**
		 * test de datation d'un album : correct
		 */
		try{
			hub.verifDate("2011-04-23");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@Test
	void testAffichage() {
		/**
		 * test d'affichage des chansons des albums : correct
		 */
		try{
			hub.affchanson_album(output, input);
		} catch (Exception e) {
			assertFalse(e instanceof IllegalArgumentException);
		}

	}

	@Test
	void testBcpPlaylist() {
		/**
		 * test d'affichage des chansons des albums : correct
		 */
		try{
			String name,idd;
			int ans,inde=-1,cptt=0;
			name = "playlist_test1";
			idd = hub.creaID(0);
			for(Playlist play : playlists){
				if(name.equals(play.getNom())==true){
					inde=-1;
				}
				inde=cptt;
				cptt ++;
			}
			if(inde == -1){
				System.out.print("La playlist :"+name+" existe déjà !");
				return;
			}
			Playlist crea_play = new Playlist(name,idd);
			playlists.add(crea_play);

			int index=-1,cpttt=0;
			name = "playlist_test2";
			idd = hub.creaID(0);
			for(Playlist play : playlists){
				if(name.equals(play.getNom())==true){
					index=-1;
				}
				index=cpttt;
				cpttt ++;
			}
			Playlist crea_playing = new Playlist(name,idd);
			playlists.add(crea_playing);
		} catch (Exception e) {
			assertFalse(e instanceof IllegalArgumentException);
		}

	}

}
