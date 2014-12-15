package utils.tests;

import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import java.util.Iterator;

import static org.junit.Assert.*;
import static utils.MethodInfo.*;

public class MethodInfoTest {
    @Test
    public void methodNameTest() {
        assertEquals("methodNameTest", getCurrentMethodName());
    }
}
