# dataflow-tutorial
* SimplePubsub.java：
PubSubでメッセージを受けて、単純にタイムスタンプを出力するだけのSimpleなパイプライン。

PubSubにメッセージを出力するクライアントはこんな感じ。
```
while true
  do sleep 1; 
  gcloud beta pubsub topics publish test --message `date +%s`; 
  done
```

