package com.example.uc.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import java.lang.reflect.Field;

/**
 * 反射处理字体
 */
public final class FontsOverride {
    private static final String DEFAULT_MONOSPACE_BOLD_FONT_FILENAME = "fonts/TitilliumWeb-Bold.ttf";
    private static final String DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME = "fonts/TitilliumWeb-Regular.ttf";
    private static final int normal_idx = 0;
    private static final int sans_idx = 1;
    private static final int serif_idx = 2;
    private static final int monospace_idx = 3;
    private static Context mContext;

    /**
     * 设置默认字体
     *
     * @param context context
     *
     */
    public static void setDefaultFont(Context context) {
        try {
            mContext = context;
            setDefaultFonts2();

            // The following code is only necessary if you are using the android:typeface attribute
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                setDefaultFontForTypeFaceSans();
//                setDefaultFontForTypeFaceSansSerif();
//                setDefaultFontForTypeFaceMonospace();
            }
        } catch (NoSuchFieldException e) {
            // Field does not exist in this (version of the) class
            logFontError(e);
        } catch (IllegalAccessException e) {
            // Access rights not set correctly on field, i.e. we made a programming error
            logFontError(e);
        } catch (Throwable e) {
            // Must not crash app if there is a failure with overriding fonts!
            logFontError(e);
        }
    }



    private static void setDefaultFonts2() throws NoSuchFieldException, IllegalAccessException {
        final Typeface bold = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_BOLD_FONT_FILENAME);
        final Typeface normal = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME);

        Field defaultField = Typeface.class.getDeclaredField("DEFAULT");
        defaultField.setAccessible(true);
        defaultField.set(null, normal);

        Field defaultBoldField = Typeface.class.getDeclaredField("DEFAULT_BOLD");
        defaultBoldField.setAccessible(true);
        defaultBoldField.set(null, bold);


        Field sDefaults = Typeface.class.getDeclaredField("sDefaults");
        sDefaults.setAccessible(true);
        sDefaults.set(null, new Typeface[]{normal, bold, Typeface.create((String) null, Typeface.ITALIC), Typeface.create((String) null, Typeface.BOLD_ITALIC)});

        Field monospaceDefaultField = Typeface.class.getDeclaredField("MONOSPACE");
        monospaceDefaultField.setAccessible(true);
        monospaceDefaultField.set(null, normal);
    }

    private static AssetManager getAssets() {
        return mContext.getAssets();
    }


    private static void setDefaultFontForTypeFaceMonospace() throws NoSuchFieldException, IllegalAccessException {
        final Typeface bold = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_BOLD_FONT_FILENAME);
        final Typeface normal = Typeface.createFromAsset(getAssets(), DEFAULT_MONOSPACE_NORMAL_FONT_FILENAME);

        setTypeFaceDefaults(normal, bold, monospace_idx);
    }

    private static void setTypeFaceDefaults(Typeface normal, Typeface bold, int typefaceIndex) throws NoSuchFieldException, IllegalAccessException {
        Field typeFacesField = Typeface.class.getDeclaredField("sTypefaceCache");
        typeFacesField.setAccessible(true);

        SparseArray<SparseArray<Typeface>> sTypefaceCacheLocal = new SparseArray<SparseArray<Typeface>>(3);
        typeFacesField.get(sTypefaceCacheLocal);

        SparseArray<Typeface> newValues = new SparseArray<Typeface>(2);
        newValues.put(Typeface.NORMAL, normal);
        newValues.put(Typeface.BOLD, bold);
        sTypefaceCacheLocal.put(typefaceIndex, newValues);

        typeFacesField.set(null, sTypefaceCacheLocal);
    }

    private static void logFontError(Throwable e) {
        Log.e("FontsOv","font_override Error overriding font", e);
    }
}