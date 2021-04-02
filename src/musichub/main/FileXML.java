package musichub.main;

import musichub.business.*;
import musichub.util.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

import org.w3c.dom.*;
import java.io.IOException;
import java.io.File;
import java.util.UUID;

import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;  

/**
 * Cette classe effectue toutes les actions liées aux fichiers .xml
 *  - lit et remplit les listes des classes Playlist, Album, Chanson et LivreAudio
 *  - écrit les listes des classes Playlist, Album, Chanson et LivreAudio dans les fichiers .xml
 * classe inspirée du document d'aide fourni avec le sujet (Demo.xml)
 *
 * @version 1.0
 *
 * @see MusicHub
 * @author PROSPA Florence et MICONNET Sandrine
 */

 public class FileXML{
    private TransformerFactory transformerFactory;
	private Transformer transformer;
	private DocumentBuilderFactory documentFactory;
	private DocumentBuilder documentBuilder;

	private static String XML_ELEMENTS = "files/elements.xml";
	private static String XML_ALBUMS = "files/albums.xml";
	private static String XML_PLAYLISTS = "files/playlists.xml";
	
    //Constructeur
    public FileXML() {
		try {
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			documentFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentFactory.newDocumentBuilder();
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
	}
	
/**
* La methode qui transforme le document en memoire en fichier XML sur le disque dur
* @param document le document à transformer
* @param filePath le chemin (répértoire et nom) du fichier XML à créer 
*/	
	public void createXMLFile(Document document, String filePath)
	{
		try {
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filePath));

		// If you use
		// StreamResult result = new StreamResult(System.out);
		// the output will be pushed to the standard output ...
		// You can use that for debugging 

        //transform the DOM Object to an XML File
		transformer.transform(domSource, streamResult);
		
		} catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
		System.out.println("Done creating XML File" + filePath);
	}
	
/**
* La methode qui crée le document en memoire
* @return le document créé
*/	
	public Document createXMLDocument()
	{
		return documentBuilder.newDocument();
	}	
	
	public NodeList parseXMLFile (String filePath) {
		NodeList elementNodes = null;
		try {
			Document document= documentBuilder.parse(new File(filePath));
			Element root = document.getDocumentElement();
			
			elementNodes = root.getChildNodes();	
		}
		catch (SAXException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return elementNodes;
	}

	
/**
* Cette méthode permet de lire dans le fichier playlists.xml avec le format suivant
* <playlists>
* 	<playlist>
*		<nom></nom>
*		<ID></ID>
*	</playlist>
* </playlists>
* @param playlists la liste contenant les playlists du MusicHub
* @return une liste de type Playlist rempli avec les éléments du fichier
*/
    public LinkedList<Playlist> readPlaylists( LinkedList<Playlist> playlists) {
	
        String nom= null;
        String id= null;
		NodeList nodes = this.parseXMLFile(XML_PLAYLISTS);
        System.out.println("\t\tLecture playlists.xml en cours ...");
		if (nodes == null) return null;
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
        //vérification
// 				System.out.println("node "+currentElement.getNodeName());
				if (currentElement.getNodeName().equals("playlist")) 	{
					try {
						nom = currentElement.getElementsByTagName("nom").item(0).getTextContent();
						id = null;
						id = currentElement.getElementsByTagName("ID").item(0).getTextContent();
					} catch (Exception ex) {
						System.out.println("Something is wrong with the XML client element");
					}
				}
				Playlist ici = new Playlist(nom,id);
                playlists.add(ici);
			}  
		}
		return playlists;
	}

/**
* Cette méthode permet d'écrire dans le fichier playlists.xml les éléments dans la liste playlist avec le format suivant
* <playlists>
* 	<playlist>
*		<nom></nom>
*		<ID></ID>
*	</playlist>
* </playlists>
* @param playlists liste de type Playlist rempli avec les playlists courante du MusicHub
*/
    public void writePlaylists(LinkedList<Playlist> playlists) {
		Document document = this.createXMLDocument();
		if (document == null) return;

 		// create root element
		Element root = document.createElement("playlists");
		document.appendChild(root);

		for( Playlist courant : playlists)
		{
            //save one "client" element; create a loop to save more elements!!
            Element client = document.createElement("playlist");
            
            // nom element
            String nom = courant.getNom();
            Element nomElement = document.createElement("nom");
            nomElement.appendChild(document.createTextNode(nom));
            client.appendChild(nomElement);
                    
            //ID element
            String ID = courant.getID();
            Element IDElement = document.createElement("ID");
            IDElement.appendChild(document.createTextNode(ID));
            client.appendChild(IDElement);
                                
            root.appendChild(client);
        }
		
		this.createXMLFile(document, XML_PLAYLISTS);
	}
	

