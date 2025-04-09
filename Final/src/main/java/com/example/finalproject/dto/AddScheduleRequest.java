package com.example.finalproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class AddScheduleRequest {
    @Min(value = 1, message = "Invalid train ID")
    private int trainId;
    @Min(value = 1, message = "Invalid route ID")
    private int routeId;
    @NotBlank
    private String dep;
    @NotBlank
    private String arr;

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

    public LocalDateTime getDep() {
        return LocalDateTime.parse(dep);
    }

    public void setDep(LocalDateTime dep) {
        this.dep = dep.toString();
    }

    public LocalDateTime getArr() {
        return LocalDateTime.parse(arr);
    }

    public void setArr(LocalDateTime arr) {
        this.arr = arr.toString();
    }
}
