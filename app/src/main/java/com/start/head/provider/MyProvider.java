package com.start.head.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyProvider extends ContentProvider {
    public static final int TABLE1_DIR=0;
    public static final int TABLE1_ITEM=1;
    public static final int TABLE2_DIR=2;
    public static final int TABLE2_ITEM=3;
    private static UriMatcher uriMatcher;
    private static String thisName="com.start.head.provider";
    /**
     *          static{}(静态代码块)与{}(非静态代码块)的异同点
     * 相同点：都是在JVM加载类时且在构造方法执行之前执行，在类中都可以定义多个，
     * 　　　　一般在代码块中对一些static变量进行赋值。
     * 不同点：静态代码块在非静态代码块之前执行(静态代码块—>非静态代码块—>构造方法)。
     * 　　　　静态代码块只在第一次new执行一次，之后不再执行，而非静态代码块在每new
     * 　　　　一次就执行一次。非静态代码块可在普通方法中定义(不过作用不大)；而静态代码块不行。
     * */
    static {
        //静态方法必须静态变量
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(thisName,"table1",TABLE1_DIR);
        uriMatcher.addURI(thisName,"table1/#",TABLE1_ITEM);
        uriMatcher.addURI(thisName,"table2",TABLE2_DIR);
        uriMatcher.addURI(thisName,"table2/#",TABLE2_ITEM);
    }
    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                //查询table1表中的所有数据
                break;
            case TABLE1_ITEM:
                //查询table1表中的单条数据
                break;
            case TABLE2_DIR:
                //查询table2表中的所有数据
                break;
            case TABLE2_ITEM:
                //查询table2表中的单条数据
                break;
        }
        return null;
    }
    /**
     *表示匹配任意长度的任意字符：*
     *表示匹配任意长度的数字：#
     * */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String head="vnd.android.cursor.";
        String tail="/vnd"+thisName;
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                return head+"dir/"+tail+"table1";
            case TABLE1_ITEM:
                return head+"item/"+tail+"table1";
            case TABLE2_DIR:
                return head+"dir/"+tail+"table2";
            case TABLE2_ITEM:
                return head+"item/"+tail+"table2";
            default:
                break;
        }
        return null;
        /**
         * 必须以开头
         * 如果内容URL以路径结尾，则后接android.cursor.dir/，如果内容URL以id结尾，则后接android.cursor.item/
         * 最后接上vnd.<authority>.<path>
         * */
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
