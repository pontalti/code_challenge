create table INSTRUMENT_PRICE_MODIFIER (
    ID bigint generated by default as identity,
    MULTIPLIER double,
    NAME varchar(255),
    primary key (id)
);

INSERT INTO INSTRUMENT_PRICE_MODIFIER ( NAME, MULTIPLIER) VALUES ('INSTRUMENT1', 7.00);
INSERT INTO INSTRUMENT_PRICE_MODIFIER ( NAME, MULTIPLIER) VALUES ('INSTRUMENT2', 11.00);
INSERT INTO INSTRUMENT_PRICE_MODIFIER ( NAME, MULTIPLIER) VALUES ('INSTRUMENT3', 12.00);