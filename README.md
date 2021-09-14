Cloud Run without CPU throttling
---

Cloud Run の "CPU always allocated" を使い、バックグラウンド処理を実行するサンプル

# Go

[こちらのサンプル](https://cloud.google.com/run/docs/quickstarts/build-and-deploy/go) をベースに、Go の Goroutine を使った非同期処理を加えました。デプロイして、

```bash
SERVICE_NAME=bg-go
gcloud beta run deploy "${SERVICE_NAME}" --source go --platform managed --region asia-northeast1 --no-allow-unauthenticated --quiet --no-cpu-throttling
```

サービスにアクセスすることでバックグラウンド処理を動かします。

```bash
SERVICE_URL=$(gcloud run services describe "${SERVICE_NAME}" --region asia-northeast1 --format 'value(status.address.url)')
curl -H "Authorization: Bearer $(gcloud auth print-identity-token)" "${SERVICE_URL}"
```

ログを確認してみます。

```bash
gcloud logging read "resource.type=cloud_run_revision AND resource.labels.service_name=${SERVICE_NAME}" --limit 10 --format json | jq -r '.[].textPayload'
```

# Node.js

Cloud Run ではこれまで、https://cloud.google.com/blog/ja/products/serverless/running-effective-nodejs-apps-on-cloud-functions の「3. リクエストのライフサイクルを理解し、落とし穴を回避する」といった対策が必要でしたが・・

[こちらのサンプル](https://cloud.google.com/run/docs/quickstarts/build-and-deploy/nodejs) をベースに、Node.js の async を使った非同期処理を加えてみました。

```bash
SERVICE_NAME=bg-nodejs
gcloud beta run deploy "${SERVICE_NAME}" --source nodejs --platform managed --region asia-northeast1 --no-allow-unauthenticated --quiet --no-cpu-throttling
```

# Java

[こちらのサンプル](https://github.com/GoogleCloudPlatform/buildpack-samples/tree/master/sample-java-gradle) をベースに、Java の Thread を使った非同期処理を加えました。

```bash
SERVICE_NAME=bg-java
gcloud beta run deploy "${SERVICE_NAME}" --source java --platform managed --region asia-northeast1 --no-allow-unauthenticated --quiet --no-cpu-throttling
```
