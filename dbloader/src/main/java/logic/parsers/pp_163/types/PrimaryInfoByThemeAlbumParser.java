package logic.parsers.pp_163.types;

import logic.parsers.pp_163.utils.PrimaryInfoAlbumParser;
import logic.utils.strings.OnStringFinder;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class PrimaryInfoByThemeAlbumParser implements PrimaryInfoAlbumParser, OnStringFinder {

    @Override
    public String parseTitle(Element element) {
        return firstBetween(element, "title=\"", "\"");
    }

    @Override
    public String parseOwner(Element element) {
        return firstBetween(element, "http://pp.163.com/", "/pp/");
    }

    @Override
    public Long parseID(Element element) {
        return Long.valueOf(firstBetween(element, "/pp/", ".html"));
    }

    @Override
    public String parseURL(Element element) {
        return firstBetween(element, "href=\"", "\" title");
    }
}
