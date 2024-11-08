pipeline {
    agent any


environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhubcredentials')
        DOCKER_IMAGE = 'khaledbaccouche19/baccouchekhaled-5nids2-g6'
        VERSION = "latest"
        GIT_BRANCH = 'Baccouchekhaled-5NIDS2-G6'

    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    checkout scm: [
                        $class: 'GitSCM',
                        branches: [[name: '*/Baccouchekhaled-5NIDS2-G6']],
                        userRemoteConfigs: [[
                            url: 'https://github.com/louaylaajimi2222/5NIDS2-G6-TPFOYER.git',
                            credentialsId: 'KB'
                        ]]
                    ]
                }
            }
        }

stage('Build') {
    steps {
        script {
            echo "Building the Maven project..."
            sh 'mvn clean package'
            // Check if the JAR file exists in the target directory
            sh 'ls target/'
        }
    }
}



stage('Run Unit Tests') {
    steps {
            script {
                try {
                    // Run the unit tests
                    sh 'mvn clean test'

                    // Generate the JaCoCo report after tests pass
                    sh 'mvn jacoco:report'

                    // Ensure that JaCoCo report generation is recognized by Jenkins
                    jacoco execPattern: 'target/jacoco.exec'
                } catch (Exception e) {
                    // Mark the build as failed and provide an error message
                    currentBuild.result = 'FAILURE'
                    error "Tests failed or JaCoCo report generation failed: ${e.message}"
                }
            }
        }

}


}