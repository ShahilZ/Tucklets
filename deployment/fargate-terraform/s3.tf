//# S3 configurations for the service.

resource "aws_s3_bucket" "tucklets_images" {
  bucket            = "tucklets-images"
  acl               = "public-read"

  hosted_zone_id = module.tucklets_service.hosted_zone_id

  // No need for versioning
  versioning {
    enabled         = false
  }

  tags = {
    Environment     = "Prod"
  }
}


resource "aws_s3_bucket" "tucklets_images_dev" {
  bucket            = "tucklets-images-dev"
  acl               = "public-read"

  hosted_zone_id = module.tucklets_service.hosted_zone_id

  // No need for versioning
  versioning {
    enabled         = false
  }

  tags = {
    Environment     = "Dev"
  }
}

resource "aws_s3_bucket" "tucklets_newsletters" {
  bucket            = "tucklets-newsletters"
  acl               = "public-read"

  hosted_zone_id = module.tucklets_service.hosted_zone_id

  // No need for versioning
  versioning {
    enabled         = false
  }

  tags = {
    Environment     = "Prod"
  }
}

resource "aws_s3_bucket" "tucklets_newsletters_dev" {
  bucket            = "tucklets-newsletters-dev"
  acl               = "public-read"

  hosted_zone_id = module.tucklets_service.hosted_zone_id

  // No need for versioning
  versioning {
    enabled         = false
  }

  tags = {
    Environment     = "Dev"
  }
}
