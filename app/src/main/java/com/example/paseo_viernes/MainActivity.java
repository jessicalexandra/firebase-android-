package com.example.paseo_viernes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText jetcodigo, jetnombre, jetciudad, jetcantidad;
    CheckBox jcbactivo;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String codigo, nombre, ciudad, cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ocultar la barra de tt y asociar objetos java y xml
        jetcodigo = findViewById(R.id.etcodigo);
        jetnombre = findViewById(R.id.etnombre);
        jetciudad = findViewById(R.id.etciudad);
        jetcantidad = findViewById(R.id.etcantidad);
        jcbactivo = findViewById(R.id.cbactivo);
    }

    public void Adicionar(View view) {
        codigo = jetcodigo.getText().toString();
        nombre = jetnombre.getText().toString();
        ciudad = jetciudad.getText().toString();
        cantidad = jetcantidad.getText().toString();
        if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty() || cantidad.isEmpty()) {
            Toast.makeText(this, "todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        } else {
            Map<String, Object> user = new HashMap<>();
            user.put("codigo", codigo);
            user.put("nombre", nombre);
            user.put("ciudad", ciudad);
            user.put("cantidad",cantidad);
            user.put("activo","si");

            // Add a new document with a generated ID
            db.collection("Factura")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this,"Datos guardados",Toast.LENGTH_SHORT).show();
                            limpiar_campos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"Error guardando datos ",Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

    private void limpiar_campos(){
        jetcodigo.setText("");
        jetnombre.setText("");
        jetciudad.setText("");
        jetcantidad.setText("");
        jetcodigo.requestFocus();
    }
}