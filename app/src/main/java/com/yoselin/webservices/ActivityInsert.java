package com.yoselin.webservices;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActivityInsert extends AppCompatActivity {

    EditText et_nombre_ai;
    EditText et_apellido_paterno_ai;
    EditText et_apellido_materno_ai;
    EditText et_telefono_ai;
    EditText et_email_ai;

    private String webservice_url = "http://130.100.0.1" +
            ".com/api_clientes?user_hash=12345&action=put&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        //inicialización de EditText de la vista
        et_nombre_ai = findViewById(R.id.et_nombre_ai);
        et_apellido_paterno_ai = findViewById(R.id.et_apellido_paterno_ai);
        et_apellido_materno_ai = findViewById(R.id.et_apellido_materno_ai);
        et_telefono_ai = findViewById(R.id.et_telefono_ai);
        et_email_ai = findViewById(R.id.et_email_ai);

    }

    public void btn_insertOnClick(View view){
        StringBuilder sb = new StringBuilder();
        sb.append(webservice_url);
        sb.append("nombre_c="+et_nombre_ai.getText());
        sb.append("&");
        sb.append("apepat_c="+et_apellido_paterno_ai.getText());
        sb.append("&");
        sb.append("apemat_c="+et_apellido_materno_ai.getText());
        sb.append("&");
        sb.append("telefono_c="+et_telefono_ai.getText());
        sb.append("&");
        sb.append("email="+et_email_ai.getText());
        sb.append("&");
        webServicePut(sb.toString());
        Log.e("URL",sb.toString());
    }
    private void webServicePut(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String status;
        String description;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                status = jsonObject.getString("status");
                description = jsonObject.getString("description");
                Log.e("STATUS",status);
                Log.e("DESCRIPTION",description);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }
}
