pipeline {
    agent any

    environment {
        APP_NAME = 'telecom-api'
        REGISTRY = 'host.docker.internal:5200'
        IMAGE = "${REGISTRY}/${APP_NAME}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/santhoshsk3722/telecom-api.git'
            }
        }

        stage('Verify Tools') {
            steps {
                    echo "===== Java ====="
                    java -version
                    echo "===== Docker ====="
                    docker --version
                    echo "===== Docker Access ====="
                    docker ps
                    echo "===== Registry Connectivity ====="
                    curl -s http://${REGISTRY}/v2/_catalog
                
            }
        }

        stage('Build') {
            steps {
                    echo "===== Maven Build ====="
                    docker run --rm -v "$WORKSPACE:/workspace" -w /workspace maven:3.9-eclipse-temurin-17 mvn clean package -DskipTests
                    echo "===== Build Completed ====="
                    ls -lh target/
            }
        }

        stage('Docker Build') {
            steps {
                    echo "===== Building Docker Image ====="
                    docker build -t ${APP_NAME}:${BUILD_NUMBER} .
                    echo "===== Docker Image Created ====="
                    docker images ${APP_NAME}
            }
        }

        stage('Docker Tag') {
            steps {
                    echo "===== Tagging Docker Image ====="
                    docker tag ${APP_NAME}:${BUILD_NUMBER} ${IMAGE}:${BUILD_NUMBER}
                    docker tag ${APP_NAME}:${BUILD_NUMBER} ${IMAGE}:latest
                    echo "===== Tagged Images ====="
                    docker images ${IMAGE}
            }
        }

        stage('Docker Push') {
            steps {
                    echo "===== Pushing Version Tag ====="
                    docker push ${IMAGE}:${BUILD_NUMBER}
                    echo "===== Pushing Latest Tag ====="
                    docker push ${IMAGE}:latest
                    echo "===== Docker Push Completed ====="
            }
        }

        stage('Verify Registry') {
            steps {
                    echo "===== Registry Catalog ====="
                    curl -s http://${REGISTRY}/v2/_catalog
                    echo ""
                    echo "===== Telecom API Tags ====="
                    curl -s http://${REGISTRY}/v2/${APP_NAME}/tags/list
                    echo ""
            }
        }
    }

    post {
        success {
            echo 'Telecom API CI/CD pipeline completed successfully.'
        }
        failure {
            echo 'Telecom API CI/CD pipeline failed.'
        }
    }
}