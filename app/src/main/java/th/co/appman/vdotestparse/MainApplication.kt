package th.co.appman.vdotestparse

import android.app.Application
import com.parse.Parse

class MainApplications: Application() {
    override fun onCreate() {
        super.onCreate()

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("vdo-server") // if desired
                .server("https://parse-server-vdo.herokuapp.com/parse/")
                .build()
        )
    }
}