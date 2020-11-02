#!/bin/bash
# Exporting environment variables and setting them as
# TF_VAR prefixed names so we don't have to manually pass them to terraform via -var.

export TF_VAR_AWS_SECRET_KEY=$DEV_SECRET_ACCESS_KEY
export TF_VAR_AWS_ACCESS_KEY=$DEV_ACCESS_KEY_ID
export TF_VAR_PORT_URL=$PORT_URL
export TF_VAR_SERVICE_EMAIL=$SERVICE_EMAIL
export TF_VAR_SERVICE_EMAIL_PASSWORD=$SERVICE_EMAIL_PASSWORD
export TF_VAR_POSTGRES_USER=$POSTGRES_USER
export TF_VAR_POSTGRES_CREDS=$POSTGRES_CREDS
export TF_VAR_S3_BUCKET_NAME=$S3_BUCKET_NAME

