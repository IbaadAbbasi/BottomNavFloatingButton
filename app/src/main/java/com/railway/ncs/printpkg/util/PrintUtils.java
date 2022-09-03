package com.railway.ncs.printpkg.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.print.sdk.PrinterConstants.Command;
import com.android.print.sdk.PrinterInstance;
import com.railway.ncs.R;

import java.io.IOException;

public class PrintUtils {




    public static void printImage(Resources resources, PrinterInstance mPrinter) {

        mPrinter.init();
        mPrinter.setFont(0, 0, 0, 0);


        mPrinter.printText("\n\n\n");
        mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_CENTER);
        mPrinter.printText(resources.getString(R.string.str_ncs));


        mPrinter.printText("\n");

//        mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_CENTER);
//        Bitmap bitmap1 = null;
//        try {
//            bitmap1 = BitmapFactory.decodeStream(resources.getAssets().open("support.png"));
////            bitmap1 = BitmapFactory.decodeStream(resources.getAssets().open("img.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//        mPrinter.printImage(bitmap1);
        mPrinter.printText("\n");
        mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_CENTER);
        mPrinter.printText(resources.getString(R.string.str_sr));
        mPrinter.printText("\n");
        mPrinter.printText("-------------------------------");
        mPrinter.printText("\n");



//on all leftside
        mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_LEFT);
        mPrinter.printText(resources.getString(R.string.str_from));
        mPrinter.printText("\t");
        mPrinter.printText(resources.getString(R.string.str_dara));
        mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

        mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_LEFT);
        mPrinter.printText(resources.getString(R.string.str_to));
        mPrinter.printText(resources.getString(R.string.str_rawalpindi));
        mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

        mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_LEFT);
        mPrinter.printText(resources.getString(R.string.str_class));
        mPrinter.printText(resources.getString(R.string.str_economy));
        mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);

        mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_LEFT);
        mPrinter.printText(resources.getString(R.string.str_date));
        mPrinter.printText(resources.getString(R.string.str_date_time));
//        mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 1);
        mPrinter.printText("\n\n\n");



        /*mPrinter.init();
        mPrinter.setFont(0, 0, 0, 0);
        mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_LEFT);
        mPrinter.printText(resources.getString(R.string.password));
        mPrinter.setPrinter(Command.PRINT_AND_WAKE_PAPER_BY_LINE, 2);

        Bitmap bitmap1 = null;
        try {
            bitmap1 = BitmapFactory.decodeStream(resources.getAssets().open("android.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        mPrinter.printImage(bitmap1);
        mPrinter.printText("\n\n\n\n");                     //换4行

        try {
            bitmap1 = BitmapFactory.decodeStream(resources.getAssets().open("support.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        mPrinter.setPrinter(Command.ALIGN, Command.ALIGN_CENTER);   //设置打印居中
        mPrinter.printText(resources.getString(R.string.str_image));
        mPrinter.printImage(bitmap1);
        mPrinter.printText("\n\n\n\n");                     //换4行*/
    }


}
