package myPackage;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SearchBooleanQueryTest {
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
  @DisplayName("BooleanQuery \"+lucene sam\" should return docs 0 and 1")
  void test() {
    String expected = 
    "length of top docs: 2\n" +
    "0\tSam\tLucene index option analyzed vs not analyzed\n" +
    "1\tSam\tLucene field boost and query time boost example\n";

    String actual = termQueryExample.searchBooleanQuery();

    Assertions.assertEquals(expected, actual);
  }

}
