package th.co.appman.vdotestparse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_agent.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addAgent.setOnClickListener {
            Intent(this, AddAgentActivity::class.java).apply {
                startActivity(this)
            }
        }

        removeAgent.setOnClickListener {
            Intent(this, DeleteAgentActivity::class.java).apply {
                startActivity(this)
            }
        }

        createRoom.setOnClickListener {
            Intent(this, CreateRoomActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}