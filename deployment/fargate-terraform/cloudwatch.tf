resource "aws_cloudwatch_log_group" "tucklets_log_group" {
  name = "awslogs-tucklets"

  tags = {
    Application = "tucklets-service"
  }
}
