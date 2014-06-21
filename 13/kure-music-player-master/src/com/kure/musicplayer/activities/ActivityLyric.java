package com.kure.musicplayer.activities;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kure.musicplayer.R;

public class ActivityLyric extends ActivityMaster
{
	private final String LOGTAG = "AndroidFileBrowserExampleActivity";
	private final int REQUEST_CODE_PICK_FILE = 2;
	private final int REQUEST_CODE_PICK_DIR = 1;
	private String name;
	private String artist;
//	private String FileAddr;
	View DownloadView;
	View UploadView ;
	EditText UpNameField;
	EditText upArtistField;
	EditText dlNameField;
	EditText dlArtistField;
//	TextView upAddrField;
	TextView showArtist;
	TextView showName;
	TextView showAddr;
	Button fileSelect;
	public String path="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lyric);
		showAddr= (TextView) findViewById(R.id.showAddr);

		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	public void DownloadDialog(View view){
		DownloadView= View.inflate(this, R.layout.downloaddialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Download");
		builder.setView(DownloadView);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dlNameField=(EditText)DownloadView.findViewById(R.id.dlNameField);
		        dlArtistField=(EditText)DownloadView.findViewById(R.id.dlArtistField);
		        name=dlNameField.getText().toString();
		        artist=dlArtistField.getText().toString();
		        
		        new DownloadA().execute(name, artist, "lyric");
//				showArtist=(TextView)findViewById(R.id.showArtist);
//				showName=(TextView)findViewById(R.id.showName);
//		        showName.setText(dlNameField.getText().toString());
//		        showArtist.setText(dlArtistField.getText().toString());
		    }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});

		builder.create();
		builder.show();
		
	}
	public void UploadDialog(View view){
		UploadView= View.inflate(this, R.layout.uploadialog, null);
		fileSelect=(Button)UploadView.findViewById(R.id.button1);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Upload");
		builder.setView(UploadView);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {	
		    	UpNameField=(EditText)UploadView.findViewById(R.id.upNameField);
		        upArtistField=(EditText)UploadView.findViewById(R.id.upArtistField);
		        name=UpNameField.getText().toString();
		        artist=upArtistField.getText().toString();
		        File file=new File(path);
		        try {
		        	uploadTo(name, artist, file, "lyric");
		        } catch(Exception e) {
	        		Toast.makeText(getApplicationContext(),
	        				"Received NO result from file browser",
	        				Toast.LENGTH_LONG).show();
		        }
		        
//		        upAddrField=(TextView)UploadView.findViewById(R.id.upAddrField);
//				showArtist=(TextView)findViewById(R.id.showArtist);
//				showName=(TextView)findViewById(R.id.showName);
//				showAddr=(TextView)findViewById(R.id.showAddr);
//		        showName.setText(UpNameField.getText().toString());
//		        showArtist.setText(upArtistField.getText().toString());
//      		showAddr.setText(upAddrField.getText().toString());
		    }	
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});

		builder.create();
		builder.show();
		
	}
	public void selectFile(View view){
		Log.d(LOGTAG, "Start browsing button pressed");
		Intent fileExploreIntent = new Intent(
				FileBrowserActivity.INTENT_ACTION_SELECT_FILE,
				null,
				this,
				FileBrowserActivity.class
				);
	    startActivityForResult(
	            fileExploreIntent,
	            REQUEST_CODE_PICK_FILE
	        );
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PICK_DIR) {
      	if(resultCode == RESULT_OK) {
      		String newDir = data.getStringExtra(
      				FileBrowserActivity.returnDirectoryParameter);
//      		showAddr=(EditText)findViewById(R.id.showAddr);
//      		showAddr.setText(newDir); 
	        	
      	} else {//if(resultCode == this.RESULT_OK) {
      		Toast.makeText(
      				this, 
      				"Received NO result from file browser",
      				Toast.LENGTH_LONG).show(); 
      	}//END } else {//if(resultCode == this.RESULT_OK) {
      }//if (requestCode == REQUEST_CODE_PICK_DIR) {
		
		if (requestCode == REQUEST_CODE_PICK_FILE) {
      	if(resultCode == RESULT_OK) {
      		String newFile = data.getStringExtra(
      				FileBrowserActivity.returnFileParameter);
//      		UploadView= View.inflate(this, R.layout.uploadialog, null);
//      		upAddrField=(TextView)UploadView.findViewById(R.id.upAddrField);
//      		upAddrField.setText(newFile);
      		TextView tx=(TextView) findViewById(R.id.showName);
      		tx.setMovementMethod(new ScrollingMovementMethod());
      		BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(newFile));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
      		tx.setText(br.toString());
      		
      		
      		this.path=newFile;
	        	
      	} else {//if(resultCode == this.RESULT_OK) {
      		Toast.makeText(
      				this, 
      				"Received NO result from file browser",
      				Toast.LENGTH_LONG).show(); 
      	}//END } else {//if(resultCode == this.RESULT_OK) {
      }//if (requestCode == REQUEST_CODE_PICK_FILE) {
		
		
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	public void DownloadFrom(String name,String artist,String type) {
		String url = "";
		
        if (type=="music"){
        	url="http://10.0.2.2:8000/download/music";
        }
        else{
        	url="http://10.0.2.2:8000/download/lyric";
        }
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(url);
;
	    try {
//	         Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("name", name));
	        nameValuePairs.add(new BasicNameValuePair("artist", artist));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        // Execute HTTP Post Request
	        Log.d("salam","got result");
	        HttpResponse response = httpclient.execute(httppost);
	        Log.d("migam","khodafz");
//	        File dir = getDir("myDir", Context.MODE_PRIVATE);
//	        File myFile = new File(dir, "myFile");
	        Log.d("file","1");
	        String lyric = EntityUtils.toString(response.getEntity());
	        Log.d("file","2");
	        String fileName=name+"-"+artist+".lrc";
	        Log.d("file","3");
	        File myDir=new File(Environment.getExternalStorageDirectory()+"/");
	        String path1=getFilesDir().getAbsolutePath();
	        Log.d("path",path1);
	        try {
	        	Log.d("file","4");
	        	  FileWriter Fw=new FileWriter(myDir+fileName);
	        	  Log.d("file","5");
	        	  Fw.write(lyric);
	        	  Log.d("file","6");
	        	  Fw.close();
	        	  Log.d("file","downloaded");
	        	  
	        	} catch (Exception e) {
	        	  e.printStackTrace();
	        	}
	        

	    } catch (ClientProtocolException e) {
	    	Log.d("migam","client");
	    } catch (IOException e) {
	    	Log.d("migam","client");
	    }
	}  void uploadTo(String name,String artist,File file,String type) throws Exception {
		String url = "";
      if (type=="music"){
      	url="http://10.0.2.2:8000/upload/music";
      }
      else{
      	url="http://10.0.2.2:8000/upload/lyric";
      }
      Log.d("salam",url);
      HttpClient client = new DefaultHttpClient();
      HttpPost post = new HttpPost(url);
      MultipartEntity mpEntity = new MultipartEntity();
      //Path of the file to be uploaded
      ContentBody cbFile = new FileBody(file, "text/plain");         

      //Add the data to the multipart entity
      mpEntity.addPart(type, cbFile);
      mpEntity.addPart("name", new StringBody(name, Charset.forName("UTF-8")));
      mpEntity.addPart("artist", new StringBody(artist, Charset.forName("UTF-8")));
      post.setEntity(mpEntity);
      //Execute the post request
      HttpResponse response1 = client.execute(post);
      //Get the response from the server
      HttpEntity resEntity = response1.getEntity();
      String Response=EntityUtils.toString(resEntity);
      Log.d("Response:", Response);
      //Generate the array from the response
      JSONArray jsonarray = new JSONArray("["+Response+"]");
      JSONObject jsonobject = jsonarray.getJSONObject(0);
      //Get the result variables from response 
      String result = (jsonobject.getString("result"));
      String msg = (jsonobject.getString("msg"));
      //Close the connection
      client.getConnectionManager().shutdown();
  }
    class DownloadA extends AsyncTask<String, Void, Void>    {
   	 

    	 
        // Executed on the UI thread before the
        // time taking task begins

 
        // Executed on a special thread and all your
        // time taking tasks should be inside this method
        @Override
        protected Void doInBackground(String... strings) {
        	DownloadFrom(strings[0], strings[1], strings[2]);
        	return null;
        }
    }
    
}