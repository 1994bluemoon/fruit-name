package com.example.dminh.fruitname.Activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dminh.fruitname.Adapters.ListviewAdapter;
import com.example.dminh.fruitname.Models.FruitNames;
import com.example.dminh.fruitname.R;
import com.example.dminh.fruitname.phpResponse.HttpWebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listview1)
    ListView listview1;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    String HttpServerURL = "http://dvqchoangdang.000webhostapp.com/ShowFruitsName.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        new GetServerResponseFunction(MainActivity.this).execute();

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FruitNames ListViewClickItem = (FruitNames) parent.getItemAtPosition(position);

                Toast.makeText(MainActivity.this, ListViewClickItem.getFruit_Name(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class GetServerResponseFunction extends AsyncTask<Void, Void,Void>{

        public Context context;

        String DataHolder;

        List<FruitNames> fruitNamesList;

        public GetServerResponseFunction(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);

            listview1.setVisibility(View.VISIBLE);

            if(fruitNamesList != null)
            {
                ListviewAdapter adapter = new ListviewAdapter(fruitNamesList, context);

                listview1.setAdapter(adapter);
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpWebServices httpWebServices = new HttpWebServices(HttpServerURL);
            try
            {
                httpWebServices.ExecutePostRequest();

                if(httpWebServices.getResponseCode() == 200)
                {
                    DataHolder = httpWebServices.getResponse();

                    if(DataHolder != null)
                    {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(DataHolder);

                            JSONObject jsonObject;

                            FruitNames fruitNames;

                            fruitNamesList = new ArrayList<FruitNames>();

                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                fruitNames = new FruitNames();

                                jsonObject = jsonArray.getJSONObject(i);

                                fruitNames.Fruit_Name = jsonObject.getString("fruit_name");

                                fruitNamesList.add(fruitNames);
                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpWebServices.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }
    }
}
