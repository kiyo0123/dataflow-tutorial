mvn compile exec:java \
      -Dexec.mainClass=com.example.SimplePubsub \
      -Dexec.args="--subscription=projects/fukudak-dflab/subscriptions/sub2 \
                     --streaming=true"

