package pro.tl.travelkit.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import pro.tl.travelkit.R;

public class LinerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setTheme(R.style.AppTheme);
		setContentView(R.layout.activity_liner);
	}
}
