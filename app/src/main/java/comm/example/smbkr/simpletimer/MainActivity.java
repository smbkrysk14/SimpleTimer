package comm.example.smbkr.simpletimer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private boolean isRun = false;
    private Timer timer;
    private CountUpTimerTask timerTask = null;
    private TextView timerText;
    private long count = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = (TextView)findViewById(R.id.txtTime);
        timerText.setText("00:00.0");
        //Button btn = (Button)findViewById(R.id.Btn1);
        //btn.setText("START");
    }

    public void onBtn1(View view) {

        if (!isRun) {
            if (null != timer) {
                timer.cancel();
                timer = null;
            }

            this.Start(view);

            isRun = true;
            //Button btn = (Button)findViewById(R.id.Btn1);
            //btn.setText("STOP");

        }else {

            isRun = false;

            this.Stop(view);

            //Button btn = (Button)findViewById(R.id.Btn1);
            //btn.setText("START");

        }

    }

    private void Start(View view) {

        TextView textView = (TextView) findViewById(R.id.txtTime);

        timer = new Timer();
        timerTask = new CountUpTimerTask();
        timer.schedule(timerTask, 0, 100);

    }

    private void Stop(View view) {

        if(null != timer){
            timer.cancel();
            timer = null;
            //timerText.setText("00:00.0");
        }
    }

    class CountUpTimerTask extends TimerTask {
        @Override
        public void run() {
            // handlerを使って処理をキューイングする
            handler.post(new Runnable() {
                public void run() {
                    count++;
                    long mm = count*100 / 1000 / 60;
                    long ss = count*100 / 1000 % 60;
                    long ms = (count*100 - ss * 1000 - mm * 1000 * 60)/100;
                    // 桁数を合わせるために02d(2桁)を設定
                    timerText.setText(String.format("%1$02d:%2$02d.%3$01d", mm, ss, ms));
                }
            });
        }
    }
}
