package sngine.app.webview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.SslErrorHandler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricManager;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import androidx.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.onesignal.OneSignal;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OSNotificationReceivedEvent;

public class MainActivity extends AppCompatActivity {

    //Permission variables
    static boolean SngineApp_JSCRIPT = SngineConfig.SngineApp_JSCRIPT;
    static boolean SngineApp_FUPLOAD = SngineConfig.SngineApp_FUPLOAD;
    static boolean SngineApp_CAMUPLOAD = SngineConfig.SngineApp_CAMUPLOAD;
    static boolean SngineApp_ONLYCAM = SngineConfig.SngineApp_ONLYCAM;
    static boolean SngineApp_MULFILE = SngineConfig.SngineApp_MULFILE;
    static boolean SngineApp_LOCATION = SngineConfig.SngineApp_LOCATION;
    static boolean SngineApp_RATINGS = SngineConfig.SngineApp_RATINGS;
    static boolean SngineApp_PULLFRESH = SngineConfig.SngineApp_PULLFRESH;
    static boolean SngineApp_PBAR = SngineConfig.SngineApp_PBAR;
    static boolean SngineApp_ZOOM = SngineConfig.SngineApp_ZOOM;
    static boolean SngineApp_SFORM = SngineConfig.SngineApp_SFORM;
    static boolean SngineApp_OFFLINE = SngineConfig.SngineApp_OFFLINE;
    static boolean SngineApp_EXTURL = SngineConfig.SngineApp_EXTURL;
    static boolean SngineApp_OFFLINE_CACHE = SngineConfig.SngineApp_OFFLINE_CACHE;
    static boolean SngineApp_BIOMETRIC = SngineConfig.SngineApp_BIOMETRIC;
    static boolean SngineApp_DARK_MODE = SngineConfig.SngineApp_DARK_MODE;
    static boolean SngineApp_BACKGROUND_AUDIO = SngineConfig.SngineApp_BACKGROUND_AUDIO;
    static boolean SngineApp_CRASH_REPORTING = SngineConfig.SngineApp_CRASH_REPORTING;
    static boolean SngineApp_PERFORMANCE_MONITORING = SngineConfig.SngineApp_PERFORMANCE_MONITORING;

    //Security variables
    static boolean SngineApp_CERT_VERIFICATION = SngineConfig.SngineApp_CERT_VERIFICATION;

    //Configuration variables
    private static String Sngine_URL = SngineConfig.Sngine_URL;
    private String CURR_URL = Sngine_URL;

    private String oneSignalUserID;

    private static String Sngine_F_TYPE = SngineConfig.Sngine_F_TYPE;

    private static String Sngine_ONESIGNAL_APP_ID = SngineConfig.Sngine_ONESIGNAL_APP_ID;

    public static String ASWV_HOST = aswm_host(Sngine_URL);

    //Careful with these variable names if altering
    WebView swvp_view;
    ProgressBar swvp_progress;
    TextView swvp_loading_text;
    NotificationManager swvp_notification;
    Notification swvp_notification_new;

    // Fullscreen video support
    private View mCustomView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private int mOriginalOrientation;
    private int mOriginalSystemUiVisibility;

    private String swvp_cam_message;
    private ValueCallback<Uri> swvp_file_message;
    private ValueCallback<Uri[]> swvp_file_path;
    private final static int swvp_file_req = 1;

    private final static int loc_perm = 1;
    private final static int file_perm = 2;

    private SecureRandom random = new SecureRandom();

    private static final String TAG = MainActivity.class.getSimpleName();

    // Additional variables for new features
    private OkHttpClient okHttpClient;
    private BillingClient billingClient;
    private BiometricPrompt biometricPrompt;
    private MediaSessionCompat mediaSession;
    private SharedPreferences sharedPreferences;
    
