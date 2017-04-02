package com.amay077.gpsapp.viewmodel;

import android.text.format.DateFormat;

import com.amay077.gpsapp.usecases.LocationUseCase;
import com.amay077.gpsapp.util.LatLonUtil;
import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import jp.keita.kagurazaka.rxproperty.Nothing;
import jp.keita.kagurazaka.rxproperty.ReadOnlyRxProperty;
import jp.keita.kagurazaka.rxproperty.RxCommand;
import jp.keita.kagurazaka.rxproperty.RxProperty;

public class RecordViewModel {

    public final RxProperty<Boolean> isDmsFormat = new RxProperty<>(false);
    public final ReadOnlyRxProperty<List<String>> formattedRecords;

    /// 度分秒表示かのトグル
    public final RxCommand<Nothing> toggleIsDmsFormatCommand;

    @Inject
    public RecordViewModel(LocationUseCase locationUseCase) {

        formattedRecords = new ReadOnlyRxProperty<>(Observable.combineLatest(isDmsFormat, locationUseCase.records,
                (isDms, records) -> {
            return Stream.of(records).map(l -> DateFormat.format("kk:mm:ss",l.getTime()).toString()
                    + " - " + LatLonUtil.formatLocation(l, isDms))
                    .toList();
        }));

        // 度分秒表示かのトグル
        toggleIsDmsFormatCommand = new RxCommand<>(); // いつでも実行可能
        toggleIsDmsFormatCommand.subscribe(dummy -> {
            isDmsFormat.set(!isDmsFormat.get());
        });
    }
}
