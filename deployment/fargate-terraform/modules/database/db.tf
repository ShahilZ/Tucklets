resource "aws_db_instance" "postgres" {
  allocated_storage    = 20
  storage_type         = "gp2"
  engine               = "postgres"
  engine_version       = "12.3"
  instance_class       = "db.t2.micro"
  identifier           = "${var.app_name}-db-1"
  username             = local.db_creds.username
  password             = local.db_creds.password

  skip_final_snapshot  = true

  parameter_group_name = "default.postgres12"
  publicly_accessible  = true
  vpc_security_group_ids = [aws_security_group.tucklets_db_access.id]

}
