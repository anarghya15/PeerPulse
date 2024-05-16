pipeline {
    agent any
    environment {
        frontendRepositoryName = "anarghya15/peerpulse-frontend"
        backendRepositoryName = "anarghya15/peerpulse-backend"
        tag = "latest"
        frontendImage = ""
        backendImage = ""
    }
    stages {
        stage('Fetch code from github') {
            steps {
                git branch: 'main',
                url: 'https://github.com/anarghya15/PeerPulse'
            }
        }
        stage('Build backend code using Maven') {
            steps {
                script{
                    sh 'mvn -f peerpulse-backend clean install'
                }
            }
        }
        stage('Create backend docker image') {
            steps {
                script{
                    backendImage = docker.build(backendRepositoryName + ":" + tag, "./peerpulse-backend")
                }
            }
        }
        stage('Push backend image to docker hub') {
            steps {
                script{
                    // By default, the registry will be dockerhub
                    docker.withRegistry('', 'DockerHubCred'){
                        backendImage.push()
                    }
                }
            }
        }
        stage('Create frontnd docker image') {
            steps {
                script{
                    frontendImage = docker.build(frontendRepositoryName + ":" + tag, "./peerpulse-frontend")
                }
            }
        }
        stage('Push frontend image to docker hub') {
            steps {
                script{
                    // By default, the registry will be dockerhub
                    docker.withRegistry('', 'DockerHubCred'){
                        frontendImage.push()
                    }
                }
            }
        }
        
        stage('Deploy to Kubernetes using Ansible') {
            steps {
              ansiblePlaybook installation: 'Ansible', playbook: 'deploy-playbook.yml'
            }
        }
    }
}
