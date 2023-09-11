package ru.dk.restapp.client;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import ru.dk.restapp.dto.MeasurementDTO;
import ru.dk.restapp.models.Sensor;

import java.util.Random;

public class RestClient {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        Random random = new Random();
        Sensor sensor = new Sensor();
        sensor.setName("Sensor Name");

        for (int i = 0; i < 1000; i++) {
            MeasurementDTO measurementDTO = new MeasurementDTO();
            measurementDTO.setValue(random.nextDouble(200) - 100);
            measurementDTO.setRaining(random.nextBoolean());
            measurementDTO.setSensor(sensor);

            HttpEntity<MeasurementDTO> httpEntity = new HttpEntity<>(measurementDTO);

            restTemplate.postForObject("http://localhost:8080/measurements/add", httpEntity, String.class);
        }
    }
}
