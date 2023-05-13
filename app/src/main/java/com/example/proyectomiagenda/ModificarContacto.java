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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModificarContacto extends AppCompatActivity {
    Button Bton_Modf, Bton_Delet, Bton_ToBack;
    EditText Ver_Nom, Ver_Tel;
    String id_contacto, nombre_Cont, telefono_Cont;
    Configuraciones nueva = new Configuraciones();
    String IP = nueva.URLWB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_contacto);
        Ver_Nom = findViewById(R.id.Mos_Nom);
        Ver_Tel = findViewById(R.id.Mos_Tel);
        Bton_Modf = findViewById(R.id.Edit_Contac);
        Bton_Delet = findViewById(R.id.Del_Contac);
        Bton_ToBack = findViewById(R.id.Back_Base);
        Bton_Modf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mod_Contacto();
            }
        });
        Bton_ToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back_BD();
            }
        });
        Bton_Delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delet_Cont();
            }
        });
    }
    private void Back_BD(){
        Intent Bac_BaseD = new Intent(ModificarContacto.this, MainActivity.class);
        startActivity(Bac_BaseD);
        ModificarContacto.this.finish();
    }
    private void Mod_Contacto(){
        RequestQueue objetoPetecion = Volley.newRequestQueue(ModificarContacto.this);
        StringRequest peticion = new StringRequest(Request.Method.POST, IP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objectResultado = new JSONObject(response.toString());
                    String Estado = objectResultado.getString("Estado");
                    if(Estado.equals("1")){
                        Toast.makeText(ModificarContacto.this, "Se ha modificado el contacto", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ModificarContacto.this, "Error, Contacto No Registrado" + Estado, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModificarContacto.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accion", "modificar");
                params.put("id_contacto", id_contacto);
                params.put("nombre", Ver_Nom.getText().toString());
                params.put("telefono", Ver_Tel.getText().toString());
                return params;
            }
        };
        objetoPetecion.add(peticion);
    }
    private void Delet_Cont(){
        RequestQueue objetoPetecion = Volley.newRequestQueue(ModificarContacto.this);
        StringRequest peticion = new StringRequest(Request.Method.POST, IP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objectResultado = new JSONObject(response.toString());
                    String Estado = objectResultado.getString("Estado");
                    if(Estado.equals("1")){
                        Toast.makeText(ModificarContacto.this, "Se ha eliminado el contacto", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ModificarContacto.this, "Error, Contacto No Registrado" + Estado, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ModificarContacto.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accion", "eliminar");
                params.put("id_contacto", id_contacto);
                return params;
            }
        };
        objetoPetecion.add(peticion);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle datos = getIntent().getExtras();
        if (datos==null){
            Toast.makeText(ModificarContacto.this, "Se necesita enviar ID del contacto", Toast.LENGTH_SHORT).show();
            id_contacto ="";
            Back_BD();
        }else {
            id_contacto = datos.getString("id_contacto");
            nombre_Cont = datos.getString("nombre");
            telefono_Cont = datos.getString("telefono");
            ViewContact();
        }
    }
    private void ViewContact(){
        Ver_Nom.setText(nombre_Cont);
        Ver_Tel.setText(telefono_Cont);
    }
}