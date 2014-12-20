package services.pp_163_parser;

import java_configs.AlbumPP163ParserServiceConfig;
import logic.parsers.pp_163.impl.AlbumsParserImpl;
import logic.parsers.pp_163.services.AlbumsParserService;
import logic.parsers.pp_163.entity.Album;
import logic.parsers.pp_163.entity.Photo;
import logic.parsers.pp_163.entity.PhotoShootingCharacteristic;
import logic.query.exceptions.EmptySourceDetectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static test_utils.MethodInfo.getCurrentMethodName;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath*:context/test_config.groovy")
public class ParseAlbumsByThemeTest {

    public static final String ALBUMS_BY_THEME_URL = "http://pp.163.com/pp/#p=11&c=-1&m=3&page=";

    @Autowired
    private AlbumsParserService albumsParserService;

    @Autowired
    private ThreadPoolTaskExecutor exe;

    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void sequenceTest() throws EmptySourceDetectException {
        for (int i = 25; i <= 100; i++) {
            LOGGER.trace("Load page with albums - " + ALBUMS_BY_THEME_URL + i);
            checkData(ALBUMS_BY_THEME_URL + i);
        }
        LOGGER.trace("End - " + getCurrentMethodName());
    }

   // @Test
    public void parallelTest() throws Exception {
        LOGGER.trace("Start - " + getCurrentMethodName());
        final int count = 10;
        final AtomicInteger indexer = new AtomicInteger();
        final List<Future<Boolean>> futures = new ArrayList<>();
        for (int i = 0; i < count; i++)
            futures.add(exe.submit((() -> checkData(ALBUMS_BY_THEME_URL + indexer.incrementAndGet()))));
        for (Future<Boolean> future : futures)
            assertTrue(future.get());
        LOGGER.trace("End - " + getCurrentMethodName());
    }

    private boolean checkData(String url) throws EmptySourceDetectException {
        List<Album> albumsByThemeSource = albumsParserService.getAlbumsByThemeSource(url);
        assertEquals(20, albumsByThemeSource.size());
        for (Album album : albumsByThemeSource) {
            assertTrue(album.getCreatingTime().isAfter(LocalDateTime.MIN));
            assertTrue(!album.getPhotos().isEmpty());
            assertNotNull(album.getOwner());
            assertNotNull(album.getTitle());
            assertTrue(album.getID() > 0);
            for (Photo photo : album.getPhotos()) {
                assertNotNull(photo.getPhoto());
                assertNotNull(photo.getPhotoShootingCharacteristic());
                final PhotoShootingCharacteristic photoShootingCharacteristic = photo.getPhotoShootingCharacteristic();
                if (!photoShootingCharacteristic.isEmpty()) {
                    assertNotNull(photoShootingCharacteristic.getExposureCompensation());
                    assertNotNull(photoShootingCharacteristic.getAperture());
                    assertNotNull(photoShootingCharacteristic.getBrand());
                    assertNotNull(photoShootingCharacteristic.getFocalLength());
                    assertNotNull(photoShootingCharacteristic.getLens());
                    assertNotNull(photoShootingCharacteristic.getShutterSpeed());
                    assertNotNull(photoShootingCharacteristic.getModel());
                    assertTrue(photoShootingCharacteristic.getTakenTime().isAfter(LocalDateTime.MIN) ||
                            photoShootingCharacteristic.getTakenTime().isEqual(LocalDateTime.MIN));
                } else {
                    assertNull(photoShootingCharacteristic.getExposureCompensation());
                }
            }
        }
        return true;
    }
}
