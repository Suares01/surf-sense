CREATE TABLE ratings (
  id uuid NOT NULL,
  created_at timestamp(6) with time zone NOT NULL,
  name character varying(40) NOT NULL,
  type smallint NOT NULL,
  user_id character varying(255) NOT NULL,
  CONSTRAINT ratings_pkey PRIMARY KEY (id)
);

CREATE TABLE criteria (
  id uuid NOT NULL,
  created_at timestamp(6) with time zone NOT NULL,
  type smallint NOT NULL,
  weight real NOT NULL,
  rating_id uuid NOT NULL,
  CONSTRAINT criteria_pkey PRIMARY KEY (id),
  CONSTRAINT fk_rating FOREIGN KEY (rating_id) REFERENCES ratings (id)
);
