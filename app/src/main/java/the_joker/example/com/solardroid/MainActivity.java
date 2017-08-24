package the_joker.example.com.solardroid;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mPressure, mMagnet, mOrient;
    public String light1, light2;
    public float lightf;
    double lightw;
    TextView tv2, tv4, tv5;
    float[] mValuesMagnet=new float[3];
    float[] mValuesAccel=new float[3];
    float[] mValuesOrientation=new float[3];
    float[] mRotationMatrix=new float[9];
    CharSequence test1, test2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mMagnet=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mOrient=mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        tv2=(TextView)findViewById(R.id.tv2);
        tv5=(TextView)findViewById(R.id.tv5);
        tv4=(TextView)findViewById(R.id.tv4);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                System.arraycopy(event.values, 0, mValuesAccel, 0, 3);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                System.arraycopy(event.values, 0, mValuesMagnet, 0, 3);
                break;
            case Sensor.TYPE_LIGHT:
                lightf = event.values[0];
                light1=String.valueOf(lightf);
                tv2.setText(light1);
                lightw=lightf*0.001496;
                light2=String.valueOf(lightw);
                tv4.setText(light2);
        }
        mSensorManager.getRotationMatrix(mRotationMatrix, null, mValuesAccel, mValuesMagnet);
        mSensorManager.getOrientation(mRotationMatrix, mValuesOrientation);
        Double degree = (double) -1*Math.toDegrees(mValuesOrientation[1]); // orientation
        test1="Tilt:" + degree.toString();
        tv5.setText(test1);

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        mSensorManager.registerListener(this, mMagnet, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mOrient, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}