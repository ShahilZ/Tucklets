# Entrypoint for terraform.

module "tucklets_service" {
  source = "./modules/service"

  ecr_url = aws_ecr_repository.tucklets_ecr.repository_url 

  app_name = var.app_name
  paypal_client_id = var.paypal_client_id
  version_tag = var.version_tag
}
