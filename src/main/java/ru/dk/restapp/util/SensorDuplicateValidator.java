package ru.dk.restapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.dk.restapp.models.Sensor;
import ru.dk.restapp.services.SensorsService;

@Component
public class SensorDuplicateValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public SensorDuplicateValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorsService.getSensorByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Сенсор с таким именем уже существует");
            throw new IllegalArgumentException("Сенсор с таким именем уже существует");
        }
    }
}
