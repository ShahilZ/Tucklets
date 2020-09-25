
// AWS vars
variable "aws_region" {
  type = string
  description = "AWS Region"
  default = "us-west-2"
}

variable "aws_provider_role_arn" {
  type = string
  description = "AWS Provider's role arn"
}

variable "aws_role_arn_external_id" {
  type = string
  description = "External id for aws provider's role_arn."
}

variable "cidr_block" {
  default     = "10.0.0.0/16"
  type        = string
  description = "CIDR block for the VPC"
}

variable "public_subnet_cidr_blocks" {
  default     = ["10.0.0.0/24", "10.0.2.0/24"]
  type        = list(string)
  description = "List of public subnet CIDR blocks"
}

variable "private_subnet_cidr_blocks" {
  default     = ["10.0.1.0/24", "10.0.3.0/24"]
  type        = list(string)
  description = "List of private subnet CIDR blocks"
}

variable "availability_zones" {
  default     = ["us-west-2a", "us-west-2b"]
  type        = list(string)
  description = "List of availability zones"
}