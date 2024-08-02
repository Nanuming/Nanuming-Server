#!/bin/bash

aws ecr get-login-password --region "$AWS_REGION" | docker login --username AWS --password-stdin "$DEFAULT_IMAGE_REPOSITORY_URI"

docker pull "$IMAGE_REPOSITORY_URI":latest

docker run -d --name nanuming-server \
  -e SPRING_PROFILES_ACTIVE=prod \
  "$IMAGE_REPOSITORY_URI":latest