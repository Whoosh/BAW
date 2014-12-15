package logic.pp163;

import logic.parsers.types.AlbumsPP163Parser;
import logic.pp163.working_entity.Photo;
import logic.pp163.working_entity.PhotoShootingCharacteristic;
import logic.query.exceptions.EmptySourceDetectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import logic.pp163.working_entity.Album;
import utils.MethodInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static utils.MethodInfo.getCurrentMethodName;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:tests_context.xml")
public class ParseAlbumsByThemeTest {

    public static final String ALBUMS_BY_THEME_URL = "http://pp.163.com/pp/#p=11&c=-1&m=3&page=";

    @Autowired
    private AlbumsPP163Parser albumsPP163Parser;

    @Autowired
    private ThreadPoolTaskExecutor exe;

    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void solo() throws EmptySourceDetectException {
        for (int i = 25; i <= 100; i++) {
            LOGGER.trace("Load page with albums - " + ALBUMS_BY_THEME_URL + i);
            checkData(ALBUMS_BY_THEME_URL + i);
        }
        LOGGER.trace("End - " + getCurrentMethodName());
    }

    @Test
    public void parseAlbumsFullInfoByThemeTest() throws Exception {
        LOGGER.trace("Start - "+ getCurrentMethodName());
        final int count = 10;
        final AtomicInteger indexer = new AtomicInteger();
        final List<Future<Boolean>> futures = new ArrayList<>();
        for (int i = 0; i < count; i++)
            futures.add(exe.submit((() -> checkData(ALBUMS_BY_THEME_URL + indexer.incrementAndGet()))));
        for (Future<Boolean> future : futures)
            assertTrue(future.get());
        LOGGER.trace("End - "+getCurrentMethodName());
    }

    private boolean checkData(String url) throws EmptySourceDetectException {
        List<Album> albumsByThemeSource = albumsPP163Parser.getAlbumsByThemeSource(url);
        assertEquals(20, albumsByThemeSource.size());
        for (Album album : albumsByThemeSource) {
            assertTrue(album.getCreatingTime().toString().length() > 5);
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
