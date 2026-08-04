pipeline {
    agent any

    tools {
        maven 'maven 3.9.9'
        jdk 'Java JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Source code checked out from GitHub'
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling projects'
                bat 'mvn clean compile'
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Running JUnit tests'
                bat 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging application'
                bat 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }

        success {
            echo 'Pipeline completed successfully'
        }

        failure {
            echo 'Pipeline failed. Check the console output.'
        }
    }
}