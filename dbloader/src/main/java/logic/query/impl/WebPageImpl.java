package logic.query.impl;

import logic.query.entity.Communicator;
import logic.query.exceptions.EmptySourceDetectException;
import logic.query.services.InactiveJSCountOnPageResolverService;
import logic.query.services.WebPageService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.function.Predicate;

public class WebPageImpl implements WebPageService {

    @Autowired
    private InactiveJSCountOnPageResolverService jsResolverService;

    private long jsLoadSleepMs;

    @Override
    public Document makeQueryWithJSResult(String url, Predicate<Communicator> queryWaiter) throws EmptySourceDetectException {
        final Communicator com = new Communicator(url);
        final Predicate<Communicator> defaultJSCountPredicate = c -> c.getJobCount() > jsResolverService.getCount(url);
        while (defaultJSCountPredicate.test(com) && queryWaiter.test(com)) com.waitForJobs(jsLoadSleepMs);
        return Jsoup.parse(com.asXml());
    }

    @Override
    public Document makeQueryWithJSResult(String url) throws EmptySourceDetectException {
        return makeQueryWithJSResult(url, c -> true);
    }

    @Override
    public Document makeQueryWithoutJS(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public void setJsLoadSleepMs(long jsLoadSleepMs) {
        this.jsLoadSleepMs = jsLoadSleepMs;
    }
}
