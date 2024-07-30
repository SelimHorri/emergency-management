alter table ambulances alter column id set not null;
alter table ambulances alter column id set default gen_random_uuid();