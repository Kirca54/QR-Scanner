package com.example.qrcodescanner.scanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.qrcodescanner.R
import kotlinx.android.synthetic.main.fragment_qr_scanner.view.*
import kotlinx.android.synthetic.main.fragment_qr_scanner.*
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class QrScannerFragment : Fragment(),ZBarScannerView.ResultHandler {

   /* private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/

    companion object {
        fun newInstance(): QrScannerFragment {
            return QrScannerFragment()
        }
        /* @JvmStatic
         fun newInstance(param1: String, param2: String) =
             QrScannerFragment().apply {
                 arguments = Bundle().apply {
                     putString(ARG_PARAM1, param1)
                     putString(ARG_PARAM2, param2)
                 }
             }*/
    }

    private lateinit var mView: View

    lateinit var scannerView: ZBarScannerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:Bundle?): View? {
        mView =  inflater.inflate(R.layout.fragment_qr_scanner, container, false)
        initViews()
        onClicks()
        return mView.rootView
    }

    private fun initViews() {
        initializeQRCamera()
    }

    private fun initializeQRCamera() {
        scannerView = ZBarScannerView(context)
        scannerView.setResultHandler(this)
        scannerView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorTranslucent))
        scannerView.setBorderColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        scannerView.setLaserColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setSquareViewFinder(true)
        scannerView.setupScanner()
        scannerView.setAutoFocus(true)
        startQRCamera()
        mView.containerScanner.addView(scannerView)
    }



    override fun handleResult(rawResult: Result?) {
        onQrResult(rawResult?.contents)
        resetPreview()
    }

    private fun onQrResult(contents: String?) {
        Toast.makeText(context!!,contents,Toast.LENGTH_SHORT).show()
    }

    private fun startQRCamera() {
        scannerView.startCamera()
    }

    private fun resetPreview() {
        scannerView.stopCamera()
        scannerView.startCamera()
        scannerView.stopCameraPreview()
        scannerView.resumeCameraPreview(this)
    }

    private fun onClicks() {
        mView.flashToggle.setOnClickListener {
            if(mView.flashToggle.isSelected){
                offFlashLight()
            } else {
                onFlashLight()
            }
        }
    }

    private fun onFlashLight() {
        mView.flashToggle.isSelected = true
        scannerView.flash = true
    }

    private fun offFlashLight() {
        mView.flashToggle.isSelected = false
        scannerView.flash = false
    }


}