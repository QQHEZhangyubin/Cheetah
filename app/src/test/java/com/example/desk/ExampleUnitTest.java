package com.example.desk;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        List<String> mList = new ArrayList<>();
        mList.add("s1");
        mList.add("s2");
        mList.add("s3");
        // // method1: In Java 8 ~ String.join(..)
        // String mListStr = String.join(",", mList);
        // String mListStr = listToString(mList);
        String mListStr = listToString2(mList);
        System.out.println("mListStr = " + mListStr);

    }

    private String listToString2(List<String> mList) {

        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            String[] mListArray = mList.toArray(new String[mList.size()]);
            for (int i = 0; i < mListArray.length; i++) {
                if (i < mListArray.length - 1) {
                    convertedListStr += mListArray[i] + ",";
                } else {
                    convertedListStr += mListArray[i];
                }
            }
            return convertedListStr;
        } else return "List is null!!!";
    }
}