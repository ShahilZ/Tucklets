resource "aws_dynamodb_table" "terraform_locks_table" {
  name         = "tucklets-terraform-state-locks-table"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "LockID"
  attribute {
    name = "LockID"
    type = "S"
  }
}