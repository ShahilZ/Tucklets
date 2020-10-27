# IAM Roles

// Assumes role for other Amazon services.
resource "aws_iam_role" "ecs_task_role" {
  name                = "${var.service_name}-task-role"
  assume_role_policy  = data.aws_iam_policy_document.ecs_service_policy.json
}

data "aws_iam_policy_document" "ecs_service_policy" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ecs.amazonaws.com"]
    }
  }
}

// Default AWS Task Execution Role.
data "aws_iam_role" "task_execution_role" {
  name = "ecsTaskExecutionRole"
}
