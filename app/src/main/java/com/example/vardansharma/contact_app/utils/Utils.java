package com.example.vardansharma.contact_app.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.vardansharma.contact_app.data.models.Contact;

/**
 * Created by vardansharma on 15/11/16.
 */

public class Utils {
    public static final String BASE_URL = "http://gojek-contacts-app.herokuapp.com";

    private Utils() {
        throw new IllegalStateException("No instances please");
    }

    public static float dpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static String getProfileUrl(String url) {
        return BASE_URL + url;
    }

    public static String getDisplayName(Contact contact) {
        return new StringBuilder()
                .append(contact.getFirstName())
                .append(" ")
                .append(contact.getLastName())
                .toString();
    }
}
