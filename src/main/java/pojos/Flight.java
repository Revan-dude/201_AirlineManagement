package pojos;

import enums.FlightStatus;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)

public class Flight {

    @XmlAttribute(name = "number")
    @XmlID
    private String number;

    @XmlJavaTypeAdapter(adapters.LocalDateTimeAdapter.class)
    private LocalDateTime departure;

    @XmlJavaTypeAdapter(adapters.LocalDateTimeAdapter.class)
    private LocalDateTime arrival;

    private FlightStatus status;

    @XmlIDREF
    private Airport destination;

    @XmlIDREF
    private Airport origin;

    @XmlIDREF
    private Aircraft aircraft;

    @XmlTransient
    private double occupancy;
}
