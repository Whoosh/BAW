package test_utils.tests;

import org.junit.Test;

import static org.junit.Assert.*;
import static test_utils.MethodInfo.*;

public class MethodInfoTest {
    @Test
    public void methodNameTest() {
        assertEquals("methodNameTest", getCurrentMethodName());
    }
}
