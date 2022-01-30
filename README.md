# 株式会社ゆめみ Android エンジニアコードチェック課題

## アプリ仕様

以下に示すような GitHub repository を検索するアプリのリファクタリングを行いました。

<img src="https://user-images.githubusercontent.com/80034173/151690472-7fc1c775-033f-439b-8769-8938d87c091f.gif" width="320">

### 環境

- IDE：Android Studio Arctic Fox | 2020.3.1 Patch 1
- Kotlin：1.5.31
- Java：11
- Gradle：7.0.3
- minSdk：23
- targetSdk：31

### plugin
- Kotlin data class File from Json

## アーキテクチャ

### MVVM
アーキテクチャとして選定しました。選定した理由を、構成するクラスとともに説明します。

### Repository

Repository層を設けることで、ViewModelの保守性、テストのしやすさが向上すると考えたのでこの層を設けました。
### ViewModel

検索結果の保持、Repositoryを挟んだAPI callをするためにこのクラスを定義しました。


## 使用技術

### Retrofit

Ktor-Clientから、Retrofitを利用したAPI通信方法へ移行するリファクタリング

### StateFlow

Repository層から、Flowを使って検索結果のListをcollectし、View側へと通知するために使用

### Jetpack Compose

検索クエリを入力後に、検索結果が表示されるまでの間の circular progress indicator を実装

