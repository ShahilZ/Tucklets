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