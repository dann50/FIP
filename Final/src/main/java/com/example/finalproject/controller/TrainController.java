package com.example.finalproject.controller;

import com.example.finalproject.dto.AddScheduleRequest;
import com.example.finalproject.dto.AddStationRequest;
import com.example.finalproject.dto.ReservationRequest;
import com.example.finalproject.entity.Route;
import com.example.finalproject.entity.Station;
import com.example.finalproject.entity.Ticket;
import com.example.finalproject.entity.Train;
import com.example.finalproject.entity.TrainSchedule;
import com.example.finalproject.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<TrainSchedule>> getAllSchedules() {
        return ResponseEntity.ok(trainService.getAllSchedules());
    }

    @GetMapping("/schedules/from")
    public ResponseEntity<List<TrainSchedule>> getAllSchedulesFromStation(@RequestParam int station) {
        return ResponseEntity.ok(trainService.getSchedulesFrom(station));
    }

    @GetMapping("/schedules-on-route")
    public ResponseEntity<List<TrainSchedule>> getSchedulesBetween(@RequestParam int from, @RequestParam int to) {
        return ResponseEntity.ok(trainService.getSchedulesBetweenStations(from, to));
    }

    @PostMapping("/reserve-train")
    public ResponseEntity<Ticket> bookTrain(@RequestBody @Valid ReservationRequest request) {
        return ResponseEntity.ok(trainService.reserveTicket(request));
    }

    @PatchMapping("/depart")
    public ResponseEntity<HttpStatus> departTrain(@RequestParam int scheduleId) {
        trainService.departTrain(scheduleId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/arrive")
    public ResponseEntity<HttpStatus> arriveTrain(@RequestParam int scheduleId) {
        trainService.arriveTrain(scheduleId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/cancel-ticket")
    public ResponseEntity<HttpStatus> cancelTicket(@RequestParam long id, @RequestParam int confirmation) {
        if (confirmation == 1)
            trainService.cancelTicket(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/cancel-ticket")
    public String cancelTicket(@RequestParam long id) {
        trainService.validateTicket(id);
        return "Are you sure you want to cancel ticket? 1 for yes or 0 for no";
    }

    @PostMapping("/add-train")
    public ResponseEntity<Train> addTrain(@RequestParam String name, @RequestParam int capacity) {
        return ResponseEntity.ok(trainService.addNewTrain(name, capacity));
    }

    @PostMapping("/add-schedule")
    public ResponseEntity<TrainSchedule> addSchedule(@RequestBody @Valid AddScheduleRequest request) {

        return ResponseEntity.ok(trainService.addNewSchedule(
            request.getTrainId(), request.getRouteId(), request.getDep(), request.getArr()));
    }

    @PostMapping("/add-route")
    public ResponseEntity<Route> addRoute(@RequestParam int fromStationId, @RequestParam int toStationId) {
        return ResponseEntity.ok(trainService.addNewRoute(fromStationId, toStationId));
    }

    @PostMapping("/add-station")
    public ResponseEntity<Station> addStation(@RequestBody @Valid AddStationRequest request) {
        return ResponseEntity.ok(trainService.addNewStation(request.getName(), request.getCity(), request.getState()));
    }

}
