package com.example.finalproject.repository;

import com.example.finalproject.entity.Route;
import com.example.finalproject.entity.Station;
import com.example.finalproject.entity.Ticket;
import com.example.finalproject.entity.Train;
import com.example.finalproject.entity.TrainSchedule;
import com.example.finalproject.handler.DatabaseException;
import com.example.finalproject.repository.mapper.DatabaseMappers;
import com.example.finalproject.util.ScheduleStatus;
import com.example.finalproject.util.TicketStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TrainRepository {

    private static final String SELECT_TICKET_BY_ID = "select * from ticket where id = ?";
    private static final String SELECT_TRAIN_BY_ID = "select * from train where id = ?";
    private static final String SELECT_TRAIN_SCHEDULE_BY_ID = "select * from train_schedule where id = ?";
    private static final String SELECT_STATION_BY_ID = "select * from station where id = ?";
    private static final String SELECT_ROUTE_BY_ID = "select * from route where id = ?";

    private static final String INSERT_INTO_TICKET = "insert into ticket (schedule_id, passenger_name, " +
        "passenger_email, status, seat_number, issued_at) values (?, ?, ?, ?, ?, ?) returning id";
    private static final String INSERT_INTO_TRAIN = "insert into train (name, capacity) values (?, ?) " +
        "returning id";
    private static final String INSERT_INTO_TRAIN_SCHEDULE = "insert into train_schedule " +
        "(train_id, route_id, status, dep_time, arr_time) values (?, ?, ?, ?, ?) returning id";
    private static final String INSERT_INTO_ROUTE = "insert into route (dep_station_id, arr_station_id)" +
        " values (?, ?) returning id";
    private static final String INSERT_INTO_STATION = "insert into station (name, city, state) " +
        "values (?, ?, ?) returning id";


    private final JdbcTemplate jdbcTemplate;

    public TrainRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long addTicket(long scheduleId, String passengerName,
                            String seatNumber, String passengerEmail) {

        Long id = jdbcTemplate.queryForObject(
            INSERT_INTO_TICKET,
            new Object[] {scheduleId, passengerName, passengerEmail,
                TicketStatus.Issued.toString(), seatNumber,
                Timestamp.valueOf(LocalDateTime.now())
            },
            new int[] {Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.TIMESTAMP
            },
            Long.class
        );

        if (id == null) {
            throw new DatabaseException("Couldn't add ticket");
        }

        return id;
    }

    public Ticket getTicketById(long ticketId) {
        return jdbcTemplate.queryForObject(SELECT_TICKET_BY_ID, DatabaseMappers.TicketMapper, ticketId);
    }

    public int addTrain(String name, int capacity) {

        Integer id = jdbcTemplate.queryForObject(
            INSERT_INTO_TRAIN,
            new Object[] {name, capacity},
            new int[] {Types.VARCHAR, Types.INTEGER},
            Integer.class
        );

        if (id == null) {
            throw new DatabaseException("Couldn't add train");
        }

        return id;
    }

    public Train getTrainById(int id) {
        return jdbcTemplate.queryForObject(SELECT_TRAIN_BY_ID, DatabaseMappers.TrainMapper, id);
    }

    public long addTrainSchedule(int trainId, int routeId, LocalDateTime dep, LocalDateTime arr) {

        Long id = jdbcTemplate.queryForObject(
            INSERT_INTO_TRAIN_SCHEDULE,
            new Object[] {trainId, routeId, ScheduleStatus.Upcoming.toString(),
                Timestamp.valueOf(dep), Timestamp.valueOf(arr)
            },
            new int[] {Types.INTEGER, Types.INTEGER, Types.VARCHAR,
                Types.TIMESTAMP, Types.TIMESTAMP
            },
            Long.class
        );

        if (id == null) {
            throw new DatabaseException("Couldn't add train schedule");
        }

        return id;
    }

    /*
     * Get a TrainSchedule object by its ID
     */
    public TrainSchedule getTrainScheduleById(long id) {
        return jdbcTemplate.queryForObject(SELECT_TRAIN_SCHEDULE_BY_ID,
            DatabaseMappers.TrainScheduleMapper, id);
    }

    public int addRoute(int depId, int arrId) {

        Integer id = jdbcTemplate.queryForObject(
            INSERT_INTO_ROUTE,
            new Object[] {depId, arrId},
            new int[] {Types.INTEGER, Types.INTEGER},
            Integer.class
        );

        if (id == null) {
            throw new DatabaseException("Couldn't add route");
        }

        return id;
    }

    public Route getRouteById(int routeId) {
        return jdbcTemplate.queryForObject(SELECT_ROUTE_BY_ID, DatabaseMappers.RouteMapper, routeId);
    }

    public Integer addStation(String name, String city, String state) {

        Integer id = jdbcTemplate.queryForObject(
            INSERT_INTO_STATION,
            new Object[] {name, city, state},
            new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR},
            Integer.class
        );

        if (id == null) {
            throw new DatabaseException("Couldn't add station");
        }
        return id;
    }

    public Station getStationById(int id) {
        return jdbcTemplate.queryForObject(SELECT_STATION_BY_ID, DatabaseMappers.StationMapper, id);
    }

    public List<TrainSchedule> getAllSchedulesBetweenStations(int depId, int arrId, ScheduleStatus status) {
        String sql = "select * from train_schedule where route_id in (select id from route where " +
            "dep_station_id = ? and arr_station_id = ?) and status = ? order by dep_time desc";

        return jdbcTemplate.query(sql, DatabaseMappers.TrainScheduleMapper, depId, arrId, status.toString());
    }

    public List<TrainSchedule> getAllSchedulesFromStation(int stationId, ScheduleStatus status) {
        String sql = "select * from train_schedule where route_id in (select id from route" +
            " where dep_station_id = ?) and status = ? order by dep_time desc";

        return jdbcTemplate.query(sql, DatabaseMappers.TrainScheduleMapper, stationId, status.toString());
    }

    public List<TrainSchedule> getAllSchedulesByStatus(ScheduleStatus status) {
        String sql = "select * from train_schedule where status = ? order by dep_time desc";

        return jdbcTemplate.query(sql, DatabaseMappers.TrainScheduleMapper, status.toString());
    }

    /*
     * Mark arrival of train on a given schedule
     */
    public void updateTrainScheduleStatus(long scheduleId, String status) {
        String sql = "update train_schedule set status = ? where id = ?";
        jdbcTemplate.update(sql, status, scheduleId);
    }

    /*
     * Update ticket status
     */
    public void updateTicketStatus(long ticketId, String status) {
        String sql = "update ticket set status = ? where id = ?";
        jdbcTemplate.update(sql, status, ticketId);
    }

    /*
     * Set the tickets on this particular schedule to expired
     */
    public void updateTicketsOnSchedule(long scheduleId, String status) {
        String sql = "update ticket set status = ? where schedule_id = ?";
        jdbcTemplate.update(sql, status, scheduleId);
    }

    /*
     * Gets the number of seats reserved on a particular schedule
     */
    public int getReservedSeatCount(long scheduleId) {
        String sql = "select count(*) from ticket where schedule_id = ?";
        Integer count =  jdbcTemplate.queryForObject(sql, Integer.class, scheduleId);
        return count == null ? 0 : count;
    }

}
