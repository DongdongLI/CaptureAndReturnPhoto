package com.example.captureandreturnphoto;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends Activity {

	Button captureBtn;
	Button videoBtn;
	Button jump;
	ImageView image;
	VideoView videoView;
	
	final static Integer CAPTURE_CODE=0x000;
	final static Integer VIDEO_CAPTURE_CODE=0X001;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        captureBtn=(Button)findViewById(R.id.capture);
        videoBtn=(Button)findViewById(R.id.video);
        jump=(Button)findViewById(R.id.jump);
        
        image=(ImageView)findViewById(R.id.image);
        videoView=(VideoView)findViewById(R.id.videoView1);
        
        captureBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, CAPTURE_CODE);
			}
		});
        
        videoBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
				if(intent.resolveActivity(getPackageManager())!=null)
					startActivityForResult(intent, VIDEO_CAPTURE_CODE);
			}
		});
        
        jump.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,RecordActivity.class));
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode==CAPTURE_CODE){
    		if(resultCode==RESULT_OK){
    			Bitmap bitmap=(Bitmap)data.getExtras().get("data");
    			image.setImageBitmap(bitmap);
    		}
    	}else if(requestCode==VIDEO_CAPTURE_CODE){
    		if(resultCode==RESULT_OK){
    			//Uri videoUri=data.getData();
    			Uri videoUri=Uri.parse("http://techslides.com/demos/sample-videos/small.mp4");
    			videoView.setVideoURI(videoUri);
    			MediaController mc = new MediaController(this);
    			mc.setAnchorView(videoView);
    			videoView.setMediaController(mc);
    			videoView.requestFocus();
    			videoView.start();
    		}
    	}
    }
    
    
}
