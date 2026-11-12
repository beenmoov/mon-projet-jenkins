pipeline {
    agent any

    tools {
        maven 'maven3' 
    }

    stages {
        stage('Checkout du code') {
            steps {
                git credentialsId: 'git-credentials', url: 'https://github.com/beenmoov/mon-projet-jenkins.git'
            }
        }

        stage('Build et Tests Maven') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Build de l\'image Docker') {
            steps {
                sh 'docker build -t beenmoov/mon-projet-jenkins:1.0.0 .'
            }
        }

        stage('Push sur Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
                    sh 'docker push beenmoov/mon-projet-jenkins:1.0.0'
                }
            }
        }
    }
}