CREATE TABLE station (
    id integer primary key generated always as identity,
    name varchar(100) not null,
    city varchar(100),
    state varchar(100)
);

CREATE TABLE train (
    id integer primary key generated always as identity,
    name varchar(100) not null,
    capacity integer not null
);

CREATE TABLE route (
    id integer primary key generated always as identity,
    dep_station_id integer not null,
    arr_station_id integer not null,
    constraint dep_route_fk_1 foreign key (dep_station_id) references station (id),
    constraint arr_route_fk_2 foreign key (arr_station_id) references station (id)
);
create unique index on route(dep_station_id, arr_station_id);

CREATE TABLE train_schedule (
    id bigint primary key generated always as identity,
    train_id integer not null,
    route_id integer not null,
    status varchar(10) not null,
    dep_time timestamp with time zone not null,
    arr_time timestamp with time zone not null,
    constraint train_fk_1 foreign key (train_id) references train (id),
    constraint route_fk_1 foreign key (route_id) references route (id)
);
create index on train_schedule(train_id, route_id);

CREATE TABLE ticket (
    id bigint primary key generated always as identity,
    schedule_id bigint not null,
    passenger_name varchar(100) not null,
    passenger_email varchar(100) not null,
    status varchar(10) not null,
    seat_number varchar(10) not null,
    issued_at timestamp with time zone not null,
    constraint schedule_fk_1 foreign key (schedule_id) references train_schedule (id)
);
create index on ticket(schedule_id, passenger_email, passenger_name);