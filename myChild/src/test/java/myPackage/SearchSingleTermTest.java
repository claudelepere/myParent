package myPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
  void test() {
    System.out.println("=====================================================SearchSingleTermTest start");
    StringBuilder sB = new StringBuilder();
    sB.append(termQueryExample.searchSingleTerm("title", "lucene"));
    sB.append(termQueryExample.searchSingleTerm("title", "example"));
    sB.append(termQueryExample.searchSingleTerm("title", "Example"));
    sB.append(termQueryExample.searchSingleTerm("title", "lucene example"));
    System.out.println("=====================================================SearchSingleTermTest end\n");
    
    String expected =
    "length of top docs: 5\n" +
    "3\tSmith\tLucene BooleanQuery is deprecated as of 5.3.0\n" +
    "4\tSmith\tWhat is term vector in Lucene\n" +
    "0\tSam\tLucene index option analyzed vs not analyzed\n" +
    "1\tSam\tLucene field boost and query time boost example\n" +
    "2\tJack\tHow to do Lucene search highlight example\n" +
    "length of top docs: 2\n" +
    "1\tSam\tLucene field boost and query time boost example\n" +
    "2\tJack\tHow to do Lucene search highlight example\n" +
    "length of top docs: 0\n" +
    "length of top docs: 0\n";

    String actual = sB.toString();
    
    Assertions.assertEquals(expected, actual);
  }

}
