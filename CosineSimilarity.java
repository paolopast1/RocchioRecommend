package indexing;

import java.util.HashMap;

//Classe che si occupa di calcolare la similarità del coseno fra due HashMap che rappresentano i vettori dei documenti
//fra cui si vuole calcolare la similarità
public class CosineSimilarity {

	public static double getCosineSimilarity(HashMap<String, Double> d, HashMap<String, Double> r)
	{
		double sim = 0;
		for(String k: r.keySet())
		{
			if(d.keySet().contains(k))
			{
				sim += d.get(k).doubleValue()*r.get(k).doubleValue();
			}
		}
		return sim;
	}
}
