package me.topits.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author QingKe
 * @date 2020-09-17 11:32
 **/
public class GsonFactory {

    public static Gson gson() {
        return new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .disableInnerClassSerialization()
                .create();
    }

}
