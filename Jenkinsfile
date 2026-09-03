pipeline {
    agent any

    environment {
        IMAGE_NAME = 'telecom-api'
        REGISTRY = 'host.docker.internal:5200'
    }

    stages {
        stage('Verify Tools') {
            steps {
                sh """
                    echo "===== Java ====="
                    java -version

                    echo "===== Docker ====="
                    docker --version

                    echo "===== Docker Access ====="
                    docker ps

                    echo "===== Registry Connectivity ====="
                    curl -s http://${REGISTRY}/v2/_catalog
                """
            }
        }

        stage('Build') {
            steps {
                sh """
                    echo "===== Maven Build ====="

                    if command -v mvn >/dev/null 2>&1; then
                        mvn clean package -DskipTests
                    else
                        echo "Maven not found on Jenkins agent."
                        echo "Using Maven Docker image for build."
                        
                        docker run --rm -v "${WORKSPACE}:/workspace" -w /workspace maven:3.9-eclipse-temurin-17 mvn clean package -DskipTests
                    fi
                """
            }
        }

        stage('Docker Build') {
            steps {
                sh """
                    echo "===== Building Docker Image ====="

                    docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} .

                    echo "===== Docker Image Created ====="
                    docker images ${IMAGE_NAME}
                """
            }
        }

        stage('Docker Tag') {
            steps {
                sh """
                    echo "===== Tagging Docker Image ====="

                    docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}
                    docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${REGISTRY}/${IMAGE_NAME}:latest

                    echo "===== Tagged Images ====="
                    docker images ${REGISTRY}/${IMAGE_NAME}
                """
            }
        }

        stage('Docker Push') {
            steps {
                sh """
                    echo "===== Pushing Versioned Image ====="
                    docker push ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}

                    echo "===== Pushing Latest Image ====="
                    docker push ${REGISTRY}/${IMAGE_NAME}:latest

                    echo "===== Registry Contents ====="
                    curl -s http://${REGISTRY}/v2/_catalog

                    echo ""
                    echo "===== Image Tags ====="
                    curl -s http://${REGISTRY}/v2/${IMAGE_NAME}/tags/list
                    echo ""
                """
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