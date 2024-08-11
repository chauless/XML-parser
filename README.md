# SmartForm Application Documentation

## Features

- **Data Downloading**: The application downloads XML files from a specified URL, extracts relevant data, and processes it.
- **Data Processing**: The application processes the downloaded XML content to extract village and village part information and stores it in the database.
- **Asynchronous Processing with Kafka**: The application uses Apache Kafka for messaging, allowing the data download and data processing to be decoupled into separate services.
- **REST API**: The application provides a REST API endpoint to trigger the download and processing of data.
- **Dockerized Deployment**: The application is fully containerized using Docker, allowing easy deployment and scaling.

## Technologies Used

- **Spring Boot**: The backbone of the application, used to create the REST API, manage dependencies, and set up application components.
- **Apache Kafka**: Used for messaging between the services, enabling asynchronous data processing.
- **PostgreSQL**: A relational database used to store village and village part information.
- **Docker**: The application is containerized using Docker, allowing easy setup and deployment.
- **JUnit**: Used for unit testing the application's components.

## Setting Up and Running the Application

### Prerequisites

- **Docker**: Ensure Docker is installed on your machine.
- **Kafka**: The application requires a running Kafka instance.
- **PostgreSQL**: A running PostgreSQL instance is required for data storage.

### Running the Application

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-repository/smartform.git
   cd smartform
   ```

2. **Deploy the application:**

Run the deploy.sh script to build and start all necessary Docker containers (PostgreSQL, Kafka, Zookeeper, and the Spring Boot application).

```bash
./deploy.sh
```

3. **Access the application:**

Once the deployment is complete, you can access the application at:

```bash
http://localhost:8081/download
```
## Configuration

The application is pre-configured using application.yml and environment variables set in the docker-compose.yml file. However, you can adjust configurations if necessary.
