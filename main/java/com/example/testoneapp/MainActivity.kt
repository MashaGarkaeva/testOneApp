package com.example.testoneapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ComponentCaller
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.node.ViewAdapter
import androidx.compose.ui.tooling.data.ContextCache
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.util.Calendar
import java.util.UUID
import kotlin.uuid.Uuid

class MainActivity : Activity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var listView: ListView
    private lateinit var btnDiscover: Button
    private lateinit var btn_EnableBl: Button
    private lateinit var btnPaired: Button
    private lateinit var deviceList: MutableList<String>
    private lateinit var pairedDevices: Set<BluetoothDevice>
    private lateinit var devices: MutableList<BluetoothDevice>
    private var bluetoothSocket: BluetoothSocket? = null
    private val my_uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private val TAG = "Bluetooth Connection"

    companion object{
        private val REQUEST_ENABLE_BT = 1
        private val REQUEST_BLUETOOTH_PERMISSIONS = 2
    }

    private val receiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            when (action){
                BluetoothDevice.ACTION_FOUND ->{
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT)
                            != PackageManager.PERMISSION_GRANTED
                        ){
                            return
                        }
                        if (!devices.contains(it)) {
                            devices.add(it)
                            deviceList.add("${it.name ?: "Unknown"} (${it.address})")
                            (listView.adapter as ArrayAdapter<String>).notifyDataSetChanged()
                        }
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Toast.makeText(context, "Поиск завершен", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initViews()
        initBluetoothAdapter()
        setupButtons()
        registerReceiver()

        listView.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, deviceList)


        val modul:Button =  findViewById(R.id.btn_model)

        modul.setOnClickListener {
            val intent = Intent(this@MainActivity, Modul::class.java)
            startActivity(intent)
        }

        val terminal:Button =  findViewById(R.id.btn_terminal)

        terminal.setOnClickListener {
            val intent = Intent(this@MainActivity, Terminal::class.java)
            startActivity(intent)
        }

        val time_general: TextView = findViewById(R.id.time)
        val calendar: Calendar = Calendar.getInstance()
        val simpleTimeFormatGeneral = SimpleDateFormat("hh:mm")
        val timeGeneralText = simpleTimeFormatGeneral.format(calendar.time)
        time_general.text = timeGeneralText
    }

    private fun initViews(){
        btn_EnableBl = findViewById(R.id.btn_EnableBlu)
        btnDiscover = findViewById(R.id.btnDiscover)
        listView = findViewById(R.id.listView)
        btnPaired = findViewById(R.id.btnPaired)
        deviceList = mutableListOf()
        devices = mutableListOf()
    }

    private fun initBluetoothAdapter(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null){
            Toast.makeText(this,"Bluetooth не поддерживается", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    private fun requestNeededPermissions(){
        val permission = arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        ActivityCompat.requestPermissions(this, permission, REQUEST_BLUETOOTH_PERMISSIONS)
    }

    private fun setupButtons(){
        btn_EnableBl.setOnClickListener {
            if (!bluetoothAdapter.isEnabled){
                val enableBtnIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return@setOnClickListener
                }
                startActivityForResult(enableBtnIntent, REQUEST_ENABLE_BT)
            } else {
                Toast.makeText(this, "Bluetooth уже включен", Toast.LENGTH_SHORT).show()
            }
        }

        btnDiscover.setOnClickListener {
            if (checkPermission()){
                startDiscovery()
            } else {
                requestNeededPermissions()
            }
        }

        btnPaired.setOnClickListener {
            showPairedDevices()
        }

        listView.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick (parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                if (checkPermission()){
                    connectToDevice(devices[position])
                } else {
                    requestNeededPermissions()
                }
            }
        }
        /*
        listView.setOnClickListener {parent, view, position, id ->
            if (checkPermissionGranted()){
                connectToDevice(devices[position])
            } else {
                requestPermissions()
            }
        }*/
    }

    private fun registerReceiver(){
        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_FOUND)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        }
        registerReceiver(receiver, filter)
    }

    private fun showPairedDevices(){
        deviceList.clear()
        devices.clear()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val pairedDevices = bluetoothAdapter.bondedDevices
        pairedDevices.forEach { device ->
            devices.add(device)
            deviceList.add("${device.name ?: "Unknown"} (Paired - ${device.address})")
        }

        (listView.adapter as ArrayAdapter<String>).notifyDataSetChanged()
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun startDiscovery(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED){
            return
        }

        deviceList.clear()
        devices.clear()

        (listView.adapter as ArrayAdapter<String>).notifyDataSetChanged()

        if (bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.startDiscovery()
            Toast.makeText(this, "Поиск устройств....", Toast.LENGTH_SHORT).show()
        }

        pairedDevices = bluetoothAdapter.bondedDevices
        pairedDevices.forEach { device ->
            deviceList.add("${device.name ?: "Unknown"}\n${device.address}")
            devices.add(device)
        }

        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,deviceList)
        listView.adapter = adapter
        Toast.makeText(this, "Найдено ${deviceList.size} устройство", Toast.LENGTH_SHORT).show()
    }

    private fun connectToDevice(device: BluetoothDevice){
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)!= PackageManager.PERMISSION_GRANTED){
                return
            }

            bluetoothSocket = device.createRfcommSocketToServiceRecord(my_uuid)
            bluetoothSocket?.let { socket ->
                bluetoothAdapter.cancelDiscovery()
                socket.connect()

                runOnUiThread {
                    Toast.makeText(this, "Подключено ${device.name}", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: IOException){
            try {
                bluetoothSocket?.close()
            } catch (closeException: IOException){
                closeException.printStackTrace()
            }
            runOnUiThread {
                Toast.makeText(this, "Ошибка подключения ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            REQUEST_ENABLE_BT -> {
                if (resultCode == RESULT_OK){
                    Toast.makeText(this, "Bluetooth включен", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Bluetooth не включен", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            bluetoothSocket?.close()
        } catch (e: IOException){
            e.printStackTrace()
        }
    }

}

    private fun setContentView() {
        TODO("Not yet implemented")
    }

