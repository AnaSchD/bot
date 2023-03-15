--liquibase formatted sql
-- changeset anScherbakova:1

CREATE TABLE IF NOT EXISTS NotificationTask
(
    Id
    INT,
    DateTime
    TIMESTAMP,
    Text
    VARCHAR,
    TextMessage
    VARCHAR
);
ALTER TABLE NotificationTask
    ADD COLUMN chatId VARCHAR;
ALTER TABLE NotificationTask
DROP
COLUMN chatId;