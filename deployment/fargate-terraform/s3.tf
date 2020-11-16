//# S3 configurations for the service.
//
//resource "aws_s3_bucket" "tucklets_images" {
//  bucket            = "tucklets-images"
//  acl               = "public-read"
//
//  cors_rule {
//    allowed_headers = ["*"]
//    allowed_methods = ["PUT", "POST"]
//    allowed_origins = ["https://tucklets.net"]
//  }
//
//  tags = {
//    Environment     = "Prod"
//  }
//}
//
//
//resource "aws_s3_bucket" "tucklets_images_dev" {
//  bucket            = "tucklets-images-dev"
//  acl               = "public-read"
//
//  cors_rule {
//    allowed_headers = ["*"]
//    allowed_methods = ["PUT", "POST"]
//    allowed_origins = ["https://tucklets.net"]
//  }
//
//  tags = {
//    Environment     = "Dev"
//  }
//}
//
//resource "aws_s3_bucket" "tucklets_newsletters_dev" {
//  bucket            = "tucklets-newsletters-dev"
//  acl               = "public-read"
//
//  cors_rule {
//    allowed_headers = ["*"]
//    allowed_methods = ["PUT", "POST"]
//    allowed_origins = ["https://tucklets.net"]
//  }
//
//  tags = {
//    Environment     = "Dev"
//  }
//}