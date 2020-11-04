resource "aws_acm_certificate" "default" {
  domain_name       = var.domain_name
  validation_method = "DNS"
}