# lib-term

## ライブラリを公開する
### バージョンあげる
```groovy
group 'com.naosim.ddd'
version '0.4.0' // ここをあげる
```

### ビルドする
```
./gradlew uploadArchives
```

### masterにマージする

## ライブラリを使う
gradleに下記を記述する
```gradle
repositories {
    ...
    maven { url 'https://naosim.github.io/lib-term/' }
}

dependencies {
   ...
    compile group:'com.naosim', name:'libterm', version:'0.3.0' // 設定したバージョン
}
```