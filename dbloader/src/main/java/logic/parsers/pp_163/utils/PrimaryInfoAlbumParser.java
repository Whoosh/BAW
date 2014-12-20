package logic.parsers.pp_163.utils;

import org.jsoup.nodes.Element;

public interface PrimaryInfoAlbumParser {

    String parseTitle(Element element);

    String parseOwner(Element element);

    Long parseID(Element element);

    String parseURL(Element element);
}
