package pojos;

import adapters.LocalDateAdapter;
import adapters.PreferencesAdapter;
import jakarta.xml.bind.Element;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.namespace.QName;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)

public class Passenger {

    @XmlAttribute(name = "id")
    @XmlID
    private String id;

    private String firstName;

    private String lastName;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthDate;

    @XmlJavaTypeAdapter(PreferencesAdapter.class)
    private List<String> preferences = new ArrayList<>();

    @XmlAnyAttribute
    private Map<QName, String> extraAttributes = new HashMap<>();

    @XmlAnyElement
    private List<Element> extraElements;

    @Override
    public String toString() {
        return String.format("%s %s (%s)",
                firstName, lastName, id);
    }
}
