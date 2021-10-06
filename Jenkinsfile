import groovy.transform.Field

podTemplate(label: 'bc16', containers: [
	containerTemplate(name: 'docker', image: 'docker:19.03', command: 'cat', ttyEnabled: true),
	containerTemplate(name: 'java', image: 'adoptopenjdk/openjdk11', command: 'cat', ttyEnabled: true) ],
	volumes: [hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')]
)
	{
		node('bc16'){
    environment {
        docker_image=""
        DOCKERHUB_CREDENTIALS= credentials('dockerhub_token_sss')
       
    }	

     

	    stage('Checkout Source') {
      
        git 'https://github.com/sharan-sripada/bc16-backend.git'
      
    }
	    stage('Build Jar'){
	     
	        
	           container('java'){
   
	            sh 'organizationService/gradlew build'

	            sh 'jobsService/gradlew build'
	           }
	            
	    }
	    stage('Build Docker'){
            
            container('docker'){

            sh "docker build -t sharansripada/org_jenkins:latest organizationService/"
            sh "docker build -t sharansripada/job_jenkins:latest jobsService/"
			sh "docker tag sharansripada/job_jenkins:latest sharansripada/job_jenkins:${BUILD_NUMBER}"
			sh "docker tag sharansripada/org_jenkins:latest sharansripada/org_jenkins:${BUILD_NUMBER}"
            sh 'docker images'
            
        }
	    }
	    
	   stage('Push Docker'){
	        
	            container('docker'){
                withCredentials([usernamePassword(credentialsId: 'dockerhub_token_sss', usernameVariable: 'username', passwordVariable: 'password')]) {

	          sh 'docker login -u $username -p $password'
	            //sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
	            sh "docker push sharansripada/org_jenkins:${BUILD_NUMBER}"
				sh "docker push sharansripada/org_jenkins:latest"
	            sh "docker push sharansripada/job_jenkins:${BUILD_NUMBER}"
				sh "docker push sharansripada/job_jenkins:latest"
	           
	        }
              }
	    }
             stage ('Invoking helm build') {
        	
		    build job: 'helm-bc16', parameters: [string(name: 'be_version', value: BUILD_NUMBER)]
	    }  
                    
				

    }}
