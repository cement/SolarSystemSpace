package com.cement.app;

import com.cement.javastlreader.R;
import com.cement.stlview.STLActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity{

	private Toast exitToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.start_earth_activity){
			return true;
		}
		if (id == R.id.start_stlreader_activity) {
			Intent intent = new Intent(this,STLActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if (intent != null) {
	        boolean isExit = intent.getBooleanExtra("isExit", false);
	        if (isExit) {
	            this.finish();
	        }
	    }
	}

	@Override
	public void onBackPressed() {
		//TODO
		//super.onBackPressed();
		if(exitToast == null){
			exitToast = Toast.makeText(this, "再按一次退出程序！", Toast.LENGTH_SHORT);
			//exitToast.getView().setBackgroundResource(R.drawable.background);
		}
		if(exitToast.getView().isShown()){
			this.finish();
		}else{
			exitToast.show();
		}
	}

}
