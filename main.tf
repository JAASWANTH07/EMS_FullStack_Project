terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.aws_region
}

# ---------------- SECURITY GROUP ----------------
resource "aws_security_group" "ems_sg" {
  name        = "ems-sg"
  description = "Allow HTTP, App, and SSH access"

  ingress {
    description = "HTTP (Frontend)"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "Spring Boot Backend"
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "SSH Access"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# ---------------- AMI (Amazon Linux 2) ----------------
data "aws_ami" "amazon_linux2" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn2-ami-hvm-*-x86_64-gp2"]
  }
}

# ---------------- IAM ROLE + PROFILE ----------------
resource "aws_iam_role" "ec2_role" {
  name = "ems-ec2-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "ec2.amazonaws.com"
      }
    }]
  })
}

resource "aws_iam_role_policy_attachment" "ssm_core" {
  role       = aws_iam_role.ec2_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}

resource "aws_iam_instance_profile" "ec2_profile" {
  name = "ems-ec2-profile"
  role = aws_iam_role.ec2_role.name
}

# ---------------- EC2 INSTANCE ----------------
resource "aws_instance" "ems_server" {
  ami                    = data.aws_ami.amazon_linux2.id
  instance_type          = var.instance_type
  key_name               = "linux-pwd"
  vpc_security_group_ids = [aws_security_group.ems_sg.id]
  iam_instance_profile   = aws_iam_instance_profile.ec2_profile.name
  associate_public_ip_address = true

  user_data = <<-EOF
              #!/bin/bash
              exec > >(tee /var/log/user-data.log|logger -t user-data -s 2>/dev/console) 2>&1
              set -x
              
              yum update -y
              amazon-linux-extras install docker -y
              systemctl start docker
              systemctl enable docker
              usermod -a -G docker ec2-user

              # Wait for Docker to be ready
              sleep 10

              # Pull and run EMS backend container
              docker pull ${var.docker_username}/ems-backend:latest
              docker rm -f ems-backend || true
              docker run -d \
                --name ems-backend \
                --restart unless-stopped \
                -p 8080:8080 \
                -e SPRING_DATASOURCE_URL="${var.datasource_url}" \
                -e SPRING_DATASOURCE_USERNAME="${var.datasource_username}" \
                -e SPRING_DATASOURCE_PASSWORD="${var.datasource_password}" \
                ${var.docker_username}/ems-backend:latest

              # Pull and run EMS frontend container
              docker pull ${var.docker_username}/ems-frontend:latest
              docker rm -f ems-frontend || true
              docker run -d \
                --name ems-frontend \
                --restart unless-stopped \
                -p 80:80 \
                ${var.docker_username}/ems-frontend:latest
              EOF

  tags = {
    Name = "ems-server"
  }
}

# ---------------- OUTPUTS ----------------
output "ec2_public_ip" {
  value       = aws_instance.ems_server.public_ip
  description = "Public IP of the EMS EC2 instance"
}

output "backend_url" {
  value       = "http://${aws_instance.ems_server.public_ip}:8080"
  description = "EMS Backend URL"
}

output "frontend_url" {
  value       = "http://${aws_instance.ems_server.public_ip}"
  description = "EMS Frontend URL"
}
