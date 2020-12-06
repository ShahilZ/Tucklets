resource "aws_ecs_cluster" "tucklets_cluster" {
  name = "${var.app_name}-cluster"
}
