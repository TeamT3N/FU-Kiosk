package com.TeamT3N.fukiosk;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ImageGallery extends Activity{

	private static final int PICK_IMAGE = 1;
	private static final int PICK_Camera_IMAGE = 2;
	private ImageView imgView;
	private Button upload,cancel,b;
	private Bitmap bitmap;
	private String ip = globalconfig.ipaddress;
	private String uploadphoto = ip +"student/upload_photo.php";

	private SessionHandler session;
	private ProgressDialog dialog;
	Uri imageUri;
	
	MediaPlayer mp=new MediaPlayer();






	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_gallery);
		session = new SessionHandler(getApplicationContext());
		imgView = (ImageView) findViewById(R.id.Profile);
		upload = (Button) findViewById(R.id.imguploadbtn);
		cancel = (Button) findViewById(R.id.imgcancelbtn);
		b = (Button) findViewById(R.id.btnSelectPhoto);

		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){
                selectImage();
			}
		});



		upload.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (bitmap == null) {
					Toast.makeText(getApplicationContext(),
							"Please select image", Toast.LENGTH_SHORT).show();
				} else {
					dialog = ProgressDialog.show(ImageGallery.this, "Uploading",
							"Please wait...", true);
					new ImageGalleryTask().execute();
					Toast.makeText(getApplicationContext(),
							"Upload Finish!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageGallery.this.finish();
			}
		});

		
	}










	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
                 getMenuInflater().inflate(R.menu.activity_image_gallery, menu);
                 return true;
        }


	private void selectImage() {
		final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
		AlertDialog.Builder builder = new AlertDialog.Builder(ImageGallery.this);
		builder.setTitle("Add Photo!");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")){


                    //define the file-name to save photo taken by Camera activity
                    String fileName = "new-photo-name.jpg";
                    //create parameters for Intent with filename
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    values.put(MediaStore.Images.Media.DESCRIPTION,"Image captured by camera");
                    //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                    imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    //create new Intent
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, PICK_Camera_IMAGE);
				}
				else if (options[item].equals("Choose from Gallery")){
				    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				    startActivityForResult(intent, PICK_IMAGE);
				}
				else if (options[item].equals("Cancel")){
				    dialog.dismiss();
                }
			}
		});
		builder.show();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case R.id.camera:
	        	//define the file-name to save photo taken by Camera activity
	        	String fileName = "new-photo-name.jpg";
        	//create parameters for Intent with filename
	        	ContentValues values = new ContentValues();
	        	values.put(MediaStore.Images.Media.TITLE, fileName);
	        	values.put(MediaStore.Images.Media.DESCRIPTION,"Image captured by camera");
	        	//imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
	        	imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	        	//create new Intent
	        	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        	intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	        	intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
	        	startActivityForResult(intent, PICK_Camera_IMAGE);
	            return true;
	        
	        case R.id.gallery:
	        	try {
				Intent gintent = new Intent();
				gintent.setType("image/*");
				gintent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(
				Intent.createChooser(gintent, "Select Picture"),
				PICK_IMAGE);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
				e.getMessage(),
				Toast.LENGTH_LONG).show();
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}
	        	return true;
        }
		return false;
    }

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri selectedImageUri = null;
		String filePath = null;
		switch (requestCode ) {
				case PICK_IMAGE:
					if (resultCode == Activity.RESULT_OK) {
						selectedImageUri = data.getData();
					}
					break;
				case PICK_Camera_IMAGE:
					 if (resultCode == RESULT_OK) {
		 		        //use imageUri here to access the image
		 		    	selectedImageUri = imageUri;
		 		    /*	Bitmap mPic = (Bitmap) data.getExtras().get("data");
						selectedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mPic, getResources().getString(R.string.app_name), Long.toString(System.currentTimeMillis())));*/
				    } else if (resultCode == RESULT_CANCELED) {
		 		        Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
		 		    } else {
		 		    	Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
		 		    }
					 break;
			}
		
			if(selectedImageUri != null){
					try {
						// OI FILE Manager
						String filemanagerstring = selectedImageUri.getPath();
			
						// MEDIA GALLERY
						String selectedImagePath = getPath(selectedImageUri);
			
						if (selectedImagePath != null) {
							filePath = selectedImagePath;
						} else if (filemanagerstring != null) {
							filePath = filemanagerstring;
						} else {
							Toast.makeText(getApplicationContext(), "Unknown path",
									Toast.LENGTH_LONG).show();
							Log.e("Bitmap", "Unknown path");
						}
			
						if (filePath != null) {
							decodeFile(filePath);
						} else {
							bitmap = null;
						}
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "Internal error",
								Toast.LENGTH_LONG).show();
						Log.e(e.getClass().getName(), e.getMessage(), e);
					}
			}
	
	}

	class ImageGalleryTask extends AsyncTask<Void, Void, String> {
		@SuppressWarnings("unused")
		@Override
		protected String doInBackground(Void... unsued) {

				InputStream is;
			    BitmapFactory.Options bfo;
			    Bitmap bitmapOrg;
			    ByteArrayOutputStream bao ;
			   
			    bfo = new BitmapFactory.Options();
			    bfo.inSampleSize = 2;
			 //   bitmapOrg = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + customImage, bfo);
			      
			    bao = new ByteArrayOutputStream();
			    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
				byte [] ba = bao.toByteArray();
				String ba1 = Base64.encodeBytes(ba);
        Student student = session.getStudentDetails();
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			//nameValuePairs.add(new BasicNameValuePair("studentid",student.getStudentID()));
				nameValuePairs.add(new BasicNameValuePair("image",ba1));
				nameValuePairs.add(new BasicNameValuePair("studentid",student.getStudentID()));


            Log.v("log_tag", System.currentTimeMillis()+".jpg");
				try{
				        HttpClient httpclient = new DefaultHttpClient();
				        HttpPost httppost = new 
                      //  Here you need to put your server file address
				        HttpPost(ip +"student/upload_photo.php");
				        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				        HttpResponse response = httpclient.execute(httppost);
				        HttpEntity entity = response.getEntity();
				        is = entity.getContent();
				        Log.v("log_tag", "In the try Loop" );
				   }catch(Exception e){
				        Log.v("log_tag", "Error in http connection "+e.toString());
				   }
			return "Success";
			// (null);
		}

		@Override
		protected void onProgressUpdate(Void... unsued) {

		}

		@Override
		protected void onPostExecute(String sResponse) {
			try {
				if (dialog.isShowing())
					dialog.dismiss();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						e.getMessage(),
						Toast.LENGTH_LONG).show();
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}
		}

	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}





	public void decodeFile(String filePath) {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 1024;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		bitmap = BitmapFactory.decodeFile(filePath, o2);

		imgView.setImageBitmap(bitmap);

	}

}

