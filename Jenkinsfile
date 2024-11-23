pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhubs')
        SONAR_CREDENTIALS = credentials('sonars')
    }

    stages {
        stage("Git Clone") {
            steps {
                git branch: 'louay', url: 'https://github.com/louaylaajimi2222/5NIDS2-G6-TPFOYER.git'
            }
        }

        stage("Maven Build") {
            steps {
                sh "mvn clean install"
            }
        }

        stage("Cleanup Previous Build") {
            steps {
                script {
                    sh """
                    if [ \$(docker ps -a -q -f name=myappjava_${BUILD_NUMBER - 1}) ]; then
                        docker rm -f myappjava_${BUILD_NUMBER - 1}
                    fi
                    """
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
                      -Dsonar.projectKey=devsecopss \
                      -Dsonar.projectName='devsecopss' \
                      -Dsonar.host.url=http://192.168.179.130:9000 \
                      -Dsonar.token=sqp_f5a605ea354b96426dfdf352a1ca64b08c93e454
                    '''
                }
            }
        }

        stage('Mockito Tests') {
            steps {
                sh 'mvn test -DargLine="-javaagent:target/jacoco-agent.jar=destfile=target/jacoco.exec"'
            }
        }

        stage("DOCKER IMAGE") {
            steps {
                sh "docker build -t louay222/fy:9.0.0 ."
            }
        }
       stage('Scan Docker Image') {
            steps {
                sh '''
               trivy  image  --db-repository public.ecr.aws/aquasecurity/trivy-db --java-db-repository public.ecr.aws/aquasecurity/trivy-java-db --scanners vuln  --timeout=15m louay222/fy:9.0.0

                '''
            }
        } 
            stage("Dast scan with ZAP OWASP") {
            steps {
                script {   
                   def zapStatus = sh(
                        script: '''
                        chmod 777 -R /home/louay/5NIDS2-G6-TPFOYER/zapss
                        docker run -v /home/louay/5NIDS2-G6-TPFOYER/zapss/:/zap/wrk/:rw --network="host" zaproxy/zap-stable zap-baseline.py -t http://192.168.179.130:8088/tpfoyer/swagger-ui/index.html#/universite-rest-controller/modifyUniversite -r scan-report.html -ignorewarnings
                        ''',
                        returnStatus: true
                    )
                    echo "ZAP Scan completed with status: ${zapStatus}"
                }
                sh " cat /home/louay/5NIDS2-G6-TPFOYER/zapss/scan-report.html"
            }
        }
        

        stage("DOCKER-COMPOSE") {
            steps {
                sh "docker-compose up -d  "
            }
        }
    }
}
