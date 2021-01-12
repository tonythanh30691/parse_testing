package th.co.appman.vdotestparse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.parse.ParseObject
import kotlinx.android.synthetic.main.activity_add_agent.*

class AddAgentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_agent)


        btnAdd.setOnClickListener {

            val parseObject = ParseObject("agents")

            parseObject.put("name", etName.text.toString())
            parseObject.put("platform", etPlatform.text.toString())
            parseObject.put("status", etStatus.text.toString())
            parseObject.put("token", etToken.text.toString())
            parseObject.put("env", etEnv.text.toString())

            parseObject.saveInBackground()

            Toast.makeText(this, "Create Agent Done", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}