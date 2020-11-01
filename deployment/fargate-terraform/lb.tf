//// AWS Load-balancer.
//resource "aws_lb" "tucklets_lb" {
//  name               = "tucklets-lb"
//  internal           = false
//  load_balancer_type = "application"
//  security_groups    = [aws_security_group.lb_sg.id]
//  subnets            = [aws_subnet.public.id]
//
//  enable_deletion_protection = true
//
//  tags = {
//    Environment = "production"
//  }
//}

resource "aws_lb" "ecs_lb" {
  name                = "tucklets-lb"
  security_groups     = [aws_security_group.tucklets_lb.id]
  subnets             = data.aws_subnet_ids.default.ids
}

resource "aws_lb_target_group" "ecs_target_group" {
  name                = "tucklets-ecs-target-group"
  port                = "80"
  protocol            = "HTTP"
  vpc_id              = data.aws_vpc.default.id
  target_type         = "ip"

  health_check {
    healthy_threshold   = "5"
    unhealthy_threshold = "2"
    interval            = "30"
    matcher             = "200"
    path                = "/health"
    port                = "traffic-port"
    protocol            = "HTTP"
    timeout             = "5"
  }
}

resource "aws_alb_listener" "default_http_listener" {
  load_balancer_arn = aws_lb.ecs_lb.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    target_group_arn = aws_lb_target_group.ecs_target_group.arn
    type             = "forward"
  }
}