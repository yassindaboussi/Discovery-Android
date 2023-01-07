package tn.yassin.discovery.Utils


import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore

object FileUtils {

    fun getRealPathFromURI(context: Context,contentURI: Uri): String? {
        var result: String? = null
        val cursor: Cursor = context.getContentResolver().query(contentURI, null, null, null, null)!!
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            if (cursor.moveToFirst()) {
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                result = cursor.getString(idx)
            }
            cursor.close()
        }
        return result
    }

    fun getPath(context: Context, uri: Uri): String? {

        val atLeastKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        if (atLeastKitKat) {

            val docId: String = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":")
            val type = split[0]
            //Logger.up("Primary: $type")
            if ("image" == type) {
                val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                fun getDataColumn(
                    context: Context,
                    uri: Uri,
                    selection: String,
                    selectionArgs: Array<String>
                ): String {
                    var cursor: Cursor?
                    val column = "_data"
                    val projection = arrayOf(column)

                    cursor = context.getContentResolver().query(
                        uri, projection, selection, selectionArgs,
                        null
                    )
                    if (cursor != null && cursor.moveToFirst()) {

                        val columnIndex = cursor.getColumnIndexOrThrow(column)
                        return cursor.getString(columnIndex)
                    }
                    cursor?.close()
                    return ""
                }
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        }
        return null
    }
}