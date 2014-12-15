package logic.query;

import logic.query.exceptions.EmptySourceDetectException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.function.Predicate;

public class WebPageQueriesHandler {

    @Autowired
    private InactiveJSCountOnPageManager jsManager;

    private long jsLoadSleep;

    public Document makeQueryWithJSResult(String url, Predicate<Communicator> queryWaiter) throws EmptySourceDetectException {
        final Communicator com = new Communicator(url);
        final Predicate<Communicator> defaultJSCountPredicate = c -> c.getJobCount() > jsManager.getCount(url);
        while (defaultJSCountPredicate.test(com) && queryWaiter.test(com)) com.waitForJobs(jsLoadSleep);
        return Jsoup.parse(com.asXml());
    }

    public Document makeQueryWithJSResult(String url) throws EmptySourceDetectException {
        return makeQueryWithJSResult(url, c -> true);
    }

    public Document makeQueryWithoutJS(String url) throws IOException {
        return Jsoup.connect(url).execute().parse();
    }

    public void setJsLoadSleep(long jsLoadSleep) {
        this.jsLoadSleep = jsLoadSleep;
    }
}
