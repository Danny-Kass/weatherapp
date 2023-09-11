package ru.dk.restapp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dk.restapp.dto.MeasurementDTO;
import ru.dk.restapp.models.Measurement;
import ru.dk.restapp.models.Sensor;
import ru.dk.restapp.services.MeasurementsService;
import ru.dk.restapp.util.ErrorResponse;
import ru.dk.restapp.util.SensorValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    public MeasurementsController(MeasurementsService measurementsService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);
        Sensor sensor = measurement.getSensor();
        sensorValidator.validate(sensor, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldErrors().stream()
                    .map(error -> "Поле " + error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }
        measurementsService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<Measurement> index() {
        return measurementsService.index();
    }

    @GetMapping("/rainyDaysCount")
    public long rainyDaysCount() {
        return measurementsService.rainyDaysCount();
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
