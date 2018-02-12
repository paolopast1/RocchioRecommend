package indexing;


import java.io.File;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.jdom2.JDOMException;


//Classe per la indicizzazione dei documenti che rappresentano il contenuto di ogni film
//Il metodo main richiama il metodo per parserizzare il file xml
//args[0]-> cartella in cui salvare l'indice 
//args[1]-> indirizzo del file xml
public class MovieIndexer {

	public static void main(String args[]) throws IOException, JDOMException
	{
		FSDirectory fsdir = FSDirectory.open(new File(args[0]));
    	IndexWriterConfig iwc = new IndexWriterConfig(Version.LATEST, new StandardAnalyzer());
    	iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
    	IndexWriter writer = new IndexWriter(fsdir, iwc);
    	
    	
    	LinkedList<Movie> list = XmlIndexing.processMovies(args[1]);
		FieldType mytype = new FieldType(TextField.TYPE_NOT_STORED);
        mytype.setStoreTermVectors(true);
        mytype.setTokenized(true);
		for(Movie m: list)
		{
			Document d = new Document();
			d.add(new StringField("id", m.getId(), Field.Store.YES));			
			List<String> actors = m.getActors();
			String indActors = new String();
			for(String a: actors)
			{
				indActors = indActors.concat(a + " ");
			}
			List<String> genres = m.getGenres();
			String indGenres = new String("");
			for(String a: genres)
			{
				indGenres= indGenres+ a + " ";
			}
			List<String> directors = m.getDirectors();
			String indDir = new String("");
			for(String a: directors)
			{
				indDir= indDir+ a + " ";
			}
			d.add(new Field("actors", indActors, mytype));
			d.add(new Field("genres", indGenres, mytype));
			d.add(new Field("title", m.getTitle(), mytype));
			d.add(new Field("directors", indDir, mytype));
			writer.addDocument(d);
		}
		writer.close();
		
	}
}
