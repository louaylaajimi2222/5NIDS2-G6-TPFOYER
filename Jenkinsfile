pipeline {
    agent any

   
    stages {
        stage('Main') {
            steps {
                // Compile the Spring Boot project
                echo "Echo Test of khaled Branch"
            }
        }

       
        stage('compile') {
            steps {
                // Check out the code from the repository
                checkout scm

                // Run Maven clean install
                sh 'mvn compile'
            }
        }

        
    }
}