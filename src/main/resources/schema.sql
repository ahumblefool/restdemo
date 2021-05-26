DROP TABLE IF EXISTS TODO;

CREATE TABLE TODO (
    id IDENTITY,
    content VARCHAR,
    status BOOLEAN,
    created_at timestamp,
    end_date timestamp
);