// Routing variables
variable "domain_name" {
  description = "Name of the service's domain"
  default     = "tucklets.net"
}

variable "record_ttl" {
  description = "TTL (Time-to-live) for a DNS record"
  default     = 60
}
