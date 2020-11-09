// Run this Terraform locally to set up Terraform backend; it is not part of the build pipeline.
provider "aws" {
  region = var.aws_region
  version = "~> 2.12"

  secret_key = var.aws_secret_key
  access_key = var.aws_access_key

}