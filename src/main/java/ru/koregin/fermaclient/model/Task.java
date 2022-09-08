package ru.koregin.fermaclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
public class Task {
    private Long id;
    private String name;
    private String status;
    private ZonedDateTime created;
    private ZonedDateTime completed;

    public Task(String name) {
        this.name = name;
    }

    @JsonCreator
    public Task(@JsonProperty("id") Long id,
                @JsonProperty("name") String name,
                @JsonProperty("status") String status,
                @JsonProperty("created") ZonedDateTime created,
                @JsonProperty("completed") ZonedDateTime completed) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.created = created;
        this.completed = completed;
    }
}
