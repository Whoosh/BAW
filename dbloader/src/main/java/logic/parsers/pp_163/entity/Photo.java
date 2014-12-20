package logic.parsers.pp_163.entity;

import logic.parsers.pp_163.types.PhotoInfoParser;
import logic.query.exceptions.LoadPhotoException;
import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;

public class Photo {

    private final static String ERROR_URL = "failpp.gif";
    private final static String SNIFFER_PNG = "sniff.png";
    private final static String HOST_ID_DELIMITER = "==/";

    private final PhotoShootingCharacteristic photoShootingCharacteristic;

    private BufferedImage photo;

    private String url;

    public Photo(Element element, PhotoInfoParser photoInfoParser) throws LoadPhotoException {
        photoShootingCharacteristic = new PhotoShootingCharacteristic(element, photoInfoParser);
        url = photoInfoParser.parseURL(element);
        if (url.contains(ERROR_URL) || url.contains(SNIFFER_PNG)) {
            url = photoInfoParser.parseLazyURL(element);
            if (!url.contains(HOST_ID_DELIMITER))
                throw new LoadPhotoException(" \n No URL On Source - " + element, getClass());
        }
        try {
            photo = ImageIO.read(new URL(getUrl()));
        } catch (MalformedURLException e) {
            throw new LoadPhotoException(e + " \n URL Format error - " + url, getClass());
        } catch (IOException e) {
            throw new LoadPhotoException(e + " \n Error load - " + url, getClass());
        }
    }

    public BufferedImage getPhoto() {
        return photo;
    }

    public PhotoShootingCharacteristic getPhotoShootingCharacteristic() {
        return photoShootingCharacteristic;
    }

    public String getUrl() {
        return url;
    }
}
