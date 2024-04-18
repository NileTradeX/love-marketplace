def label = "mypod-${UUID.randomUUID().toString()}" as String
podTemplate(label: label, cloud: 'kubernetes') {
    node(label) {
        stage('Run shell') {
            sh 'sleep 30s'
            sh 'echo hello world.'
        }
    }
}
