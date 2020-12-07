resource "aws_security_group" "tucklets_db_access" {
  name        = "tucklets-db-access-sg"
  description = "Allows public access to Tucklets from external clients"
  vpc_id      = data.aws_vpc.default_vpc.id

  ingress {
    description = "Inbound rules"
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]

  }

  egress {
    description = "Outbound rules: public for access via external clients"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

}