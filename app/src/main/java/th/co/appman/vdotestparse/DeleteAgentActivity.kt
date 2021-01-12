package th.co.appman.vdotestparse

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_delete_agent.*


class DeleteAgentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_agent)

        btnDelete.setOnClickListener {
            val query = ParseQuery.getQuery<ParseObject>("agents")
            query.whereEqualTo("name", etName.text.toString())
            query.findInBackground { scoreList, e ->
                if (e == null) {
                    scoreList.forEach {
                        it.deleteInBackground()
                    }

                    Toast.makeText(this, "Delete done", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Delete fail", Toast.LENGTH_SHORT).show()

                }

                finish()
            }
        }
    }
}