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

import java.util.Timer;
import java.util.TimerTask;

public class Game_Activity extends AppCompatActivity implements SensorEventListener {

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
    //Velocity
    public static int VELOCITY;
    public static boolean finish;
    //Timer
    public static int time;
    public static boolean stop;

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

        //Velocity
        VELOCITY = 5;
        finish = false;
        time = 0;
        stop = false;

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
            if(!finish) {
                ax -= (int) event.values[0] * VELOCITY;
                ay += (int) event.values[1] * VELOCITY;
            }
            //boundary for the field of play
            if(ax <= (top + 100)){
                ax = top + 100;
            }
            if(ax >= (top + (bottom - 1175))){
                ax = top + (bottom - 1175);
            }
            if(ay <= (left + 250)) {
                ay = left + 250;
            }
            if(ay >= (left + right + 950)){
                ay = left + right + 950;
            }
            //Check for Finish
            //canvas.drawRect(right-175, bottom-175, right, bottom, paint);
            if(ay >= (left + right + 930) && ax >= bottom-1175  ){
                finish = true;
                stop = true;
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
            if(!finish) {
                float radius = 75;
                //Background
                paint.setColor(Color.rgb(128, 0, 0));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(left - 500, top - 500, right + 500, bottom + 500, paint);
                //Time
                paint.setColor(Color.rgb(255, 255, 255));
                paint.setTextSize(100);
                canvas.drawText("Time: " + String.valueOf(time), 0, 150, paint);
                //field of play
                paint.setColor(Color.rgb(0, 0, 0));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(left, top + 200, right, bottom, paint);
                //finish line
                paint.setColor(Color.rgb(255, 255, 255));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRect(right - 175, bottom - 175, right, bottom, paint);
                //Circle
                paint.setColor(Color.rgb(255, 255, 0));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(ax, ay, radius, paint);
            } else {
                paint.setTextSize(100);
                canvas.drawText("Finish", (width/2)-200, height/3, paint);
                canvas.drawText("Time Completed: " + String.valueOf(time), (width/2)-500, (height/3)+250, paint);
                canvas.drawText("Will Redirect to Main Page", (width/2)-500, (height/3)+450, paint);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
            if(!stop) {
                time += 1;
            }
            invalidate();
        }
    }
}