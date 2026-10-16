
pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    options {
        timestamps()
    }

    environment {
        DOCKER_IMAGE = 'simrakhan06/devops-pipeline'
    }

    stages {

        stage('Checkout Source Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/simra-06/devops-pipeline.git'
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat 'mvn sonar:sonar -Dsonar.projectKey=devops-pipeline'
                }
            }
        }
        stage('Verify Docker') {
            steps {
                bat 'where.exe docker'
                bat 'docker --version'
                bat 'docker info'
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKERHUB_USERNAME',
                        passwordVariable: 'DOCKERHUB_PASSWORD'
                    )
                ]) {
                    bat 'docker build -t "%DOCKER_IMAGE%:%BUILD_NUMBER%" -t "%DOCKER_IMAGE%:latest" .'

                    bat 'echo %DOCKERHUB_PASSWORD%| docker login -u "%DOCKERHUB_USERNAME%" --password-stdin'

                    bat 'docker push "%DOCKER_IMAGE%:%BUILD_NUMBER%"'
                    bat 'docker push "%DOCKER_IMAGE%:latest"'

                    bat 'docker logout'
                }
            }
        }

        stage('Apply Kubernetes Manifests') {
            steps {
                bat 'kubectl apply -f k8s/'
            }
        }

        stage('Update Kubernetes Deployment') {
            steps {
                bat 'kubectl set image deployment/devops-app devops-app="%DOCKER_IMAGE%:%BUILD_NUMBER%"'
            }
        }

        stage('Verify Deployment') {
            steps {
                bat 'kubectl rollout status deployment/devops-app --timeout=180s'
                bat 'kubectl get deployments'
                bat 'kubectl get pods'
                bat 'kubectl get services'
            }
        }
    }

    post {
        success {
            echo 'DevOps pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check the Console Output for errors.'
        }
    }
}
