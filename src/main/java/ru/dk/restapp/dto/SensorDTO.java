package ru.dk.restapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Имя сенсора не должно быть пустым")
    @Size(min = 3, max = 30, message = "Имя сенсора должно быть от 3х до 30ти символов")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
