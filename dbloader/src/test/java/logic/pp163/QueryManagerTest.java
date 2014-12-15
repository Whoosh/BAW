package logic.pp163;

import logic.query.exceptions.EmptySourceDetectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import logic.query.WebPageQueriesHandler;

import java.io.IOException;

import static junit.framework.Assert.*;
import static utils.MethodInfo.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:tests_context.xml")
public class QueryManagerTest {

    public static final String URL = "http://pp.163.com/john650413/pp/13907123.html";

    @Autowired
    private WebPageQueriesHandler queryManager;

    private static final Logger LOGGER = LogManager.getLogger();

    @Test(timeout = 60 * 2000)
    public void query() throws IOException {
        LOGGER.trace("Start query in - " + getCurrentMethodName());
        Document result = queryManager.makeQueryWithJSResult(URL);
        LOGGER.trace("End query in - " + getCurrentMethodName());
        assertNotNull(result);
        assertTrue(result.body().toString().contains("body"));
        LOGGER.trace("End - " + getCurrentMethodName());
    }

    @Test(timeout = 60 * 1000)
    public void queryWithPredicate() throws EmptySourceDetectException {
        LOGGER.trace("Start - " + getCurrentMethodName());
        assertNotNull(queryManager.makeQueryWithJSResult(URL, c -> c.containsAny("品牌：")));
        LOGGER.trace("End - " + getCurrentMethodName());
    }

    @Test
    public void queryWithoutJSRunning() throws IOException {
        assertNotNull(queryManager.makeQueryWithoutJS(URL));
    }

    @Test
    public void manyQueries() throws IOException {
        for (int i = 0; i < 10; i++) {
            assertNotNull(queryManager.makeQueryWithJSResult(URL));
        }
    }
}
