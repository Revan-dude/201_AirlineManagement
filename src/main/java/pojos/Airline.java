package pojos;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "airline")

public class Airline {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAnyAttribute
    private Map<QName, String> extraAttributes = new HashMap<>();

    @XmlElementWrapper(name = "passengers")
    @XmlElement(name = "passenger")
    private List<Passenger> passengers;

    @XmlElementWrapper(name = "bookings")
    @XmlElement(name = "booking")
    private List<Booking> bookings;

    @XmlElementWrapper(name = "flights")
    @XmlElement(name = "flight")
    private Set<Flight> flights;

    @XmlElementWrapper(name = "airports")
    @XmlElement(name = "airport")
    private Set<Airport> airports;

    @XmlElementWrapper(name = "aircraft")
    @XmlElement(name = "plane")
    private Set<Aircraft> aircraft;
}
