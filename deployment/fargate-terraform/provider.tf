# AWS provider
terraform {
  required_version = ">= 0.13"
}

provider "aws" {
  version = "~> 2.12"
  region = var.aws_region

  assume_role {
    role_arn     = var.aws_provider_role_arn
    session_name = "Deployment"
    external_id  = var.aws_role_arn_external_id
  }
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}