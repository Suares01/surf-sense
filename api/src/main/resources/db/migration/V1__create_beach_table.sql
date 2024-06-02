CREATE TABLE beaches (
  id uuid NOT NULL,
  city character varying(255) NULL,
  country_code smallint NULL,
  created_at timestamp(6) with time zone NOT NULL,
  image_url character varying(255) NULL,
  latitude double precision NOT NULL,
  longitude double precision NOT NULL,
  name character varying(50) NOT NULL,
  "position" smallint NOT NULL,
  user_id character varying(255) NOT NULL,
  image character varying(255) NULL,
  CONSTRAINT beaches_pkey PRIMARY KEY (id)
);
