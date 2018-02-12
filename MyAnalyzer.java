
package indexing;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;

import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.pattern.PatternReplaceCharFilter;
import org.apache.lucene.analysis.pattern.PatternReplaceFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;

//Analizzatore utilizzato nell'indicizzazione
//L'analizzatore applica prima lo StandardTokenizer, modifica tutti i token in minuscolo, elimina le stopword e infine elimina tutti i token di lunghezza 2
public class MyAnalyzer extends Analyzer {

	private CharArraySet sw = StopFilter.makeStopSet(new ArrayList<>(StopAnalyzer.ENGLISH_STOP_WORDS_SET));
	@Override
	public TokenStreamComponents createComponents(String fieldName, Reader reader) {
		StandardTokenizer source = new StandardTokenizer(reader);
		TokenStream filter = new LowerCaseFilter(source);
		filter = new StopFilter(filter, sw);
		filter = new LengthFilter(filter, 2, Integer.MAX_VALUE);
		
		return new TokenStreamComponents(source, filter);
		}
	}

