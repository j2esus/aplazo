create table scheme(
    id bigserial primary key,
    id_customer bigint not null,
    sub_total numeric(10,2) not null,
    installment_amount numeric(10,2),
    rate numeric(10,2) not null,
    is_next_period boolean not null
);