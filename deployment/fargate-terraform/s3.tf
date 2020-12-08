//# S3 configurations for the service.

resource "aws_s3_bucket" "tucklets_public" {
  bucket            = "tucklets-public"
  acl               = "public-read"

  hosted_zone_id = module.tucklets_service.hosted_zone_id

  // Recommended to version buckets.
  versioning {
    enabled         = true
  }

  tags = {
    Environment     = "Prod"
  }
}


resource "aws_s3_bucket" "tucklets_public_dev" {
  bucket            = "tucklets-public-dev"
  acl               = "public-read"

  hosted_zone_id = module.tucklets_service.hosted_zone_id

  // Recommended to version buckets.
  versioning {
    enabled         = true
  }

  tags = {
    Environment     = "Dev"
  }
}


resource "aws_s3_bucket_policy" "public_read_prod" {
  bucket = aws_s3_bucket.tucklets_public.id

  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Id": "prod_tucklets_public_policy",
  "Statement": [
    {
      "Sid":"PublicRead",
      "Effect":"Allow",
      "Principal": "*",
      "Action":["s3:GetObject","s3:GetObjectVersion"],
      "Resource":["${aws_s3_bucket.tucklets_public.arn}/*"]
    }
  ]
}
POLICY
}

resource "aws_s3_bucket_policy" "public_read_dev" {
  bucket = aws_s3_bucket.tucklets_public_dev.id

  policy = <<POLICY
{
  "Version": "2012-10-17",
  "Id": "prod_tucklets_public_policy",
  "Statement": [
    {
      "Sid":"PublicRead",
      "Effect":"Allow",
      "Principal": "*",
      "Action":["s3:GetObject","s3:GetObjectVersion"],
      "Resource":["${aws_s3_bucket.tucklets_public_dev.arn}/*"]
    }
  ]
}
POLICY
}