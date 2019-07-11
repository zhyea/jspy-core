drop table if exists user;
create table if not exists user
(
    id          int auto_increment primary key,
    username    varchar(64) unique not null,
    password    varchar(64)        not null,

    deleted     tinyint                     default 0,
    insert_time datetime                    default current_date,
    op_time     timestamp          not null default current_timestamp on update current_timestamp
);


create table if not exists app
(
    id          int auto_increment primary key,

    app_code    varchar(16) unique not null,
    app_name    varchar(64)        not null,

    deleted     tinyint                     default 0,
    insert_time datetime                    default current_date,
    op_time     timestamp          not null default current_timestamp on update current_timestamp
);

create table if not exists memory_usage
(
    id            int auto_increment primary key,

    app_code      varchar(16),
    ip            varchar(32),

    `type`        varchar(16),
    `name`        varchar(32),
    manager_names varchar(64),

    init          bigint,
    used          bigint,
    `committed`   bigint,
    `max`         bigint,
    event_time    datetime           default current_date,
    is_peak       tinyint            default 0,

    deleted       tinyint            default 0,
    insert_time   datetime           default current_date,
    op_time       timestamp not null default current_timestamp on update current_timestamp
);



create table if not exists gc_stat
(
    id             int auto_increment primary key,

    app_code       varchar(32),
    ip             varchar(32),

    gc_id          int,
    `type`         varchar(16),
    action         varchar(32),
    `name`         varchar(32),
    cause          varchar(32),

    start_time     bigint,
    duration       bigint,

    usage_before   bigint,
    usage_after    bigint,
    event_time     bigint,

    major_gc_count bigint,
    minor_gc_count bigint,

    deleted        tinyint            default 0,
    insert_time    datetime           default current_date,
    op_time        timestamp not null default current_timestamp on update current_timestamp
);


create table if not exists thread_stat
(
    id            int auto_increment primary key,

    app_code      varchar(32),
    ip            varchar(32),

    current       bigint,
    peak          bigint,
    total_started bigint,
    daemon        bigint,

    deleted       tinyint            default 0,
    insert_time   datetime           default current_date,
    op_time       timestamp not null default current_timestamp on update current_timestamp
);


create table if not exists class_loading_stat
(
    id             int auto_increment primary key,

    app_code       varchar(32),
    ip             varchar(32),

    total_loaded   bigint,
    current_loaded bigint,
    unloaded       bigint,

    deleted        tinyint            default 0,
    insert_time    datetime           default current_date,
    op_time        timestamp not null default current_timestamp on update current_timestamp
);


create table if not exists method_stats
(
    id          int auto_increment primary key,

    app_code    varchar(32),
    ip          varchar(32),

    method_id   varchar(64),

    std_dev     bigint,
    min         bigint,
    max         bigint,
    mean        bigint,

    percentile999  bigint,
    percentile98   bigint,
    percentile95   bigint,
    percentile90   bigint,
    percentile75   bigint,
    median      bigint,

    deleted     tinyint            default 0,
    insert_time datetime           default current_date,
    op_time     timestamp not null default current_timestamp on update current_timestamp
);