package com.amay077.gpsapp.usecases;

import android.location.Location;
import android.location.LocationManager;

import com.amay077.gpsapp.api.LocationClient;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

@Singleton
public class LocationUseCase {
    // ストップウォッチの状態を更新＆通知するための Subject 群
    /// 受信した緯度経度
    private final BehaviorSubject<Location> _location = BehaviorSubject.create(); // 位置
    /// 実行状態
    private final BehaviorSubject<Boolean> _isRunning = BehaviorSubject.createDefault(false); // 実行中か？
    /// 記録された緯度経度群
    private final BehaviorSubject<List<Location>> _records = BehaviorSubject.createDefault(Collections.emptyList()); // レコード群

    private final LocationClient _client;

    // スレッドを超えて値を更新(onNext)するための SerializedSubject 群
    private final Subject<Location> _locationSerialized = _location.toSerialized();
    private final Subject<Boolean> _isRunningSerialized = _isRunning.toSerialized();

    // Model として公開するプロパティ
    // Subject をそのまま公開したくないので Observable<> にしている
    public final Observable<Location> location = _location;
    public final Observable<Boolean> isRunning = _isRunning;
    public final Observable<List<Location>> records = _records;

    @Inject
    public LocationUseCase(LocationClient client) {
        _client = client;

        // OnLocationChanged イベントを Observable に変換
        _client.setOnLocationChangeListener(location -> _locationSerialized.onNext(location));

        // OnRunningChanged イベントを Observable に変換
        _client.setOnRunningChangeListener(isRunning -> _isRunningSerialized.onNext(isRunning));
    }

    public void startOrStop()
    {
        if (_client.isRunning())
        {
            _client.stop();
        }
        else
        {
            _records.onNext(Collections.emptyList());
            _client.start();
        }
    }

    public void record()
    {
        _client.getLatestLocation().ifPresent(latest -> {
            final List<Location> newRecords = new ArrayList<>();
            newRecords.addAll(_records.getValue());
            newRecords.add(latest);
            _records.onNext(Collections.unmodifiableList(newRecords));
        });
    }

    public Optional<Location> getBestLocation()
    {
        // Records から最も精度のよい(Accuracyの小さい)Locationを返す。
        //  このメソッドは Reactive ではないので、使い方に注意が必要。
        return Stream.of(_records.getValue())
                .sortBy(location -> location.getAccuracy())
                .findFirst();
    }
}
