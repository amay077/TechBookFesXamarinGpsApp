﻿using System;
using Microsoft.Practices.Unity;
using Prism.Mvvm;
using Prism.Unity;
using GpsAppXamarinForms.UseCases;
using GpsAppXamarinForms.Views;
using Xamarin.Forms;

namespace GpsAppXamarinForms
{
    public class FormsApp : PrismApplication
    {
        public FormsApp(IPlatformInitializer initializer = null) : base(initializer) 
        {
        }

        protected override void OnInitialized()
        {
            NavigationService.NavigateAsync("NavigationPage/MainPage");
        }

        protected override void RegisterTypes()
        {
            Container.RegisterType<LocationUseCase, LocationUseCase>(new ContainerControlledLifetimeManager());

            Container.RegisterTypeForNavigation<NavigationPage>();
            Container.RegisterTypeForNavigation<MainPage>();
            Container.RegisterTypeForNavigation<RecordsPage>();
        }

        protected override void ConfigureViewModelLocator()
        {
            base.ConfigureViewModelLocator();
            ViewModelLocationProvider.SetDefaultViewTypeToViewModelTypeResolver(viewType =>
            {
                var thisNameSpace = this.GetType().Namespace;
                if (viewType.Namespace.StartsWith(thisNameSpace, StringComparison.Ordinal))
                {
                    var vmNameSpace = viewType.Namespace.Replace("Views", "ViewModels");
                    var vmClassName = viewType.Name.Replace("Page", "ViewModel");
                    var vmTypeName = $"{vmNameSpace}.{vmClassName}";
                    return Type.GetType(vmTypeName);
                }
                else
                {
                    return Type.GetType($"{viewType.FullName}ViewModel");
                }
            });
        }
    }
}
