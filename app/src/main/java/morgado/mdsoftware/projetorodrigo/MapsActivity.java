package morgado.mdsoftware.projetorodrigo;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private boolean allowNetwork;
    private LocationManager locationManager;
    private GoogleMap mMap;
    private int cont = 0;
    EditText procurarTarefasEdit;

    View mapView;
    String tipo;
    private MarkerOptions markerLeitura;
    FirebaseAuth mAuth;

    //  var de leitura de BD
    Set set = new HashSet();
    Map<String, Object> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMap);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ImageButton procurarTarefa = (ImageButton) findViewById(R.id.butProcurarTarefa);
        procurarTarefasEdit = (EditText)findViewById(R.id.procurarTarefa);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null) {
                tipo = params.getString("Tipo").trim();

                if (tipo.equals("comum")){
                    procurarTarefa.setVisibility(View.GONE);
                    procurarTarefasEdit.setVisibility(View.GONE);
                }else {
                    procurarTarefa.setVisibility(View.VISIBLE  );
                    procurarTarefasEdit.setVisibility(View.VISIBLE);
                }

            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        procurarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encontrarEndereco(procurarTarefasEdit.getText().toString());
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view

            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,20,30);


            View compassButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("5"));
            RelativeLayout.LayoutParams layoutParamsCompass = (RelativeLayout.LayoutParams)
                    compassButton.getLayoutParams();

            layoutParamsCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParamsCompass.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

            layoutParamsCompass.setMargins(20,0,0,30);



        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers");
        regMarkers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    map = (Map<String, Object>) dataSnapshot.getValue();
                }
                try {
                    set = map.keySet();
                }catch (NullPointerException n){
                    // erro tratado
                }

                for (Object i : set) {

                    String palavra = (String) i;
                    Log.i("Makers",palavra);
                    lerLatLng(palavra);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                return false;
            }
        });
    }

    // Métodos do ciclo de vida do Fragment
    // eles trabalham com a localização, chamando os métodos de location listener

    // Método para verificar se o GPS está ativado //


    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    Toast.makeText(MapsActivity.this,
                            "Permissão de Localização Negada, ...:(", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        allowNetwork = true;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent it = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(it);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } else {

            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

    }


    // Aqui é colocado no mapa aquele pontinho azul de localização atual



    @Override
    public void onLocationChanged(Location location) {
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            allowNetwork = false;
        }

        if (allowNetwork || location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            configurarLocalizaçãoAtual(new LatLng(location.getLatitude(), location.getLongitude())); // método ainda não implementado
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    // Aqui é colocado no mapa aquele pontinho azul de localização atual
    public void configurarLocalizaçãoAtual(LatLng latLng){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {

         //   metodoCentral(latLng.latitude,latLng.longitude);
            mMap.setMyLocationEnabled(true);

            if (cont == 0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                cont++;
            }

            MyLocation myLocation = new MyLocation();
            mMap.setLocationSource(myLocation);
            myLocation.setLocation(latLng);

        }
    }
    // Fim  //


    public class MyLocation implements LocationSource {
        private OnLocationChangedListener listener;

        @Override
        public void activate(OnLocationChangedListener listener) {
            this.listener = listener;

         //   Log.i("Script", "activate()");
        }

        @Override
        public void deactivate() {
      //      Log.i("Script", "deactivate()");
        }


        public void setLocation(LatLng latLng){
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(latLng.latitude);
            location.setLongitude(latLng.longitude);

            if(listener != null){
                listener.onLocationChanged(location);
            }
        }
    }

    // FIM DE CLASSE INTERNA //


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.tarefasMenu:
             //   irParaTarefas();
                return true;
            /*
            case R.id.deslogar:
                irParaDeslogar();
                return true;
             */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void encontrarEndereco(String endereco){
        List<Address> list = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(this);

        try {
            list = (ArrayList<Address>) geocoder.getFromLocationName(endereco, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();

        }
        if(list != null && list.size() > 0) {
            Address a = list.get(0);

            double auxLat = a.getLatitude();
            double auxLng = a.getLongitude();



            String addressN = "Agencia: "+ a.getThoroughfare()  + "," ; // Rua
            //   address += "Postal Code: " + addressList.get(0).getPostalCode() + "\n"; // Postal Code
             // addressN += "" + a.getFeatureName();   // Numero
         //   addressN += "Bairro: " + a.getSubLocality() + ";\n"; // Bairro
          //  addressN += "" + a.getLocality() + " ";
           // addressN += ", " + a.getAdminArea() + " ";
           // addressN += ", " + a.getCountryName() + ".";


            LatLng latLng = new LatLng(auxLat,auxLng);

            customAddMarker(latLng, addressN);

        }else{
            Toast.makeText(MapsActivity.this,"Local não encontrado",Toast.LENGTH_SHORT).show();
        }



    }

    public void customAddMarker(LatLng latLng, String descricao ){
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.bb);

        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latLng.latitude, latLng.longitude)).title("Teste").snippet(descricao).icon(icon).draggable(true );



        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(update);
        mMap.addMarker(marker);

       DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers").child((""+latLng.latitude +"_"+ latLng.longitude).replace(".","").replace("-","sM_"));
        Tarefa tarefa = new Tarefa();
      //  tarefa.setEndereço(descricao);
        tarefa.setLatLng(latLng);
        regMarkers.setValue(tarefa);

        regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers").child((""+latLng.latitude +"_"+ latLng.longitude).replace(".","").replace("-","sM_")).child("Endereco");
        tarefa = new Tarefa();
        tarefa.setEndereço(descricao);
        regMarkers.setValue(tarefa);




    }

    public void lerLatLng(final String id){
        final ArrayList arrayList = new ArrayList();
        DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers").child(id).child("latLng");
        regMarkers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    Double aux = d.getValue(Double.class);
                    arrayList.add(aux);


                }
                final BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.bb);
                try {

                     //mMap.addMarker(markerLeitura);

                    DatabaseReference regMarkers = FirebaseDatabase.getInstance().getReference().child("Bancos").child("Makers").child(id).child("Endereco");
                    regMarkers.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {

                                String aux = d.getValue(String.class);
                                markerLeitura = new MarkerOptions().position(
                                        new LatLng( Double.parseDouble(""+ arrayList.get(0)) ,  Double.parseDouble(""+ arrayList.get(1)))).icon(icon);
                                double numero = Math.random() * 20;
                                int valorAleatorio = ((int) Math.round(numero));

                                markerLeitura.title(aux).snippet("Numero de pessoas: "+valorAleatorio + " Tempo de espera: "+valorAleatorio*3 +"m");
                                mMap.addMarker(markerLeitura);


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }catch (IndexOutOfBoundsException i){
                    // tratar erro
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}