CREATE TABLE subscription
(
    subscription_id bigint NOT NULL,
    sponsor_id bigint NOT NULL,
    braintree_customer_id VARCHAR(255) NOT NULL,
	braintree_subscription_id VARCHAR(255) NOT NULL,
	archive_date date,
    PRIMARY KEY (subscription_id)
);

CREATE SEQUENCE subscription_generator;
