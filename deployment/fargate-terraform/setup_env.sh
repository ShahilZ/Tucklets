#!/bin/bash
# Exporting environment variables and setting them as
# TF_VAR prefixed names so we don't have to manually pass them to terraform via -var.

export TF_VAR_aws_secret_key=$DEV_SECRET_ACCESS_KEY
export TF_VAR_aws_access_key=$DEV_ACCESS_KEY_ID
export TF_VAR_paypal_client_id=$PAYPAL_CLIENT_ID

