resource "aws_db_instance" "postgres" {
  allocated_storage         = 20
  max_allocated_storage     = 40
  storage_type              = "gp2"
  engine                    = "postgres"
  engine_version            = "12.3"
  instance_class            = "db.t2.micro"
  identifier                = "${var.app_name}-db-1"
  username                  = local.db_creds.username
  password                  = local.db_creds.password

  name                      = var.app_name


  # Must be set if we are not skipping final snapshots.
  final_snapshot_identifier = "tucklets-db-snapshot-final"
  parameter_group_name      = "default.postgres12"
  publicly_accessible       = true
  vpc_security_group_ids    = [aws_security_group.tucklets_db_access.id]

  # Backups/maintenance
  backup_window             = "00:30-01:30"
  backup_retention_period   = 7
  maintenance_window        = "Sun:02:00-Sun:03:00"



}
