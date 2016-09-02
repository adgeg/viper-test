package fr.viper.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;

import fr.viper.MapperModule;

import static fr.viper.utils.AssertDate.assertSameHour;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonUserTest {
    @Test
    public void deserialization() throws IOException {
        final String json = "{\"last-name\":\"Doe\",\"first-name\":\"John\",\"last-login\":\"2016-09-01T12:35:14\"}";
        final ObjectMapper mapper = new MapperModule().getObjectMapper();
        final JsonUser user = mapper.readValue(json, JsonUser.class);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertSameHour(user.getLastLogin(), 2016, 8, 1, 12);
    }
}