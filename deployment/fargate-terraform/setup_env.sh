#!/bin/bash
# Exporting environment variables and setting them as
# TF_VAR prefixed names so we don't have to manually pass them to terraform via -var.

export TF_VAR_aws_secret_key=$DEV_SECRET_ACCESS_KEY
export TF_VAR_aws_access_key=$DEV_ACCESS_KEY_ID
export TF_VAR_port_url=$PORT_URL
export TF_VAR_service_email=$SERVICE_EMAIL
export TF_VAR_service_email_password=$SERVICE_EMAIL_PASSWORD
export TF_VAR_postgres_user=$POSTGRES_USER
export TF_VAR_postgres_creds=$POSTGRES_CREDS
export TF_VAR_s3_bucket_name=$S3_BUCKET_NAME
export TF_VAR_ssl_key_store_password=$SSL_KEY_STORE_PASSWORD

