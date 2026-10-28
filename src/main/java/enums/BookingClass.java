package enums;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlEnumValue;

public enum BookingClass {
    @XmlEnumValue("Economy")
    ECONOMY,
    @XmlEnumValue("Premium Economy")
    PREMIUM_ECONOMY,
    @XmlEnumValue("Business")
    BUSINESS,
    @XmlEnumValue("First")
    FIRST,
}
