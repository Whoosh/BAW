package logic.parsers.types;

import logic.parsers.methods.markers.OnStringFinder;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhotoInfoParser implements OnStringFinder {

    @Autowired
    private ShootingCharacteristicParser shootingCharacteristicParser;

    public String parseURL(Element element) {
        return firstBetween(element, "src=\"", "\" ");
    }

    public ShootingCharacteristicParser getShootingCharacteristicParser() {
        return shootingCharacteristicParser;
    }

    public String parseLazyURL(Element element) {
        return firstBetween(element, "\" data-lazyload-src=\"", "\"");
    }
}
