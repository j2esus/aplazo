create table credit_line(
    id bigserial primary key,
    id_customer bigint not null,
    amount numeric(10,2) not null
);

create unique index unique_credit_line_customer on credit_line(id_customer);
