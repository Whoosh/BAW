package logic.pp163.working_entity;

import logic.parsers.types.PhotoInfoParser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import logic.parsers.types.ShootingCharacteristicParser;

import java.time.LocalDateTime;

public class PhotoShootingCharacteristic {

    private static final String TIME_CHARACTERISTIC = "拍摄时间：";

    private String brand;
    private String model;
    private String focalLength;
    private String aperture;
    private String shutterSpeed;
    private String ISO;
    private String exposureCompensation;
    private String lens;

    private LocalDateTime takenTime;

    private final ShootingCharacteristicParser shootingCharacteristicParser;
    private boolean empty;

    public PhotoShootingCharacteristic(Element element, PhotoInfoParser photoInfoParser) {
        shootingCharacteristicParser = photoInfoParser.getShootingCharacteristicParser();
        final String characteristic = element.toString();
        if (characteristic.contains(TIME_CHARACTERISTIC)) {
            parseData(characteristic);
        } else {
            empty = true;
        }
    }

    private void parseData(String characteristics) {
        brand = shootingCharacteristicParser.parseBrand(characteristics);
        model = shootingCharacteristicParser.parseModel(characteristics);
        focalLength = shootingCharacteristicParser.parseFocalLength(characteristics);
        aperture = shootingCharacteristicParser.parseAperture(characteristics);
        shutterSpeed = shootingCharacteristicParser.parseShutterSpeed(characteristics);
        exposureCompensation = shootingCharacteristicParser.parseExposureCompensation(characteristics);
        takenTime = shootingCharacteristicParser.parseTimeTaken(characteristics);
        lens = shootingCharacteristicParser.parseLens(characteristics);
        ISO = shootingCharacteristicParser.parseISO(characteristics);
    }

    public boolean isEmpty() {
        return empty;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public String getAperture() {
        return aperture;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public String getExposureCompensation() {
        return exposureCompensation;
    }

    public String getLens() {
        return lens;
    }

    public LocalDateTime getTakenTime() {
        return takenTime;
    }

    public String getISO() {
        return ISO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhotoShootingCharacteristic that = (PhotoShootingCharacteristic) o;

        if (empty != that.empty) return false;
        if (ISO != null ? !ISO.equals(that.ISO) : that.ISO != null) return false;
        if (aperture != null ? !aperture.equals(that.aperture) : that.aperture != null) return false;
        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;
        if (exposureCompensation != null ? !exposureCompensation.equals(that.exposureCompensation) : that.exposureCompensation != null)
            return false;
        if (focalLength != null ? !focalLength.equals(that.focalLength) : that.focalLength != null) return false;
        if (lens != null ? !lens.equals(that.lens) : that.lens != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (shootingCharacteristicParser != null ? !shootingCharacteristicParser.equals(that.shootingCharacteristicParser) : that.shootingCharacteristicParser != null)
            return false;
        if (shutterSpeed != null ? !shutterSpeed.equals(that.shutterSpeed) : that.shutterSpeed != null) return false;
        if (takenTime != null ? !takenTime.equals(that.takenTime) : that.takenTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = brand != null ? brand.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (focalLength != null ? focalLength.hashCode() : 0);
        result = 31 * result + (aperture != null ? aperture.hashCode() : 0);
        result = 31 * result + (shutterSpeed != null ? shutterSpeed.hashCode() : 0);
        result = 31 * result + (ISO != null ? ISO.hashCode() : 0);
        result = 31 * result + (exposureCompensation != null ? exposureCompensation.hashCode() : 0);
        result = 31 * result + (lens != null ? lens.hashCode() : 0);
        result = 31 * result + (takenTime != null ? takenTime.hashCode() : 0);
        result = 31 * result + (shootingCharacteristicParser != null ? shootingCharacteristicParser.hashCode() : 0);
        result = 31 * result + (empty ? 1 : 0);
        return result;
    }
}
