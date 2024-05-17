pipeline {
    agent any
    environment {
        frontendRepositoryName = "anarghya15/peerpulse-frontend"
        backendRepositoryName = "anarghya15/peerpulse-backend"
        KUBECONFIG = "/var/lib/jenkins/.kube/config"
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
        stage('Build frontend code using npm') {
            steps {
                script {
                    // Navigate to the frontend directory and install dependencies
                    sh 'cd peerpulse-frontend && npm install'
                    
                    // Run tests (if you have tests configured)
                    sh 'cd peerpulse-frontend && npm test'
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
    post {
        success {
            mail subject: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
      			body: """SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':
                Check console output at ${env.BUILD_URL}""", 
            	to: 'h.anarghya@iiitb.ac.in'
        }
        failure {
            mail subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
      			body: """FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':
                Check console output at ${env.BUILD_URL}""", 
            	to: 'h.anarghya@iiitb.ac.in'
        }
    }
}
