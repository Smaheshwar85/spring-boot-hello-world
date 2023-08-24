  pipeline{
	  
    agent any  
     tools {
    maven 'M3'
  }
	  
 environment {
        GCR_REGISTRY = "gcr.io" // Change to your GCR registry URL
        PROJECT_ID = "devopsjunction23" // Change to your GCP Project ID
        IMAGE_NAME = "java-webserver" // Change to your desired image name
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
                // Authenticate with GCP using a service account key
                //withCredentials([string(credentialsId: 'gcp-service-account-key', variable: 'GCP_SA_KEY')]) {
                    //sh "echo ${GCP_SA_KEY} | base64 --decode > gcp-key.json"
                   // sh "gcloud auth activate-service-account --key-file=gcp-key.json"
                //}
                
                // Push the Docker image to GCR
                sh "docker push ${GCR_REGISTRY}/${PROJECT_ID}/${IMAGE_NAME}:${IMAGE_TAG}"
            }
        }
    
            
    }
}

  
