package myPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SearchQueryParserTest {
  static TermQueryExample termQueryExample = new TermQueryExample();

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    termQueryExample.createIndex();
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {
    termQueryExample.closeDirectory();
  }

  @BeforeEach
  void setUp() throws Exception {
  }

  @AfterEach
  void tearDown() throws Exception {
  }

  @Test
  @DisplayName("parsed Query \"lucene term vector\" should return docs 4, 3, 0, 1 and 2")
  void test_lucene_term_vector() {
    String expected =
    "length of top docs: 5\n" +
    "4\tSmith\tWhat is term vector in Lucene\n" +
    "3\tSmith\tLucene BooleanQuery is deprecated as of 5.3.0\n" +
    "0\tSam\tLucene index option analyzed vs not analyzed\n" +
    "1\tSam\tLucene field boost and query time boost example\n" +
    "2\tJack\tHow to do Lucene search highlight example\n";
    
    String actual = termQueryExample.searchQueryParser("lucene term vector");
    
    Assertions.assertEquals(expected, actual);
  }

  @Test
  @DisplayName("parsed Query \"lucene OR example\" should return docs 1, 2, 3, 4 and 0")
  void test_lucene_OR_example() {
    String expected =
    "length of top docs: 5\n" +
    "1\tSam\tLucene field boost and query time boost example\n" +
    "2\tJack\tHow to do Lucene search highlight example\n" +
    "3\tSmith\tLucene BooleanQuery is deprecated as of 5.3.0\n" +
    "4\tSmith\tWhat is term vector in Lucene\n" +
    "0\tSam\tLucene index option analyzed vs not analyzed\n";
    
    String actual = termQueryExample.searchQueryParser("lucene OR example");
    
    Assertions.assertEquals(expected, actual);
  }

  @Test
  @DisplayName("parsed Query \"lucene AND example AND author:sam\" should return doc 1")
  void test_lucene_AND_example_AND_author_sam() {
    String expected =
    "length of top docs: 1\n" +
    "1\tSam\tLucene field boost and query time boost example\n";
    
    String actual = termQueryExample.searchQueryParser("lucene AND example AND author:sam");
    
    Assertions.assertEquals(expected, actual);
  }

}
