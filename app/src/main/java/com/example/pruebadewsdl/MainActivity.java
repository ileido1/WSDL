package com.example.pruebadewsdl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {
private String metodo = "GetMensaje";
private String WSLD = "http://localhost:8080/WebApplication1/prueba?wsdl";
private String NAMESPACE = "http://ws.proyecto.empresa.com/";
private String URL = "http://192.168.92.94:8080/WebApplication1/prueba";
private EditText parametro1;
private String SOAPACTION ="", mensaje,parametro;
private Button enviar;
private TextView respuesta;
SoapPrimitive RESULTADO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parametro1 = (EditText)findViewById(R.id.parametro);
        enviar = (Button)findViewById(R.id.buttonenviar);
        respuesta = (TextView) findViewById(R.id.respuesta);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            parametro = parametro1.getText().toString();
            SegundoPlano tarea = new SegundoPlano();
            tarea.execute();
            }
        });
    }

    private  class SegundoPlano extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
        }
        @Override
        protected Void doInBackground(Void... params){
            conexion();
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            respuesta.setText(RESULTADO.toString() + mensaje);
        }

    }
    private  void conexion(){
        try {
            SoapObject request = new SoapObject(NAMESPACE, metodo);
            request.addProperty("codigo",parametro);
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet=true;
            soapEnvelope.setOutputSoapObject(request);

            HttpTransportSE transportSE = new HttpTransportSE(URL);
            transportSE.call(SOAPACTION,soapEnvelope);
            RESULTADO = (SoapPrimitive) soapEnvelope.getResponse();
            mensaje = "ok";
        }catch (Exception ex) {
            mensaje = "Error" + ex.getMessage();
        }
    }
}
