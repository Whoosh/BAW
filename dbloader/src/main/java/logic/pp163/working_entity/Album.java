package logic.pp163.working_entity;

import logic.query.exceptions.LoadPhotoException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import logic.parsers.methods.markers.PrimaryInfoAlbumParser;
import logic.parsers.types.ExtendedAlbumInfoParser;

import java.time.LocalDateTime;
import java.util.Set;

public class Album {

    private long ID;
    private int watchCount;
    private int likeCount;
    private int commentCount;

    private String url;
    private String title;
    private String owner;

    private LocalDateTime creatingTime;
    private Set<Photo> photos;

    private ExtendedAlbumInfoParser extendedAlbumInfoParser;

    public Album fillData(Element element, PrimaryInfoAlbumParser primaryInfoAlbumParser) {
        title = primaryInfoAlbumParser.parseTitle(element);
        owner = primaryInfoAlbumParser.parseOwner(element);
        ID = primaryInfoAlbumParser.parseID(element);
        url = primaryInfoAlbumParser.parseURL(element);
        return this;
    }

    public Album loadExtendedInfo(Document deployedAlbumInfo) throws LoadPhotoException {
        final String asHTML = deployedAlbumInfo.outerHtml();
        photos = extendedAlbumInfoParser.loadPhotos(deployedAlbumInfo);
        creatingTime = extendedAlbumInfoParser.parseCratingDateTime(asHTML);
        watchCount = extendedAlbumInfoParser.parseWatchCount(asHTML);
        likeCount = extendedAlbumInfoParser.parseLikeCount(asHTML);
        commentCount = extendedAlbumInfoParser.parseCommentCount(asHTML);
        return this;
    }

    public void setExtendedAlbumInfoParser(ExtendedAlbumInfoParser extendedAlbumInfoParser) {
        this.extendedAlbumInfoParser = extendedAlbumInfoParser;
    }

    public String getTitle() {
        return title;
    }

    public String getOwner() {
        return owner;
    }

    public String getURL() {
        return url;
    }

    public int getWatchCount() {
        return watchCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public LocalDateTime getCreatingTime() {
        return creatingTime;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public long getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (ID != album.ID) return false;
        if (commentCount != album.commentCount) return false;
        if (likeCount != album.likeCount) return false;
        if (watchCount != album.watchCount) return false;
        if (creatingTime != null ? !creatingTime.equals(album.creatingTime) : album.creatingTime != null) return false;
        if (owner != null ? !owner.equals(album.owner) : album.owner != null) return false;
        if (photos != null ? !photos.equals(album.photos) : album.photos != null) return false;
        if (title != null ? !title.equals(album.title) : album.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (int) (ID ^ (ID >>> 32));
        return result;
    }
}
