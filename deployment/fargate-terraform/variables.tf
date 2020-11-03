
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

variable "aws_provider_role_arn" {
  type = string
  description = "AWS Provider's role arn"
}

variable "aws_role_arn_external_id" {
  type = string
  description = "External id for aws provider's role_arn."
}

// Network variables
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

// Service variables
variable "service_name" {
  default     = "tucklets-service"
  description = "Name of service"
}

variable "service_desired_task_count" {
  default     = 2
  description = "Desired number of tasks"
}

variable "service_task_cpu_count" {
  default     = 1024
  description = "Number of cpus for service task"
}

variable "service_task_memory" {
  default     = 2048
  description = "Memory size for task"
}

// Routing variables
variable "domain_name" {
  description = "Name of the service's domain"
  default = "tucklets.tf"
}

// App variables
variable "app_name" {
  default     = "tucklets"
  description = "Name of application"
}

variable "version_tag" {
  description = "Version tag of the built image (passed in via pipeline)"
}

variable "port_url" {
  description = "Port URL for postgres"
}

variable "service_email" {
  description = "Email address used by the service to send emails"
}

variable "service_email_password" {
  description = "Password for the email used by the service"
}

variable "postgres_user" {
  description = "Postgres username"
}

variable "postgres_creds" {
  description = "Postgres user's password"
}

variable "s3_bucket_name" {
  description = "Name of the s3 bucket"
}