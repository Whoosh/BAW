package logic.parsers.pp_163.impl;

import javafx.util.Pair;
import logic.parsers.pp_163.services.AlbumsParserService;
import logic.query.exceptions.EmptySourceDetectException;
import logic.parsers.pp_163.types.PrimaryInfoByThemeAlbumParser;
import logic.parsers.pp_163.utils.PrimaryInfoAlbumParser;
import logic.query.services.WebPageService;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import logic.parsers.pp_163.entity.Album;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.LogManager.*;

@Service
public abstract class AlbumsParserImpl implements AlbumsParserService {

    private static final int ALWAYS_ONE_ELEMENT = 0;
    private static final String ONE_OF_CHARACTERISTIC_NAME = "拍摄时间：";
    private static final String NO_CHARACTERISTICS = "这张相片没有";

    private static final Logger LOGGER = getLogger();

    @Autowired
    private ThreadPoolTaskExecutor queryHelper;

    @Autowired
    private PrimaryInfoByThemeAlbumParser albumByThemeParsingRule;

    @Autowired
    private WebPageService webPageService;

    @Override
    public List<Album> getAlbumsByThemeSource(String url) throws EmptySourceDetectException {
        return getAlbums(url, albumByThemeParsingRule);
    }

    private List<Album> getAlbums(String url, PrimaryInfoAlbumParser albumsParser) throws EmptySourceDetectException {
        final Document response = webPageService.makeQueryWithJSResult(url);
        final Elements albumsBody = response.body().getElementsByAttributeValue("class", "pss clearfix js-alist");
        final List<Album> albums = albumsBody
                .get(ALWAYS_ONE_ELEMENT)
                .getElementsByTag("li")
                .stream()
                .map(info -> makeAlbumBy(info, albumsParser))
                .collect(Collectors.toList());
        return loadAlbumsExtendedInfo(albums);
    }

    private List<Album> loadAlbumsExtendedInfo(List<Album> albums) {
        final ArrayDeque<Pair<Album, Future<Album>>> loadTasks = new ArrayDeque<>(albums
                .stream()
                .map(album -> new Pair<>(album, queryHelper.submit(() -> runLoadExtendedInfoFor(album))))
                .collect(Collectors.toList()));
        while (!loadTasks.isEmpty()) {
            handleExtendedInfoTask(loadTasks);
        }
        return albums;
    }

    private void handleExtendedInfoTask(ArrayDeque<Pair<Album, Future<Album>>> loadTasks) {
        final Pair<Album, Future<Album>> task = loadTasks.pollFirst();
        final Album key = task.getKey();
        try {
            task.getValue().get();
        } catch (Exception ex) {
            LOGGER.trace("Corrupted data in extended album info loader function, caused by \n" + ex + "\n. reload...");
            loadTasks.addLast(new Pair<>(key, queryHelper.submit(() -> runLoadExtendedInfoFor(key))));
        }
    }

    private Album runLoadExtendedInfoFor(Album album) throws EmptySourceDetectException {
        return album.loadExtendedInfo(webPageService.makeQueryWithJSResult(
                album.getURL(),
                c -> !(c.containsAny(NO_CHARACTERISTICS, ONE_OF_CHARACTERISTIC_NAME))));
    }

    protected abstract Album makeAlbumBy(Element info, PrimaryInfoAlbumParser primaryInfoAlbumParser);
}
