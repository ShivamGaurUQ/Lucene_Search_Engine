package develop;

import java.awt.List;
import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;
import java.nio.file.Paths;
import java.util.*;


public class SimpleSearcher {
	ArrayList<String> listUrls=new ArrayList<String>();
	private static final String indexDirectory = "C:/Users/shiva/eclipse-workspace/Assign4/index";

	private String queryString = "India";

	private static final int maxHits = 10;
	

	public static void main(String[] args) throws Exception {

		//File indexDir = new File(indexDirectory);

		//SimpleSearcher searcher = new SimpleSearcher();

		
	}
	
	public SimpleSearcher(String query) {
		
		
		this.queryString=query;try {
		searchIndex(indexDirectory, queryString);
		}catch(Exception e) {}

	}

	private void searchIndex(String indexDir, String queryStr)
			throws Exception {

		Directory directory = FSDirectory.open(Paths.get(indexDir));
		
		IndexReader  indexReader  = DirectoryReader.open(directory);

		IndexSearcher searcher = new IndexSearcher(indexReader);
		
		Analyzer analyzer = new StandardAnalyzer();
		
		QueryBuilder builder = new QueryBuilder(analyzer);
		
		Query query = builder.createPhraseQuery("content", queryStr);
		
		TopDocs topDocs =searcher.search(query, maxHits);
		
		ScoreDoc[] hits = topDocs.scoreDocs;
		
        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            //System.out.println(d.get("url") + " Score :"+hits[i].score);
            listUrls.add(d.get("url"));
            
        }
        
		//System.out.println("Found " + hits.length);

	}
	
	public ArrayList<String> listurls(){
		
		return listUrls;
		
		
		
	}

}
