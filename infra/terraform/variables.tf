variable "region" {
  description = "Define qual a região da isntancia"
  default     = "sa-east-1"

}

variable "name" {
  description = "Nome da instancia do servidor"
  default     = "agenda01"
}

variable "env" {
  description = "Ambeinte que o servidor suporta"
  default     = "prod"

}

variable "instance_type" {
  description = "AWS Instance Type - Define a configuração de hardware da máquina"
  default     = "t2.micro"
}

variable "repo" {
  description = "Repositorio da aplicação"
  default     = "https://github.com/Aline-Ferreira1980/TCC-1"
}


variable "aws_access_key_id" {
  description = "Access Key do usuário a ser usado"
  sensitive   = true

}

variable "aws_secret_key" {
  description = "Secret Key do usuário a ser usado"
  sensitive   = true

}

variable "aws_token" {
  description = "Secret Key do usuário a ser usado"
  sensitive   = true
}