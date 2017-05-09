package gerenciador.engefourjunior.com.gerenciadorengefour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run() {
                finish();

                Intent it = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(it);

            }
        }, 3000);
    }
}
