package com.example.qrcodescanner.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.qrcodescanner.MainActivity
import com.example.qrcodescanner.R

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            checkForPremission()
        },2500)

    }

    private fun checkForPremission() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
            goToMainActivity()
        }
        else{
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE)
    }

    private fun goToMainActivity() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_PERMISSION_REQUEST_CODE){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                goToMainActivity()
            } else if(isUserDenied()){
                showGoToAppsettingsDialog()
            }
            else{
                requestPermissions()
            }
        }
    }

    private fun showGoToAppsettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Grant Permissions")
            .setMessage("This app needs camera permissions")
            .setPositiveButton("Grant"){
                dialog, which -> goToAppSettings()
            }
            .setNegativeButton("Cancel"){
                    dialog, which -> Toast.makeText(this,"The app needs permission to start",Toast.LENGTH_SHORT)
                    .show()
                    finish()
            }.show()
    }

    private fun goToAppSettings() {
        val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",packageName,null))
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun isUserDenied(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA).not()
        } else {
            return false
        }

    }

    override fun onRestart() {
        super.onRestart()
        checkForPremission()
    }
}