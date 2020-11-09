// Create all roles needed for service to run.


// TODO: Remove tf_state_agent
//resource "aws_iam_role" "tf_state_agent" {
//  name                = "terraform-state-agent"
//  assume_role_policy  = data.aws_iam_policy_document.tf-state-agent-policy.json
//}
//
//
//data "aws_iam_policy_document" "tf-state-agent-policy" {
//  statement {
//    actions = [
//      "s3:ListBucket",
//      "s3:GetObject",
//      "s3:PutObject"
//    ]
//
//    resources = [
//      "arn:aws:s3:::tucklets-terraform-state",
//      "arn:aws:s3:::tucklets-terraform-state/global/s3/terraform.tfstate"
//    ]
//  }
//
//  statement {
//    actions = [
//      "dynamodb:GetItem",
//      "dynamodb:PutItem",
//      "dynamodb:DeleteItem"
//    ]
//    resources = ["arn:aws:dynamodb:*:*:table/tucklets-terraform-state-locks-table"]
//  }

//}