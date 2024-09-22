#!/bin/bash

AWS_REGION=$(aws ssm get-parameter --name "/nanuming/AWS_REGION" --query "Parameter.Value" --output text)
DEFAULT_IMAGE_REPOSITORY_URI=$(aws ssm get-parameter --name "/nanuming/DEFAULT_IMAGE_REPOSITORY_URI" --query "Parameter.Value" --output text)
IMAGE_REPOSITORY_URI=$(aws ssm get-parameter --name "/nanuming/IMAGE_REPOSITORY_URI" --query "Parameter.Value" --output text)

aws ecr get-login-password --region "$AWS_REGION" | docker login --username AWS --password-stdin "$DEFAULT_IMAGE_REPOSITORY_URI"

docker pull "$IMAGE_REPOSITORY_URI":latest

docker run -d --name nanuming-server \
  -e SPRING_PROFILES_ACTIVE=prod \
  -p 8080:8080 \
  "$IMAGE_REPOSITORY_URI":latest