package indexing;

import java.util.*;


//Classe per rappresentare le caratteristiche principali dei film da usare nell'indicizzazione
//Si è scelto di indicizzare i generi, gli attori, i registi, la trama, il titolo e l'id di ogni film
public class Movie {
	List<String> genres;
	List<String> actors;
	List<String> directors;
	String plot;
	String title;
	int id;
	
	Movie()
	{
		genres = new LinkedList<String>();
		actors = new LinkedList<String>();
		directors = new LinkedList<String>();
		plot = new String();
		title = new String();
	}
	
	public void setId(int x)
	{
		id = x;
	}
	
	public void addGenre(String g)
	{
		genres.add(g);
	}
	
	public void addActors(String a)
	{
		actors.add(a);
	}
	
	public void addDirector(String d)
	{
		directors.add(d);
	}
	
	public void setPlot(String p)
	{
		plot = p;
	}
	
	public void setTitle(String p)
	{
		title = p;
	}
	
	public String getId()
	{
		return String.valueOf(id).toString();
	}
	
	public List<String> getDirectors()
	{
		return directors;
	}
	
	public List<String> getGenres()
	{
		return genres;
	}
	
	public List<String> getActors()
	{
		return actors;
	}
	
	public String getPlot()
	{
		return plot;
	}
	
	public String getTitle()
	{
		return title;
	}
}
