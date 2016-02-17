package com.sample.retrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final TextView txtdata = (TextView) findViewById(R.id.data);

        RestClient.ApiInterface service = RestClient.getClient(getApplicationContext());
        Call<ResponseBody> call = service.getData("anna");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit.Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    if (response.isSuccess()) {
                        String code = String.valueOf(response.code());
                        if (response.code() == 200) {
                            String body = response.body().string();
                            String message = response.message();
                            String hader = String.valueOf(response.headers());

                            txtdata.setText(
                                    "Code : " + code + "\n\n" +
                                            "Message : " + message + "\n\n" +
                                            "Header : " + hader+"\n\n"+
                                            "Body : " + body + "\n\n");

                            JSONObject jsonObject = new JSONObject(body);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(a);
                                String login = jsonObject1.getString("login");
                            }
                        } else {
                            Log.e("response erro body", "" + response.errorBody());

                        }


                    }
                } catch (Exception e) {
                    Log.e("response error", e.getMessage());

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("response failure", t.getMessage());

            }
        });

//        RestClient.ApiInterface service = RestClient.getClient(getApplicationContext());
//        Call<ResponseBody> call = service.pinActivation(buildDefaultParams());
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(retrofit.Response<ResponseBody> response, Retrofit retrofit) {
//                try {
//                    if(response.isSuccess()){
//                        Log.e("response success",""+response.body().string());
//                            String data = response.body().string();
//                            JSONObject jsonObject = new JSONObject(data);
//                            String result = jsonObject.getString("result");
//                            String refno = jsonObject.getString("refNo");
//                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getApplicationContext(),refno,Toast.LENGTH_SHORT).show();
//                    }else{
//                        Log.e("response failed",""+response.body().string());
//                    }
//                }catch (Exception e){
//                    Log.e("response error",""+e.getMessage());
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.e("response failure",""+t.getMessage());
//
//            }
//        });


//        RestClient.GitApiInterface service2 = RestClient.getClient();
//        Call<Response> call2 = service2.getUsersNamedTom("anna");
//        call2.enqueue(new Callback<Response>() {
//            @Override
//            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
//                Log.e("Response",""+response.body());
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });

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

    private Map buildDefaultParams() {
        Map defaults = new HashMap();
        defaults.put("param1", "isiparam1");
        defaults.put("param2", "isiparam2");
        return defaults;
    }
}
