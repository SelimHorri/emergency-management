alter table drivers alter column id set not null;
alter table drivers alter column id set default gen_random_uuid();