create table public.user_statistic
(
    id              integer generated always as identity,
    id_user         integer not null,
    id_character    integer not null,
    victory_counter integer,
    defeat_counter  integer
);