CREATE TABLE sponsor_braintree_detail
(
    sponsor_braintree_detail_id bigint NOT NULL,
    sponsor_id bigint NOT NULL,
    braintree_customer_id VARCHAR(255) NOT NULL,
	braintree_subscription_id VARCHAR(255) NOT NULL,
	archive_date date,
    PRIMARY KEY (sponsor_braintree_detail_id)
);

CREATE SEQUENCE sponsor_braintree_detail_generator;
