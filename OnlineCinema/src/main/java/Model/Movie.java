package Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Movie {
    @JsonProperty("id")
    public long id;

    public Movie() {} // Конструктор по умолчанию для Jackson

    public Movie(long id) {
        this.id = id;
    }
}