/**
* Cette méthode permet de lire dans le fichier albums.xml avec le format suivant 
*<albums>
*	<album>
*		<titre></titre>
*		<artiste></artiste>
*		<duree></duree>
*		<date></date>
*		<ID></ID>
*	</album>
*</albums>
* @param albums la liste contenant les albums du MusicHub
* @return la liste albums remplie avec les éléments contenus dans le fichier albums.xml
*/
    public LinkedList<Album> readAlbums( LinkedList<Album> albums ){
		NodeList nodes = this.parseXMLFile(XML_ALBUMS);
		System.out.println("\t\tLecture albums.xml en cours ...");
		if (nodes == null) return null;
		String titre = null;
		String artiste = null;
		String duree = null;
		String date = null;
		String id = null;
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
        //vérification
// 				System.out.println("node "+currentElement.getNodeName());
				if (currentElement.getNodeName().equals("album")) 	{
					try {
						titre = currentElement.getElementsByTagName("titre").item(0).getTextContent();
						 artiste = currentElement.getElementsByTagName("artiste").item(0).getTextContent();
						 duree = currentElement.getElementsByTagName("duree").item(0).getTextContent();
						 date = currentElement.getElementsByTagName("date").item(0).getTextContent();
						 id = currentElement.getElementsByTagName("ID").item(0).getTextContent();
// 						//verify that I read everything correctly:
// 						System.out.println(titre + ", " + artiste + ", " + duree + ", " + date + ", "+ id);
					} catch (Exception ex) {
						System.out.println("Something is wrong with the XML client element");
					}
					Date date1 = null;
					try{
                        date1 =  new SimpleDateFormat("yyyy-MM-dd").parse(date);
                    } catch (Exception e) {
                        System.out.println("Date hasn't working out");
                    }
					Album ici = new Album(titre,artiste,Integer.parseInt(duree),date1,id);
					albums.add(ici);
				}
			}  
		}
		return albums;
	}

/**
* Cette méthode permet d'écrire dans le fichier albums.xml avec le format suivant 
*<albums>
*	<album>
*		<titre></titre>
*		<artiste></artiste>
*		<duree></duree>
*		<date></date>
*		<ID></ID>
*	</album>
*</albums>
* @param albums la liste contenant les playlists du MusicHub
*/
    public void writeAlbums(LinkedList<Album> albums) {
		Document document = this.createXMLDocument();
		if (document == null) return;

 		// create root element
		Element root = document.createElement("albums");
		document.appendChild(root);

		for( Album courant : albums)
		{
            //save one "client" element; create a loop to save more elements!!
            Element client = document.createElement("album");
            
            // titre element
            String titre = courant.getTitre();
            Element titreElement = document.createElement("titre");
            titreElement.appendChild(document.createTextNode(titre));
            client.appendChild(titreElement);
                    
            //artiste element
            String artiste = courant.getArtiste();
            Element artisteElement = document.createElement("artiste");
            artisteElement.appendChild(document.createTextNode(artiste));
            client.appendChild(artisteElement);
                    
            //duree element
            int dureeinteger = courant.getDuree();
            String duree = String.valueOf(dureeinteger);
            Element dureeElement = document.createElement("duree");
            dureeElement.appendChild(document.createTextNode(duree));
            client.appendChild(dureeElement);
            
            //date element
            Date dateDate = courant.getDate();
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            StringBuffer bufferdate = new StringBuffer().append(formater.format(dateDate));
            
            String date = bufferdate.toString();
            Element dateElement = document.createElement("date");
            dateElement.appendChild(document.createTextNode(date));
            client.appendChild(dateElement);
            
            //ID element
            String ID = courant.getID();
            Element IDElement = document.createElement("ID");
            IDElement.appendChild(document.createTextNode(ID));
            client.appendChild(IDElement);
            
            root.appendChild(client);
        }
		
		this.createXMLFile(document, XML_ALBUMS);
	}

