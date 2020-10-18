// AWS vars
variable "aws_region" {
  type = string
  description = "AWS Region"
  default = "us-west-2"
}

variable "aws_access_key" {
  type = string
  description = "AWS Access Key"
}

variable "aws_secret_key" {
  type = string
  description = "AWS Secret Key"
}