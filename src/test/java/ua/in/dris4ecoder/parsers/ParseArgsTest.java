package ua.in.dris4ecoder.parsers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alex Korneyko on 17.05.2016.
 */
public class ParseArgsTest {

    @Test
    public void testPort() throws Exception {

        assertEquals(80, ParseArgs.port(new String[] {"p:80"}));
    }
}