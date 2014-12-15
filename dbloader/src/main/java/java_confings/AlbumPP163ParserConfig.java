package java_confings;

import logic.parsers.methods.markers.PrimaryInfoAlbumParser;
import logic.parsers.types.AlbumsPP163Parser;
import logic.parsers.types.ExtendedAlbumInfoParser;
import logic.pp163.working_entity.Album;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class AlbumPP163ParserConfig {

    @Autowired
    private ExtendedAlbumInfoParser extendedAlbumInfoParser;

    @Bean
    @Scope("prototype")
    public Album album(Element info, PrimaryInfoAlbumParser primaryInfoAlbumParser) {
        Album album = new Album();
        album.setExtendedAlbumInfoParser(extendedAlbumInfoParser);
        return album.fillData(info, primaryInfoAlbumParser);
    }

    @Bean
    public AlbumsPP163Parser pp163Parser() {
        return new AlbumsPP163Parser() {
            @Override
            protected Album makeAlbumBy(Element info, PrimaryInfoAlbumParser primaryInfoAlbumParser) {
                return album(info, primaryInfoAlbumParser);
            }
        };
    }
}
