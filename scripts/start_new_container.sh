#!/bin/bash

AWS_REGION=$(aws ssm get-parameter --name "/nanuming/AWS_REGION" --query "Parameter.Value" --output text)
DEFAULT_IMAGE_REPOSITORY_URI=$(aws ssm get-parameter --name "/nanuming/DEFAULT_IMAGE_REPOSITORY_URI" --query "Parameter.Value" --output text)
IMAGE_REPOSITORY_URI=$(aws ssm get-parameter --name "/nanuming/IMAGE_REPOSITORY_URI" --query "Parameter.Value" --output text)
DB_URL=$(aws ssm get-parameter --name "/nanuming/DB_URL" --with-decryption --query "Parameter.Value" --output text)
DB_USER=$(aws ssm get-parameter --name "/nanuming/DB_USER" --with-decryption --query "Parameter.Value" --output text)
DB_PASSWORD=$(aws ssm get-parameter --name "/nanuming/DB_PASSWORD" --with-decryption --query "Parameter.Value" --output text)
S3_BASE_URL=$(aws ssm get-parameter --name "/nanuming/S3_BASE_URL" --with-decryption --query "Parameter.Value" --output text)
MAIN_IMAGE_PATH=$(aws ssm get-parameter --name "/nanuming/MAIN_IMAGE_PATH" --with-decryption --query "Parameter.Value" --output text)
CONFIRM_IMAGE_PATH=$(aws ssm get-parameter --name "/nanuming/CONFIRM_IMAGE_PATH" --with-decryption --query "Parameter.Value" --output text)
IOS_CLIENT_ID=$(aws ssm get-parameter --name "/nanuming/IOS_CLIENT_ID" --with-decryption --query "Parameter.Value" --output text)



aws ecr get-login-password --region "$AWS_REGION" | docker login --username aws --password-stdin "$DEFAULT_IMAGE_REPOSITORY_URI"

docker pull "$IMAGE_REPOSITORY_URI":latest

docker run -d --name nanuming-server \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_URL="$DB_URL" \
  -e DB_USER="$DB_USER" \
  -e DB_PASSWORD="$DB_PASSWORD" \
  -e S3_BASE_URL="$S3_BASE_URL" \
  -e MAIN_IMAGE_PATH="$MAIN_IMAGE_PATH" \
  -e CONFIRM_IMAGE_PATH="$CONFIRM_IMAGE_PATH" \
  -e IOS_CLIENT_ID="$IOS_CLIENT_ID" \
  "$IMAGE_REPOSITORY_URI":latest