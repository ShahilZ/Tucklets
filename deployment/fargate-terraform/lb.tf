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
  security_groups     = [aws_security_group.http.id, aws_security_group.https.id]
  subnets             = data.aws_subnet_ids.default.ids
}

resource "aws_lb_target_group" "ecs_https_target_group" {
  name                  = "tucklets-ecs-https-target-group"
  port                  = "443"
  protocol              = "HTTPS"
  vpc_id                = data.aws_vpc.default.id
  target_type           = "ip"

  stickiness {
    type                = "lb_cookie"
    enabled             = true
  }

  health_check {
    healthy_threshold   = "5"
    unhealthy_threshold = "3"
    interval            = "45"
    matcher             = "200"
    path                = "/health"
    port                = "traffic-port"
    protocol            = "HTTPS"
    timeout             = "5"
  }
}

resource "aws_alb_listener" "default_http_listener" {
  load_balancer_arn  = aws_lb.ecs_lb.arn
  port               = "80"
  protocol           = "HTTP"

  default_action {
    type = "redirect"

    redirect {
      port           = "443"
      protocol       = "HTTPS"
      status_code    = "HTTP_301"
    }
  }
}

resource "aws_alb_listener" "default_https_listener" {
  load_balancer_arn  = aws_lb.ecs_lb.id
  port               = "443"
  protocol           = "HTTPS"
  ssl_policy         = "ELBSecurityPolicy-2016-08"
  certificate_arn    = aws_acm_certificate.default.arn

  default_action {
    target_group_arn = aws_lb_target_group.ecs_https_target_group.arn
    type             = "forward"
  }
}