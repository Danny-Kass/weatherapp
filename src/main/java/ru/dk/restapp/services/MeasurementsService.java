package ru.dk.restapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dk.restapp.models.Measurement;
import ru.dk.restapp.repositories.MeasurementsRepository;
import ru.dk.restapp.repositories.SensorsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;

    private final SensorsRepository sensorsRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }

    public List<Measurement> index() {
        return measurementsRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement) {
        measurement.setTimestamp(LocalDateTime.now());
        measurement.setSensor(sensorsRepository.findByName(measurement.getSensor().getName()).orElseThrow(() -> new IllegalArgumentException("Сенсор с таким именем не существует")));
        measurementsRepository.save(measurement);
    }

    public long rainyDaysCount() {
        return measurementsRepository.countByRaining(true);
    }
}
