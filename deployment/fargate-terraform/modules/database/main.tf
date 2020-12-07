locals {
  db_creds = jsondecode(data.aws_secretsmanager_secret_version.db_creds.secret_string)
}

data "aws_vpc" "default_vpc" {
  default = true
}