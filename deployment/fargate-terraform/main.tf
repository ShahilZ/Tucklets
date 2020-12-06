# Entrypoint for terraform.

module "tucklets_service" {
  source = "./modules/service"

  ecr_url = aws_ecr_repository.tucklets_ecr.repository_url
  certificate_arn = module.routing.certificate_arn

  app_name = var.app_name
  paypal_client_id = var.paypal_client_id
  version_tag = var.version_tag
}

module "routing" {
  source = "./modules/routing"

  lb_dns_name = module.tucklets_service.lb_dns_name
  lb_zone_id = module.tucklets_service.lb_zone_id
}