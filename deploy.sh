docker-compose down

docker-compose up --build -d

echo "Waiting for services to start..."
sleep 10

docker-compose ps

echo "Application logs:"
docker-compose logs app

echo "Deployment completed. Monitoring services..."

docker-compose logs -f