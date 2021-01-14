package th.co.appman.vdotestparse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.SaveCallback
import kotlinx.android.synthetic.main.activity_add_agent.*
import kotlinx.android.synthetic.main.activity_create_room.*

class CreateRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room)

        etRoomId.setText(System.currentTimeMillis().toString())

        btnCreate.setOnClickListener {

            val parseObject = ParseObject("room")

            parseObject.put("roomId", etRoomId.text.toString())
            parseObject.put("action", etAction.text.toString())

            val serverPeer = ParseObject("peer")
            serverPeer.put("cameraEnable", etCameraEnable.text.toString() == "true")
            serverPeer.put("micEnable", etMicEnable.text.toString() == "true")
            serverPeer.put("streamId", etStreamID.text.toString())

            parseObject.add("peer", serverPeer)

            parseObject.saveInBackground()

            val claimObject = ParseObject("claim")
            claimObject.put("clientName", etClientName.text.toString())
            claimObject.put("claimId", etRoomId.text.toString())

            claimObject.put("state", etState.text.toString())

            val accidentInfo = ParseObject("accidentInfo")
            accidentInfo.put("dateTime", System.currentTimeMillis())
            accidentInfo.put("place", etPlace.text.toString())
            accidentInfo.put("shortInfo", etShort.text.toString())

            claimObject.put("accidentInfo", accidentInfo)

            claimObject.saveInBackground {
                Log.d("TESTTONY", "exception $it")
            }


            Toast.makeText(this, "Create Room finish", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}