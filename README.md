# TechBookFesXamarinGpsApp

## これは何？

これは Xamarintans（ざまりたんず） が執筆した「Essential Xamarin Yang (陽)」という技術同人誌の、
第1章「Xamarin.Android で始めるクロスプラットモバイルアプリ開発」 − 「1.8  Xamarinによる「クロスプラットフォーム」MVVM+Rxアプリケーション」 で登場する「GPSアプリケーション」を Android ネイティブと Xamarin(Xamarin.Forms) で実装したリポジトリです。

書籍での解説の通り、Rx(RxJava や RxSwift) と MVVM、データバインディングを利用して開発されたモバイルアプリが、Xamarin(Xamarin.Forms) でどのように共通化できるかを示したリポジトリです。



## ディレクトリ構成

### /GpsAppAndroid

Android ネイティブ(Java)で実装したプロジェクトです。Android Studio 2.2.3 で開発しています。

### /GpsAppXamarinForms

Xamarin.Forms(C#)で実装したプロジェクトです。Visual Studio for Mac Preview 5 で開発しています。対応プラットフォームは Android
 と iOS です。
