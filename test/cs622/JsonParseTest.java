package cs622;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class JsonParseTest {

	@Test
	void test() {

		TestClass testClass = new TestClass();

		assertTrue(testClass.testReturn() == 1);
	}

}
