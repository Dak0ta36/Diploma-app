package com.example.diploma2;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MotionGraph extends AppCompatActivity{
    TextView txt_current_accel, txt_prev_accel, txt_accel, txt_Current_Y, txt_pitch;
    ProgressBar progressBar;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private Sensor mGyroscope;
    private float CurrentValue;
    private float PreviousValue;
    private float CurrentY;
    private float CurrentZ;
    private float CurrentGX;
    private float CurrentGY;
    private float CurrentGZ;
    private float CurrentMX;
    private float CurrentMZ;
    private float CurrentMY;



    private int pointsPlotted = 5;
    private int graphIntervalCounter =0;
    private Button savegraph;


    private Viewport viewport;

    LineGraphSeries<DataPoint> AX = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(0, 1),
            new DataPoint(1, 5),
            new DataPoint(2, 3),
            new DataPoint(3, 2),


    });
            LineGraphSeries<DataPoint> AY = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(0, 1),
                    new DataPoint(1, 5),
                    new DataPoint(2, 3),
                    new DataPoint(3, 2),}
    );
            LineGraphSeries<DataPoint> AZ = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(0, 1),
                    new DataPoint(1, 5),
                    new DataPoint(2, 3),
                    new DataPoint(3, 2),



    });
    LineGraphSeries<DataPoint> GX = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(0, 1),
            new DataPoint(1, 5),
            new DataPoint(2, 3),
            new DataPoint(3, 2),



    });
    LineGraphSeries<DataPoint> GY = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(0, 1),
            new DataPoint(1, 5),
            new DataPoint(2, 3),
            new DataPoint(3, 2),



    });
    LineGraphSeries<DataPoint> GZ = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(0, 1),
            new DataPoint(1, 5),
            new DataPoint(2, 3),
            new DataPoint(3, 2),



    });
    public SensorEventListener GyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float gx =sensorEvent.values[0];
            float gy =sensorEvent.values[1];
            float gz =sensorEvent.values[2];

            CurrentGX = gx;
            CurrentGY= gy;
            CurrentGZ= gz;



            GX.appendData(new DataPoint(pointsPlotted,CurrentGX), true, pointsPlotted);


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    public SensorEventListener MagListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float mx =sensorEvent.values[0];
            CurrentMX =mx;

                    }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    public SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float ax =sensorEvent.values[0];
            float ay =sensorEvent.values[1];
            float az = sensorEvent.values[2];




            CurrentValue = ax;
            CurrentY = ay;
            CurrentZ = az;




            double ChangeIn = Math.abs(CurrentValue-PreviousValue);
            PreviousValue = CurrentValue;

            txt_current_accel.setText("Current= " + CurrentValue);
            txt_prev_accel.setText("Previous= " +PreviousValue);
            txt_accel.setText("AccChange=" +ChangeIn);
            txt_Current_Y.setText("Y:"+CurrentY);

            pointsPlotted++;

            if (pointsPlotted>1000){
                pointsPlotted =1;
                AX.resetData(new DataPoint[] {new DataPoint(1, 0)});
                AY.resetData(new DataPoint[] {new DataPoint(1, 0)});
                AZ.resetData(new DataPoint[] {new DataPoint(1, 0)});
            }
            AX.appendData( new DataPoint(pointsPlotted,ChangeIn), true, pointsPlotted);
            AY.appendData( new DataPoint(pointsPlotted,CurrentY), true, pointsPlotted);
            AZ.appendData( new DataPoint(pointsPlotted,CurrentZ), true, pointsPlotted);
            viewport.setMaxX(pointsPlotted);
            viewport.setMinX(pointsPlotted-200);




        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_graph);

        txt_accel = findViewById(R.id.txt_accel);
        txt_current_accel = findViewById(R.id.txt_current_accel);
        txt_prev_accel = findViewById(R.id.txt_prev_accel);
        txt_Current_Y =findViewById(R.id.txt_Current_Y);
        progressBar = findViewById(R.id.progressBar);
        mSensorManager= (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer=  mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope= mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mMagnetometer= mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        viewport = graph.getViewport();
        viewport.setScrollable(true);
        viewport.setXAxisBoundsManual(true);
        GridLabelRenderer gridLabelRenderer = graph.getGridLabelRenderer();
        gridLabelRenderer.setGridColor(Color.BLACK);
        gridLabelRenderer.setHorizontalLabelsColor(Color.BLACK);
        gridLabelRenderer.setVerticalLabelsColor(Color.BLACK);
        graph.addSeries(AX);
        AX.setColor(Color.BLUE);
        graph.setTitle("Accelerometer");
        graph.getViewport().setBorderColor(Color.BLUE);
        graph.addSeries(AY);
        AY.setColor(Color.RED);
        graph.addSeries(AZ);
        AZ.setColor(Color.GREEN);
        graph.addSeries(GX);
        GX.setColor(Color.MAGENTA);



    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(GyroListener,mGyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(MagListener,mMagnetometer,SensorManager.SENSOR_DELAY_NORMAL);

        }



    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);



    }
}

