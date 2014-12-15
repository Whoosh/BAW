package logic.query;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
import logic.query.exceptions.EmptySourceDetectException;

import java.io.IOException;
import java.util.Arrays;

public class Communicator {

    private final HtmlPage page;
    private final JavaScriptJobManager jobManager;

    public Communicator(String url) throws EmptySourceDetectException {
        try {
            final WebClient client = new WebClient();
            page = client.getPage(url);
            jobManager = page.getEnclosingWindow().getJobManager();
        } catch (IOException e) {
            throw new EmptySourceDetectException("Can't create web page, caused by " + e);
        }
    }

    public int getJobCount() {
        return jobManager.getJobCount();
    }

    public void waitForJobs(long m) {
        jobManager.waitForJobs(m);
    }

    public String asXml() {
        return page.asXml();
    }

    public boolean containsAny(String... args) {
        return Arrays.stream(args).filter(x -> page.toString().contains(x)).findFirst().isPresent();
    }
}
