package ru.dk.restapp.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import ru.dk.restapp.models.Sensor;

public class MeasurementDTO {
    @NotNull
    @Range(min = -100, max = 100)
    private double value;

    @NotNull
    private boolean raining;

    @NotNull
    private Sensor sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
