using System;
using System.Collections.Generic;
using System.Linq;
using System.Reactive.Linq;
using Reactive.Bindings;
using GpsAppXamarinForms.Extensions;
using GpsAppXamarinForms.UseCases;

namespace GpsAppXamarinForms.ViewModels
{
    public class RecordsViewModel
    {
        public ReactiveProperty<bool> IsDmsFormat { get; } = new ReactiveProperty<bool>(true);
        
        public ReadOnlyReactiveProperty<IEnumerable<string>> FormattedRecords { get; }

        public RecordsViewModel(LocationUseCase locationUseCase)
        {
            // なんかフクザツになっちゃったけど、IsDmsFormat が変わる度に、
            // ObservableCollection を ReactiveProperty<IEnumerable<string>>
            // に変換してます。
            FormattedRecords = IsDmsFormat.CombineLatest(
                locationUseCase.Records.ToCollectionChanged().ToReactiveProperty(), (isDms, _) => 
                {
                    // ここは LINQ ね
                    return locationUseCase
                        .Records
                        .Select(l => $"{l.Time:HH:mm.ss} - {l.Latitude.Format(isDms)}/{l.Longitude.Format(isDms)}");
                })
                .ToReadOnlyReactiveProperty();
        }
    }
}
