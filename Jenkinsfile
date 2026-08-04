pipeline {
	agent any
	tools {
		maven 'maven 3.9.9'
		jdk 'Java JDK 17'
	}
	stages {
		stage("test") {
			steps {
				echo "Start Test"
				bat "mvn test"
			}
		}
	}
}