pipeline {
    agent any

    tools {
        maven 'Maven-3.9.11'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/santhoshsk3722/telecom-api.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn clean package -DskipTests'
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