using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Reactive.Linq;
using Reactive.Bindings;
using GpsAppXamarinForms.Api.DataModels;
using GpsAppXamarinForms.Extensions;
using GpsAppXamarinForms.Models;
using GpsAppXamarinForms.UseCases;

namespace GpsAppXamarinForms.ViewModels
{
    public class RecordsViewModel
    {
        public ReactiveProperty<bool> IsDmsFormat { get; } = new ReactiveProperty<bool>(true);
        
        public ReadOnlyReactiveProperty<IEnumerable<string>> FormattedRecords { get; }

        public RecordsViewModel(LocationUseCase locationUseCase)
        {

            FormattedRecords = IsDmsFormat.CombineLatest(
                locationUseCase.Records.ToCollectionChanged().ToReactiveProperty(), (isDms, _) => 
                {
                    return locationUseCase
                        .Records
                        .Select(l => $"{l.Time:HH:mm.ss} - {l.Latitude.Format(isDms)}/{l.Longitude.Format(isDms)}");
                })
                .ToReadOnlyReactiveProperty();
        }
    }
}
