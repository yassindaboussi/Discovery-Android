package tn.yassin.discovery.Utils

import android.content.Context
import android.os.Environment
import android.widget.Toast


object StorageChecker {
    fun checkStorageAvailability(context: Context?) {
        var isStorageExist = false
        var isStorageWritable = false
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {
            isStorageWritable = true
            isStorageExist = isStorageWritable
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            isStorageExist = true
            isStorageWritable = false
            Toast.makeText(context, "Storage is read only", Toast.LENGTH_SHORT).show()
        } else {
            isStorageWritable = false
            isStorageExist = isStorageWritable
            Toast.makeText(context, "Storage is not exist", Toast.LENGTH_SHORT).show()
        }
    }
}