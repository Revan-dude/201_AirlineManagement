package pojos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Airport {

    @XmlAttribute(name = "code")
    @XmlID
    @EqualsAndHashCode.Include
    private String code;

    private String name;

    private String city;

    private String country;

    @Override
    public String toString() {
        return String.format("%s (%s)",
                code, city);
    }
}
