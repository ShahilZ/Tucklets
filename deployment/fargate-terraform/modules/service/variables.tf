// Service variables
variable "service_name" {
  default     = "tucklets-service"
  description = "Name of service"
}

variable "service_desired_task_count" {
  default     = 1
  description = "Desired number of tasks"
}

variable "service_task_cpu_count" {
  default     = 512
  description = "Number of cpus for service task"
}

variable "service_task_memory" {
  default     = 1024
  description = "Memory size for task"
}

// Routing variables
variable "domain_name" {
  description = "Name of the service's domain"
  default     = "tucklets.net"
}

variable "record_ttl" {
  description = "TTL (Time-to-live) for a DNS record"
  default     = 60
}

variable "app_name" {
  description = "Name of application"
}

variable "version_tag" {
  description = "Version tag of the built image (passed in via pipeline)"
}

variable "aws_region" {
  type = string
  description = "AWS Region"
  default = "us-west-2"
}