package th.co.appman.vdotestparse

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_upload_file.*
import java.io.ByteArrayOutputStream


class UploadFileActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "UploadFileActivity"
        private const val PICK_IMAGE_FILE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_file)

        btnOpen.setOnClickListener {
            openFile()
        }
        btnUpload.setOnClickListener {
            uploadImage()
        }
        btnDownload.setOnClickListener {
            downloadImage()
        }
        btnClear.setOnClickListener {
            ivPreview.setImageDrawable(null)
        }
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == PICK_IMAGE_FILE && resultCode == Activity.RESULT_OK) {
            val selectedImage = resultData?.data
            try {
                val bitmapImage = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                ivPreview.setImageBitmap(bitmapImage)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        val drawable = ivPreview.drawable as BitmapDrawable?
        drawable?.let {
            val stream = ByteArrayOutputStream()
            it.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            val file = ParseFile("image.jpeg", byteArray)
            val parseObject = ParseObject("Image")
            parseObject.put("image", file)
            parseObject.put("creator", "test")
            parseObject.saveInBackground { exception ->
                if (exception == null) {
                    Toast.makeText(this@UploadFileActivity, "Image has been shared", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@UploadFileActivity, "Issue uploading image", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun downloadImage() {
        val query = ParseQuery<ParseObject>("Image")
        query.whereEqualTo("creator", "test")
        query.orderByAscending("createdAt")
        query.findInBackground { parseObjects, exception ->
            Log.d(TAG, "parseObjects = $parseObjects, exception = $exception")
            if (parseObjects.size > 0 && exception == null) {
                for (parseObject in parseObjects) {
                    val file = parseObject["image"] as? ParseFile
                    Log.d(TAG, "file.url = ${file?.url}")
                    file?.getDataInBackground { data, ex ->
                        if (ex == null && data != null) {
                            val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                            ivPreview.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        }
    }
}