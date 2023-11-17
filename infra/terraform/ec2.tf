

resource "aws_security_group" "app_sg" {
  name        = "app-sg"
  description = "Security group for the Spring Boot app"
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "app_instance" {
  ami           = data.aws_ami.ubuntu.id
  instance_type = var.instance_type
  security_groups = [aws_security_group.app_sg.name]

  tags = {
    Name        = var.name
    Environment = var.env
    Provisioner = "Terraform"
    Repo        = var.repo
  }

}


# sudo apt update
#  sudo apt install openjdk-19-jre -y
#  sudo apt install openjdk-19-jdk -y
# sudo groupadd -r cesep
# sudo useradd -r -g cesep cesep
# sudo mkdir /var/www
# sudo chown cesep:cesep /var/www
# /usr/bin/java -jar /var/www/gestao_agenda_backend-0.0.1-spring-boot.jar


# https://github.com/NecoHorne/springboot-ubuntu-nginx