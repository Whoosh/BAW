package test_utils.tests;

import logic.query.services.WebPageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:context/test_config.groovy"})
public class LoadGroovyContextTest {

    @Autowired
    private WebPageService webPageService;

    @Test
    public void testG() {
        assertNotNull(webPageService);
    }
}
