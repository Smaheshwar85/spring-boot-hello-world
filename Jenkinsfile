  pipeline{
	  
    agent any  
     tools {
    maven 'M3'
  }

	  
 environment {
        GCR_REGISTRY = "gcr.io" // Change to your GCR registry URL
        PROJECT_ID = "devopsjunction23" // Change to your GCP Project ID
        IMAGE_NAME = "hello-world" // Change to your desired image name
        IMAGE_TAG = "latest" // Change to your desired image tag
    }
	
        stages{
		 stage('Git Checkout'){
		     steps{
	        git 'https://github.com/Smaheshwar85/spring-boot-hello-world'  
	        
           }
		 }

		
        stage('Build') {
            steps {
                // Assuming you have a Maven project
		                           def workspace = WORKSPACE
    // ${workspace} will now contain an absolute path to job workspace on slave

    workspace = env.WORKSPACE
    // ${workspace} will still contain an absolute path to job workspace on slave

    // When using a GString at least later Jenkins versions could only handle the env.WORKSPACE variant:
    echo "Current workspace is ${env.WORKSPACE}"
                      sh 'ls -lrt /var/lib/jenkins/workspace/pipeline-docker'

                sh 'mvn clean package -DskipTests'
                archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
            }
        }
        
        stage('Build Docker Image') {
            steps {
                // Build the Docker image using the Dockerfile in the project directory
                script {
                    def dockerImage = docker.build("${GCR_REGISTRY}/${PROJECT_ID}/${IMAGE_NAME}:${IMAGE_TAG}", '.')
                }
            }
        }
		
		   stage('Push to GCR') {
            steps {
                
		    def dockerImageTag = "${GCR_REGISTRY}/${PROJECT_ID}/${IMAGE_NAME}:${IMAGE_TAG}"
                    withCredentials([file(credentialsId: 'cred', variable: 'CRED')]) {
               
                        sh "docker buildx build --platform linux/amd64 -t $dockerImageTag ."
                  def repositoryname = "${IMAGE_NAME}-${env.BUILD_NUMBER}"

               def command = """
    gcloud auth activate-service-account --key-file="$CRED"
    printf 'yes' | gcloud artifacts repositories create $repositoryname  --repository-format=docker --location=us-central1 --description="created repo"
    gcloud auth configure-docker us-central1-docker.pkg.dev
"""

sh(script: command, returnStdout: true).trim()

                 sh "docker tag gcr.io/devopsjunction23/hello-world us-central1-docker.pkg.dev/terraform-gcp-395808/$repositoryname/gcr.io/devopsjunction23/hello-world"
                  sh "docker push us-central1-docker.pkg.dev/terraform-gcp-395808/$repositoryname/gcr.io/devopsjunction23/hello-world"
                //sh "docker push ${GCR_REGISTRY}/${PROJECT_ID}/${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }
    
            
    }
}

  
