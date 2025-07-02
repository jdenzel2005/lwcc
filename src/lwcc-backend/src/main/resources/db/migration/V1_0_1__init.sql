create table lwcc_customer
(
    created_date       timestamp(6),
    last_modified_date timestamp(6),
    version            bigint,
    id                 uuid not null,
    info_text          varchar(100),
    city               varchar(255),
    country            varchar(255),
    firstname          varchar(255),
    house_number       varchar(255),
    lastname           varchar(255),
    street             varchar(255),
    vat_id             varchar(255),
    zip                varchar(255),
    primary key (id)
);
