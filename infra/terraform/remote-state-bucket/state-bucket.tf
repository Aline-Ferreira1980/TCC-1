# module "remote-state-bucket" - defines the S3 bucket for Terraform state

data "aws_caller_identity" "current" {

}

resource "aws_s3_bucket" "remote_state" {
  bucket = "tfstate-${data.aws_caller_identity.current.account_id}"

  tags = {
    description = "Terraform State Bucket"
    env         = "Prod"
    repo        = var.repo
  }
}

resource "aws_s3_bucket_ownership_controls" "controls_remote_state" {
  bucket = aws_s3_bucket.remote_state.id
  rule {
    object_ownership = "BucketOwnerPreferred"
  }
}

resource "aws_s3_bucket_acl" "acl_remote_state" {
  depends_on = [aws_s3_bucket_ownership_controls.controls_remote_state]

  bucket = aws_s3_bucket.remote_state.id
  acl    = "private"
}

resource "aws_s3_bucket_versioning" "versioning_remote_state" {
  bucket = aws_s3_bucket.remote_state.id
  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_dynamodb_table" "lock" {
  name = "terraform-state-lock"
  billing_mode = "PAY_PER_REQUEST"
  hash_key = "LockID"
  attribute {
    name = "LockID"
    type = "S"
  }

    tags = {
    description = "Terraform State Lock"
    env         = "Prod"
    repo        = var.repo
  }
}

output "lock_table" {
  value = aws_dynamodb_table.lock.id
}

output "bucket_name" {
  value = aws_s3_bucket.remote_state.id
}

output "bucket_arn" {
  value = aws_s3_bucket.remote_state.arn
}