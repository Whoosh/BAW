package java_configs;

import logic.parsers.pp_163.services.AlbumsParserService;
import logic.parsers.pp_163.utils.PrimaryInfoAlbumParser;
import logic.parsers.pp_163.impl.AlbumsParserImpl;
import logic.parsers.pp_163.types.ExtendedAlbumInfoParser;
import logic.parsers.pp_163.entity.Album;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class AlbumPP163ParserServiceConfig {

    @Autowired
    private ExtendedAlbumInfoParser extendedAlbumInfoParser;

    // FIXME он может быть бином (prototype), c груви конфигом, не работает как надо О_о.
    //@Bean
    //@Scope("prototype")
    public Album album(Element info, PrimaryInfoAlbumParser primaryInfoAlbumParser) {
        Album album = new Album();
        album.setExtendedAlbumInfoParser(extendedAlbumInfoParser);
        return album.fillData(info, primaryInfoAlbumParser);
    }

    @Bean
    public AlbumsParserService albumsParserService() {
        return new AlbumsParserImpl() {
            @Override
            protected Album makeAlbumBy(Element info, PrimaryInfoAlbumParser primaryInfoAlbumParser) {
                return album(info, primaryInfoAlbumParser);
            }
        };
    }
}
