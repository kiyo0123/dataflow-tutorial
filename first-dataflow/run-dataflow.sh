mvn compile exec:java \
      -Dexec.mainClass=com.example.WordCount \
      -Dexec.args="--project=${PROJECT_ID} \
      --stagingLocation=gs://${STAGING_BUCKET}/staging/ \
      --output=gs://${OUTPUT_BUCKET}/output \
      --runner=DataflowRunner"


