package indexing;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;


//Classe che effettua il parsing del file xml riempiendo una lista di oggetti di classe Movie
//Si è deciso di memorizzare solo alcuni campi dell'xml, ovvero: summary, stars, title, genres
public class XmlIndexing {
	
	public static LinkedList<Movie> processMovies(String path) throws JDOMException, IOException
	{
		LinkedList<Movie> movieList = new LinkedList<Movie>();
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(path);
		
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<Element> list = rootNode.getChildren("movie");
			for(int i = 0; i < list.size(); i++)
			{
				Movie filmTemp = new Movie();
				Element node = (Element) list.get(i);
				List<Element> featureList = node.getChildren("stars");
				for(int j = 0; j < featureList.size(); j++)
				{
					Element internalNode = (Element) featureList.get(j);
									
					String starsValue = internalNode.getValue();
					String[] starsVect = starsValue.split("\n");
					for(int k = 1; k < starsVect.length-1; k++)
					{
						starsVect[k] = starsVect[k].substring(6);
						filmTemp.addActors(starsVect[k]);	
					}
				}
				featureList = node.getChildren("genres");
				for(int j = 0; j < featureList.size(); j++)
				{
					Element internalNode = (Element) featureList.get(j);
					String genresValue = internalNode.getValue();
					String[] genresVect = genresValue.split("\n");
					for(int k = 1; k < genresVect.length-1; k++)
					{
						genresVect[k] = genresVect[k].substring(6);
						filmTemp.addGenre(genresVect[k]);	
					}
				}
				featureList = node.getChildren("summary");
				for(int j = 0; j < featureList.size(); j++)
				{
					Element internalNode = (Element) featureList.get(j);
					String summaryValue = internalNode.getValue();
					String[] summaryVect = summaryValue.split("\n");
					for(int k = 1; k < summaryVect.length-1; k++)
					{
						summaryVect[k] = summaryVect[k].substring(6);
						filmTemp.setPlot(summaryVect[k]);
					}
				}
				featureList = node.getChildren("directors");
				for(int j = 0; j < featureList.size(); j++)
				{
					Element internalNode = (Element) featureList.get(j);
					String directorsValue = internalNode.getValue();
					String[] directorsVect = directorsValue.split("\n");
					for(int k = 1; k < directorsVect.length-1; k++)
					{
						directorsVect[k] = directorsVect[k].substring(6);
						filmTemp.addDirector(directorsVect[k]);
					}
				}
				String id = node.getChildText("id");
				filmTemp.setId(Integer.parseInt(id));
				movieList.add(filmTemp);
				
				String title = node.getChildText("title");
				filmTemp.setTitle(title);
				
			}
			return movieList;
			
	}
}
