# TechBookFesXamarinGpsApp

![screenshot01](screenshot_01.png)

## これは何？

これは Xamarintans（ざまりたんず） が執筆した「Essential Xamarin Yang (陽)」という技術同人誌の、
第1章「Xamarin.Android で始めるクロスプラットモバイルアプリ開発」 − 「1.8  Xamarinによる「クロスプラットフォーム」MVVM+Rxアプリケーション」 で登場する「GPSアプリケーション」を Android ネイティブと Xamarin(Xamarin.Forms) で実装したリポジトリです。

書籍での解説の通り、Rx(RxJava や RxSwift) と MVVM、データバインディングを利用して開発されたモバイルアプリが、Xamarin(Xamarin.Forms) でどのように共通化できるかを示したリポジトリです。



## ディレクトリ構成

### /GpsAppAndroid

Android ネイティブ(Java)で実装したプロジェクトです。Android Studio 2.2.3 で開発しています。

#### 主な使用技術
* Data Binding - Android Data Binding
* Rx - RxJava 2.0
* ViewModel - RxProperty
* Dependency Injection - Dagger2
* Others - Lightweight-Stream-API, RetroLambda

### /GpsAppXamarinForms

Xamarin.Forms(C#)で実装したプロジェクトです。Visual Studio for Mac Preview 5 で開発しています。対応プラットフォームは Android
 と iOS です。

#### 主な使用技術
* Data Binding - Xamarin.Forms Data Binding
* Rx - Reactive Extensions
* ViewModel - ReactiveProperty, Prism for Xamarin.Forms
* Dependency Injection - Unity(Prism.Unity)

## ブランチについて
master ブランチは、最新のソースコードを示しています。書籍に対応するソースコードは、書籍の版に対応した tag を付与しています。
