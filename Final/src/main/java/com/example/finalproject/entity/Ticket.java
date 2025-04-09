package com.example.finalproject.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Ticket {
    private static final DateTimeFormatter Formatter =
        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    private long id;
    private long scheduleId;
    private String passengerName;
    private String passengerEmail;
    private String status;
    private String seatNumber;
    private LocalDateTime issuedAt;
    private String from;
    private String to;
    private String departure;
    private String arrival;

    public Ticket(long id, long scheduleId, String passengerName, String passengerEmail,
                  String status, String seatNumber, LocalDateTime issuedAt) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
        this.status = status;
        this.seatNumber = seatNumber;
        this.issuedAt = issuedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
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

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = Formatter.format(departure);
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = Formatter.format(arrival);
    }
}
