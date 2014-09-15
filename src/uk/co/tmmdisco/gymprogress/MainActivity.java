package uk.co.tmmdisco.gymprogress;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	private SitesAdapter mAdapter;
	private ListView gymnastsList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("Gymnasts", "OnCreate()");
		setContentView(R.layout.activity_main);

		//Get reference to our ListView
		gymnastsList = (ListView)findViewById(R.id.gymnastsList);
		
		//Set the click listener to launch the browser when a gymnast is clicked.
		gymnastsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				String url = mAdapter.getItem(pos).getDisciplineLink();
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
				
			}
			
		});
		
		/*
		 * If network is available download the xml from the Internet.
		 * If not then try to use the local file from last time.
		 */
		if(isNetworkAvailable()){
			Log.i("Gymnasts", "starting download Task");
			SitesDownloadTask download = new SitesDownloadTask();
			download.execute();
		}else{
			mAdapter = new SitesAdapter(getApplicationContext(), -1, SitesXmlPullParser.getGymnastsFromFile(MainActivity.this));
			gymnastsList.setAdapter(mAdapter);
		}

	}
	
	//Helper method to determine if Internet connection is available.
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	} 
	
	/*
	 * AsyncTask that will download the xml file for us and store it locally.
	 * After the download is done we'll parse the local file.
	 */
	private class SitesDownloadTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			//Download the file
			try {
				Downloader.DownloadFromUrl("http://tmmdisco.co.uk/gymnasts.xml", openFileOutput("gymnasts.xml", Context.MODE_PRIVATE));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			//setup our Adapter and set it to the ListView.
			mAdapter = new SitesAdapter(MainActivity.this, -1, SitesXmlPullParser.getGymnastsFromFile(MainActivity.this));
			gymnastsList.setAdapter(mAdapter);
			Log.i("Gymnasts", "adapter size = "+ mAdapter.getCount());
		}
	}

}
