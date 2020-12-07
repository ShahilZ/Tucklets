# Entrypoint for terraform.

module "tucklets_service" {
  source = "./modules/service"

  ecr_url = aws_ecr_repository.tucklets_ecr.repository_url 

  app_name = var.app_name
  version_tag = var.version_tag
}
