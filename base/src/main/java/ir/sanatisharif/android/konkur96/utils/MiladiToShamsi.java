package ir.sanatisharif.android.konkur96.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class MiladiToShamsi {

    private static int iYear;
    private static int iMonth;
    private static int iDay;
    public static String time;
    public static String Date;

    static int shamsiDay, shamsiMonth, shamsiYear;

    static int dayCount, farvardinDayDiff, deyDayDiff;

    public int getiYear() {
        return iYear;
    }

    public int getiMonth() {
        return iMonth;
    }

    public int getiDay() {
        return iDay;
    }

    static int sumDayMiladiMonth[] = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273,
            304, 334};

    static int sumDayMiladiMonthLeap[] = {0, 31, 60, 91, 121, 152, 182, 213, 244,
            274, 305, 335};

    public MiladiToShamsi() {

    }

    public static void MiladiToShamsi(int iMiladiMonth, int iMiladiDay, int iMiladiYear)

    {


        farvardinDayDiff = 79;

        if (MiladiIsLeap(iMiladiYear)) {
            dayCount = sumDayMiladiMonthLeap[iMiladiMonth - 1] + iMiladiDay;

        } else {
            dayCount = sumDayMiladiMonth[iMiladiMonth - 1] + iMiladiDay;

        }

        if ((MiladiIsLeap(iMiladiYear - 1))) {

            deyDayDiff = 11;

        } else {
            deyDayDiff = 10;

        }

        if (dayCount > farvardinDayDiff) {
            dayCount = dayCount - farvardinDayDiff;

            if (dayCount <= 186) {
                switch (dayCount % 31) {
                    case 0:
                        shamsiMonth = dayCount / 31;
                        shamsiDay = 31;
                        break;

                    default:
                        shamsiMonth = (dayCount / 31) + 1;
                        shamsiDay = (dayCount % 31);
                        break;
                }

                shamsiYear = iMiladiYear - 621;

            } else {
                dayCount = dayCount - 186;
                switch (dayCount % 30) {
                    case 0:
                        shamsiMonth = (dayCount / 30) + 6;
                        shamsiDay = 30;

                        break;

                    default:
                        shamsiMonth = (dayCount / 30) + 7;
                        shamsiDay = (dayCount % 30);

                        break;
                }
                shamsiYear = iMiladiYear - 621;
            }

        } else {
            dayCount = dayCount + deyDayDiff;

            switch (dayCount % 30) {
                case 0:
                    shamsiMonth = (dayCount / 30) + 9;
                    shamsiDay = 30;
                    break;

                default:
                    shamsiMonth = (dayCount / 30) + 10;
                    shamsiDay = (dayCount % 30);
                    break;

            }
            shamsiYear = iMiladiYear - 622;
        }

        iYear = shamsiYear;
        iMonth = shamsiMonth;
        iDay = shamsiDay;

    }

    static Boolean MiladiIsLeap(int miladiYear) {

        if (((miladiYear % 100) != 0 && (miladiYear % 4) == 0)
                || ((miladiYear % 100) == 0 && (miladiYear % 400) == 0))

            return true;
        else
            return false;

    }

    public void dateTime() {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());

        MiladiToShamsi(Integer.parseInt(time.substring(4, 6)),// month
                Integer.parseInt(time.substring(6, 8)),// day
                Integer.parseInt(time.substring(0, 4)));// year

        if (iMonth < 10 && iDay < 10)
            Date = iYear + "-0" + iMonth + "-0" + iDay;
        else if (iMonth < 10)
            Date = iYear + "-0" + iMonth + "-" + iDay;
        else if (iDay < 10)
            Date = iYear + "-" + iMonth + "-0" + iDay;
        else
            Date = iYear + "-" + iMonth + "-" + iDay;

        MiladiToShamsi.time =
                time.substring(9, 11) + ":"
                        + time.substring(11, 13) + ":"
                        + time.substring(13, 15);

    }

    public static void displayDateMiladi(String date) {

        // 2017-07-01 10:34:17
        MiladiToShamsi(Integer.parseInt(date.substring(5, 7)),// month
                Integer.parseInt(date.substring(8, 10)),// day
                Integer.parseInt(date.substring(0, 4)));// year

        if (iMonth < 10 && iDay < 10)
            Date = iYear + "-0" + iMonth + "-0" + iDay;
        else if (iMonth < 10)
            Date = iYear + "-0" + iMonth + "-" + iDay;
        else if (iDay < 10)
            Date = iYear + "-" + iMonth + "-0" + iDay;
        else
            Date = iYear + "-" + iMonth + "-" + iDay;
    }

    public static void displayDateMiladiToShamsi(String date) {

        displayDateMiladi(date);
        String year = Date.substring(0, 4);
        int day = Integer.parseInt(Date.substring(8, 10));
        int month = Integer.parseInt(Date.substring(5, 7));

        String temp = "";

        switch (month) {
            case 1:
                temp = "فروردین";
                break;

            case 2:
                temp = "اردیبهشت";
                break;

            case 3:
                temp = "خرداد";
                break;

            case 4:
                temp = "تیر";
                break;

            case 5:
                temp = "مرداد";
                break;

            case 6:
                temp = "شهریور";
                break;

            case 7:
                temp = "مهر";
                break;

            case 8:
                temp = "آبان";
                break;

            case 9:
                temp = "آذر";
                break;

            case 10:
                temp = "دی";
                break;

            case 11:
                temp = "بهمن";
                break;

            case 12:
                temp = "اسفند";
                break;

        }

        time = date.substring(11, 13) + ":" + date.substring(14, 16);
        Date = day + " " + temp + " " + year + " ";

    }

    public static void displayDateShamsi(String date) {

        String year = date.substring(0, 4);
        int day = Integer.parseInt(date.substring(8, 10));
        int month = Integer.parseInt(date.substring(5, 7));

        String temp = "";

        switch (month) {
            case 1:
                temp = "فروردین";
                break;

            case 2:
                temp = "اردیبهشت";
                break;

            case 3:
                temp = "خرداد";
                break;

            case 4:
                temp = "تیر";
                break;

            case 5:
                temp = "مرداد";
                break;

            case 6:
                temp = "شهریور";
                break;

            case 7:
                temp = "مهر";
                break;

            case 8:
                temp = "آبان";
                break;

            case 9:
                temp = "آذر";
                break;

            case 10:
                temp = "دی";
                break;

            case 11:
                temp = "بهمن";
                break;

            case 12:
                temp = "اسفند";
                break;

        }

       // time = date.substring(11, 13) + ":" + date.substring(14, 16);
        Date = day + " " + temp + " " + year + " ";

    }

}