    // Navigation drawer
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            Uri[] results = null;
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == swvp_file_req) {
                    if (null == swvp_file_path) {
                        return;
                    }
                    if (intent == null || intent.getData() == null) {
                        if (swvp_cam_message != null) {
                            results = new Uri[]{Uri.parse(swvp_cam_message)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        } else {
                            if (SngineApp_MULFILE) {
                                if (intent.getClipData() != null) {
                                    final int numSelectedFiles = intent.getClipData().getItemCount();
                                    results = new Uri[numSelectedFiles];
                                    for (int i = 0; i < numSelectedFiles; i++) {
                                        results[i] = intent.getClipData().getItemAt(i).getUri();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            swvp_file_path.onReceiveValue(results);
            swvp_file_path = null;
        } else {
            if (requestCode == swvp_file_req) {
                if (null == swvp_file_message) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                swvp_file_message.onReceiveValue(result);
                swvp_file_message = null;
            }
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Firebase Crashlytics initialization
        if (SngineApp_CRASH_REPORTING) {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        }

        // Firebase Analytics initialization
        if (SngineApp_PERFORMANCE_MONITORING) {
            FirebaseAnalytics.getInstance(this);
        }

        // Offline Caching Setup
        if (SngineApp_OFFLINE_CACHE) {
            setupOfflineCaching();
        }

        // Biometric Authentication Setup
        if (SngineApp_BIOMETRIC) {
            setupBiometricAuthentication();
        }

        // In-App Purchases Setup
        setupInAppPurchases();

        // Dark Mode Setup
        if (SngineApp_DARK_MODE) {
            setupDarkMode();
        }

        // Background Audio Setup
        if (SngineApp_BACKGROUND_AUDIO) {
            setupBackgroundAudio();
        }

        // Multi-language Support Setup
        setupMultiLanguageSupport();

        // OneSignal Initialization
        if(!Objects.equals(Sngine_ONESIGNAL_APP_ID, "")) {
            OneSignal.setAppId(Sngine_ONESIGNAL_APP_ID);
            OneSignal.initWithContext(this);

            // Get OneSignal user ID after initialization
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (OneSignal.getDeviceState() != null) {
                        oneSignalUserID = OneSignal.getDeviceState().getUserId();
                    }
                }
            }, 1000);

            // Set up notification opened handler
            OneSignal.setNotificationOpenedHandler(new OneSignal.OSNotificationOpenedHandler() {
                @Override
                public void notificationOpened(OSNotificationOpenedResult result) {
                    OSNotification notification = result.getNotification();
                    if (notification != null) {
                        String url = null;
                        // Try to get URL from additional data
                        if (notification.getAdditionalData() != null) {
                            url = notification.getAdditionalData().optString("url", null);
                        }
                        
                        if (url != null && !url.isEmpty()) {
                            // Handle deep linking
                            if (url.startsWith("sngine://")) {
                                url = url.replace("sngine://", Sngine_URL);
                            }
                            aswm_view(url, false);
                        }
                        Log.d(TAG, "Notification opened: " + notification.getTitle());
                    }
                }
            });

            // Set up notification received handler
            OneSignal.setNotificationWillShowInForegroundHandler(new OneSignal.OSNotificationWillShowInForegroundHandler() {
                @Override
                public void notificationWillShowInForeground(OSNotificationReceivedEvent event) {
                    OSNotification notification = event.getNotification();
                    if (notification != null) {
                        Log.d(TAG, "Notification will display: " + notification.getTitle());
                        // Complete the notification display
                        event.complete(notification);
                    }
                }
            });
        }

        Intent intent = getIntent();
        String action = intent.getAction();
        if(action == null){
        //Prevent the app from being started again when it is still alive in the background
        if (!isTaskRoot()) {
            finish();
            return;
        }
        }

        setContentView(R.layout.activity_main);
        
        // Setup toolbar and navigation drawer
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_more);
        }
        
        drawerLayout = findViewById(R.id.drawer_layout);
        setupNavigationDrawer();
        
        swvp_view = findViewById(R.id.msw_view);
        swvp_view.getSettings().setJavaScriptEnabled(true);

        final SwipeRefreshLayout pullfresh = findViewById(R.id.pullfresh);
        if (SngineApp_PULLFRESH) {
            pullfresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pull_fresh();
                    pullfresh.setRefreshing(false);
                }
            });
            swvp_view.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (swvp_view.getScrollY() == 0) {
                        pullfresh.setEnabled(true);
                    } else {
                        pullfresh.setEnabled(false);
                    }
                }
            });
        } else {
            pullfresh.setRefreshing(false);
            pullfresh.setEnabled(false);
        }

        if (SngineApp_PBAR) {
            swvp_progress = findViewById(R.id.msw_progress);
        } else {
            findViewById(R.id.msw_progress).setVisibility(View.GONE);
        }
        swvp_loading_text = findViewById(R.id.msw_loading_text);
        Handler handler = new Handler();

        //Launching app rating request
        if (SngineApp_RATINGS) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    get_rating();
                }
            }, 1000 * 60); //running request after few moments
        }

        //Getting basic device information
        get_info();

        //Getting GPS location of device if given permission
        get_location();

        //Webview settings; defaults are customized for best performance
        WebSettings webSettings = swvp_view.getSettings();
        swvp_view.getSettings().setUserAgentString("Sngine");
        swvp_view.getSettings().setMediaPlaybackRequiresUserGesture(false);

        if (!SngineApp_OFFLINE) {
            webSettings.setJavaScriptEnabled(SngineApp_JSCRIPT);
        }
        webSettings.setSaveFormData(SngineApp_SFORM);
        webSettings.setSupportZoom(SngineApp_ZOOM);
        webSettings.setGeolocationEnabled(SngineApp_LOCATION);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);

        swvp_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        swvp_view.setHapticFeedbackEnabled(false);

        swvp_view.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {

                if (!check_permission(2)) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, file_perm);
                } else {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                    request.setMimeType(mimeType);
                    String cookies = CookieManager.getInstance().getCookie(url);
                    request.addRequestHeader("cookie", cookies);
                    request.addRequestHeader("User-Agent", userAgent);
                    request.setDescription(getString(R.string.dl_downloading));
                    request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    assert dm != null;
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), getString(R.string.dl_downloading2), Toast.LENGTH_LONG).show();
                }
            }
        });

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        swvp_view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        swvp_view.setVerticalScrollBarEnabled(false);
        swvp_view.setWebViewClient(new Callback());

        // Add JavaScript interface for device sensors and native operations
        swvp_view.addJavascriptInterface(new JSInterface(this), "Android");

        //Rendering the default URL
        aswm_view(Sngine_URL, false);

        //Get Cam & Mic & location permissions
        if (!check_permission(1) || !check_permission(3) || !check_permission(4)) {
            //Cam & Mic & location permissions not granted so request them
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 20);
        }

        swvp_view.setWebChromeClient(new WebChromeClient() {
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }

            // Fullscreen video support
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                if (mCustomView != null) {
                    onHideCustomView();
                    return;
                }
                mCustomView = view;
                mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                mOriginalOrientation = getRequestedOrientation();
                mCustomViewCallback = callback;
                FrameLayout decor = (FrameLayout) getWindow().getDecorView();
                decor.addView(mCustomView, new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE);
                setRequestedOrientation(mOriginalOrientation);
                mCustomView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onHideCustomView() {
                if (mCustomView == null) {
                    return;
                }
                FrameLayout decor = (FrameLayout) getWindow().getDecorView();
                decor.removeView(mCustomView);
                mCustomView = null;
                getWindow().getDecorView().setSystemUiVisibility(mOriginalSystemUiVisibility);
                setRequestedOrientation(mOriginalOrientation);
                if (mCustomViewCallback != null) {
                    mCustomViewCallback.onCustomViewHidden();
                }
                mCustomViewCallback = null;
            }

            //Handling input[type="file"]
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (check_permission(3)) {
                    if (SngineApp_FUPLOAD) {
                        if (swvp_file_path != null) {
                            swvp_file_path.onReceiveValue(null);
                        }
                        swvp_file_path = filePathCallback;
                        Intent takePictureIntent = null;
                        if (SngineApp_CAMUPLOAD) {
                            takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(MainActivity.this.getPackageManager()) != null) {
                                File photoFile = null;
                                Uri photoURI = null;
                                try {
                                    photoFile = create_image();
                                    photoURI = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
                                } catch (IOException ex) {
                                    Log.e(TAG, "Image file creation failed", ex);
                                }
                                if (photoURI != null) {
                                    swvp_cam_message = "file:" + photoFile.getAbsolutePath();
                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                } else {
                                    takePictureIntent = null;
                                }
                            }
                        }
                        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        if (!SngineApp_ONLYCAM) {
                            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                            contentSelectionIntent.setType(Sngine_F_TYPE);
                            if (SngineApp_MULFILE) {
                                contentSelectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            }
                        }
                        Intent[] intentArray;
                        if (takePictureIntent != null) {
                            intentArray = new Intent[]{takePictureIntent};
                        } else {
                            intentArray = new Intent[0];
                        }

                        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                        chooserIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.fl_chooser));
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                        startActivityForResult(chooserIntent, swvp_file_req);
                    }
                    return true;
                } else {
                    get_file();
                    return false;
                }
            }

            //Getting webview rendering progress
            @Override
            public void onProgressChanged(WebView view, int p) {
                if (SngineApp_PBAR) {
                    swvp_progress.setProgress(p);
                    if (p == 100) {
                        swvp_progress.setProgress(0);
                    }
                }
            }
        });
        if (getIntent().getData() != null) {
            String path = getIntent().getDataString();
            if(path != null && path.startsWith("sngine://")) {
                path = path.replace("sngine://", "https://");
            }
            aswm_view(path, false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        swvp_view.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        swvp_view.onResume();
        //Coloring the "recent apps" tab header; doing it onResume, as an insurance
        if (Build.VERSION.SDK_INT >= 23) {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            ActivityManager.TaskDescription taskDesc;
            taskDesc = new ActivityManager.TaskDescription(getString(R.string.app_name), bm, getColor(R.color.colorPrimary));
            MainActivity.this.setTaskDescription(taskDesc);
        }
        get_location();
    }

    //Setting activity layout visibility
    private class Callback extends WebViewClient {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            get_location();
        }

        public void onPageFinished(WebView view, String url) {
            findViewById(R.id.msw_welcome).setVisibility(View.GONE);
            findViewById(R.id.msw_view).setVisibility(View.VISIBLE);
            // Inject JavaScript to save the OneSignal user ID after the page has loaded
            if (oneSignalUserID != null) {
                swvp_view.loadUrl("javascript:saveAndroidOneSignalUserId('" + oneSignalUserID + "')");
            }
        }

        //For android below API 23
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (!DetectConnection.isInternetAvailable(MainActivity.this)) {
                aswm_view("file:///android_asset/offline.html", false);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.went_wrong), Toast.LENGTH_SHORT).show();
                aswm_view("file:///android_asset/error.html", false);
            }
        }

        //Overriding webview URLs
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            CURR_URL = url;
            return url_actions(view, url);
        }

        //Overriding webview URLs for API 23+ [suggested by github.com/JakePou]
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            CURR_URL = request.getUrl().toString();
            return url_actions(view, request.getUrl().toString());
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            if (SngineApp_CERT_VERIFICATION) {
                super.onReceivedSslError(view, handler, error);
            } else {
                handler.proceed(); // Ignore SSL certificate errors
            }
        }
    }

    //Random ID creation function to help get fresh cache every-time webview reloaded
    public String random_id() {
        return new BigInteger(130, random).toString(32);
    }

    //Opening URLs inside webview with request
    void aswm_view(String url, Boolean tab) {
        if (tab) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } else {
            if (url.contains("?")) { // check to see whether the url already has query parameters and handle appropriately.
                url += "&";
            } else {
                url += "?";
            }
            url += "rid=" + random_id();
            swvp_view.loadUrl(url);
        }
    }

    //Actions based on shouldOverrideUrlLoading
    public boolean url_actions(WebView view, String url) {
        boolean a = true;
        //Show toast error if not connected to the network
        if (!SngineApp_OFFLINE && !DetectConnection.isInternetAvailable(MainActivity.this)) {
            Toast.makeText(getApplicationContext(), getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
            aswm_view("file:///android_asset/offline.html", false);
            return true;
        } else if (url.startsWith("sngine:")) {
            // Replace custom scheme with actual URL
            String newUrl = url.replace("sngine://", "https://");
            aswm_view(newUrl, false);

            //Use this in a hyperlink to redirect back to default URL :: href="refresh:android"
        } else if (url.startsWith("refresh:")) {
            String ref_sch = (Uri.parse(url).toString()).replace("refresh:", "");
            if (ref_sch.matches("URL")) {
                CURR_URL = Sngine_URL;
            }
            pull_fresh();

            //Use this in a hyperlink to launch default phone dialer for specific number :: href="tel:+919876543210"
        } else if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            startActivity(intent);

            //Use this to open your apps page on google play store app :: href="rate:android"
        } else if (url.startsWith("rate:")) {
            final String app_package = getPackageName(); //requesting app package name from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + app_package)));
            } catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + app_package)));
            }

            //Sharing content from your webview to external apps :: href="share:URL" and remember to place the URL you want to share after share:___
        } else if (url.startsWith("share:")) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, view.getTitle());
            intent.putExtra(Intent.EXTRA_TEXT, view.getTitle() + "\nVisit: " + (Uri.parse(url).toString()).replace("share:", ""));
            startActivity(Intent.createChooser(intent, getString(R.string.share_w_friends)));

            //Use this in a hyperlink to exit your app :: href="exit:android"
        } else if (url.startsWith("exit:")) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            //Getting location for offline files
        } else if (url.startsWith("offloc:")) {
            String offloc = Sngine_URL + "?loc=" + get_location();
            aswm_view(offloc, false);
            Log.d("OFFLINE LOC REQ", offloc);

            //Opening external URLs in android default web browser
        } else if (SngineApp_EXTURL && !aswm_host(url).equals(ASWV_HOST)) {
            aswm_view(url, true);

        } else {
            a = false;
        }
        return a;
    }

    //Getting host name
    public static String aswm_host(String url) {
        if (url == null || url.length() == 0) {
            return "";
        }
        int dslash = url.indexOf("//");
        if (dslash == -1) {
            dslash = 0;
        } else {
            dslash += 2;
        }
        int end = url.indexOf('/', dslash);
        end = end >= 0 ? end : url.length();
        int port = url.indexOf(':', dslash);
        end = (port > 0 && port < end) ? port : end;
        return url.substring(dslash, end);
    }

    //Reloading current page
    public void pull_fresh() {
        aswm_view((!CURR_URL.equals("") ? CURR_URL : Sngine_URL), false);
    }

    //Getting device basic information
    public void get_info() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(Sngine_URL, "DEVICE=android");
        cookieManager.setCookie(Sngine_URL, "DEV_API=" + Build.VERSION.SDK_INT);
    }

    //Checking permission for storage and camera for writing and uploading images
    public void get_file() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        //Checking for storage permission to write images for upload
        if (SngineApp_FUPLOAD && SngineApp_CAMUPLOAD && !check_permission(2) && !check_permission(3)) {
            ActivityCompat.requestPermissions(MainActivity.this, perms, file_perm);

            //Checking for WRITE_EXTERNAL_STORAGE permission
        } else if (SngineApp_FUPLOAD && !check_permission(2)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, file_perm);

            //Checking for CAMERA permissions
        } else if (SngineApp_CAMUPLOAD && !check_permission(3)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, file_perm);
        }
    }

    //Using cookies to update user locations
    public String get_location() {
        String newloc = "0,0";
        //Checking for location permissions
        if (SngineApp_LOCATION && (Build.VERSION.SDK_INT < 23 || check_permission(1))) {
            GPSTrack gps;
            gps = new GPSTrack(MainActivity.this);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            if (gps.canGetLocation()) {
                if (latitude != 0 || longitude != 0) {
                    if (!SngineApp_OFFLINE) {
                        CookieManager cookieManager = CookieManager.getInstance();
                        cookieManager.setAcceptCookie(true);
                        cookieManager.setCookie(Sngine_URL, "lat=" + latitude);
                        cookieManager.setCookie(Sngine_URL, "long=" + longitude);
                    }
                    newloc = latitude + "," + longitude;
                } else {
                    Log.w("New Updated Location:", "NULL");
                }
            } else {
                show_notification(1, 1);
                Log.w("New Updated Location:", "FAIL");
            }
        }
        return newloc;
    }

    //Checking if particular permission is given or not
    public boolean check_permission(int permission) {
        switch (permission) {
            case 1:
                return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

            case 2:
                return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

            case 3:
                return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

            case 4:
                return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    //Creating image file for upload
    private File create_image() throws IOException {
        @SuppressLint("SimpleDateFormat")
        String file_name = new SimpleDateFormat("yyyy_mm_ss").format(new Date());
        String new_name = "file_" + file_name + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(new_name, ".jpg", storageDir);
    }

    //Launching app rating dialoge [developed by github.com/hotchemi]
    public void get_rating() {
        if (DetectConnection.isInternetAvailable(MainActivity.this)) {
            AppRate.with(this)
                    .setStoreType(StoreType.GOOGLEPLAY)     //default is Google Play, other option is Amazon App Store
                    .setInstallDays(SngineConfig.ASWR_DAYS)
                    .setLaunchTimes(SngineConfig.ASWR_TIMES)
                    .setRemindInterval(SngineConfig.ASWR_INTERVAL)
                    .setTitle(R.string.rate_dialog_title)
                    .setMessage(R.string.rate_dialog_message)
                    .setTextLater(R.string.rate_dialog_cancel)
                    .setTextNever(R.string.rate_dialog_no)
                    .setTextRateNow(R.string.rate_dialog_ok)
                    .monitor();
            AppRate.showRateDialogIfMeetsConditions(this);
        }
        //for more customizations, look for AppRate and DialogManager
    }

    //Creating custom notifications with IDs
    public void show_notification(int type, int id) {
        long when = System.currentTimeMillis();
        swvp_notification = (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent();
        if (type == 1) {
            i.setClass(MainActivity.this, MainActivity.class);
        } else if (type == 2) {
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        } else {
            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            i.addCategory(Intent.CATEGORY_DEFAULT);
            i.setData(Uri.parse("package:" + MainActivity.this.getPackageName()));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        }
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "");
        switch (type) {
            case 1:
                builder.setTicker(getString(R.string.app_name));
                builder.setContentTitle(getString(R.string.loc_fail));
                builder.setContentText(getString(R.string.loc_fail_text));
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.loc_fail_more)));
                builder.setVibrate(new long[]{350, 350, 350, 350, 350});
                builder.setSmallIcon(R.mipmap.ic_launcher);
                break;

            case 2:
                builder.setTicker(getString(R.string.app_name));
                builder.setContentTitle(getString(R.string.loc_perm));
                builder.setContentText(getString(R.string.loc_perm_text));
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.loc_perm_more)));
                builder.setVibrate(new long[]{350, 700, 350, 700, 350});
                builder.setSound(alarmSound);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                break;
        }
        builder.setOngoing(false);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        builder.setWhen(when);
        builder.setContentIntent(pendingIntent);
        swvp_notification_new = builder.build();
        swvp_notification.notify(id, swvp_notification_new);
    }

    //Checking if users allowed the requested permissions or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                get_location();
            }
        }
    }

    //Action on back key tap/click
    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (drawerLayout != null && drawerLayout.isDrawerOpen(findViewById(R.id.nav_drawer))) {
                    drawerLayout.closeDrawers();
                    return true;
                }
                if (swvp_view.canGoBack()) {
                    swvp_view.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        swvp_view.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        swvp_view.restoreState(savedInstanceState);
    }

    // Offline Caching Setup
    private void setupOfflineCaching() {
        try {
            File cacheDir = new File(getCacheDir(), "okhttp");
            long cacheSize = 10 * 1024 * 1024; // 10 MB
            Cache cache = new Cache(cacheDir, cacheSize);
            okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .build();
            Log.d(TAG, "Offline caching setup completed");
        } catch (Exception e) {
            Log.e(TAG, "Failed to setup offline caching", e);
        }
    }

    // Biometric Authentication Setup
    private void setupBiometricAuthentication() {
        BiometricManager biometricManager = BiometricManager.from(this);
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
            Executor executor = Executors.newSingleThreadExecutor();
            biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Authentication succeeded", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show());
                }
            });
            Log.d(TAG, "Biometric authentication setup completed");
        } else {
            Log.w(TAG, "Biometric authentication not available");
        }
    }

    // In-App Purchases Setup
    private void setupInAppPurchases() {
        billingClient = BillingClient.newBuilder(this)
                .setListener((billingResult, purchases) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
                        for (Purchase purchase : purchases) {
                            handlePurchase(purchase);
                        }
                    }
                })
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "Billing client setup completed");
                } else {
                    Log.e(TAG, "Billing client setup failed: " + billingResult.getDebugMessage());
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.w(TAG, "Billing service disconnected");
            }
        });
    }

    // Dark Mode Setup
    private void setupDarkMode() {
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("dark_mode", false);
        applyDarkMode(isDarkMode);
        Log.d(TAG, "Dark mode setup completed");
    }

    // Background Audio Setup
    private void setupBackgroundAudio() {
        mediaSession = new MediaSessionCompat(this, "SngineMediaSession");
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                // Handle play action
            }

            @Override
            public void onPause() {
                super.onPause();
                // Handle pause action
            }
        });
        mediaSession.setActive(true);
        Log.d(TAG, "Background audio setup completed");
    }

    // Handle In-App Purchase
    private void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            // Grant entitlement to the user
            Log.d(TAG, "Purchase successful: " + purchase.getOrderId());
        }
    }

    // Apply Dark Mode
    private void applyDarkMode(boolean isDark) {
        // Implement dark mode logic here
        // This could involve changing themes or injecting CSS
        if (isDark) {
            // Inject CSS for dark mode into WebView
            String darkModeCSS = "javascript:(function() { " +
                    "var style = document.createElement('style'); " +
                    "style.innerHTML = 'body { background-color: #121212 !important; color: #ffffff !important; } " +
                    "a { color: #bb86fc !important; } " +
                    "input, textarea, select { background-color: #1e1e1e !important; color: #ffffff !important; border: 1px solid #333 !important; }'; " +
                    "document.head.appendChild(style); })()";
            swvp_view.loadUrl(darkModeCSS);
        } else {
            // Remove dark mode CSS
            String removeDarkModeCSS = "javascript:(function() { " +
                    "var styles = document.querySelectorAll('style'); " +
                    "for (var i = 0; i < styles.length; i++) { " +
                    "if (styles[i].innerHTML.includes('background-color: #121212')) { " +
                    "styles[i].parentNode.removeChild(styles[i]); break; }}})()";
            swvp_view.loadUrl(removeDarkModeCSS);
        }
        Log.d(TAG, "Dark mode applied: " + isDark);
    }

    // Multi-language Support Implementation
    private void setupMultiLanguageSupport() {
        // Get current locale and inject language preference to WebView
        String currentLanguage = getResources().getConfiguration().locale.getLanguage();
        String languageScript = "javascript:(function() { " +
                "localStorage.setItem('appLanguage', '" + currentLanguage + "'); " +
                "if (window.updateAppLanguage) { window.updateAppLanguage('" + currentLanguage + "'); } " +
                "})()";
        swvp_view.loadUrl(languageScript);
        Log.d(TAG, "Multi-language support setup completed for language: " + currentLanguage);
    }

    // Change app language
    public void changeAppLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Handle RTL for supported languages (e.g., Arabic, Hebrew)
        if (languageCode.equals("ar") || languageCode.equals("he")) {
            config.setLayoutDirection(locale);
        }

        // Restart activity to apply language changes
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    
    // Setup Navigation Drawer
    private void setupNavigationDrawer() {
        TextView navHome = findViewById(R.id.nav_home);
        TextView navSettings = findViewById(R.id.nav_settings);
        TextView navAbout = findViewById(R.id.nav_about);
        TextView navHelp = findViewById(R.id.nav_help);
        TextView navShare = findViewById(R.id.nav_share);
        TextView navRate = findViewById(R.id.nav_rate);
        
        navHome.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            aswm_view(Sngine_URL, false);
        });
        
        navSettings.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });
        
        navAbout.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        });
        
        navHelp.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            startActivity(new Intent(MainActivity.this, HelpActivity.class));
        });
        
        navShare.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) + "\n" + 
                "https://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(intent, getString(R.string.share_w_friends)));
        });
        
        navRate.setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            final String app_package = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + app_package)));
            } catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + app_package)));
            }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(findViewById(R.id.nav_drawer))) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(findViewById(R.id.nav_drawer));
            }
            return true;
        } else if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (id == R.id.menu_help) {
            startActivity(new Intent(this, HelpActivity.class));
            return true;
        } else if (id == R.id.menu_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) + "\n" + 
                "https://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(intent, getString(R.string.share_w_friends)));
            return true;
        } else if (id == R.id.menu_rate) {
            final String app_package = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + app_package)));
            } catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + app_package)));
            }
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
