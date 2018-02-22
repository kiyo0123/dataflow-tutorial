mvn compile exec:java \
      -Dexec.mainClass=com.example.SimplePubsub \
      -Dexec.args="--project=${PROJECT_ID} \
      --stagingLocation=gs://${STAGING_BUCKET}/staging/ \
      --subscription=projects/fukudak-dflab/subscriptions/sub1 \
      --streaming=true \
      --runner=DataflowRunner"


