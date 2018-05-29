package com.tuxlu.polyvox.Room

import android.app.Activity
import android.content.Intent
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import fr.tikione.c2e.ArticleViewer
import fr.tikione.c2e.R
import fr.tikione.c2e.Utils.TmpUtils.Companion.getColor
import fr.tikione.c2e.core.SimpleMagArticle
import fr.tikione.c2e.core.SimpleMagCategory
import fr.tikione.c2e.core.SimpleMagCategoryExpandable


/**
 * Created by tuxlu on 19/01/18.
 */

open class MagCategoryBinder(val v: View) : GroupViewHolder(v) {

    fun setData(group: SimpleMagCategoryExpandable) {
        v.findViewById<TextView>(R.id.categoryName).text = group.name
        val con = v.context

        val color = when {
            group.name.contains("test") || group.name.contains("doigt") -> {
                getColor(con, R.color.article_tests)
            }
            group.name.contains("venir") -> {
                getColor(con, R.color.article_avenir)
            }
            group.name.contains("chantier") -> {
                getColor(con, R.color.article_chantier)
            }
            group.name.contains("Garage") || group.name.contains("hardware") -> {
                getColor(con, R.color.article_hardware)
            }
            group.name.contains("part") -> {
                getColor(con, R.color.article_apart)
            }
            else -> {
                getColor(con, R.color.article_base)
            }
        }
        group.color = color
    }



    fun rotateIcon(from: Float) {
        val arrow = v.findViewById<ImageView>(R.id.arrow)
        arrow.animate().rotation(from)
    }

    override fun collapse() {
        super.collapse()
        rotateIcon(0f)
    }

    override fun expand() {
        super.expand()
        rotateIcon(90f)
    }
}

open class MagArticleBinder(val v: View, val currentNum: Int) : ChildViewHolder(v) {

    fun setData(data: SimpleMagArticle, color: Int) {
        data.title
        data.filename

        if (data.filename.length > 15) //Edito
        {
            v.findViewById<View>(R.id.titleView).visibility = View.GONE
            v.findViewById<View>(R.id.editoView).visibility = View.VISIBLE

            v.findViewById<TextView>(R.id.editoTitle).text = data.title

            val editoHtml: Spanned = if (android.os.Build.VERSION.SDK_INT >= 24)
                Html.fromHtml(data.filename, Html.FROM_HTML_MODE_COMPACT);
            else
                Html.fromHtml(data.filename)

            v.findViewById<TextView>(R.id.editoText).text = editoHtml
            return;
        }

        v.findViewById<View>(R.id.titleView).visibility = View.VISIBLE
        v.findViewById<View>(R.id.editoView).visibility = View.GONE
        val articleName = v.findViewById<TextView>(R.id.articleName);
        articleName.text = data.title
        if (data.isRead)
            articleName.setTextColor(v.context.resources.getColor(R.color.lighter_gray))

        val context =v.context
        v.findViewById<View>(R.id.titleView).setOnClickListener { _ ->
            //initFragment
            val intent = Intent(context, ArticleViewer::class.java)
            intent.putExtra("file", data.filename)
            intent.putExtra("title", data.title)
            intent.putExtra("color", color)
            intent.putExtra("num", currentNum)
            context.startActivity(intent)
        }
    }
}


class MagSummaryAdapter(groups: List<SimpleMagCategoryExpandable>, val currentNum: Int) : ExpandableRecyclerViewAdapter<MagCategoryBinder, MagArticleBinder>(groups) {

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): MagCategoryBinder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_summary_category, parent, false)
        return MagCategoryBinder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): MagArticleBinder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_summary_article, parent, false)
        return MagArticleBinder(view, currentNum)
    }

    override fun onBindChildViewHolder(holder: MagArticleBinder, flatPosition: Int, group: ExpandableGroup<*>,
                                       childIndex: Int) {
        val color = (group as SimpleMagCategoryExpandable).color
        val data = (group).items[childIndex];
        holder.setData(data, color)
        }

    override fun onBindGroupViewHolder(holder: MagCategoryBinder?, flatPosition: Int, group: ExpandableGroup<*>?) {
        holder!!.setData(group as SimpleMagCategoryExpandable)
    }
}