/**
* Cette méthode permet de lire dans le fichier elements.xml avec le format suivant
*<elements>
*	<chansons>
*        <titre></titre>
*        <artiste></artiste>
*        <duree></duree>
*        <ID></ID>
*        <contenu></contenu>
*        <genre></genre>
*    </chansons>
*    <livreaudios>
*        <titre></titre>
*        <auteur></auteur>
*        <duree></duree>
*        <ID></ID>
*        <contenu></contenu>
*        <langue></langue>
*        <categorie></categorie>
*    </livreaudios>
*</elements>
* @param chansons  la liste contenant les chansons du MusicHub
* @return la liste chanson remplie avec les éléments de type Chanson contenus dans le fichier elements.xml
*/
    public LinkedList<Chanson> readElementsChansons( LinkedList<Chanson> chansons) {
		NodeList nodes = this.parseXMLFile(XML_ELEMENTS);
        System.out.println("\t\tLecture elements.xml en cours ...");
		if (nodes == null) return null;
		String titre = null;
		String artiste = null;
		String duree = null;
		String contenu = null;
		String genre = null;
		String id = null;
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
        //vérification
// 				System.out.println("node "+currentElement.getNodeName());
				if (currentElement.getNodeName().equals("chansons") /*&&  currentElement.getNodeName().equals("livreaudios")==false*/) 	{
					try {
						 titre = currentElement.getElementsByTagName("titre").item(0).getTextContent();
						 artiste = currentElement.getElementsByTagName("artiste").item(0).getTextContent();
						 duree = currentElement.getElementsByTagName("duree").item(0).getTextContent();
						 contenu = currentElement.getElementsByTagName("contenu").item(0).getTextContent();
						 genre = currentElement.getElementsByTagName("genre").item(0).getTextContent();
						 id = currentElement.getElementsByTagName("ID").item(0).getTextContent();
						//verify that I read everything correctly:
// 						System.out.println(titre + ", " + artiste + ", " + duree + ", " + id +", "+contenu+", "+genre);
					} catch (Exception ex) {
						System.out.println("Something is wrong with the XML client element");
					}
					genre = genre.toUpperCase();
					if( genre.equals("HIP-HOP")) genre = "HIPHOP";
					Genre genre1 = Genre.valueOf(genre);
// 					System.out.println(genre1.toString());
					Chanson ici = new Chanson(titre,artiste,Integer.parseInt(duree),id,contenu,genre1);
					chansons.add(ici);
				}

			}  
		}
		return chansons;
	}

/**
* Cette méthode permet de lire dans le fichier elements.xml avec le format suivant
*<elements>
*	<chansons>
*        <titre></titre>
*        <artiste></artiste>
*        <duree></duree>
*        <ID></ID>
*        <contenu></contenu>
*        <genre></genre>
*    </chansons>
*    <livreaudios>
*        <titre></titre>
*        <auteur></auteur>
*        <duree></duree>
*        <ID></ID>
*        <contenu></contenu>
*        <langue></langue>
*        <categorie></categorie>
*    </livreaudios>
*</elements>
* @param livres  la liste contenant les livres audios du MusicHub
* @return la liste livres remplie avec les éléments de type LivreAudio contenus dans le fichier elements.xml
*/
    public LinkedList<LivreAudio> readElementsLivres(LinkedList<LivreAudio> livres) {
		NodeList nodes = this.parseXMLFile(XML_ELEMENTS);
        System.out.println("\t\tLecture elements.xml en cours ...");
		if (nodes == null) return null;
		String titre = null;
		String auteur = null;
		String duree = null;
		String contenu = null;
		String langue = null;
		String categorie = null;
		String id = null;
		for (int i = 0; i<nodes.getLength(); i++) {
			if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE)   {
				Element currentElement = (Element) nodes.item(i);
        //vérification
// 				System.out.println("node "+currentElement.getNodeName());
				if (currentElement.getNodeName().equals("livreaudios")) 	{
					try {
						titre = currentElement.getElementsByTagName("titre").item(0).getTextContent();
						 auteur = currentElement.getElementsByTagName("auteur").item(0).getTextContent();
						duree = currentElement.getElementsByTagName("duree").item(0).getTextContent();
						contenu = currentElement.getElementsByTagName("contenu").item(0).getTextContent();
						 langue = currentElement.getElementsByTagName("langue").item(0).getTextContent();
						 categorie = currentElement.getElementsByTagName("categorie").item(0).getTextContent();
						 id = currentElement.getElementsByTagName("ID").item(0).getTextContent();
						//verify that I read everything correctly:
// 						System.out.println(titre + ", " + auteur + ", " + duree + ", " + id +", "+contenu+", "+langue+", "+categorie+", ");
					} catch (Exception ex) {
						System.out.println("Something is wrong with the XML client element");
					}
					categorie = categorie.toUpperCase();
					langue = langue.toUpperCase();
					Langue langue1 = Langue.valueOf(langue);
					Categorie categorie1 = Categorie.valueOf(categorie);
					LivreAudio ici = new LivreAudio(titre,auteur,Integer.parseInt(duree),id,contenu,langue1,categorie1);
					livres.add(ici);
				}
			}  
		}
		return livres;
	}
	
