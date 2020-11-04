resource "aws_ecr_repository" "tucklets_ecr" {
  name = "tucklets-images"
}

resource "aws_ecr_lifecycle_policy" "tucklets_ecr_lifecycle_policy" {
  repository = aws_ecr_repository.tucklets_ecr.name

  policy = <<EOF
{
    "rules": [
        {
            "rulePriority": 1,
            "description": "Keeping only 3 youngest images; expires the old ones",
            "selection": {
                "tagStatus": "untagged",
                "countType": "imageCountMoreThan",
                "countNumber": 3
            },
            "action": {
                "type": "expire"
            }
        }
    ]
}
EOF
}