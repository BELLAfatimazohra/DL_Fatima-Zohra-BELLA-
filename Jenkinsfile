pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "spring_app:latest"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/BELLAfatimazohra/DL_Fatima-Zohra-BELLA-', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker-compose down'
                sh 'docker-compose up -d'
            }
        }
    }
}
