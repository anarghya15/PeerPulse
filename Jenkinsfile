pipeline {
    agent any
    environment {
        frontendRepositoryName = "anarghya15/peerpulse-frontend"
        backendRepositoryName = "anarghya15/peerpulse-backend"
        KUBECONFIG = "/var/lib/jenkins/.kube/config"
        AZURE_SUBSCRIPTION_ID = credentials('azure-subscription-id')
        AZURE_TENANT_ID = credentials('azure-tenant-id')
        AZURE_CLIENT_ID = credentials('azure-service-principal')
        AZURE_CLIENT_SECRET = credentials('azure-client-secret')
        AZURE_RESOURCE_GROUP = "spe-project-peerpulse"
        AZURE_AKS_CLUSTER_NAME = "peerpulse-kubecluster"
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

        stage('Configure kubectl for AKS') {
            steps {
                script {
                    // Login to Azure
                    sh '''
                    az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET --tenant $AZURE_TENANT_ID
                    az account set --subscription $AZURE_SUBSCRIPTION_ID
                    az aks get-credentials --resource-group $AZURE_RESOURCE_GROUP --name $AZURE_AKS_CLUSTER_NAME --file $KUBECONFIG
                    '''
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
