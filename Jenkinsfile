pipeline {

    agent any

    environment {
        REGISTRY = 'telecom-api-registry:5000'
        IMAGE_NAME = 'telecom-api'
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

                    echo "===== Maven ====="
                    mvn -version

                    echo "===== Docker ====="
                    docker --version

                    echo "===== Docker Containers ====="
                    docker ps
                '''
            }
        }

        stage('Build') {
            steps {
                sh '''
                    echo "===== Maven Build ====="
                    mvn clean package -DskipTests
                '''
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    echo "===== Building Docker Image ====="

                    docker build \
                        -t ${IMAGE_NAME}:${BUILD_NUMBER} .

                    echo "===== Docker Images ====="

                    docker images ${IMAGE_NAME}
                '''
            }
        }

        stage('Docker Tag') {
            steps {
                sh '''
                    echo "===== Tagging Docker Image ====="

                    docker tag \
                        ${IMAGE_NAME}:${BUILD_NUMBER} \
                        ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}

                    docker tag \
                        ${IMAGE_NAME}:${BUILD_NUMBER} \
                        ${REGISTRY}/${IMAGE_NAME}:latest

                    echo "===== Tagged Images ====="

                    docker images ${REGISTRY}/${IMAGE_NAME}
                '''
            }
        }

        stage('Docker Push') {
            steps {
                sh '''
                    echo "===== Pushing Docker Images ====="

                    docker push ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}

                    docker push ${REGISTRY}/${IMAGE_NAME}:latest

                    echo "===== Push Completed ====="
                '''
            }
        }

    }

    post {

        success {
            echo 'Telecom API CI/CD image build and registry push completed successfully.'
        }

        failure {
            echo 'Telecom API CI/CD pipeline failed.'
        }
    }
}