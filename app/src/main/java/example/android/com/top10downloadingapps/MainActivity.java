package example.android.com.top10downloadingapps;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button btnParse;
    private ListView listApps;
    private String mFileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instantiate
        btnParse = (Button) findViewById(R.id.btnParse);
        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                ParseApp parseApp = new ParseApp(mFileContents);
                parseApp.process();
                //create adapter
                ArrayAdapter <Application> arrayAdapter=new ArrayAdapter<Application>(
                        MainActivity.this,R.layout.list_item, parseApp.getApplications());
                listApps.setAdapter(arrayAdapter);
            }
        });

        listApps = (ListView) findViewById(R.id.listView);

        //create new instance of the downloadData class
        DownloadData downloadData = new DownloadData();

        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //We use AsyncTask to download data
    //1st parameter - downloading location
    //2nd -use void (download relatively small,so  we don't need progress bar)
    //3rd param is for result
    private class DownloadData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXMLFile(params[0]);
            if (mFileContents == null) {
                Log.d("DownloadData", "Error downloading");
            }
            return mFileContents;
        }
//once doinBackground complete we execute onPost methode


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("DownloadData", "Result was" + result);

        }

        private String downloadXMLFile(String urlPath) {
            //create temp buffer to store the content of XML file
            StringBuilder tempBuffer = new StringBuilder();

            //to handle errors use try & catch
            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d("DownloadData", "The response code was" + response);
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charRead = isr.read(inputBuffer);
                    if (charRead <= 0) {
                        break;
                    }
                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));
                }
                return tempBuffer.toString();
            } catch (IOException e) {
                Log.d("DownloadData", "IO Exception reading data:" + e.getMessage());
                //e.printStackTrace();
            } catch (SecurityException e) {
                Log.d("DownloadData", "Security Exception.Needs Permition?" + e.getMessage());

            }

            return null;
        }


    }
}