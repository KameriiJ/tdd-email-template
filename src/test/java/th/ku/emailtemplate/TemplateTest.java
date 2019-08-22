package th.ku.emailtemplate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;

public class TemplateTest {
	
	private Template template;
	
	@BeforeEach
	void setup() {
	template = new Template("${one}, ${two}, ${three}");
	template.set("one", "1");
	template.set("two", "2");
	template.set("three", "3");
	}
    
    @Test
	void testEvaluateOneVariable() {
		Template template = new Template("Hello, ${name}");
		template.set("name", "Reader");
		assertEquals("Hello, Reader", template.evaluate());
	}
    
    @Test
    void testEvaluateDifferentValue() {
	    Template template = new Template("Hi, ${name}");
	    template.set("name", "someone else");
	    assertEquals("Hi, someone else", template.evaluate());
    }

    @Test
	void testEvalueMultipleVariables() {
		assertTemplateEvaluatesTo("1, 2, 3");
	}
	
	@Test
	void testEvaluateUnknownVariablesAreIgnored() {
		template.set("doesnotexist", "whatever");
		assertTemplateEvaluatesTo("1, 2, 3");
	}
	
	private void assertTemplateEvaluatesTo(String expected) {
		assertEquals(expected, template.evaluate());
	}
	
	@Test
	public void testEvaluateMissingValueRaisesException() {
		Throwable exception = assertThrows(MissingValueException.class,() -> { new Template("${foo}").evaluate(); });
		assertEquals("No value for ${foo}", exception.getMessage());
	}
}
