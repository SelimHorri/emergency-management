create table if not exists emergency_calls (
    id uuid primary key,
    caller_name varchar(50),
    caller_phone_number varchar(10),
    location_address varchar(255),
    description varchar(255),
    call_time timestamp,
    ambulance_id uuid,
    driver_id uuid
);