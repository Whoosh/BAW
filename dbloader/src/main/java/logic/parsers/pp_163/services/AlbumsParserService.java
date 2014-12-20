package logic.parsers.pp_163.services;

import logic.query.exceptions.EmptySourceDetectException;
import logic.parsers.pp_163.entity.Album;

import java.util.List;

public interface AlbumsParserService {
    List<Album> getAlbumsByThemeSource(String url) throws EmptySourceDetectException;
}
