create table if not exists ambulances (
    id uuid primary key,
    license_plate varchar(255),
    status varchar(255),
    location varchar(255)
);