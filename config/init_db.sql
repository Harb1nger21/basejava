-- auto-generated definition
create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     NOT NULL
);

alter table resume
    owner to postgres;

-- auto-generated definition
create table contact
(
    id          serial
        constraint contact_pk
            primary key,
    resume_uuid char(36) not null REFERENCES resume (uuid) ON DELETE CASCADE,
    type        text     not null,
    value       text     not null
);

alter table contact
    owner to postgres;

create unique index contact__uuid_type_index
    on contact (resume_uuid, type);

-- auto-generated definition
create table section
(
    id          integer default nextval('"Sections_id_seq"'::regclass) not null
        constraint sections_pk
            primary key,
    type        text                                                   not null,
    value       text                                                   not null,
    resume_uuid char(36)                                               not null
        constraint section_resume_uuid__fk
            references resume
            on update restrict on delete cascade
);

alter table section
    owner to postgres;

create unique index sections_type_resume_id_uindex
    on section (type, resume_uuid);