package pojos;

import enums.BookingClass;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)

public class Booking {
    @XmlAttribute(name = "number")
    @XmlID
    private String number;

    private String seat;

    @XmlJavaTypeAdapter(adapters.BigDecimalAdapter.class)
    private BigDecimal price;

    @XmlElement(name = "class")
    private BookingClass bookingClass;

    @XmlIDREF
    private Passenger passenger;

    @XmlIDREF
    private Flight flight;
}
