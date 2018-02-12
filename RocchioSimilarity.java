package indexing;

//Classe che rappresenta l'indice di un film e il valore di similarità con il vettore prototipo
//Implementa la classe astratta Comparable per poter ordinare liste di oggetti di classe RocchioSimilarity
public class RocchioSimilarity implements Comparable{

	private Integer i;
	private double similarity;
	
	RocchioSimilarity()
	{
		i = new Integer(0);
		similarity = 0;
	}
	
	RocchioSimilarity(Integer id, double sim)
	{
		i = id;
		similarity = sim;
	}
	
	public double getSimilarity()
	{
		return similarity;
	}
	
	public Integer getId()
	{
		return i;
	}
	
	public void setId(Integer id)
	{
		i = id;
	}
	
	public void setSimilarity(double sim)
	{
		similarity = sim;
	}
	
	@Override
	public int compareTo(Object x) {
		if(this.similarity > ((RocchioSimilarity) x).getSimilarity())
			return -1;
		else
			return 1;
	}
}
