CREATE USER ideate with PASSWORD 'ideate';
CREATE DATABASE ideate OWNER ideate;


create table UserConnection (userId text not null,
    providerId text not null,
    providerUserId text,
    rank int not null,
    displayName text,
    profileUrl varchar(512),
    imageUrl varchar(512),
    accessToken text not null,
    secret text,
    refreshToken text,
    expireTime bigint,
    primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);


   create table auth_user (
        id  serial not null,
        active boolean not null,
        created timestamp,
        updated timestamp,
        account_non_expired boolean not null,
        account_non_locked boolean not null,
        auto_created boolean not null,
        credentials_non_expired boolean not null,
        email text,
        enabled boolean not null,
        full_name text,
        image_url text,
        password text,
        social_profile text,
        user_name text,
        primary key (id)
    );

    create table auth_user_roles (
        auth_user int4 not null,
        roles int4
    );

    alter table auth_user 
        drop constraint UK_klvc3dss72qnlrjp2bai055mw;

    alter table auth_user 
        add constraint UK_klvc3dss72qnlrjp2bai055mw unique (email);

    alter table auth_user 
        drop constraint UK_9grev4dtpf1qsywx7lon7yhf;

    alter table auth_user 
        add constraint UK_9grev4dtpf1qsywx7lon7yhf unique (user_name);

    alter table auth_user_roles 
        add constraint FK_8i6hv5jhi6xx6lbi2yjhq6uyt 
        foreign key (auth_user) 
        references auth_user;