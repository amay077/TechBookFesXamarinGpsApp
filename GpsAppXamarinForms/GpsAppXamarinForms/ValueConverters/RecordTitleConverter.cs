using System;
using System.Globalization;
using Xamarin.Forms;

namespace GpsAppXamarinForms.ValueConverters
{
    public class RecordTitleConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            var count = (int)value;
            return count > 0 ? $"RECORD({count})" : "RECORD";
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
