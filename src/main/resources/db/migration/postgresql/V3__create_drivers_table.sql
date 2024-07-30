create table if not exists drivers (
    id uuid primary key,
    name varchar(255),
    license_number varchar(255),
    phone_number varchar(255)
);