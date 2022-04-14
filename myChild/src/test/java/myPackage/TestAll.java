package myPackage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ SearchSingleTermTest.class, SearchBooleanQueryTest.class, SearchQueryParserTest.class })
//@SelectClasses({ SearchSingleTermTest.class })
//@SelectClasses({ SearchBooleanQueryTest.class })
//@SelectClasses({ SearchQueryParserTest.class })
public class TestAll {
}