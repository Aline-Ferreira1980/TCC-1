terraform {
  required_version = " >=1.2.2"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.62.0"
    }
  }
}

provider "aws" {
  region     = var.region

}