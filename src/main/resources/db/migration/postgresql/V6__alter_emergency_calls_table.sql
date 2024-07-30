alter table emergency_calls alter column id set not null;
alter table emergency_calls alter column id set default gen_random_uuid();

alter table emergency_calls alter column caller_name set not null;
alter table emergency_calls alter column caller_phone_number set not null;
alter table emergency_calls alter column call_time set not null;

alter table emergency_calls alter column ambulance_id set not null;
alter table emergency_calls add constraint fk_ambulances_ambulance_id foreign key (ambulance_id) references ambulances (id);

alter table emergency_calls alter column driver_id set not null;
alter table emergency_calls add constraint fk_drivers_driver_id foreign key (driver_id) references drivers (id);