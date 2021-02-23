pipeline {
   # agent {
  #     label "haimaxy-jnlp"
	#}
	
	environment {
        JIRA_SITE='aasjira'}
    stages {
        stage('Clone') {
            steps {
                echo "1.Git Clone Code"
                checkout scm
                script {
            build_tag = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
			if (env.BRANCH_NAME != 'main') {
                build_tag = "${env.BRANCH_NAME}-${build_tag}"
        }
            }
        }
		}
        stage('Maven') {
            steps {
                echo "2.Maven Build Stage"
                sh 'mvn -B clean package -Dmaven.test.skip=true'
            }
        }
		stage('Sonar') {
            steps {
                echo "2.Sonar Stage"
                sh 'mvn sonar:sonar -Dsonar.host.url=http://192.168.6.150:8050 -Dsonar.login=d6c8fb1706d3d5824db5ea1c9d9101d0b47801eb'
            }
        }
        stage('Image') {
            steps {
            echo "3.Image Build Stage"
            sh 'ls -l'
            sh "docker build -t firexuxiaoman/jenkins-demo:${build_tag} ."
			sh "docker tag firexuxiaoman/jenkins-demo:${build_tag} reg.analyticservice.net/jenkins/jenkins-demo:${build_tag}"
            }
        }
        stage('Push') {
            steps {
            echo "4.Push Docker Image Stage"
            sh "docker login -u fire -p Pass1234 reg.analyticservice.net "
            sh "docker push reg.analyticservice.net/jenkins/jenkins-demo:${build_tag}"
            }
        }
        stage('YAML') {
		steps {
        echo "6. Change YAML File Stage"
        sh "sed -i 's/<BUILD_TAG>/${build_tag}/' k8s.yaml"
        sh "sed -i 's/<BRANCH_NAME>/${env.BRANCH_NAME}/' k8s.yaml"
		script {
		if (env.BRANCH_NAME == 'main') {
            input "确认要部署线上环境吗？？"
			}
		}
        }
		}
        stage('Deploy') {
		steps {
        echo "7. Deploy To K8s Stage"
        sh 'kubectl apply -f k8s.yaml --record'
		sh 'kubectl apply -f ingress.yaml --record'
        }
  }
        stage('jira') {
            steps {
			withEnv(['JIRA_SITE=AASjenkins']){
                comment_issues()
            }
	}
	}
	}
   }

@NonCPS
void comment_issues() {
    def issue_pattern = "[a-zA-Z]([a-zA-Z]+)-\\d+"

    // Find all relevant commit ids
    currentBuild.changeSets.each {changeSet ->
        changeSet.each { commit ->
            String msg = commit.getMsg()
			String email = commit .getAuthorEmail()
			String committer = email.split('@')[0].trim()
			String commitId = commit.getCommitId()
            msg.findAll(issue_pattern).each {
                // Actually post a comment	
                id -> 
				        def ts = new Date(commit.getTimestamp())
            			def comment = [ body: msg + '\n' +
            			'Current Build: ' + '[' + currentBuild.number +' | '+ currentBuild.absoluteUrl +']\n' +
            			'Git Commit ID: ' + '[' + commitId + '|https://gogs.analyticservice.net/fire.xu/python-jemo/commit/'+commitId + ']\n' +
            			'Author: 		' + '[~'+ committer + ']\n' +
						'Timestamp:		' + ts.format('yyyy/MM/DD HH:mm:ss')+ '\n' +
            			'Files:			' + commit.getAffectedPaths() ]
            			  jiraAddComment idOrKey: id, input: comment 
            }
        }
    }
}