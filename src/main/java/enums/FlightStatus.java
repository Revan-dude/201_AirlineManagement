package enums;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlEnumValue;

public enum FlightStatus {
    @XmlEnumValue("Scheduled")
    SCHEDULED,
    @XmlEnumValue("Delayed")
    DELAYED,
    @XmlEnumValue("Cancelled")
    CANCELLED
}
