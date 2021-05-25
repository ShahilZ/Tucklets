# Entrypoint for terraform.

module "tucklets_service" {
  source      = "./modules/service"
  app_name    = var.app_name
  version_tag = var.version_tag
  aws_region  = var.aws_region
}

module "tucklets_db" {
  source      = "./modules/database"
  app_name    = var.app_name
}
