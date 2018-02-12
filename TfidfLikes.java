package indexing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.*;
public class TfidfLikes {

	
	//Metodo per trovare la lista degli indici dei film che piacciono all'utente
	private static LinkedList<Integer> getIdLikes(int numUser, String path) throws NumberFormatException, IOException
	{
		 File r = new File(path);
	     FileReader fr = new FileReader(r);
	     BufferedReader br = new BufferedReader(fr);
	     LinkedList<Integer> res = new LinkedList<Integer>();
	     String s;
	     while((s = br.readLine()) != null)
	     {
	    	 String[] ratings = s.split("\t");
	    	 if(ratings[0].equals(Integer.toString(numUser)))
	    	 {
	    		 res.add(new Integer(Integer.parseInt(ratings[1])));
	    	 }
	     }
	     br.close();
	     return res;
	}
	
	//Metodo per trovare, per ogni documento che piace all'utente, l'insieme di coppie termine-tfidf 
	//Si è usata una hashMap che ha come chiave l'id del film e come valore un'altra hashMap che a sua volta ha come chiave
	//il termine e come valore il tfidf del termine stesso
	public static HashMap<Integer, HashMap<String, Double>> getVectLikes(int numUser, String indexPath, String ratingPath) throws IOException
	{
		HashMap<Integer, HashMap<String, Double>> res = new HashMap<Integer, HashMap<String, Double>>();
		HashMap<Integer, HashMap<String, Double>> allMovies = TfidfResolver.getTfIdfDocs(indexPath);
		LinkedList<Integer> likes = getIdLikes(numUser, ratingPath);
		for(Integer i:likes)
		{
			res.put(i, allMovies.get(i));
		}
		return res;
	}
	
	//Metodo per trovare il vettore prototipo dei like dell'utente sotto forma di lista di oggetti di classe TermWeight
	public static HashMap<String, Double> getRocchioLikes(int numUser, String indexPath, String ratingPath) throws IOException
	{
		HashMap<Integer, HashMap<String, Double>> likesVect = getVectLikes(numUser, indexPath, ratingPath);
		HashMap<String, Double> rocchio = new HashMap<String, Double>();
		for(Integer i: likesVect.keySet())
		{
			HashMap<String, Double> temp = likesVect.get(i);
			for(String j : temp.keySet())
			{
				if(rocchio.keySet().contains(j))
				{
					rocchio.put(j, rocchio.get(j).doubleValue() + temp.get(j).doubleValue());
				}
				else
				{
					rocchio.put(j, temp.get(j).doubleValue());
				}
			}
		}
		TfidfResolver.normalize(rocchio);
		return rocchio;
	}
}
