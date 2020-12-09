# IAM Roles

// Assumes role for other Amazon services.
resource "aws_iam_role" "ecs_task_role" {
  name                = "${var.service_name}-task-role"
  assume_role_policy  = data.aws_iam_policy_document.ecs_service_assume_role_policy.json
}

# Default policy for assumeRole (so ECS can use any task role).
data "aws_iam_policy_document" "ecs_service_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
  }
}

// Default AWS Task Execution Role.
data "aws_iam_role" "task_execution_role" {
  name = "ecsTaskExecutionRole"
}

// Policy for actual service task role
data "aws_iam_policy_document" "ecs_task_role_policy_document" {
  statement {
    effect = "Allow"
    actions   = [
      "secretsmanager:GetResourcePolicy",
      "secretsmanager:GetSecretValue",
      "secretsmanager:DescribeSecret",
      "secretsmanager:ListSecretVersionIds",
      "s3:*Object"
    ]
    resources = ["*"]
  }
}

resource "aws_iam_role_policy" "ecs_task_role_policy" {
  name        = "${var.app_name}-task-role-policy"
  policy = data.aws_iam_policy_document.ecs_task_role_policy_document.json
  role = aws_iam_role.ecs_task_role.id
}