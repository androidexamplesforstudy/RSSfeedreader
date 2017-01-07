package akubarek.pl.rssfeedreader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: starting AsyncTask");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("URL");
        Log.d(TAG, "onCreate: done");
    }

    private class DownloadData extends AsyncTask <String, Void, String> {
        private static final String TAG = "DownloadData";
        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground: starts with: " + params[0]);
            String rssFeed = downloadXML(params[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is" + s);
        }

        private String downloadXML (String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: response code was : " + response);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char [] inputBuffer = new char[500];
                while (true) {
                    charsRead = reader.read(inputBuffer); // how many read from those 500
                    if (charsRead < 0) {
                        break;
                    }
                    if (charsRead > 0) {
                        xmlResult.append(String.valueOf(inputBuffer, 0, charsRead));
                        // add data store in inputBuffer from character 0 to [how many was read]
                    }
                }
                reader.close();
            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URDL" + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IOException reading data " + e.getMessage());
            }
            return xmlResult.toString();
        }
    }
}
