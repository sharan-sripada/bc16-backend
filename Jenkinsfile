pipeline{
	
	agent{
			
			kubernetes {
			    
			    yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: java
            image: adoptopenjdk/openjdk11
            command:
            - cat
            tty: true
          - name: docker
            image: docker:19.03
            command:
            - cat
            tty: true
            volumeMounts:
            - name: dockersock
              mountPath: /var/run/docker.sock
          volumes:
            - name: dockersock
              hostPath:
                path: /var/run/docker.sock
        '''
    
    }
	}
	
		
    environment {
        docker_image=""
        DOCKERHUB_CREDENTIALS= credentials('dockerhub_token_sss')
        MY_KUBECONFIG = credentials('config-file')
    }
	stages{
	    stage('Checkout Source') {
      steps {
        git 'https://github.com/sharan-sripada/bc16-backend.git'
      }
    }
	    stage('Build Jar'){
	     
	        steps{
	           container('java'){
   
	            sh 'organizationService/gradlew build'

	            sh 'jobsService/gradlew build'
	           }
	            
	    }}
	    stage('Build Docker'){
            steps{
            container('docker'){

            sh 'docker build -t sharansripada/org_jenkins:latest organizationService/'
            sh 'docker build -t sharansripada/job_jenkins:latest jobsService/'
            sh 'docker images'
            
        }}  
	    }
	    
	   stage('Push Docker'){
	        steps{
	            container('docker'){
	            sh 'ls'
	            sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
	            sh 'docker push sharansripada/org_jenkins:latest'
	            sh 'docker push sharansripada/job_jenkins:latest'
	           
	        }
	        }
	    }
               
                    
				
	}    
	post{
	    always{
	        container('docker'){
	         sh 'docker logout'
	    }}
	}
               
                    
				
	}    
