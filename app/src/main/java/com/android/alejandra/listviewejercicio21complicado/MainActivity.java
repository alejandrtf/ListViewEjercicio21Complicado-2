package com.android.alejandra.listviewejercicio21complicado;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
    // defino 2 constantes para controlar el modo de funcionamiento del botón
    // Si está en modo AÑADIR, vale 0
    // Si está en modo EDITAR vale 1
    private final int ANIADIR = 0;
    private final int EDITAR = 1;

    private ListView lvLista;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> datosLista;
    private Button btAniadirEditar;
    private Button btCancelar;
    private EditText etTexto;

    // declaro e inicializo a modo AÑADIR una variable para controlar el modo
    // del botón
    private int ModoBoton = ANIADIR;
    // variable para saber posición del elemento elegido
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtengo la referencia a los objetos
        // obtengo la referencia a la ListView
        lvLista = (ListView) findViewById(R.id.lvLista);
        // obtengo la referencia al botón AÑADIR
        btAniadirEditar = (Button) findViewById(R.id.btAniadirEditar);
        //obtengo referencia boton cancelar
        btCancelar = (Button) findViewById(R.id.btCancelarEditar);
        //obtengo referencia al cuadro de texto EditText
        etTexto = (EditText) findViewById(R.id.etTextoAniadirEditar);

        if (savedInstanceState == null) {
            // no se guard� a�n estado

            // inicializo el ArrayList con los datos para la lista
            datosLista = new ArrayList<String>();

        } else {
            // hay estado guardado

            datosLista = savedInstanceState.getStringArrayList("arrayDatos");
            pos = savedInstanceState.getInt("posItemElegido");
            ModoBoton = savedInstanceState.getInt("modoBoton");
        }
        // creo el adaptador para la lista
        adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, datosLista);

        // asigno el adaptador a la ListView
        lvLista.setAdapter(adaptador);

        // asigno el listener que responde al evento hacer click en un elemento
        // de la Lista
        lvLista.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int posicion, long id) {
                // guardo la posición del elemento elegido para usarla después
                MainActivity.this.pos = posicion;
                // cambio el texto del botón
                btAniadirEditar.setText("Editar entrada: " + posicion);
                // cambio el modo del botón a EDITAR
                ModoBoton = EDITAR;
                // asigno el texto del elemento elegido al cuadro de texto
                // (EditText)
                etTexto.setText((String) lvLista.getItemAtPosition(posicion));
                //hago visible el botón cancelar
                btCancelar.setVisibility(Button.VISIBLE);

            }
        });


        // Asigno el listener que controla el evento click del botón
        btAniadirEditar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (ModoBoton == ANIADIR) {

                    // botón en modo AÑADIR

                    // añado el texto al ArrayList de donde toma los datos la
                    // ListView
                    datosLista.add(etTexto.getText().toString());

                } else {
                    // botón en modo EDITAR
                    // cambio el texto del botón de nuevo a AÑADIR
                    btAniadirEditar.setText("AÑADIR");
                    //oculto el botón cancelar otra vez
                    btCancelar.setVisibility(Button.INVISIBLE);
                    // guardo el texto modificado en el ArrayList de donde toma
                    // los datos la
                    // ListView
                    datosLista.set(MainActivity.this.pos, etTexto.getText()
                            .toString());
                    // cambio el modo del botón a AÑADIR otra vez
                    ModoBoton = ANIADIR;

                }
                // notifico los cambios al adaptador
                adaptador.notifyDataSetChanged();
                // limpio el cambo de texto
                etTexto.setText("");

            }
        });


        // Asigno el listener que controla los cambias en el texto del editext
        etTexto.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                // aquí codifico lo que quiero hacer cuando cambie el texto del
                // EditText
                if (s.length() > 0)
                    btAniadirEditar.setEnabled(true);
                else
                    btAniadirEditar.setEnabled(false);

            }
        });

        //asigno escuchador evento onClick boton Cancelar
        btCancelar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // cambio el texto del botón de nuevo a AÑADIR
                btAniadirEditar.setText("AÑADIR");
                //oculto el botón cancelar otra vez
                btCancelar.setVisibility(Button.INVISIBLE);
                // cambio el modo del botón a AÑADIR otra vez
                ModoBoton = ANIADIR;
                // limpio el cambo de texto
                etTexto.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);
        // guardo el array con los datos, la pos del item elegido y el
        // modo del botón
        guardarEstado.putStringArrayList("arrayDatos", datosLista);
        guardarEstado.putInt("posItemElegido", pos);
        guardarEstado.putInt("modoBoton", ModoBoton);

    }

}
