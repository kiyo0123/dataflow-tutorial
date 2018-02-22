package com.example;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.*;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
//import SLF4J packages.
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

interface SimplePubsubOptions extends PipelineOptions {

  @Description("Pubsub Subscription path")
  String getSubscription();
  void setSubscription(String value);

}

class Tap extends DoFn<String, String> {
  private static final Logger LOG = LoggerFactory.getLogger(Tap.class);
  @DoFn.ProcessElement
  public void processElement(ProcessContext c) {
    long now = System.currentTimeMillis() / 1000L;
    long pubTime = Long.parseLong(c.element());
    long lag = now-pubTime;
    System.out.println("### Recieved:" + now + "; Published:" + pubTime + "; Gap: " + lag + "sec");
    System.out.println("@@@ c.timestamp():" + c.timestamp() );
    System.out.println("@@@ DateTime.now():" + DateTime.now(DateTimeZone.UTC));
    c.output(c.element());
  }
}

public class SimplePubsub {
  //public static final String TIMESTAMP_ATTRIBUTE = "timestamp_ms";
  private static final Logger LOG = LoggerFactory.getLogger(SimplePubsub.class);
  public static final String TIMESTAMP_ATTRIBUTE = "publishTime";

  public static void main(String[] args) throws Exception {
    SimplePubsubOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(SimplePubsubOptions.class);
    Pipeline p = Pipeline.create(options);
    PCollection<String> sourcePipeline = p.apply("Read Pubsub Event", PubsubIO.readStrings()
        //.withTimestampAttribute(TIMESTAMP_ATTRIBUTE)
        .fromSubscription(options.getSubscription()))
        .apply("Tap", ParDo.of(new Tap()));

    p.run();
  }
}
