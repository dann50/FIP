package com.example.finalproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ReservationRequest {
    @NotBlank(message = "Name cannot be blank")
    private String passengerName;
    @Email(message = "Invalid email format")
    private String passengerEmail;
    @Min(value = 1, message = "Schedule not found")
    private long scheduleId;

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public @Min(value = 1, message = "Schedule not found") long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(@Min(value = 1, message = "Schedule not found") long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
