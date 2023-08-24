pipeline {
    agent any
    tools {
        maven 'M3'
    }

    environment {
        GCR_REGISTRY = "us-central1-docker.pkg.dev" // Change to your GCR registry URL
        PROJECT_ID = "devopsjunction23" // Change to your GCP Project ID
        IMAGE_NAME = "hello-world" // Change to your desired image name
        IMAGE_TAG = "latest" // Change to your desired image tag
    }

    stages {
        stage('Git Checkout') {
            steps {
                git 'https://github.com/Smaheshwar85/spring-boot-hello-world'
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests'
                    archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def dockerImageTag = "${GCR_REGISTRY}/${PROJECT_ID}/${IMAGE_NAME}:${IMAGE_TAG}"
                    def dockerImage = docker.build(dockerImageTag, '.')
                }
            }
        }

        stage('Push to GCR') {
            steps {
                script {
                    def dockerImageTag = "${GCR_REGISTRY}/${PROJECT_ID}/${IMAGE_NAME}:${IMAGE_TAG}"
                    

                    withCredentials([file(credentialsId: 'cred', variable: 'CRED')]) {
                      sh "docker buildx build --platform linux/amd64 -t $dockerImageTag ."
                        
                                 def repositoryName = "${IMAGE_NAME}-${env.BUILD_NUMBER}"
                      echo "Current workspace is $repositoryName"
                             def command = """
    gcloud auth activate-service-account --key-file="$CRED"
    printf 'yes' | gcloud artifacts repositories create $repositoryname --repository-format=docker --location=us-central1 --description="created repo"
    gcloud auth configure-docker us-central1-docker.pkg.dev
"""
                        sh(script: command, returnStdout: true).trim()
                     
                            
                           
                  sh "docker tag gcr.io/devopsjunction23/hello-world us-central1-docker.pkg.dev/terraform-gcp-395808/$repositoryname/gcr.io/devopsjunction23/hello-world"
                  sh "docker push us-central1-docker.pkg.dev/terraform-gcp-395808/$repositoryname/gcr.io/devopsjunction23/hello-world"
                 
                    }
                }
            }
        }
    }
}
