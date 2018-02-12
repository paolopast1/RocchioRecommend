package indexing;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;


import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;

import org.apache.lucene.index.IndexReader;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;

import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class TfidfResolver {

	//metodo che restituisce una rappresentazione di tutti i documenti con il relativo vettore di pesi
	//Si è utilizzata una HashMap che ha come chiavi interi che rappresentano gli id dei vari documenti e come 
	//valore un'altra HashMap che ha come chiavi i termini presenti nel relativo documento e come valori i tfidf del termine chiave
	//Si è inoltre deciso di pesare i termini a seconda del campo in cui si trovano
	public static HashMap<Integer, HashMap<String, Double>> getTfIdfDocs(String path) throws IOException
	{
	    IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(path)));
	    HashMap<Integer, HashMap<String, Double>> termVectorDocs = new HashMap<Integer, HashMap<String, Double>>();
	    for(int i = 0; i < reader.numDocs(); i++)
	    {
	    	Document doc = reader.document(i);         
	    	    	
	    	Terms termVectorTitle = reader.getTermVector(i, "title");
	    	Terms termVectorActors = reader.getTermVector(i, "actors");
	    	Terms termVectorGenres = reader.getTermVector(i, "genres");
	    	Terms termVectorDir = reader.getTermVector(i, "directors");
	    	BytesRef term = null;
	    	
	    	
	    	HashMap<String, Double> tfidfmap = new HashMap<String, Double>();
	    	if(termVectorTitle != null)
	    	{
	    		TermsEnum itrT = termVectorTitle.iterator(null);
	    		while ((term = itrT.next()) != null) {     
	    			String termText = term.utf8ToString();                              
	    			long termFreq = itrT.totalTermFreq();   
	    			Term termInstance = new Term("title", term);                              
	    			long docCount = reader.docFreq(termInstance);	

	    			double tfidf = (double) 0.15*(termFreq*(Math.log10((double)reader.numDocs()/docCount)));
	    			if(termVectorDocs.containsKey(termText))
	    			{
	    				tfidfmap.put(termText, tfidfmap.get(termText).doubleValue() + tfidf);
	    			}
	    			else
	    			{
	    				tfidfmap.put(termText, tfidf);
	    			}
	    		}
	    	}
	    	if(termVectorActors != null)
	    	{
	    		TermsEnum itrA = termVectorActors.iterator(null);
	    		while ((term = itrA.next()) != null) {     
	    			String termText = term.utf8ToString();                              
	    			long termFreq = itrA.totalTermFreq();   
	    			Term termInstance = new Term("actors", term);                              
	    			long docCount = reader.docFreq(termInstance);	

	    			double tfidf = (double) 0.35*(termFreq*(Math.log10((double)reader.numDocs()/docCount)));
	    			if(termVectorDocs.containsKey(termText))
	    			{
	    				tfidfmap.put(termText, tfidfmap.get(termText).doubleValue() + tfidf);
	    			}
	    			else
	    			{
	    				tfidfmap.put(termText, tfidf);
	    			}
	    		}
	    	}
	    	
	    	if(termVectorGenres != null)
	    	{
	    		TermsEnum itrG = termVectorGenres.iterator(null);
	    		while ((term = itrG.next()) != null) {     
	    			String termText = term.utf8ToString();                              
	    			long termFreq = itrG.totalTermFreq();   
	    			Term termInstance = new Term("genres", term);                              
	    			long docCount = reader.docFreq(termInstance);	

	    			double tfidf = (double) 0.15*(termFreq*(Math.log10((double)reader.numDocs()/docCount)));
	    			if(termVectorDocs.containsKey(termText))
	    			{
	    				tfidfmap.put(termText, tfidfmap.get(termText).doubleValue() + tfidf);
	    			}
	    			else
	    			{
	    				tfidfmap.put(termText, tfidf);
	    			}
	    	
	    		}
	    	}
	    	if(termVectorDir != null)
	    	{
	    		TermsEnum itrD = termVectorDir.iterator(null);
	    		while ((term = itrD.next()) != null) {     
	    			String termText = term.utf8ToString();                              
	    			long termFreq = itrD.totalTermFreq();   
	    			Term termInstance = new Term("directors", term);                              
	    			long docCount = reader.docFreq(termInstance);	

	    			double tfidf = (double) 0.35*(termFreq*(Math.log10((double)reader.numDocs()/docCount)));
	    			if(termVectorDocs.containsKey(termText))
	    			{
	    				tfidfmap.put(termText, tfidfmap.get(termText).doubleValue() + tfidf);
	    			}
	    			else
	    			{
	    				tfidfmap.put(termText, tfidf);
	    			}
	    		}
	    	}
	    	normalize(tfidfmap);
	    	termVectorDocs.put(new Integer(Integer.parseInt(doc.get("id"))), tfidfmap);
	    }
	   	reader.close();
        return termVectorDocs;
	}
	
	//Metodo che permette di normalizzare il vettore di pesi di un documento rappresentato sotto forma di HashMap
	public static void normalize(HashMap<String, Double> tvm)
	{
		double norma = 0;
		for (String t: tvm.keySet())
		{
			norma += Math.pow(tvm.get(t).doubleValue(), 2);
		}
		norma = Math.sqrt(norma);
		for (String t: tvm.keySet())
		{
			tvm.put(t, tvm.get(t)/norma);
		}
	}
}
