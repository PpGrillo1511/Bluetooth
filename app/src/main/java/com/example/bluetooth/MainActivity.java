package com.example.bluetooth;

import static androidx.core.content.ContextCompat.startActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> pairedDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnListDevices = findViewById(R.id.btnListDevices);
        ListView listViewDevices = findViewById(R.id.listViewDevices);
        pairedDevicesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewDevices.setAdapter(pairedDevicesArrayAdapter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btnListDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPairedDevices();
            }
        });
    }

    private void showPairedDevices() {
        pairedDevicesArrayAdapter.clear();

        if (bluetoothAdapter == null) {
            // El dispositivo no soporta Bluetooth
            // Manejar esta situación
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            // Si Bluetooth no está activado, solicitar al usuario que lo active
            // Puedes abrir la ventana de configuración de Bluetooth
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
            return;
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            pairedDevicesArrayAdapter.add("No se encontraron dispositivos emparejados");
        }
    }
}

