

resource "aws_codepipeline" "agenda_cesep_pipeline" {
  name     = "agenda-cesep-pipeline"
  role_arn = aws_iam_role.codepipeline_role.arn

  artifact_store {
    location = aws_s3_bucket.codepipeline_bucket.bucket
    type     = "S3"
  }

  stage {
    name = "Source"

    action {
      name             = "Source"
      category         = "Source"
      owner            = "AWS"
      provider         = "CodeStarSourceConnection"
      version          = "1"
      output_artifacts = ["source_output"]

      configuration = {
        ConnectionArn    = aws_codestarconnections_connection.git_connection.arn
        FullRepositoryId = "Aline-Ferreira1980/TCC-1"
        BranchName       = "simplificado"
      }
    }
  }

  stage {
    name = "Build"

    action {
      name            = "BuildAction"
      category        = "Build"
      owner           = "AWS"
      provider        = "CodeBuild"
      version         = "1"
      configuration = {
        ProjectName = "agenda-cesep-codebuild-project" # Nome do projeto CodeBuild
      }
      input_artifacts = ["source_output"]
      output_artifacts = ["build_output"]
    }
  }

   stage {
    name = "Deploy"

    action {
      name             = "DeployAction"
      category         = "Deploy"
      owner            = "AWS"
      provider         = "CodeDeployToInstance"
      input_artifacts  = ["source_artifact"]
      version          = "1"

      configuration = {
        ApplicationName          = aws_codedeploy_deployment_group.cesep_agenda_deploygrup.app_name
        DeploymentGroupName      = aws_codedeploy_deployment_group.cesep_agenda_deploygrup.deployment_group_name
        S3Bucket                 = aws_s3_bucket.codepipeline_bucket.id
        FileExistsBehavior       = "OVERWRITE"
        DeploymentConfigName     = "CodeDeployDefault.OneAtATime"
      }
    }
  }
}

resource "aws_codestarconnections_connection" "git_connection" {
  name          = "git-connection"
  provider_type = "GitHub"
}

resource "aws_s3_bucket" "codepipeline_bucket" {
  bucket = "tcc-store-bucket"
}

resource "aws_s3_bucket_ownership_controls" "controls_pipeline_bucket" {
  bucket = aws_s3_bucket.codepipeline_bucket.id
  rule {
    object_ownership = "BucketOwnerPreferred"
  }
}

resource "aws_s3_bucket_acl" "codepipeline_bucket_acl" {
  depends_on = [aws_s3_bucket_ownership_controls.controls_pipeline_bucket]
  bucket = aws_s3_bucket.codepipeline_bucket.id
  acl    = "private"
}

data "aws_iam_policy_document" "assume_role" {
  statement {
    effect = "Allow"

    principals {
      type        = "Service"
      identifiers = ["codepipeline.amazonaws.com"]
    }

    actions = ["sts:AssumeRole"]
  }
}

resource "aws_iam_role" "codepipeline_role" {
  name               = "codepipeline-role"
  assume_role_policy = data.aws_iam_policy_document.assume_role.json
}

resource "aws_iam_role_policy" "codepipeline_policy" {
  name   = "codepipeline_policy"
  role   = aws_iam_role.codepipeline_role.id
  policy = data.aws_iam_policy_document.codepipeline_policy.json
}

resource "aws_codedeploy_deployment_group" "cesep_agenda_deploygrup" {
  app_name              = "cesep-agenda"             # Nome da sua aplicação no CodeDeploy
  deployment_group_name = "cesep-agenda-deploygrup"       # Nome do grupo de implantação
  service_role_arn      = aws_iam_role.codepipeline_role.arn # Papel de serviço do CodeDeploy

  deployment_style {
    deployment_option = "WITH_TRAFFIC_CONTROL"
    deployment_type   = "BLUE_GREEN"
  }

  ec2_tag_set {
    ec2_tag_filter {
      key   = "Name"       # Substitua pela chave de tag que você usou para suas instâncias EC2
      type  = "KEY_AND_VALUE"
      value = aws_instance.app_instance.tags.Name # Substitua pelo valor da tag que identifica sua instância EC2
    }
  }
  
  # Configurações adicionais, como políticas de implantação, regras de implantação, podem ser adicionadas aqui.
}