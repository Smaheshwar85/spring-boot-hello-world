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
                    withCredentials([file(credentialsId: 'cred_host', variable: 'CRED')]) {
                // sh "docker build -t $dockerImageTag ."
                        sh "docker buildx build --platform linux/amd64 -t $dockerImageTag ."
                  //def repositoryname = "${IMAGE_NAME}-${env.BUILD_NUMBER}"
                        def repositoryname = "global"
                        
                      //  if (repositoryname.equals("global")){
                           //  echo "Repository already exists. Deleting..."
                            //sh '''gcloud artifacts repositories delete --quiet --project=devopsjunction23 --location=us-central1 $repositoryname'''
                      //  }
    //def checkCondition= sh ''' gcloud artifacts repositories describe --project=devopsjunction23 --location=us-central1 hello-world-269'''
                        //sh(script: checkCondition, returnStdout: true).trim()
                        //echo "find value to delete. $checkCondition"
  //if (${checkCondition} > null){
   // echo "Repository already exists. Deleting..."
 // sh ''' gcloud artifacts repositories delete --quiet --project=devopsjunction23 --location=us-central1 $repositoryname'''
  //}
                            

    def command = """
    gcloud auth activate-service-account --key-file="$CRED"
    gcloud config set project devopsjunction23
    printf 'yes' | gcloud artifacts repositories create global --repository-format=docker --location=us-central1 --description="created repo"
    gcloud artifacts repositories add-iam-policy-binding global --location=us-central1 --member=allUsers --role=roles/artifactregistry.admin
    gcloud auth configure-docker us-central1-docker.pkg.dev
"""

                       

sh(script: command, returnStdout: true).trim()

                 sh "docker tag us-central1-docker.pkg.dev/devopsjunction23/hello-world us-central1-docker.pkg.dev/devopsjunction23/global/gcr.io/devopsjunction23/hello-world"
                  sh "docker push us-central1-docker.pkg.dev/devopsjunction23/global/gcr.io/devopsjunction23/hello-world"
                        
                        //def commanddep = """
// gcloud run deploy  $repositoryname --image us-central1-docker.pkg.dev/terra-project-396714/hello-world-236/gcr.io/devopsjunction23/hello-world --platform managed --region us-central1 --allow-unauthenticated --port 8082
 //"""
        // sh(script: commanddep, returnStdout: true).trim()
                      
                    //sh "docker build -t $dockerImageTag ."
                    //sh "docker tag gcr.io/devopsjunction23/hello-world us-central1-docker.pkg.dev/my-project/my-repo/test-imagemy-image"
                    //sh "docker push us-central1-docker.pkg.dev/my-project/my-repo/test-imagemy-image"
                    }
                }
            }
        }

        
    
     // stage('Cleanup') {
           // steps {
              //  sh 'docker rmi -f $(docker images -f "dangling=true" -q)'
           // }
        //}
        
    }
}
