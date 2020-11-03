resource "aws_acm_certificate" "default" {
  domain_name       = "tucklets.com"
  validation_method = "DNS"
}