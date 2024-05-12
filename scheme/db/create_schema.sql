create table scheme(
    id bigserial primary key,
    id_customer bigint not null,
    sub_total numeric(10,2) not null,
    installment_amount numeric(10,2),
    rate numeric(10,2) not null,
    is_next_period boolean not null
);

create table payment_date(
    id bigserial primary key,
    payment_date date not null,
    id_scheme bigint not null,
    constraint fk_scheme foreign key(id_scheme) references scheme(id)
);