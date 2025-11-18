package sngine.app.webview;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JSInterface implements SensorEventListener {
    private Context context;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float[] accelerometerValues = new float[3];

    public JSInterface(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @JavascriptInterface
    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String getDeviceInfo() {
        return android.os.Build.MODEL + " - " + android.os.Build.VERSION.RELEASE;
    }

    @JavascriptInterface
    public void startAccelerometer() {
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @JavascriptInterface
    public void stopAccelerometer() {
        sensorManager.unregisterListener(this);
    }

    @JavascriptInterface
    public String getAccelerometerData() {
        return accelerometerValues[0] + "," + accelerometerValues[1] + "," + accelerometerValues[2];
    }

    @JavascriptInterface
    public void vibrateDevice(int duration) {
        android.os.Vibrator vibrator = (android.os.Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(duration);
        }
    }

    @JavascriptInterface
    public void changeLanguage(String languageCode) {
        if (context instanceof MainActivity) {
            ((MainActivity) context).changeAppLanguage(languageCode);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerValues, 0, event.values.length);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
}
