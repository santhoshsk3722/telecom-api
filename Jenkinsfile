pipeline {

    agent any

    environment {
        APP_NAME = "telecom-api"
        REGISTRY = "host.docker.internal:5200"
        IMAGE = "${REGISTRY}/${APP_NAME}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/santhoshsk3722/telecom-api.git'
            }
        }

        stage('Verify Tools') {
            steps {
                sh '''
                    echo "===== Java ====="
                    java -version

                    echo "===== Docker ====="
                    docker --version

                    echo "===== Docker Containers ====="
                    docker ps

                    echo "===== Registry ====="
                    curl -s http://${REGISTRY}/v2/_catalog
                '''
            }
        }

        stage('Build') {
            agent {
                docker {
                    image 'maven:3.9-eclipse-temurin-17'
                    reuseNode true
                }
            }

            steps {
                sh '''
                    echo "===== Maven Build ====="
                    mvn clean package -DskipTests

                    echo "===== Build Artifact ====="
                    ls -lh target/

                    test -f target/telecom-api-0.0.1-SNAPSHOT.jar
                '''
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    echo "===== Building Docker Image ====="

                    docker build \
                        -t ${APP_NAME}:${BUILD_NUMBER} \
                        .

                    echo "===== Docker Images ====="

                    docker images ${APP_NAME}
                '''
            }
        }

        stage('Docker Tag') {
            steps {
                sh '''
                    echo "===== Tagging Docker Image ====="

                    docker tag \
                        ${APP_NAME}:${BUILD_NUMBER} \
                        ${IMAGE}:${BUILD_NUMBER}

                    docker tag \
                        ${APP_NAME}:${BUILD_NUMBER} \
                        ${IMAGE}:latest

                    echo "===== Tagged Images ====="

                    docker images ${IMAGE}
                '''
            }
        }

        stage('Docker Push') {
            steps {
                sh '''
                    echo "===== Pushing Docker Image ====="

                    docker push ${IMAGE}:${BUILD_NUMBER}

                    docker push ${IMAGE}:latest

                    echo "===== Registry Contents ====="

                    curl -s http://${REGISTRY}/v2/_catalog

                    echo ""

                    echo "===== Image Tags ====="

                    curl -s http://${REGISTRY}/v2/${APP_NAME}/tags/list

                    echo ""
                '''
            }
        }
    }

    post {

        success {
            echo """
            ==========================================
            Telecom API CI/CD Pipeline SUCCESS
            ==========================================
            Build Number : ${BUILD_NUMBER}
            Image        : ${IMAGE}:${BUILD_NUMBER}
            Latest       : ${IMAGE}:latest
            Registry     : ${REGISTRY}
            ==========================================
            """
        }

        failure {
            echo """
            ==========================================
            Telecom API CI/CD Pipeline FAILED
            ==========================================
            Build Number : ${BUILD_NUMBER}
            ==========================================
            """
        }
    }
}
```
