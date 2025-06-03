CREATE TABLE arqueo
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    id_arqueo   BIGINT                NULL,
    descripcion VARCHAR(255)          NULL,
    fecha       date                  NULL,
    CONSTRAINT pk_arqueo PRIMARY KEY (id)
);