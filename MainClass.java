package indexing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import org.jdom2.JDOMException;

//Classe che stampa a video i primi k risultati ordinati in base ai valori di similarità
//args[0] -> percorso del file xml
//args[1] -> numero di film da raccomandare
//args[2] -> id dell'utente a cui raccomandare nuovi item
//args[3] -> percorso della cartella contenente l'indice
//args[4] -> percorso del file contenente i rating degli utenti in formato binario

public class MainClass {
	
	public static void main(String args[]) throws IOException, JDOMException
	{
		//caricamento di tutti i vettori dei film
		HashMap<Integer, HashMap<String, Double>> allMovies = TfidfResolver.getTfIdfDocs(args[3]);
		
		//calcolo del vettore prototipo dei like
		HashMap<String, Double> rocchio =  TfidfLikes.getRocchioLikes(Integer.parseInt(args[2]), args[3], args[4]);
		
		ArrayList<RocchioSimilarity> recommended = new ArrayList<RocchioSimilarity>();
		ArrayList<RocchioSimilarity> toRemove = new ArrayList<RocchioSimilarity>();
		
		//caricamento dei vettori dei film che piacciono all'utente
		HashMap<Integer, HashMap<String, Double>> likeMovies = TfidfLikes.getVectLikes(Integer.parseInt(args[2]), args[3], args[4]);
		
		//Calcolo della similarità fra tutti i film e il vettore prototipo
		for(Integer i : allMovies.keySet())
		{
			double sim = CosineSimilarity.getCosineSimilarity(allMovies.get(i), rocchio);
			recommended.add(new RocchioSimilarity(i, sim));
		}
		
		//Ordinamento sulla base del valore di similarità
		Collections.sort(recommended);
		
		//Caricamento della lista dei film da rimuovere perchè già votati dall'utente
		for(RocchioSimilarity t: recommended)
		{
			if(likeMovies.keySet().contains(t.getId()))
				toRemove.add(t);
		}
		
		//Rimozione dei film già votati
		recommended.removeAll(toRemove);
		
		//Indicizzazione del file XML per recuperare i titoli dei film
		LinkedList<Movie> allMovieDescr = XmlIndexing.processMovies(args[0]);
		
		//Stampa dei primi k titoli
		for(int i = 0; i < Integer.parseInt(args[1]); i++)
		{
			for(Movie j:allMovieDescr)
			{
				if(Integer.parseInt(j.getId()) == recommended.get(i).getId().intValue())
				{
					System.out.println(j.getTitle() + "-> " + recommended.get(i).getSimilarity());
					break;
				}
			}
			
		}
		
		
	}
}
