package fr.tikione.c2e

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebViewClient
import android.view.Menu
import kotlinx.android.synthetic.main.article_webview.*
import android.view.WindowManager
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.view.MenuItem
import android.view.View
import fr.tikione.c2e.Utils.TmpUtils
import java.io.File
import android.support.design.widget.CoordinatorLayout




class ArticleViewer : AppCompatActivity() {

    private var darkMode: Boolean = false
    var fontSize: Int = 14
    private lateinit var preferences: SharedPreferences
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val b = intent.extras
        preferences = this.getSharedPreferences("app", MODE_PRIVATE);
/*
        val color = b.getInt("color")
        val header = b.getString("header")
        val url = b.getString("url")
        darkMode = preferences.getBoolean("darkMode", false)
        fontSize = preferences.getInt("fontSize", 14)
*/
        setContentView(R.layout.article_webview)
        setSupportActionBar(toolbar);
        collapsing_toolbar.setContentScrimColor(resources.getColor(android.R.color.holo_orange_light))

        val bar = supportActionBar
        bar?.setDisplayHomeAsUpEnabled(true)
        bar?.setDisplayShowHomeEnabled(true)
        bar?.setDisplayShowTitleEnabled(false)

        val header = ""
        val head = File(header)
        if (head.exists() && head.isFile && head.canRead()) {
            val bit = BitmapFactory.decodeFile(header)
            backgroundImageView.setImageBitmap(bit)
        } else {
            appBarLayout.layoutParams.height = convertDipToPixels(66.0f)
            //backgroundImageView.setBackgroundColor(color)
            backgroundImageView.setBackgroundColor(resources.getColor(android.R.color.holo_orange_light))
        }

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) //transparent status bar

        //helper.initActionBar(this)

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        val file = File(TmpUtils.getFilesPath(this.baseContext), "test.html")
        webView.loadUrl("file:///" + file.absolutePath)
    }

    fun convertDipToPixels(dips: Float): Int {
        return (dips *  applicationContext.resources.displayMetrics.density + 0.5f).toInt()
    }

    private fun setDarkMode() {
        webView.evaluateJavascript("setDarkMode();", null)
        val item = menu.getItem(0)
        if (!darkMode)
            item.icon = resources.getDrawable(R.drawable.baseline_wb_sunny_black_18dp, null)
        else
            item.icon = resources.getDrawable(R.drawable.baseline_brightness_3_black_18dp, null)
    }

    override fun onCreateOptionsMenu(nMenu: Menu): Boolean {
        menuInflater.inflate(R.menu.article_menu, nMenu)
        this.menu = nMenu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.lightMode -> {
                setDarkMode()
                darkMode = !darkMode
                preferences.edit().putBoolean("darkMode", darkMode).apply()
                true
            }
            R.id.textsizeMinus -> {
                true
            }
            R.id.textsizePlus -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}