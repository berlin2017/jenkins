//package com.berlin.lslibrary.vpn
//
//import android.app.Notification
//import android.content.Context
//import android.content.Intent
//import android.net.VpnService
//import android.os.ParcelFileDescriptor
//import android.util.Log
//import com.berlin.lslibrary.R
//import java.io.FileInputStream
//import java.io.FileOutputStream
//import java.io.IOException
//import java.net.InetSocketAddress
//import java.nio.ByteBuffer
//import java.nio.channels.DatagramChannel
//
//
//class LSVpnService : VpnService() {
//
//    private lateinit var thread: Thread
//    private lateinit var mInterface: ParcelFileDescriptor
//
//    val ACTION_CONNECT = "com.example.android.toyvpn.START"
//
//    val ACTION_DISCONNECT = "com.example.android.toyvpn.STOP"
//
//    val TAG = "LSVpnService"
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//
//        thread = Thread(Runnable {
//            try {
//                mInterface = Builder().addAddress(intent?.getStringExtra("address"), 24)
//                    .addDnsServer("8.8.8.8")
//                    .setSession("LSVpnService")
//                    .establish()
//
//                //b. Packets to be sent are queued in this input stream.
//                val inputStream = FileInputStream(
//                    mInterface.getFileDescriptor()
//                )
//                //b. Packets received need to be written to this output stream.
//                val out = FileOutputStream(
//                    mInterface.getFileDescriptor()
//                )
//                // Allocate the buffer for a single packet.
//                val packet = ByteBuffer.allocate(32767)
//
//                //c. The UDP channel can be used to pass/get ip package to/from server
//                val tunnel = DatagramChannel.open()
//                // Connect to the server, localhost is used for demonstration only.
//                tunnel.connect(InetSocketAddress("61.31.92.159", 1723))
//                //tunnel.connect(new InetSocketAddress("127.0.0.1", 8087));
//                //d. Protect this socket, so package send by it will not be feedback to the vpn service.
//                protect(tunnel.socket())
//                //e. Use a loop to pass packets.
//                while (true) {
//                    Log.e("LSVpnService", "true")
//
//                    Thread.sleep(100)
//                }
//            } catch (e: Exception) {
//
//            }
//        }, "LSVpnService")
//
//        thread.start()
//
//        if (intent != null && ACTION_DISCONNECT.equals(intent.getAction())) {
//            disconnect()
//            return START_NOT_STICKY;
//        } else {
//            connect()
//            return START_STICKY;
//        }
//    }
//
//    override fun onDestroy() {
//        if (thread != null) {
//            thread.interrupt();
//        }
//        super.onDestroy()
//    }
//
//    private fun connect() {
//        // Become a foreground service. Background services can be VPN services too, but they can
//        // be killed by background check before getting a chance to receive onRevoke().
//        updateForegroundNotification(R.string.connecting)
//        mHandler.sendEmptyMessage(R.string.connecting)
//        // Extract information from the shared preferences.
//        val prefs = getSharedPreferences(ToyVpnClient.Prefs.NAME, Context.MODE_PRIVATE)
//        val server = "61.31.92.159"//prefs.getString(ToyVpnClient.Prefs.SERVER_ADDRESS, "");
//        val secret = "123456789".toByteArray()//= prefs.getString(ToyVpnClient.Prefs.SHARED_SECRET, "").getBytes();
//        val port: Int
//        try {
//            port = Integer.parseInt("1723")//Integer.parseInt(prefs.getString(ToyVpnClient.Prefs.SERVER_PORT, ""));
//        } catch (e: NumberFormatException) {
//            Log.e("MyVPN", "Bad port: " + prefs.getString(ToyVpnClient.Prefs.SERVER_PORT, null), e)
//            return
//        }
//
//        // Kick off a connection.
//        startConnection(
//            ToyVpnConnection(
//                this, mNextConnectionId.getAndIncrement(), server, port, secret
//            )
//        )
//    }
//
//    private fun disconnect() {
//        mHandler.sendEmptyMessage(R.string.disconnected)
//        setConnectingThread(null)
//        setConnection(null)
//        stopForeground(true)
//    }
//
//    private fun updateForegroundNotification(message: Int) {
//        startForeground(
//            1, Notification.Builder(this)
//                //.setSmallIcon(R.drawable.ic_vpn)
//                .setContentText(getString(message))
//                .setContentIntent(mConfigureIntent)
//                .build()
//        )
//    }
//
//    private fun startConnection(connection: ToyVpnConnection) {
//        // Replace any existing connecting thread with the  new one.
//        val thread = Thread(connection, "ToyVpnThread")
//        setConnectingThread(thread)
//        // Handler to mark as connected once onEstablish is called.
//        connection.setConfigureIntent(mConfigureIntent)
//        connection.setOnEstablishListener(object : ToyVpnConnection.OnEstablishListener() {
//            fun onEstablish(tunInterface: ParcelFileDescriptor) {
//                mHandler.sendEmptyMessage(R.string.connected)
//                mConnectingThread.compareAndSet(thread, null)
//                setConnection(Connection(thread, tunInterface))
//            }
//        })
//        thread.start()
//    }
//
//    private fun setConnectingThread(thread: Thread?) {
//        val oldThread = mConnectingThread.getAndSet(thread)
//        if (oldThread != null) {
//            oldThread!!.interrupt()
//        }
//    }
//
//    private fun setConnection(connection: Connection?) {
//        val oldConnection = mConnection.getAndSet(connection)
//        if (oldConnection != null) {
//            try {
//                oldConnection!!.first.interrupt()
//                oldConnection!!.second.close()
//            } catch (e: IOException) {
//                Log.e(TAG, "Closing VPN interface", e)
//            }
//
//        }
//    }
//
//    private inner class Connection : Pair<Thread, ParcelFileDescriptor> {
//        constructor(first: Thread, second: ParcelFileDescriptor):super(first,second){
//
//        }
//    }
//
//
//}