# SES configuration to send emails.

resource "aws_ses_domain_identity" "tucklets" {
  domain = var.domain_name
}

resource "aws_ses_email_identity" "no_reply"{
  email = "no-reply@${aws_ses_domain_identity.tucklets.domain}"
}

resource "aws_route53_record" "service_amazonses_verification_record" {
  zone_id = aws_route53_zone.tucklets_public_zone.id
  name    = "_amazonses.${var.domain_name}"
  type    = "TXT"
  ttl     = "300"
  records = [aws_ses_domain_identity.tucklets.verification_token]
}


resource "aws_ses_domain_mail_from" "service_domain_from" {
  domain           = aws_ses_domain_identity.tucklets.domain
  mail_from_domain = "mail.${aws_ses_domain_identity.tucklets.domain}"
}

# Route53 MX record
resource "aws_route53_record" "service_ses_domain_mail_from_mx" {
  zone_id = aws_route53_zone.tucklets_public_zone.id
  name    = aws_ses_domain_mail_from.service_domain_from.mail_from_domain
  type    = "MX"
  ttl     = "300"
  records = ["10 feedback-smtp.${var.aws_region}.amazonses.com"] # Change to the region in which `aws_ses_domain_identity.example` is created
}

# Route53 TXT record for SPF (prevents emails from going to spam)
resource "aws_route53_record" "service_ses_domain_mail_from_txt" {
  zone_id = aws_route53_zone.tucklets_public_zone.id
  name    = aws_ses_domain_mail_from.service_domain_from.mail_from_domain
  type    = "TXT"
  ttl     = "300"
  records = ["v=spf1 include:amazonses.com -all"]
}