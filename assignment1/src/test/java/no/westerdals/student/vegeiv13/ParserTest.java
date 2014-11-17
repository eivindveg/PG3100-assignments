package no.westerdals.student.vegeiv13;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test cases for Parser class in Familiar project.
 * 
 * @author Charlie Greenbacker
 * @author <a href="mailto:charlieg@cis.udel.edu">charlieg@cis.udel.edu</a>
 * @author <a
 *         href="http://www.cis.udel.edu/~charlieg">www.cis.udel.edu/~charlieg
 *         </a>
 * @version 1.0, &nbsp; 08 Sept 2009
 *
 * @author Eivind Vegsundvåg
 * @author <a href="mailto:vegeiv13@student.westerdals.no">vegeiv13@student.westerdals.no</a>
 * @version 1.2
 *
 */
public class ParserTest {

	private String output; // common variable used across individual tests

    @Test
    public void testConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor = Parser.class.getConstructor((Class<?>[]) null);
        int modifiers = constructor.getModifiers();

        // Assert that the constructor is private, so we can catch this and rewrite this test if the constructor
        // needs a proper test.
        assertTrue(Modifier.isPrivate(modifiers));

        // Make accessible so we can instantiate a parser.
        constructor.setAccessible(true);

        // Instantiate, test fails if instantiation fails(Like if an actual constructor
        // that does take parameters is implemented)
        constructor.newInstance((Class<?>[]) null);
    }

	@Test
	public void testParseEvent() {
		output = Parser.parseEvent("born JOHN_A_SMITH 18851105");
		assertEquals("complete single-participant event",
				"EVENT: born; PARTICIPANTS: John A Smith; DATE: Nov 5 1885",
				output);
		output = Parser
				.parseEvent("married JOHN_A_SMITH & MARY_E_JONES 19090623");
		assertEquals(
				"complete multi-participant event",
				"EVENT: married; PARTICIPANTS: John A Smith, Mary E Jones; DATE: Jun 23 1909",
				output);

		// handling bad input
		output = Parser.parseEvent(" ");
		assertEquals("empty input string",
				"EVENT: unknown; PARTICIPANTS: none; DATE: unknown", output);
		output = Parser.parseEvent(null);
		assertEquals("null input",
				"EVENT: unknown; PARTICIPANTS: none; DATE: unknown", output);
		output = Parser.parseEvent("Born john_smith 8/10/1965");
		assertEquals("incorrect input format",
				"EVENT: unknown; PARTICIPANTS: none; DATE: unknown", output);
	}

	@Test
	public void testParseEventType() {
		output = Parser.parseEventType("born JOHN_A_SMITH 18851105");
		assertEquals("basic event type", "born", output);

		// handling bad input
		output = Parser
				.parseEventType("born married JOHN_A_SMITH & MARY_E_JONES 19090623");
		assertEquals("multiple event types", "born", output);
		output = Parser
				.parseEventType("born-married JOHN_A_SMITH & MARY_E_JONES 19090623");
		assertEquals("mal-formed event type", "unknown", output);
		output = Parser.parseEventType("JOHN_A_SMITH & MARY_E_JONES 19090623");
		assertEquals("no event type", "unknown", output);
		output = Parser.parseEventType(null);
		assertEquals("null input", "unknown", output);
	}

	@Test
	public void testParseNames() {
		output = Parser.parseNames("born JOHN_A_SMITH 18851105");
		assertEquals("complete single-participant event", "John A Smith",
				output);
		output = Parser
				.parseNames("married JOHN_A_SMITH & MARY_E_JONES 19090623");
		assertEquals("complete multi-participant event",
				"John A Smith, Mary E Jones", output);

		// handling bad input
		output = Parser
				.parseNames("born JOHN_A_SMITH & JACK_A_SMITH & JOE_A_SMITH 19090623");
		assertEquals("triple-participant event", // doesn't make much sense, but
													// it's handled
				"John A Smith, Jack A Smith, Joe A Smith", output);
		output = Parser.parseNames("born 19090623");
		assertEquals("zero-participant event", "none", output);
		output = Parser.parseNames("born john_smith 19090623");
		assertEquals("mal-formed name", "none", output);
		output = Parser.parseNames(null);
		assertEquals("null input", "none", output);
	}

	@Test
	public void testParseDate() {
		output = Parser.parseDate("born JOHN_A_SMITH 18851105");
		assertEquals("basic date", "Nov 5 1885", output);
		output = Parser.parseDate("born JOHN_A_SMITH 98761231");
		assertEquals("super future date", "Dec 31 9876", output);

		// handling bad input
		output = Parser.parseDate("born JOHN_A_SMITH");
		assertEquals("no date", "unknown", output);
		output = Parser.parseDate("born JOHN_A_SMITH 1909AB05");
		assertEquals("mal-formed date", "unknown", output);
		output = Parser.parseDate("born JOHN_A_SMITH 19091105 19110401");
		assertEquals("multiple dates", "Nov 5 1909", output);
		output = Parser.parseDate("born JOHN_A_SMITH 1911 19091105");
		assertEquals("multiple dates, incomplete one first", "unknown", output);
		output = Parser.parseDate(null);
		assertEquals("null input", "unknown", output);
	}

	@Test
	public void testCapitalizeFirst() {
        String input = "1111 testString";
		String expected = "1111 TestString";

        output = Parser.capitalizeFirst(null);
        assertEquals(output, "");

        output = Parser.capitalizeFirst(input);
        assertEquals(output, expected);
	}

    @Test
    public void testParseEvents() {
        List<String> inputList;
        List<String> outputList;
        // Set up some input for the parser, as well as a single control input String
        String input = "born JOHN_A_SMITH 18851105";
        inputList = Arrays.asList(input, "married JOHN_A_SMITH & MARY_E_JONES 19090623", " ");

        // Receive the output
        String expected = Parser.parseEvent(input);
        outputList = Parser.parseEvents(inputList);

        // Check size:
        assertEquals("list parsed", inputList.size(), outputList.size());

        // Assume that if one is parsed, all are parsed. Perform standard parse check for first:
        assertEquals("complete single-participant event",
                expected,
                outputList.get(0));
    }

}