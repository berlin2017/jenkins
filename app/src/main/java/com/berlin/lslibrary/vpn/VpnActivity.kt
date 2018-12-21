package com.berlin.lslibrary.vpn

import android.app.Activity
import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.view.MenuItem
import android.view.View
import com.berlin.lslibrary.MainActivity
import com.berlin.lslibrary.R
import kotlinx.android.synthetic.main.activity_recyclerview.*
import kotlinx.android.synthetic.main.activity_vpn.*

class VpnActivity : AppCompatActivity() {

    var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_vpn)

        address.text = Editable.Factory.getInstance().newEditable("59.39.88.235")
        userName.text = Editable.Factory.getInstance().newEditable("ycoavpn")
        password.text = Editable.Factory.getInstance().newEditable("yc1CvpnS309H658")
    }

    fun connect(view: View) {

//        var intent = VpnService.prepare(this)
//        if (intent != null) {
//            startActivityForResult(intent, 0)
//        } else {
//            onActivityResult(0, RESULT_OK, null)
//        }

        VpnUtil.init(this)

        //查询检查是否已经存在VPN
        var vpnProfile = VpnUtil.getVpnProfile()
        if (vpnProfile == null) {
            vpnProfile = VpnUtil.createVpnProfile("vpn1", "192.168.191.1", "vpntest", "123456");
        } else {
            VpnUtil.setParams(vpnProfile, "vpn1", "192.168.191.1", "vpntest", "123456");
        }


        if (isConnected) {
            VpnUtil.disconnect(this)
        } else {
            //连接
            VpnUtil.connect(this, vpnProfile)
        }
        !isConnected
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) {
            val prefix = packageName
//            val intent = Intent(this, ToyVpnService::class.java)
//                .putExtra("$prefix.ADDRESS", address.text.toString())
//                .putExtra("$prefix.PORT", mServerPort.getText().toString())
//                .putExtra("$prefix.SECRET", mSharedSecret.getText().toString())
//            startService(intent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}