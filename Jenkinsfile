pipeline {
    agent {
        kubernetes {
            inheritFrom 'molgenis-jdk11'
        }
    }
    environment {
        REPOSITORY = 'molgenis/molgenis-tools-emx-downloader'
        TIMESTAMP = sh(returnStdout: true, script: "date -u +'%F_%H-%M-%S'").trim()
    }
    stages {
        stage('Prepare') {
            steps {
                script {
                    env.GIT_COMMIT = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                }
                container('vault') {
                    script {
                        sh "mkdir ${JENKINS_AGENT_WORKDIR}/.rancher"
                        sh "mkdir ${JENKINS_AGENT_WORKDIR}/.m2"
                        sh(script: "vault read -field=value secret/ops/jenkins/maven/settings.xml > ${JENKINS_AGENT_WORKDIR}/.m2/settings.xml")
                        env.GITHUB_TOKEN = sh(script: "vault read -field=value secret/ops/token/github", returnStdout: true)
                        env.SONAR_TOKEN = sh(script: 'vault read -field=value secret/ops/token/sonar', returnStdout: true)
                        env.GITHUB_USER = sh(script: 'vault read -field=username secret/ops/token/github', returnStdout: true)
                    }
                }
            }
        }
        stage('Steps [ PR ]') {
            when {
                changeRequest()
            }
            environment {
                // PR-1234-231
                TAG = "PR-${CHANGE_ID}-${BUILD_NUMBER}"
            }
            stages {
                stage('Build [ PR ]') {
                    steps {
                        container('maven') {
                            script {
                                env.PREVIEW_VERSION = sh(script: "grep version pom.xml | grep SNAPSHOT | cut -d'>' -f2 | cut -d'<' -f1", returnStdout: true).trim() + "-${TAG}"
                            }
                            sh "mvn -B versions:set -DnewVersion=${PREVIEW_VERSION} -DgenerateBackupPoms=false"
                            sh "mvn -B clean deploy"
                            // Fetch the target branch, sonar likes to take a look at it
                            sh "git fetch --no-tags origin ${CHANGE_TARGET}:refs/remotes/origin/${CHANGE_TARGET}"
                            sh "mvn -B sonar:sonar -Dsonar.login=$SONAR_TOKEN -Dsonar.github.oauth=$GITHUB_TOKEN -Dsonar.pullrequest.base=${CHANGE_TARGET} -Dsonar.pullrequest.branch=${BRANCH_NAME} -Dsonar.pullrequest.key=${CHANGE_ID} -Dsonar.pullrequest.provider=GitHub -Dsonar.pullrequest.github.repository=${REPOSITORY} -Dsonar.ws.timeout=120"
                        }
                    }
                    post {
                        always {
                            junit '**/target/surefire-reports/**.xml'
                        }
                        success {
                           container('maven') {
                               sh "set +x; curl -X POST -H 'Content-Type: application/json' -H 'Authorization: token $GITHUB_TOKEN' " +
                                   "--data '{\"body\":\":star: PR Preview available! Download [downloader-${PREVIEW_VERSION}.jar](https://registry.molgenis.org/repository/maven-releases/org/molgenis/downloader/${PREVIEW_VERSION}/downloader-${PREVIEW_VERSION}.jar)\"}' " +
                                   "https://api.github.com/repos/${REPOSITORY}/issues/${CHANGE_ID}/comments"
                            }
                        }
                    }
                }
            }
        }
        stage('Steps [ master ]') {
            when {
                branch 'master'
            }
            environment {
                TAG = "dev-${TIMESTAMP}"
            }
            stages {
                stage('Build [ master ]') {
                    steps {
                        container('maven') {
                            sh "mvn -B clean deploy -DskipITs"
                            sh "mvn -B sonar:sonar -Dsonar.login=${SONAR_TOKEN} -Dsonar.ws.timeout=120"
                        }
                    }
                    post {
                        always {
                            junit '**/target/surefire-reports/**.xml'
                        }
                    }
                }
                stage('Prepare Release [ master ]') {
                    steps {
                        timeout(time: 40, unit: 'MINUTES') {
                            input(message: 'Prepare to release?')
                        }
                        container('maven') {
                            sh "mvn -B release:prepare"
                            script {
                                env.TAG = sh(script: "grep project.rel release.properties | head -n1 | cut -d'=' -f2", returnStdout: true).trim()
                            }
                        }
                    }
                }
                stage('Perform release [ master ]') {
                    steps {
                        container('maven') {
                            sh "mvn -B release:perform"
                        }
                    }
                    post {
                        success {
                           molgenisSlack(message: "Released :star: Released [MOLGENIS EMX Downloader version ${TAG}](https://registry.molgenis.org/repository/maven-releases/org/molgenis/downloader/${TAG}/downloader-${TAG}.jar)", color: 'good', channel: 'platform')
                        }
                    }
                }
            }
        }
    }
}
