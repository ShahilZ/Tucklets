resource "aws_route53_record" "service_record" {
  zone_id                  = aws_route53_zone.tucklets_public_zone.zone_id
  type                     = "A"
  name                     = ""
  alias {
    name                   = aws_lb.ecs_lb.dns_name
    zone_id                = aws_lb.ecs_lb.zone_id
    evaluate_target_health = true
  }
}

# Self-validation of certificate
resource "aws_route53_record" "cert_validation" {
  name = aws_acm_certificate.default.domain_validation_options.0.resource_record_name
  type = aws_acm_certificate.default.domain_validation_options.0.resource_record_type
  zone_id = aws_route53_zone.tucklets_public_zone.zone_id
  records = [aws_acm_certificate.default.domain_validation_options.0.resource_record_value]
  ttl = var.record_ttl
}

# Add mail exchange servers.
resource "aws_route53_record" "mail_exchange" {
  zone_id                  = aws_route53_zone.tucklets_public_zone.zone_id
  type                     = "MX"
  name                     = ""

  records = [
    "10 smtp.secureserver.net",
    "10 mailstore1.secureserver.net",
  ]

  ttl = var.record_ttl
}