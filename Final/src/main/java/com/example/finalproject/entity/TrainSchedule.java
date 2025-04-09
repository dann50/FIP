package com.example.finalproject.entity;

import java.time.LocalDateTime;

public class TrainSchedule {

    private long id;
    private int trainId;
    private int routeId;
    private String status;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String from;
    private String to;

    public TrainSchedule(long id, int trainId, int routeId, String status,
                         LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.trainId = trainId;
        this.routeId = routeId;
        this.status = status;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(Station dep) {
        this.from = dep.getName() + ", " + dep.getCity() + ", " + dep.getState();
    }

    public String getTo() {
        return to;
    }

    public void setTo(Station arr) {
        this.to = arr.getName() + ", " + arr.getCity() + ", " + arr.getState();
    }
}
