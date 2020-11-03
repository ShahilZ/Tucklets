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