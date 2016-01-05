package huytranq.databasesecurity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView status = (TextView) findViewById(R.id.status);

        final String databasePath = getDatabasePath();

        findViewById(R.id.encrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new AsyncDatabase(getApplicationContext() , databasePath , 0 , status)).execute();
            }
        });

        findViewById(R.id.decrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new AsyncDatabase(getApplicationContext() , databasePath , 1 , status)).execute();
            }
        });
    }
}
