package com.cement.stlview;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.cement.app.MainActivity;
import com.cement.javastlreader.R;
import com.cement.stlreader.STLReader;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
public class STLActivity extends AppCompatActivity {

	private STLGestureGLSurfaceView mGLView;
	private STLGLRenderer renderer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
		//mGLView = new STLGLSurfaceView(this);
		mGLView = new STLGestureGLSurfaceView(STLActivity.this);
		renderer = new STLGLRenderer();
		mGLView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		mGLView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		mGLView.setZOrderOnTop(true);
		mGLView.bindRenderer(renderer);
	    mGLView.setBackgroundResource(R.drawable.background);
	    setContentView(mGLView);
		
	}

	public void drawStlFile(String path){
	      float[] coords = null;
	      float[] colors = null;
			try {
				coords = STLReader.readStlFile(new File(path));
				colors = new float[coords.length*4/3];
				Random random = new Random();
				for (int i = 0; i < colors.length; i++) {
					colors[i] = random.nextFloat();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			renderer.bindData(coords, colors);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			openFileChooser();
			return true;
		}
		if (id == R.id.renderer_mode) {
			if(mGLView.getRenderMode()==GLSurfaceView.RENDERMODE_CONTINUOUSLY){
				mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			}else{
				mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
			}
			return true;
		}
		if (id == R.id.start_earth_activity){
			Intent intent = new Intent(this,MainActivity.class);
			intent.putExtra("isExit", true);
			startActivity(intent);
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
	protected void onResume() {
		super.onResume();
		mGLView.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
		mGLView.onPause();
	}
	public void openFileChooser(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("*/*");
		try {
			startActivityForResult(Intent.createChooser(intent, "请选择一个你喜欢的软件"),1000);
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(STLActivity.this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
		}
	}
	  @Override  
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {  
	            Uri uri = data.getData(); 
	            String path = getRealPath(uri);
	            this.drawStlFile(path);
	            System.gc();
	            System.out.println("resu lt: ............................ ");
	        }  
	        super.onActivityResult(requestCode, resultCode, data);  
	    } 
	  public  String getRealPath(final Uri uri ) {
		    if ( null == uri ) return null;
		    final String scheme = uri.getScheme();
		    String data = null;
		    if ( scheme == null )
		        data = uri.getPath();
		    else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
		        data = uri.getPath();
		    } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
		        Cursor cursor = getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
		        if ( null != cursor ) {
		            if ( cursor.moveToFirst() ) {
		                int index = cursor.getColumnIndex( ImageColumns.DATA );
		                if ( index > -1 ) {
		                    data = cursor.getString( index );
		                }
		            }
		            cursor.close();
		        }
		    }
		    return data;
		}
	  
}
