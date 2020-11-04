// TODO: Pull from AWS.

resource "aws_security_group" "http" {
  name        = "tucklets-lb-http-sg"
  description = "access to the Application Load Balancer (ALB)"

  ingress {
    protocol    = "tcp"
    from_port   = 80
    to_port     = 80
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol    = "-1"
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "https" {
  name        = "tucklets-lb-https-sg"
  description = "access to the Application Load Balancer (ALB)"

  ingress {
    protocol    = "tcp"
    from_port   = 443
    to_port     = 443
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    protocol    = "-1"
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "ecs_tasks" {
  name        = "ecs-tasks-sg"
  description = "allow inbound access from the ALB only"

  ingress {
    protocol        = "tcp"
    from_port       = 8443
    to_port         = 8443
    cidr_blocks     = ["0.0.0.0/0"]
    security_groups = [aws_security_group.http.id, aws_security_group.https.id]
  }

  egress {
    protocol    = "-1"
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
}

//resource "aws_security_group" "api-ingress" {
//  name        = "api_ingress"
//  description = "Allow ingress to API"
//  vpc_id      = data.aws_vpc_default.id
//
//  ingress {
//    from_port   = 3000
//    to_port     = 3000
//    protocol    = "TCP"
//    cidr_blocks = ["0.0.0.0/0"]
//  }
//}