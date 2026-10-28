package adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PreferencesAdapter extends XmlAdapter<String, List<String>> {

    @Override
    public List<String> unmarshal(String string) throws Exception {
        return Arrays.stream(string.split(" ")).collect(Collectors.toList());
    }

    @Override
    public String marshal(List<String> strings) throws Exception {
        return String.join(" ", strings);
    }
}
