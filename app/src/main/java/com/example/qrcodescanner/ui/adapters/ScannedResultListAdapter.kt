package com.example.qrcodescanner.ui.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcodescanner.R
import com.example.qrcodescanner.db.DbHelperI
import com.example.qrcodescanner.db.entities.QrResult
import com.example.qrcodescanner.ui.dialogs.QrCodeResultDialog
import com.example.qrcodescanner.utlis.gone
import com.example.qrcodescanner.utlis.toFormattedDisplay
import com.example.qrcodescanner.utlis.visible
import kotlinx.android.synthetic.main.layout_qr_result_show.view.*
import kotlinx.android.synthetic.main.layout_qr_result_show.view.favouriteIcon
import kotlinx.android.synthetic.main.layout_single_item_qr_result.view.*
import java.nio.file.Files.delete

class ScannedResultListAdapter(
    var dbHelperI: DbHelperI,
    var context: Context,
    private var listOfScannedResult: MutableList<QrResult>
) :
    RecyclerView.Adapter<ScannedResultListAdapter.ScannedResultListViewHolder>() {

    private var resultDialog: QrCodeResultDialog =
        QrCodeResultDialog(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedResultListViewHolder {
        return ScannedResultListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_single_item_qr_result,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfScannedResult.size
    }

    override fun onBindViewHolder(holder: ScannedResultListViewHolder, position: Int) {
        holder.bind(listOfScannedResult[position], position)
    }

    inner class ScannedResultListViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(qrResult: QrResult, position: Int) {
            view.result.text = qrResult.result!!
            view.tvTime.text = qrResult.calendar.toFormattedDisplay()
            setResultTypeIcon(qrResult.resultType)
            setFavourite(qrResult.favourite)
            onClicks(qrResult, position)
        }

        private fun setResultTypeIcon(resultType: String?) {

        }

        private fun setFavourite(isFavourite: Boolean) {
            if (isFavourite)
                view.favouriteIcon.visible()
            else
                view.favouriteIcon.gone()
        }


        private fun onClicks(qrResult: QrResult, position: Int) {
            view.setOnClickListener {
                resultDialog.show(qrResult)
            }

            view.setOnLongClickListener {
                showDeleteDialog(qrResult, position)
                return@setOnLongClickListener true
            }
        }

        private fun showDeleteDialog(qrResult: QrResult, position: Int) {
            AlertDialog.Builder(context, R.style.CustomAlertDialog).setTitle("Delete")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete") { _, _ ->
                    deleteThisRecord(qrResult, position)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }.show()
        }

        private fun deleteThisRecord(qrResult: QrResult, position: Int) {
            dbHelperI.deleteQrResult(qrResult.id!!)
            listOfScannedResult.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}