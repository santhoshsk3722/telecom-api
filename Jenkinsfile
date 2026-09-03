pipeline {

    agent any

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
                sh 'mvn clean package -DskipTests'
            }
        }

    }

    post {

        success {
            echo 'Telecom API CI pipeline completed successfully.'
        }

        failure {
            echo 'Telecom API CI pipeline failed.'
        }

    }
}