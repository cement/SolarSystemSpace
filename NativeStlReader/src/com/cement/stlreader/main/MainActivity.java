package com.cement.stlreader.main;


import com.cement.stlreader.reader.NativeRenderer;
import com.cement.stlreader.reader.StlGlSurfaceView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	private StlGlSurfaceView mGLView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGLView = new StlGlSurfaceView(this);
		
		mGLView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		mGLView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		mGLView.setZOrderOnTop(true);
		//mGLView.bindNativeRenderer(new NativeRenderer());
		mGLView.setRenderer(mGLView);
		setContentView(mGLView);
		mGLView.setBackgroundResource(R.drawable.background);
	}
	
	public void chooserStlFile(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("*/*");
		try {
			startActivityForResult(Intent.createChooser(intent, "请选择一个你喜欢的软件"),1000);
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(MainActivity.this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
		}
	}
	  @Override  
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {  
	            Uri uri = data.getData(); 
	            String path = getRealFilePath(uri);
	            mGLView.drawStlModel(path);
	           // System.gc();
	            System.out.println("result: ............................ ");
	        }  
	        super.onActivityResult(requestCode, resultCode, data);  
	    } 
	  public  String getRealFilePath(final Uri uri ) {
		    if ( null == uri ) return null;
		    final String scheme = uri.getScheme();
		    String filePath = null;
		    if ( scheme == null )
		    	filePath = uri.getPath();
		    else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
		    	filePath = uri.getPath();
		    } else if (ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
		        Cursor cursor = getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
		        if ( null != cursor ) {
		            if ( cursor.moveToFirst() ) {
		                int index = cursor.getColumnIndex( ImageColumns.DATA );
		                if ( index > -1 ) {
		                	filePath = cursor.getString( index );
		                }
		            }
		            cursor.close();
		        }
		    }
		    return filePath;
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.fileChooser) {
			chooserStlFile();
			return true;
		}
		if (id == R.id.rendererMode) {
			if(mGLView.getRenderMode()==GLSurfaceView.RENDERMODE_CONTINUOUSLY){
				mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			}else{
				mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
