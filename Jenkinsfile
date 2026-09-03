pipeline {

    agent any

    environment {
        REGISTRY = 'host.docker.internal:5200'
        IMAGE_NAME = 'telecom-api'
    }

    stages {

        stage('Verify Tools') {
            steps {
                sh '''
                    echo "===== Java ====="
                    java -version

                    echo "===== Docker ====="
                    docker --version

                    echo "===== Docker Access ====="
                    docker ps

                    echo "===== Registry Connectivity ====="
                    curl -s http://${REGISTRY}/v2/_catalog
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    echo "===== Building Docker Image ====="

                    docker build \
                        -t ${IMAGE_NAME}:${BUILD_NUMBER} \
                        -t ${IMAGE_NAME}:latest \
                        .

                    echo "===== Docker Images ====="
                    docker images ${IMAGE_NAME}
                '''
            }
        }

        stage('Tag Docker Image') {
            steps {
                sh '''
                    echo "===== Tagging Docker Image ====="

                    docker tag ${IMAGE_NAME}:${BUILD_NUMBER} \
                        ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}

                    docker tag ${IMAGE_NAME}:${BUILD_NUMBER} \
                        ${REGISTRY}/${IMAGE_NAME}:latest

                    echo "===== Tagged Images ====="

                    docker images ${REGISTRY}/${IMAGE_NAME}
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                sh '''
                    echo "===== Pushing Docker Image ====="

                    docker push ${REGISTRY}/${IMAGE_NAME}:${BUILD_NUMBER}

                    docker push ${REGISTRY}/${IMAGE_NAME}:latest

                    echo "===== Docker Push Completed ====="
                '''
            }
        }

        stage('Verify Registry') {
            steps {
                sh '''
                    echo "===== Registry Contents ====="

                    curl -s http://${REGISTRY}/v2/_catalog

                    echo ""

                    echo "===== Image Tags ====="

                    curl -s http://${REGISTRY}/v2/${IMAGE_NAME}/tags/list

                    echo ""
                '''
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