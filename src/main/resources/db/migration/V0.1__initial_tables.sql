CREATE TABLE admin
(
    admin_id bigint NOT NULL,
    creation_date date NOT NULL,
    deletion_date date,
    enabled boolean NOT NULL,
    encrypted_password varchar(255) NOT NULL,
    last_login_date date NOT NULL,
    username varchar(255) NOT NULL,
    PRIMARY KEY (admin_id)
);

CREATE TABLE child
(
    child_id bigint NOT NULL,
    birth_year integer NOT NULL,
    creation_date date NOT NULL,
    deletion_date date,
    first_name varchar(255) NOT NULL,
    grade integer NOT NULL,
    information varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    last_update_date date NOT NULL,
    is_sponsored boolean,
    PRIMARY KEY (child_id)
);


CREATE TABLE sponsor
(
    sponsor_id bigint NOT NULL,
    email VARCHAR(255) NOT NULL,
	first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
	church_name VARCHAR(255),
	street_address1 VARCHAR(255),
    street_address2 VARCHAR(255),
	city VARCHAR(255),
	state integer,
    zip_code VARCHAR(255),
	country VARCHAR(255),
    subscribed boolean NOT NULL,
    phone_number VARCHAR(255) ,
	creation_date date NOT NULL,
	last_update_date date NOT NULL,
    deletion_date date,
    PRIMARY KEY (sponsor_id),
    CONSTRAINT unique_email_constraint UNIQUE (email)
);



CREATE TABLE child_additional_detail
(
   child_additional_detail_id bigint NOT NULL,
   child_id bigint NOT NULL,
   image_location VARCHAR(255) NOT NULL,
   archived_date date,
   PRIMARY KEY(child_additional_detail_id),
   CONSTRAINT fk_child
      FOREIGN KEY(child_id)
	  REFERENCES child(child_id)
);

CREATE TABLE child_sponsor_assoc
(
    child_sponsor_assoc_id bigint NOT NULL,
    child_id bigint NOT NULL,
	sponsor_id bigint NOT NULL,
	sponsored_date date NOT NULL,
    donation_duration VARCHAR(255) NOT NULL,
    deletion_date date,
    PRIMARY KEY (child_sponsor_assoc_id),
	CONSTRAINT fk_child
      FOREIGN KEY(child_id)
	  REFERENCES child(child_id),
	CONSTRAINT fk_sponsor
      FOREIGN KEY(sponsor_id)
	  REFERENCES sponsor(sponsor_id)
);


CREATE TABLE donation
(
    donation_id bigint NOT NULL,
    donation_amount numeric(19,2) NOT NULL,
    donation_duration_text VARCHAR(255) NOT NULL,
    payment_method VARCHAR(255) NOT NULL,
    creation_date date NOT NULL,
    deletion_date date,
    PRIMARY KEY (donation_id)
);



CREATE TABLE newsletter
(
    newsletter_id bigint NOT NULL,
    filename VARCHAR(255) NOT NULL,
	upload_date date,
    newsletter_location VARCHAR(255),
    PRIMARY KEY (newsletter_id)
);



CREATE TABLE sponsor_donation_assoc
(
    sponsor_donation_assoc_id bigint NOT NULL,
    sponsor_id bigint NOT NULL,
	donation_id bigint NOT NULL,
    PRIMARY KEY (sponsor_donation_assoc_id),
	CONSTRAINT fk_sponsor
      FOREIGN KEY(sponsor_id)
	  REFERENCES sponsor(sponsor_id)
);
