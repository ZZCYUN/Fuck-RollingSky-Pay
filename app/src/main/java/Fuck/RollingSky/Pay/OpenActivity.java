package Fuck.RollingSky.Pay;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
public class OpenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(OpenActivity.this, MainActivity.class);
		startActivity(intent);
        finish();
    }
    
}
