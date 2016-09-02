package fr.viper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import java.util.Date;

import fr.viper.core.entities.User;

@JsonDeserialize(builder = AutoValue_JsonUser.Builder.class)
@AutoValue
public abstract class JsonUser implements User {
    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract Date getLastLogin();

    @SuppressWarnings("unused")
    @AutoValue.Builder
    abstract static class Builder {
        @JsonProperty("first-name")
        public abstract Builder setFirstName(String firstName);

        @JsonProperty("last-name")
        public abstract Builder setLastName(String lastName);

        @JsonProperty("last-login")
        public abstract Builder setLastLogin(Date lastLogin);

        public abstract JsonUser build();
    }
}
