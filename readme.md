Usage:

Simply execute java -jar luxmed-1.0-SNAPSHOT.jar, providing luxmed username and password
It can be done by passing argline parameters: java -jar luxmed-1.0-SNAPSHOT.jar -u user -p password
Or by providing file named 'luxmed-credentials.conf' in the root dir of application.
Sample file content:

luxmed {
  user: "your@luxmed.email"
  password: "yourLuxmedPwd"
}

Only plain text format is supported, though.


