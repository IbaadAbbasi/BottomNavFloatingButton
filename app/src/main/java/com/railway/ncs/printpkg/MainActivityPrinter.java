//package com.railway.ncs.printpkg;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.databinding.DataBindingUtil;
//
//
//import com.android.print.sdk.PrinterConstants;
//import com.android.print.sdk.PrinterInstance;
//import com.android.print.sdk.bluetooth.BluetoothPort;
//import com.railway.ncs.R;
//import com.railway.ncs.databinding.ActivityMainPrinterBinding;
//import com.railway.ncs.printpkg.bluetooth.BluetoothDeviceList;
//import com.railway.ncs.printpkg.bluetooth.BluetoothOperation;
//import com.railway.ncs.printpkg.permission.EasyPermission;
//import com.railway.ncs.printpkg.util.PrintUtils;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Timer;
//
//
//public class MainActivityPrinter extends AppCompatActivity implements EasyPermission.PermissionCallback{
//    protected Context context;
//    protected ActivityMainPrinterBinding bindingnew;
//
//    public static boolean isConnected;
//    public static IPrinterOpertion myOpertion;
//    protected PrinterInstance mPrinter;
//    protected ProgressDialog dialog;
//
//
//    public static final int CONNECT_DEVICE = 1;
//    public static final int ENABLE_BT = 2;
//    public static final int REQUEST_SELECT_FILE = 3;
//    public static final int REQUEST_PERMISSION = 4;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        bindingnew = DataBindingUtil.setContentView(this, R.layout.activity_main_printer);
//
//        context = this;
//
//        initView();
//    }
//
//
//    protected void initView(){
////        bindingnew.connectLayout.setOnClickListener(onClickListener);
////        bindingnew.printImage.setOnClickListener(onClickListener);
////
//
//
//        initDialog();
//    }
//
//    protected void initDialog(){
//        if(dialog != null && dialog.isShowing()){
//            dialog.dismiss();
//        }
//
//        dialog = new ProgressDialog(context);
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.setTitle("Connecting");
//        dialog.setMessage("Please Wait...");
//        dialog.setIndeterminate(true);
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
//    }
//
//
//
///*    protected View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (v == bindingnew.connectLayout){
//                connClick();
//
//            }else if(v == bindingnew.printImage){
//                printImage();
//
//            }
//
//        }
//    };*/
//
//
//
//
//    String[] permisions = new String[]{
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//    };
//
//    protected boolean hasSDcardPermissions() {
//
//        if (EasyPermission.hasPermissions(context, permisions)) {
//            return true;
//        } else {
//            EasyPermission.with(this)
//                    .rationale("\n" +
//                            "Selecting files requires SDCard read and write permissions")
//                    .addRequestCode(REQUEST_PERMISSION)
//                    .permissions(permisions)
//                    .request();
//        }
//        return false;
//    }
//
//
//    protected void startSelectFile(){
//        if(!isConnected && mPrinter == null) {
//            return;
//        }
//
//        if(hasSDcardPermissions()){
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("*/*");
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            startActivityForResult(intent, REQUEST_SELECT_FILE);
//        }
//    }
//
//
//
//    protected void printImage(){
//        if(!isConnected && mPrinter == null) {
//            return;
//        }
//        new Thread(){
//            @Override
//            public void run() {
//                PrintUtils.printImage(context.getResources(), mPrinter);
//            }
//        }.start();
//    }
//
//
//
//    protected Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case PrinterConstants.Connect.SUCCESS:
//                    isConnected = true;
//                    mPrinter = myOpertion.getPrinter();
//                    Timer timer = new Timer();
//                    myTask = new MyTask();
//                    timer.schedule(myTask, 0, 2000);
//                    Toast.makeText(context, R.string.yesconn, Toast.LENGTH_SHORT).show();
//                    break;
//                case PrinterConstants.Connect.FAILED:
//                    if(myTask != null){
//                        myTask.cancel();
//                    }
//                    isConnected = false;
//                    Toast.makeText(context, R.string.conn_failed, Toast.LENGTH_SHORT).show();
//                    break;
//                case PrinterConstants.Connect.CLOSED:
//                    if(myTask != null){
//                        myTask.cancel();
//                    }
//                    isConnected = false;
//                    Toast.makeText(context, R.string.conn_closed, Toast.LENGTH_SHORT).show();
//                    break;
//                case PrinterConstants.Connect.NODEVICE:
//                    isConnected = false;
//                    Toast.makeText(context, R.string.conn_no, Toast.LENGTH_SHORT).show();
//                    break;
//
//                default:
//                    break;
//            }
//
//            updateButtonState();
//
//            if (dialog != null && dialog.isShowing()) {
//                dialog.dismiss();
//            }
//        }
//
//    };
//
//
//    protected String bt_mac;
//    protected String bt_name;
////    protected String wifi_mac;
////    protected String wifi_name;
//
//
//    protected void updateButtonState() {
//        if(!isConnected){
////            bindingnew.connectAddress.setText(R.string.no_conn_address);
////            bindingnew.connectState.setText(R.string.connect);
////            bindingnew.connectName.setText(R.string.no_conn_name);
//        }else{
//            if( bt_mac!=null && !bt_mac.equals("")){
////                bindingnew.connectAddress.setText(getString(R.string.str_address)+ bt_mac);
////                bindingnew.connectState.setText(R.string.disconnect);
////                bindingnew.connectName.setText(getString(R.string.str_name)+bt_name);
//            }else if(bt_mac==null ) {
//                bt_mac= BluetoothPort.getmDeviceAddress();
//                bt_name=BluetoothPort.getmDeviceName();
////                bindingnew.connectAddress.setText(getString(R.string.str_address)+bt_mac);
////                bindingnew.connectState.setText(R.string.disconnect);
////                bindingnew.connectName.setText(getString(R.string.str_name)+bt_name);
//            }else {
////                bindingnew.connectAddress.setText(getString(R.string.disconnect));
////                bindingnew.connectState.setText(R.string.disconnect);
////                bindingnew.connectName.setText(getString(R.string.disconnect));
//            }
//
//        }
//    }
//
//
//
//
//    protected void connClick(){
//        if(isConnected){
//            myOpertion.close();
//            myOpertion = null;
//            mPrinter = null;
//        }else{
//
//            new AlertDialog.Builder(context)
//                    .setTitle(R.string.str_message)
//                    .setMessage(R.string.str_connlast)
//                    .setPositiveButton(R.string.yesconn, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int arg1) {
//                            openConn();
//                        }
//                    })
//                    .setNegativeButton(R.string.str_resel, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            reselConn();
//                        }
//                    })
//                    .show();
//        }
//    }
//
//
//    public void openConn(){
//
//        myOpertion = new BluetoothOperation(context, mHandler);
//        myOpertion.btAutoConn(context,  mHandler);
//
//
//    }
//
//
//    protected void reselConn(){
//
//        myOpertion = new BluetoothOperation(context, mHandler);
//        myOpertion.chooseDevice();
//
//
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//
//        if(requestCode == CONNECT_DEVICE && resultCode == RESULT_OK){
//
//            bt_mac = data.getExtras().getString(BluetoothDeviceList.EXTRA_DEVICE_ADDRESS);
//            bt_name = data.getExtras().getString(BluetoothDeviceList.EXTRA_DEVICE_NAME);
//            dialog.show();
//            new Thread(new Runnable() {
//                public void run() {
//                    myOpertion.open(data);
//                }
//            }).start();
//
//
//
//
//        }else if(requestCode == ENABLE_BT){
//            if (resultCode == Activity.RESULT_OK) {
//                myOpertion.chooseDevice();
//            } else {
//                Toast.makeText(this, R.string.bt_not_enabled, Toast.LENGTH_SHORT).show();
//            }
//
//
//
//        }
//
//
//    }
//
//    protected MyTask myTask;
//
//    public class MyTask extends java.util.TimerTask{
//        @Override
//        public void run() {
//            if(isConnected && mPrinter != null) {
//                byte[] by = mPrinter.read();
//                if (by != null) {
//                    System.out.println(mPrinter.isConnected() + " read byte " + Arrays.toString(by));
//                }
//            }
//        }
//    }
//
//
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
//    }
//
//
//    //有权限
//    @Override
//    public void onPermissionGranted(int requestCode, List<String> perms) {
//        startSelectFile();
//    }
//
//
//    //没有权限
//    @Override
//    public void onPermissionDenied(int requestCode, List<String> perms) {
//
//
//        boolean isAskAgain = EasyPermission.checkDeniedPermissionsNeverAskAgain(
//                this,
//                "\n" +
//                        "Selecting a file requires permission，\n" +
//                        "Please enable permissions in app settings。",
//                R.string.gotoSettings, R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                }, perms);
//    }
//}
