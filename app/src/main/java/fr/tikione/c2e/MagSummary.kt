package fr.tikione.c2e

import android.app.Fragment
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.article_webview.*
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.view.*
import fr.tikione.c2e.Utils.TmpUtils
import kotlinx.android.synthetic.main.activity_mag_summary.*
import java.io.File


class MagSummary : AppCompatActivity() {

    /*
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle): View? {

        val root =  inflater.inflate(R.layout.article_webview, container, false)
        return root;
    }
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val b = intent.extras
        val magNum = b.getInt("magNum")
        setContentView(R.layout.activity_mag_summary)
        title = getString(R.string.mag_number, magNum)

        val image = File(File(TmpUtils.getFilesPath(this.applicationContext), magNum.toString()), "couv.jpg")
        if (image.exists() && image.isFile && image.canRead())
            coverMagasineImage.setImageBitmap(BitmapFactory.decodeFile(image.absolutePath))
        else
            coverMagasineImage.visibility = View.GONE
        (fragmentLayout as MagSummaryRecycler).setNum(magNum)


        val bar = supportActionBar
        bar?.setDisplayHomeAsUpEnabled(true)
        bar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}