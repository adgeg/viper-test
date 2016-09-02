package fr.viper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MapperModule {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private final ObjectMapper objectMapper;

    public MapperModule() {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()));
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
