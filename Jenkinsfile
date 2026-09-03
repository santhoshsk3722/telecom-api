pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/santhoshsk3722/telecom-api.git'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x mvnw || true'
                sh './mvnw clean compile -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Package') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'Telecom API CI Pipeline completed successfully!'
        }

        failure {
            echo 'Telecom API CI Pipeline failed!'
        }
    }
}