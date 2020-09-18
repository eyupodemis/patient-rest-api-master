if [[ "$(docker images -q mysql:latest 2> /dev/null)" == "" ]]; then
  docker pull mysql
fi

if [[ "$(docker images -q tomee:latest 2> /dev/null)" == "" ]]; then
  docker pull tomee
fi

docker network create dedalus-network || echo "Network exists"

docker-compose down -v

mvn clean package

docker rmi app:v1

docker-compose up -d db

sleep 10

docker-compose up -d web