/**
* Cette méthode permet d'écrire dans le fichier elements.xml avec le format suivant
*<elements>
*	<chansons>
*        <titre></titre>
*        <artiste></artiste>
*        <duree></duree>
*        <ID></ID>
*        <contenu></contenu>
*        <genre></genre>
*    </chansons>
*    <livreaudios>
*        <titre></titre>
*        <auteur></auteur>
*        <duree></duree>
*        <ID></ID>
*        <contenu></contenu>
*        <langue></langue>
*        <categorie></categorie>
*    </livreaudios>
*</elements>
* @param chansons la liste contenant les chansons du MusicHub
* @param livres la liste contenant les livres du MusicHub  
*/
	public void writeElements(LinkedList<Chanson> chansons, LinkedList<LivreAudio> livres) {
		Document document = this.createXMLDocument();
		if (document == null) return;

 		// create root element
		Element root = document.createElement("elements");
		document.appendChild(root);

		for( Chanson courant : chansons)
		{
            //save one "client" element; create a loop to save more elements!!
            Element client = document.createElement("chansons");
            
            // titre element
            String titre = courant.getTitre();
            Element titreElement = document.createElement("titre");
            titreElement.appendChild(document.createTextNode(titre));
            client.appendChild(titreElement);
                    
            //artiste element
            String artiste = courant.getArtiste();
            Element artisteElement = document.createElement("artiste");
            artisteElement.appendChild(document.createTextNode(artiste));
            client.appendChild(artisteElement);
                    
            //duree element
            int dureeinteger = courant.getDuree();
            String duree = String.valueOf(dureeinteger);
            Element dureeElement = document.createElement("duree");
            dureeElement.appendChild(document.createTextNode(duree));
            client.appendChild(dureeElement);
            
            //ID element
            String ID = courant.getID();
            Element IDElement = document.createElement("ID");
            IDElement.appendChild(document.createTextNode(ID));
            client.appendChild(IDElement);
            
            //contenu element
            String contenu = courant.getContenu();
            Element contenuElement = document.createElement("contenu");
            contenuElement.appendChild(document.createTextNode(contenu));
            client.appendChild(contenuElement);
            
            //genre element
            Genre genreGenre = courant.getGenre();
            String genre = genreGenre.toString();
            Element genreElement = document.createElement("genre");
            genreElement.appendChild(document.createTextNode(genre));
            client.appendChild(genreElement);
            
            root.appendChild(client);
        }

		for( LivreAudio courant : livres)
		{
            //save one "client" element; create a loop to save more elements!!
            Element client = document.createElement("livreaudios");
            
            // titre element
            String titre = courant.getTitre();
            Element titreElement = document.createElement("titre");
            titreElement.appendChild(document.createTextNode(titre));
            client.appendChild(titreElement);
                    
            //auteur element
            String auteur = courant.getAuteur();
            Element auteurElement = document.createElement("auteur");
            auteurElement.appendChild(document.createTextNode(auteur));
            client.appendChild(auteurElement);
                    
            //duree element
            int dureeinteger = courant.getDuree();
            String duree = String.valueOf(dureeinteger);
            Element dureeElement = document.createElement("duree");
            dureeElement.appendChild(document.createTextNode(duree));
            client.appendChild(dureeElement);
            
            //ID element
            String ID = courant.getID();
            Element IDElement = document.createElement("ID");
            IDElement.appendChild(document.createTextNode(ID));
            client.appendChild(IDElement);
            
            //contenu element
            String contenu = courant.getContenu();
            Element contenuElement = document.createElement("contenu");
            contenuElement.appendChild(document.createTextNode(contenu));
            client.appendChild(contenuElement);
            
            //langue element
            Langue langueLangue = courant.getLangue();
            String langue = langueLangue.toString();
            Element langueElement = document.createElement("langue");
            langueElement.appendChild(document.createTextNode(langue));
            client.appendChild(langueElement);
            
            //categorie element
            Categorie categorieCategorie = courant.getCategorie();
            String categorie = categorieCategorie.toString();
            Element categorieElement = document.createElement("categorie");
            categorieElement.appendChild(document.createTextNode(categorie));
            client.appendChild(categorieElement);
            
            root.appendChild(client);
        }
		
		this.createXMLFile(document, XML_ELEMENTS);
	}
	
//Si l'ID est mauvais on en créer un nouveau
/*
    XXX . XXX . XXX
(playlist)(album)(id propre)
*/
    public String createID(){
        return "000.000.000";
    }
}
