terraform {
  required_version = " >=1.2.2"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.62.0"
    }
  }

  backend "s3" {
    bucket         = "tfstate-430926056547"
    key            = "terraform.tfstate"
    region         = "sa-east-1"
    dynamodb_table = "terraform-state-lock"
      access_key = "AKIAWIVJRZRRS4CUOVMN"
    secret_key = "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"
  }
}

provider "aws" {
  region = "sa-east-1"
}
