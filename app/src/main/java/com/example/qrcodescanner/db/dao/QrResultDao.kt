package com.example.qrcodescanner.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.qrcodescanner.db.entities.QrResult

@Dao
interface QrResultDao {

    @Query("SELECT * FROM QrResult ORDER BY time DESC")
    fun getAllScannedResult(): List<QrResult>

    @Query("SELECT * FROM QrResult WHERE favourite = 1 ORDER BY time DESC")
    fun getAllFavouriteResult(): List<QrResult>

    @Query("DELETE FROM QrResult WHERE id = :id")
    fun deleteQrResult(id : Int) : Int

    @Insert
    fun insertQrResult(qrResult: QrResult)

    @Query("UPDATE QrResult SET favourite = 1 WHERE id = :id")
    fun addToFavourite(id : Int) : Int

    @Query("UPDATE QrResult SET favourite = 0 WHERE id = :id")
    fun removeFromFavourite(id : Int) : Int

    @Query("SELECT * FROM QrResult WHERE result = :result ")
    fun checkIfQrResultExist(result : String) : Int
}