package com.example.marble_maze;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openActivity2() {
        Intent intent = new Intent(this, com.example.marble_maze.Game_Activity.class);
        startActivity(intent);
    }
}
/*
package com.example.marble_maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Accelerometer
    public static int ax;
    public static int ay;
    //screen dimensions
    public static int height;
    public static int width;
    //game field dimensions;
    public static int left;
    public static int top;
    public static int right;
    public static int bottom;

    private long lastUpdate;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sensors
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener( this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        lastUpdate = System.currentTimeMillis();

        //Draw the screen
        myView v;
        v = new myView(this);
        setContentView(v);

        //Get screen size for boundary
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        //Set field of play
        left = 50;
        top = 25;
        right = width - 50;
        bottom = height - 450;

        //set spawn of circle
        ax = 150;
        ay = 125;
    }
    //Sensors

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener( this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            ax -= (int) event.values[0];
            ay += (int) event.values[1];

            if(ax <= (top + 100)){
                ax = top + 100;
            }
            if(ax >= (top + (bottom - 1175))){
                ax = top + (bottom - 1175);
            }
            if(ay <= (left + 50)) {
                ay = left + 50;
            }
            if(ay >= (left + right + 950)){
                ay = left + right + 950;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //Draws to the screen
    class myView extends View {
        private Paint paint;
        public myView(Context context) {
            super(context);
            init();
        }
        private void init(){
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            invalidate();
        }
        protected void onDraw(Canvas canvas) {
            float radius = 75;
            paint.setColor(Color.rgb(255,0,0));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(left, top, right, bottom, paint);

            paint.setColor(Color.rgb(255,255,0));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(ax, ay, radius, paint);
            paint.setTextSize(100);
            canvas.drawText("Marble Maze", 25, 100, paint);
            invalidate();
        }
    }
}

*/