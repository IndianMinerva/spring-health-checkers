mvn clean package -DskipTests=true && docker kill $(docker ps -q); docker system prune -a --force && docker build -t app . &&  docker-compose -f docker/docker-compose.yml up