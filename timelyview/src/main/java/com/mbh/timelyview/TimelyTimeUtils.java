package com.mbh.timelyview;

/**
 * Created By MBH on 2016-10-03.
 */

class TimelyTimeUtils {
    static boolean compareArrays(int[] array1, int[] array2) {
        if (array1 != null && array2 != null){
            if (array1.length != array2.length)
                return false;
            else
                for (int i = 0; i < array2.length; i++) {
                    if (array2[i] != array1[i]) {
                        return false;
                    }
                }
        }else{
            return false;
        }
        return true;
    }

    static void checkNull(Object object){
        if(object == null) throw new IllegalArgumentException("Argument cannot be null");
    }

    static void checkValidPositiveInt(int num){
        if(num < 0) throw new IllegalArgumentException("Number cannot be less than zero 0");
    }

    static void checkValidPositiveLong(long num){
        if(num < 0) throw new IllegalArgumentException("Number cannot be less than zero 0");
    }

    static void checkTimelyTimeArray(int [] timelyArray, boolean isShort){
        checkNull(timelyArray);
        int length = isShort?2:3;
        if(timelyArray.length != length) throw new IllegalArgumentException("Array should have 3 elements for Hour, Min, Sec");
        for (int timelyInt :
                timelyArray) {
            if(timelyInt<0) throw new IllegalArgumentException("Time cannot be less than Zero");
        }
    }

    static int tryParseInt (String str, int def){
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
