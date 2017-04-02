package com.amay077.gpsapp.viewmodel;

import android.location.Location;
import android.text.format.DateFormat;

import com.amay077.gpsapp.frameworks.messengers.Messenger;
import com.amay077.gpsapp.frameworks.messengers.ShowToastMessages;
import com.amay077.gpsapp.frameworks.messengers.StartActivityMessage;
import com.amay077.gpsapp.usecases.LocationUseCase;
import com.amay077.gpsapp.util.LatLonUtil;
import com.amay077.gpsapp.views.activities.RecordActivity;
import com.annimon.stream.Optional;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import jp.keita.kagurazaka.rxproperty.Nothing;
import jp.keita.kagurazaka.rxproperty.ReadOnlyRxProperty;
import jp.keita.kagurazaka.rxproperty.RxCommand;
import jp.keita.kagurazaka.rxproperty.RxProperty;

public class MainViewModel {

    // 簡易Messenger(EventBus のようなもの)
    public final Messenger messenger = new Messenger();

    // ■ViewModel として公開するプロパティ
    /// 緯度、経度、時刻
    public final ReadOnlyRxProperty<String> formattedLatitude;
    public final ReadOnlyRxProperty<String> formattedLongitude;
    public final ReadOnlyRxProperty<String> formattedTime;
    /// 実行中かどうか？
    public final ReadOnlyRxProperty<Boolean> isRunning;
    /// 度分秒表示か？
    public final RxProperty<Boolean> isDmsFormat = new RxProperty<>(false);
    /// 記録した数
    public final ReadOnlyRxProperty<Integer> recordCount;

    // View向けに公開するコマンド

    /// 開始 or 終了
    public final RxCommand<Nothing> startOrStopCommand;
    /// 緯度経度の記録
    public final RxCommand<Nothing> recordCommand;
    /// 度分秒表示かのトグル
    public final RxCommand<Nothing> toggleIsDmsFormatCommand;

    // コンストラクタ
    @Inject
    public MainViewModel(LocationUseCase locationUseCase) {

        // ■プロパティの実装
        // LocationUseCase の各プロパティを必要なら加工して公開
        isRunning = new ReadOnlyRxProperty<>(locationUseCase.isRunning);

        // Location の時刻をフォーマットして公開
        formattedTime = new ReadOnlyRxProperty<String>(locationUseCase.location
                .map(l -> DateFormat.format("kk:mm:ss", l.getTime()).toString()));

        // Location の緯度を度分秒または度にフォーマットして公開
        formattedLatitude = new ReadOnlyRxProperty<>(Observable.combineLatest(isDmsFormat, locationUseCase.location,
                (isDms, l) -> LatLonUtil.formatDegrees(l.getLatitude(), isDms)));

        // Location の経度を度分秒または度にフォーマットして公開
        formattedLongitude = new ReadOnlyRxProperty<>(Observable.combineLatest(isDmsFormat, locationUseCase.location,
                (isDms, l) -> LatLonUtil.formatDegrees(l.getLongitude(), isDms)));

        // 記録されたレコード群を件数として公開
        recordCount = new ReadOnlyRxProperty<>(locationUseCase.records
                .map(r -> r.size()));

        //// STOP されたら、最も精度のよい位置情報を表示して、RecordsPage へ遷移
        isRunning
                .buffer(2, 1)
                .filter(x -> x.get(0) && !x.get(1))
                .subscribe(dummy -> {
                        // 最も精度のよい緯度経度を得る
                        //  返値がメソッドは、その時点の情報でしかない(Reactiveではない)ので注意すること
                    final Optional<Location> bestLocation = locationUseCase.getBestLocation();

                    final String message = bestLocation.isPresent() ?
                            LatLonUtil.formatLocation(bestLocation.get(), isDmsFormat.get()) :
                            "記録されてません";

                    // Toast を表示させる
                    messenger.send(new ShowToastMessages(message));

                    // RecordActivity へ遷移させる
                    messenger.send(new StartActivityMessage(RecordActivity.class)); // ホントは RecordViewModel を指定して画面遷移すべき

        });

        // ■コマンドの実装
        // 開始 or 終了
        startOrStopCommand = new RxCommand<>(); // いつでも実行可能
        startOrStopCommand.subscribe(dummy -> {
            locationUseCase.startOrStop();
        });

        // 位置情報の記録
        recordCommand = new RxCommand<>(isRunning); // 実行中のみ記録可能
        recordCommand.subscribe(dummy -> {
            locationUseCase.record();
        });

        // 度分秒表示かのトグル
        toggleIsDmsFormatCommand = new RxCommand<>(); // いつでも実行可能
        toggleIsDmsFormatCommand.subscribe(dummy -> {
            isDmsFormat.set(!isDmsFormat.get());
        });

    }
}
