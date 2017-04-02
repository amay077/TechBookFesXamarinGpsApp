using System;
using GpsAppXamarinForms.Api.DataModels;

namespace GpsAppXamarinForms.Api
{
    public interface ILocationClient
    {
        event EventHandler<Location> LocationChanged;
        event EventHandler<bool> IsRunningChanged;

        Location? LatestLocation { get; }

        bool IsRunning { get; }

        void Start();
        void Stop();
    }
}
