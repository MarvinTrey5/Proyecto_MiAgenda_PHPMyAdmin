package com.example.proyectomiagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarContacto extends AppCompatActivity {
    Button AgregarC, Back;
    EditText Name, Phone;
    Configuraciones objeConfig = new Configuraciones();
    String URL = objeConfig.URLWB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_contacto);
        Name = findViewById(R.id.IngreNom);
        Phone = findViewById(R.id.IngreTel);
        AgregarC = findViewById(R.id.Add);
        Back = findViewById(R.id.Regresar);
        AgregarC.setOnClickListener(view -> {
                Add();
        });
        Back.setOnClickListener(view ->  {
                BackToBack();
        });
    }
    private void BackToBack(){
        Intent BacAct = new Intent(RegistrarContacto.this, MainActivity.class);
        startActivity(BacAct);
        RegistrarContacto.this.finish();
    }
    private void Add(){
        RequestQueue objetoPetecion = Volley.newRequestQueue(RegistrarContacto.this);
        StringRequest peticion = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objectResultado = new JSONObject(response.toString());
                    String Estado = objectResultado.getString("Estado");
                    if(Estado.equals("1")){
                        Toast.makeText(RegistrarContacto.this, "Se ha agregado el contacto", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegistrarContacto.this, "Error, Contacto No Registrado" + Estado, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrarContacto.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accion", "registrador");
                params.put("nombre", Name.getText().toString());
                params.put("telefono", Phone.getText().toString());
                return params;
            }
        };
        objetoPetecion.add(peticion);
    }
}