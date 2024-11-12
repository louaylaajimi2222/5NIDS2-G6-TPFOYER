pipeline {
    agent any

    environment {
       
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        
        SONAR = credentials('sonar')
    }

    stages {
        stage("Git Clone") {
            steps {
                git branch: 'main',
                    credentialsId: 'gitlab',
                    url: 'git@gitlab.com:devops4392533/projet_5eme.git'
            }
        }

       /* stage("Pre-commit Check") { // Uncomment the following stage if needed
            steps {
                sh "pre-commit run --all-files"
            }
        }*/

        stage("Maven Build") {
            steps {
                sh "mvn clean install"
            }
        }

        stage("Cleanup Previous Build") {
            steps {
                script {
                    // Remove the previous build container if it exists
                    sh """
                    if [ \$(docker ps -a -q -f name=myappjava_${BUILD_NUMBER - 1}) ]; then
                        docker rm -f myappjava_${BUILD_NUMBER - 1}
                    fi
                    """
                    // Remove the previous Docker image if it exists
                    sh """
                    if [ \$(docker images -q louay222/jenkinsdock_${BUILD_NUMBER - 1}) ]; then
                        docker rmi -f louay222/jenkinsdock_${BUILD_NUMBER - 1}
                    fi
                    """
                }
            }
        }

        stage("Sonar") {
            steps {
                script {
                    echo "Running Maven analysis..."
                    sh '''
                  mvn clean verify sonar:sonar \
                  -Dsonar.projectKey=devops \
                  -Dsonar.projectName='devops' \
                  -Dsonar.host.url=http://52.47.140.147:9000 \
                  -Dsonar.token=sqp_d4e843d229fa3d999ae36aaa58b2916a6928d7df
                    '''
                }
            }
        }
        stage('Mockito Tests') {
            steps {
                sh 'mvn test -DargLine="-javaagent:target/jacoco-agent.jar=destfile=target/jacoco.exec"'
            }
        }


       stage("NEXUS") {
            steps {
                script {
                    nexusArtifactUploader artifacts: [[
                        artifactId: 'tp-foyer',
                        classifier: '',
                        file: 'target/tp-foyer-5.0.0.jar',
                        type: 'jar'
                    ]],
                    credentialsId: 'nexus',
                    groupId: 'louay.devops.tn',
                    nexusUrl: '35.180.123.229:8081',
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    repository: 'artifact',
                    version: "0.0.1-$BUILD_NUMBER"
                }
            }
        }
    

        

        stage("DOCKER IMAGE") {
            steps {
                sh "docker build -t louay222/fy:9.0.0 ."
            }
        }
         /*stage("trivy scan ") {
            steps {
                sh "trivy image --scanners vuln louay222/fy:9.0.0 "
            }
        }*/

        stage("DOCKER LOGIN") {
            steps {
                sh "echo \$DOCKERHUB_CREDENTIALS_PSW | docker login -u \$DOCKERHUB_CREDENTIALS_USR --password-stdin"
            }
        }

        stage("DOCKER HUB PUSH") {
            steps {
                sh "docker push louay222/fy:9.0.0"
            }
        }

        stage("DOCKER-COMPOSE") {
            steps {
                sh "docker-compose up -d"
            }
        }
    }
}
