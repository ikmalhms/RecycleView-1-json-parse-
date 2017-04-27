package id.sch.smktelkom_mlg.learn.recyclerview1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import id.sch.smktelkom_mlg.learn.recyclerview1.adapter.HotelAdapter;


public class MainActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFishPrice;
    private HotelAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AsyncLogin().execute();

        //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        //mAdapter = new HotelAdapter(mList);
        //recyclerView.setAdapter(mAdapter);

        //fillData();
    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("http://dev.republika.co.id/android/latest/smktelkom/");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<databerita> data = new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    databerita berita = new databerita();
                    berita.title = json_data.getString("title");
                    berita.thumbnail = json_data.getString("thumbnail");
                    berita.author = json_data.getString("author");
                    berita.content = json_data.getString("content");

                    data.add(berita);
                }

                // Setup and Handover data to recyclerview
                mRVFishPrice = (RecyclerView) findViewById(R.id.recyclerView);
                mAdapter = new HotelAdapter(MainActivity.this, data);
                mRVFishPrice.setAdapter(mAdapter);
                mRVFishPrice.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

        //private void fillData() {
        //  Resources resources = getResources();
        //String[] arJudul = resources.getStringArray(R.array.places);
        //String[] arDeskripsi = resources.getStringArray(R.array.place_desc);
        //TypedArray a = resources.obtainTypedArray(R.array.places_picture);
        //Drawable[] arFoto = new Drawable[a.length()];
        //for (int i = 0; i < arFoto.length; i++) {
        //  BitmapDrawable bd = (BitmapDrawable) a.getDrawable(i);
        //RoundedBitmapDrawable rbd =
        //      RoundedBitmapDrawableFactory.create(getResources(), bd.getBitmap());
        //rbd.setCircular(true);
        // arFoto[i] = rbd;
        // }
        //a.recycle();

        //  for (int i = 0; i < arJudul.length; i++) {
        //        mList.add(new Hotel(arJudul[i], arDeskripsi[i], arFoto[i]));
        //}
        //mAdapter.notifyDataSetChanged();
    }
}
