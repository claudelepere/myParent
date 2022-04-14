package myPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SearchSingleTermTest {
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
  @DisplayName("TermQuery \"lucene\" should return docs 3, 4, 0, 1 and 2")
  void test_lucene() {
    String expected =
    "length of top docs: 5\n" +
    "3\tSmith\tLucene BooleanQuery is deprecated as of 5.3.0\n" +
    "4\tSmith\tWhat is term vector in Lucene\n" +
    "0\tSam\tLucene index option analyzed vs not analyzed\n" +
    "1\tSam\tLucene field boost and query time boost example\n" +
    "2\tJack\tHow to do Lucene search highlight example\n";

    String actual = termQueryExample.searchSingleTerm("title", "lucene");
    
    Assertions.assertEquals(expected, actual);
  }

  @Test
  @DisplayName("TermQuery \"example\" should return docs 1 and 2")
  void test_example() {
    String expected =
    "length of top docs: 2\n" +
    "1\tSam\tLucene field boost and query time boost example\n" +
    "2\tJack\tHow to do Lucene search highlight example\n";

    String actual = termQueryExample.searchSingleTerm("title", "example");
    
    Assertions.assertEquals(expected, actual);
  }

  @Test
  @DisplayName("TermQuery \"Example\" should return 0 hit")
  void test_Example() {
    String expected =
    "length of top docs: 0\n";

    String actual = termQueryExample.searchSingleTerm("title", "Example");
    
    Assertions.assertEquals(expected, actual);
  }

  @Test
  @DisplayName("TermQuery \"Example\" should return 0 hit")
  void test_lucene_example() {
    String expected =
    "length of top docs: 0\n";

    String actual = termQueryExample.searchSingleTerm("title", "lucene example");
    
    Assertions.assertEquals(expected, actual);
  }

}
