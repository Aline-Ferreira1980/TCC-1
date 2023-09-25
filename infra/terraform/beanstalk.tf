resource "aws_elastic_beanstalk_application" "agenda_cesep" {
  name = "agenda-cesep"
}

resource "aws_elastic_beanstalk_environment" "agenda_cesep_environment" {
  name                = "agenda-cesep-environment"
  application         = aws_elastic_beanstalk_application.agenda_cesep.name
  solution_stack_name = "64bit Amazon Linux 2023 v4.0.1 running Corretto 17"
  wait_for_ready_timeout  = "40m"
  setting {
      namespace = "aws:autoscaling:launchconfiguration"
      name      = "IamInstanceProfile"
      value     = "TerraformManageRole"
    }
  setting {
    namespace = "aws:ec2:vpc"
    name      = "VPCId"
    value     = "vpc-0d9eaa24ee72917d8"
  }

  setting {
    namespace = "aws:ec2:vpc"
    name      = "Subnets"
    value     = "subnet-07019b2a31d55ebbd"
  }
}