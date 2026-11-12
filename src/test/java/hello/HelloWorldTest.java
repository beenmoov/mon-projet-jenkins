package hello;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest {
    @Test
    public void testHelloWorld() {
        HelloWorld helloWorld = new HelloWorld();
        assertEquals("Hello World!", helloWorld.sayHello());
    }

    @Test
    public void testMax() {
        assertEquals(5, HelloWorld.max(3, 5));
        assertEquals(5, HelloWorld.max(5, 3));
        assertEquals(5, HelloWorld.max(5, 5));
    }
}