package com.example.finalproject.repository.mapper;

import com.example.finalproject.entity.Route;
import com.example.finalproject.entity.Station;
import com.example.finalproject.entity.Ticket;
import com.example.finalproject.entity.Train;
import com.example.finalproject.entity.TrainSchedule;
import org.springframework.jdbc.core.RowMapper;

public class DatabaseMappers {

    private DatabaseMappers() {}

    public static final RowMapper<Train> TrainMapper = (rs, rowNum) ->
        new Train(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("capacity")
        );

    public static final RowMapper<Ticket> TicketMapper = (rs, rowNum) ->
        new Ticket(
            rs.getLong("id"),
            rs.getLong("schedule_id"),
            rs.getString("passenger_name"),
            rs.getString("passenger_email"),
            rs.getString("status"),
            rs.getString("seat_number"),
            rs.getTimestamp("issued_at").toLocalDateTime()
        );

    public static final RowMapper<TrainSchedule> TrainScheduleMapper = (rs, rowNum) ->
        new TrainSchedule(
            rs.getLong("id"),
            rs.getInt("train_id"),
            rs.getInt("route_id"),
            rs.getString("status"),
            rs.getTimestamp("dep_time").toLocalDateTime(),
            rs.getTimestamp("arr_time").toLocalDateTime()
        );

    public static final RowMapper<Route> RouteMapper = (rs, rowNum) ->
        new Route(
            rs.getInt("id"),
            rs.getInt("dep_station_id"),
            rs.getInt("arr_station_id")
        );

    public static final RowMapper<Station> StationMapper = (rs, rowNum) ->
        new Station(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("city"),
            rs.getString("state")
        );
}
