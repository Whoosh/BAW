package logic.parsers.types;

import logic.parsers.methods.markers.OnStringFinder;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class ShootingCharacteristicParser implements OnStringFinder {

    public String parseBrand(String element) {
        return byStart(element, "品牌： <b> ");
    }

    public String parseModel(String element) {
        return byStart(element, "型号： <b> ");
    }

    public String parseFocalLength(String element) {
        return byStart(element, "焦距： <b> ");
    }

    public String parseAperture(String element) {
        return byStart(element, "光圈： <b> ");
    }

    public String parseShutterSpeed(String element) {
        return byStart(element, "快门速度： <b> ");
    }

    public String parseExposureCompensation(String element) {
        return byStart(element, "曝光补偿： <b> ");
    }

    public LocalDateTime parseTimeTaken(String element) {
        try {
            final String dateTime = byStart(element, "拍摄时间： <b> ");
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        } catch (DateTimeParseException ex) {
            return LocalDateTime.MIN;
        }
    }

    public String parseLens(String element) {
        return byStart(element, "镜头： <b> ");
    }

    public String parseISO(String element) {
        return byStart(element, "ISO： <b> ");
    }

    private String byStart(String element, String start) {
        return firstBetween(element, start, " </b>");
    }
}
