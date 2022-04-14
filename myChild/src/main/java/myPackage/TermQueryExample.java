package myPackage;

import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;

class TermQueryExample {
  
  private Analyzer     analyzer     = new StandardAnalyzer();
  private RAMDirectory ramDirectory;
  private IndexWriter  indexWriter;
  

//  public static void main(String[] args) throws ParseException {
//    createIndex();
//    //searchSingleTerm("title", "lucene");
//    //searchSingleTerm("title", "example");
//    //searchSingleTerm("title", "Example");
//    //searchSingleTerm("title", "lucene example");
//    
//    //searchBooleanQuery();
//    
//    searchQueryParser("lucene term vector");
//    searchQueryParser("lucene OR example");
//    searchQueryParser("lucene AND example AND author:sam");
//
//    ramDirectory.close();
//  }
  
  void closeDirectory() {
    ramDirectory.close();
  }
  
  void createIndex() {
    try {
      IndexWriterConfig config       = new IndexWriterConfig(analyzer);
                        ramDirectory = new RAMDirectory();
                        indexWriter  = new IndexWriter(ramDirectory, config);

      createDoc("Sam", "Lucene index option analyzed vs not analyzed");
      createDoc("Sam", "Lucene field boost and query time boost example");
      createDoc("Jack", "How to do Lucene search highlight example");
      createDoc("Smith", "Lucene BooleanQuery is deprecated as of 5.3.0");
      createDoc("Smith", "What is term vector in Lucene");
      
      indexWriter.close();
    } catch (IOException e) {
      System.out.println("Exception: " + e.getLocalizedMessage());
    }
  }

  private void createDoc(String author, String title) throws IOException {
    Document d = new Document();
    d.add(new TextField("author", author, Field.Store.YES));
    d.add(new TextField("title", title, Field.Store.YES));
    
    indexWriter.addDocument(d);
  }

  private String searchIndexAndDisplayResults(Query query) {
    StringBuilder sB = new StringBuilder();
    try
    {
      IndexReader   idxReader   = DirectoryReader.open(ramDirectory);
      IndexSearcher idxSearcher = new IndexSearcher(idxReader);
      TopDocs       docs        = idxSearcher.search(query, 10);
      
      sB.append("length of top docs: ").append(docs.scoreDocs.length).append("\n");
      System.out.println("length of top docs: " + docs.scoreDocs.length);
      for (ScoreDoc doc : docs.scoreDocs) {
        Document thisDoc = idxSearcher.doc(doc.doc);
        sB.append(doc.doc).append("\t").append(thisDoc.get("author")).append("\t").append(thisDoc.get("title")).append("\n");
        System.out.println(doc.doc + "\t" + thisDoc.get("author") + "\t" + thisDoc.get("title"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sB.toString();
  }
  
  String searchSingleTerm(String field, String termText) {
    Term term = new Term(field, termText);
    TermQuery termQuery = new TermQuery(term);
    return searchIndexAndDisplayResults(termQuery);
  }
  
  String searchBooleanQuery() {
    TermQuery query = new TermQuery(new Term("title", "lucene"));
    TermQuery query2 = new TermQuery(new Term("author", "sam"));
    BooleanQuery booleanQuery = new BooleanQuery();
    booleanQuery.add(query2, Occur.MUST);
    booleanQuery.add(query, Occur.SHOULD);
    return searchIndexAndDisplayResults(booleanQuery);
  }
  
  String searchQueryParser(String query) {
    QueryParser parser = new QueryParser("title", analyzer);
    Query parsedQuery = null;
    try {
      parsedQuery = parser.parse(query);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    
    return searchIndexAndDisplayResults(parsedQuery);
  }
}
