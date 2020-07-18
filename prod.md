# api server

cd api

./gradlew -Pprod clean bootJar

docker-compose -f src/main/docker/mysql.yml up -d
docker-compose -f src/main/docker/elasticsearch.yml up -d


java -jar build/libs/*.jar > ../../api-lyrics.log

port will be on 8380 by default

# app

ionic serve --prod --no-open --port 8300 -- --public-host lyrics.fafafashop.com > ../../app-lyrics.log


