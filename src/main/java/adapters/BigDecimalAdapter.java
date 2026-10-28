package adapters;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.math.BigDecimal;

public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {
    @Override
    public BigDecimal unmarshal(String string) throws Exception {
        return BigDecimal.valueOf(
                Double.parseDouble(
                        string.replaceFirst("€", " ")
                                .replace(",", ".")
                                .trim()));
    }

    @Override
    public String marshal(BigDecimal bigDecimal) throws Exception {
        return String.format("€ %.2f", bigDecimal.doubleValue()).replace(".", ",");
    }
}
