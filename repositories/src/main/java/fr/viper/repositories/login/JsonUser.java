package fr.viper.repositories.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import java.util.Date;

import fr.viper.core.entities.User;

@JsonDeserialize(builder = AutoValue_JsonUser.Builder.class)
@AutoValue
public abstract class JsonUser implements User {
    @SuppressWarnings("unused")
    @AutoValue.Builder
    abstract static class Builder {
        @JsonProperty("first-name")
        abstract Builder setFirstName(String firstName);

        @JsonProperty("last-name")
        abstract Builder setLastName(String lastName);

        @JsonProperty("last-login")
        abstract Builder setLastLogin(Date lastLogin);

        abstract JsonUser build();
    }
}
