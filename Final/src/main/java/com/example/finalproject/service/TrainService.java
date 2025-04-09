package com.example.finalproject.service;

import com.example.finalproject.dto.ReservationRequest;
import com.example.finalproject.entity.Route;
import com.example.finalproject.entity.Ticket;
import com.example.finalproject.entity.TrainSchedule;
import com.example.finalproject.handler.DatabaseException;
import com.example.finalproject.handler.ReservationException;
import com.example.finalproject.repository.TrainRepository;
import com.example.finalproject.util.ScheduleStatus;
import com.example.finalproject.util.TicketStatus;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class TrainService {

    private final TrainRepository trainRepository;
    private final MailService mailService;
    private final Random rand = new Random();

    public TrainService(TrainRepository trainRepository, MailService mailService) {
        this.trainRepository = trainRepository;
        this.mailService = mailService;
    }

    public List<TrainSchedule> getSchedulesFrom(int stationId) {
        List<TrainSchedule> schedules = trainRepository.getAllSchedulesFromStation(
            stationId, ScheduleStatus.Upcoming);
        schedules.forEach(this::setProperties);
        return schedules;
    }

    public List<TrainSchedule> getSchedulesBetweenStations(int fromStationId, int toStationId) {

        List<TrainSchedule> schedules = trainRepository.getAllSchedulesBetweenStations(
            fromStationId, toStationId, ScheduleStatus.Upcoming);
        schedules.forEach(this::setProperties);
        return schedules;
    }

    public List<TrainSchedule> getAllSchedules() {
        List<TrainSchedule> schedules = trainRepository
            .getAllSchedulesByStatus(ScheduleStatus.Upcoming);
        schedules.forEach(this::setProperties);
        return schedules;
    }

    @Transactional
    public Ticket reserveTicket(ReservationRequest request) {
        TrainSchedule ts = trainRepository.getTrainScheduleById(request.getScheduleId());
        int capacity = trainRepository.getTrainById(ts.getTrainId()).getCapacity();
        int reservedSeats = trainRepository.getReservedSeatCount(request.getScheduleId());

        if (capacity <= reservedSeats) {
            throw new ReservationException("Capacity exceeded");
        }

        long id = trainRepository.addTicket(
            request.getScheduleId(),
            request.getPassengerName(),
            String.valueOf(rand.nextInt(1, capacity + 1)),
            request.getPassengerEmail()
        );

        Ticket savedTicket = trainRepository.getTicketById(id);
        if (savedTicket == null) {
            throw new DatabaseException("Couldn't add ticket");
        }

        Route route = trainRepository.getRouteById(
            trainRepository.getTrainScheduleById(savedTicket.getScheduleId()).getRouteId());
        savedTicket.setFrom(trainRepository.getStationById(route.getDepartureStationId()));
        savedTicket.setTo(trainRepository.getStationById(route.getArrivalStationId()));
        var sch = trainRepository.getTrainScheduleById(savedTicket.getScheduleId());
        savedTicket.setDeparture(sch.getDepartureTime());
        savedTicket.setArrival(sch.getArrivalTime());

        try {
            mailService.sendConfirmationEmail(savedTicket);
        } catch (MessagingException | IOException e) {
            throw new DatabaseException(e.getMessage());
        }

        return savedTicket;
    }

    public void departTrain(int scheduleId) {
        trainRepository.updateTrainScheduleStatus(scheduleId, ScheduleStatus.Departed.toString());
    }

    public void arriveTrain(int scheduleId) {
        trainRepository.updateTrainScheduleStatus(scheduleId, ScheduleStatus.Arrived.toString());
        trainRepository.updateTicketsOnSchedule(scheduleId, TicketStatus.Expired.toString());
    }

    public void cancelTicket(long ticketId) {
        trainRepository.updateTicketStatus(ticketId, TicketStatus.Cancelled.toString());
    }

    private void setProperties(TrainSchedule trainSchedule) {
        Route route = trainRepository.getRouteById(trainSchedule.getRouteId());
        trainSchedule.setFrom(trainRepository.getStationById(route.getDepartureStationId()));
        trainSchedule.setTo(trainRepository.getStationById(route.getArrivalStationId()));
    }

    public void addNewTrain(String name, int capacity) {
        trainRepository.addTrain(name, capacity);
    }

    public void addNewSchedule(int trainId, int routeId, LocalDateTime dep, LocalDateTime arr) {
        trainRepository.addTrainSchedule(trainId, routeId, dep, arr);
    }

    public void addNewRoute(int fromStationId, int toStationId) {
        trainRepository.addRoute(fromStationId, toStationId);
    }

    public void addNewStation(String name, String city, String state) {
        trainRepository.addStation(name, city, state);
    }
}
