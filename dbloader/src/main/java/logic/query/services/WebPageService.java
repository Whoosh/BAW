package logic.query.services;

import logic.query.entity.Communicator;
import logic.query.exceptions.EmptySourceDetectException;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.function.Predicate;

public interface WebPageService {
    Document makeQueryWithJSResult(String url, Predicate<Communicator> queryWaiter) throws EmptySourceDetectException;

    Document makeQueryWithJSResult(String url) throws EmptySourceDetectException;

    Document makeQueryWithoutJS(String url) throws IOException;
}
