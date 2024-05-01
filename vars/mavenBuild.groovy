def call(Map config = [:]){
    def gitURL = config.gitURL
    def productType = config.productType
    def pomfileName = config.pomfileName
    def containerName = config.containerName

 def podTemplates = new PodTemplates()

  podTemplates.mavenTemplate {
    node(POD_LABEL) {
        stage('Get a Maven project') {
            git gitURL
            container('maven') {
                stage('Build a Maven project') {
                    sh "echo hello from $POD_CONTAINER" // displays 'hello from maven'
                    sh 'mvn -B -ntp clean install'
                }
            }
        }
      // container('maven') {
      //    stage('Build a Maven project') {
      //        sh "echo hello from $POD_CONTAINER" // displays 'hello from maven'
      //        sh 'mvn -B -ntp clean install'
      //     }
      // }
     }
  }
}
