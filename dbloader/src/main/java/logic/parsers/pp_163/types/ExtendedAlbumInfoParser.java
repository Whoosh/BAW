package logic.parsers.pp_163.types;

import logic.utils.strings.OnStringFinder;
import logic.query.exceptions.LoadPhotoException;
import org.jsoup.nodes.Document;
import logic.parsers.pp_163.entity.Photo;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExtendedAlbumInfoParser implements OnStringFinder {

    @Autowired
    private PhotoInfoParser photoInfoParser;

    public Set<Photo> loadPhotos(Document deployedInfoAlbum) throws LoadPhotoException {
        final HashSet<Photo> photos = new HashSet<>();
        final List<Element> elements = deployedInfoAlbum
                .body()
                .getElementsByAttributeValue("class", "pic-area")
                .stream()
                .collect(Collectors.toList());
        for (Element element : elements) {
            photos.add(new Photo(element, photoInfoParser));
        }
        return photos;
    }

    public LocalDateTime parseCratingDateTime(String deployedInfoAlbum) {
        final String dateTime = firstBetween(deployedInfoAlbum, "发布于：", " <");
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    public int parseWatchCount(String deployedInfoAlbum) {
        return Integer.valueOf(firstBetween(deployedInfoAlbum, "viewcount\"> ", " "));
    }

    public int parseLikeCount(String deployedInfoAlbum) {
        return Integer.valueOf(firstBetween(deployedInfoAlbum, "likecount\"> ", " "));
    }

    public int parseCommentCount(String deployedInfoAlbum) {
        return Integer.valueOf(firstBetween(deployedInfoAlbum, "cmtcount\"> ", " "));
    }
}
