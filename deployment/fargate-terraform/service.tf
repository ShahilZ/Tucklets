resource "aws_ecs_service" "tucklets_service" {
  name            = var.service_name
  cluster         = aws_ecs_cluster.tucklets_cluster.id
  task_definition = aws_ecs_task_definition.ecs_task.arn
  desired_count   = var.service_desired_task_count
  launch_type     = "FARGATE"

  network_configuration {
    security_groups  = [aws_security_group.ecs_tasks.id]
    subnets          = data.aws_subnet_ids.default.ids
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.ecs_target_group.arn
    container_name   = var.app_name
    container_port   = 8083
  }

  depends_on = [
    aws_alb_listener.default_http_listener
  ]

  //tags?
}


resource "aws_ecs_task_definition" "ecs_task" {
  family                   = var.service_name
  container_definitions    = data.template_file.container_image.rendered
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = var.service_task_cpu_count
  memory                   = var.service_task_memory
  task_role_arn            = aws_iam_role.ecs_task_role.arn
  execution_role_arn       = data.aws_iam_role.task_execution_role.arn
}

data "template_file" "container_image" {
  template = file("task-definitions/service.json")
  vars = {
    image_name             = var.app_name
    ecr_repo               = aws_ecr_repository.tucklets_ecr.repository_url
    version_tag            = var.version_tag
    port_url               = var.port_url
    service_email          = var.service_email
    service_email_password = var.service_email_password
    postgres_user          = var.postgres_user
    postgres_creds         = var.postgres_creds
    s3_bucket_name         = var.s3_bucket_name
  }
}