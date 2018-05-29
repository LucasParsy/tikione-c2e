package fr.tikione.c2e

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuxlu.polyvox.Room.MagSummaryAdapter
import fr.tikione.c2e.Utils.LoadingUtils
import fr.tikione.c2e.Utils.Recyclers.IRecycler
import fr.tikione.c2e.Utils.TmpUtils
import fr.tikione.c2e.core.*
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File


/**
 * Created by tuxlu on 19/01/18.
 */
open class MagSummaryRecycler : IRecycler<SimpleMagCategory>() {

    override val layoutListId: Int = R.layout.fragment_recycler_view
    override val recycleId: Int = R.id.recycleView
    override val itemDecoration = LinearItemDecoration(2)


    //unused params
    override val layoutObjectId: Int = R.layout.info_summary_article
    override val binder = null;
    override val requestObjectName: String = ""

    private lateinit var recycler: RecyclerView;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = super.onCreateView(inflater, container, savedInstanceState)


        return rootView
    }

    fun setNum(num: Int)
    {
        val dir = File(TmpUtils.getFilesPath(rootView.context), num.toString())
        val sumFile = File(dir, "sommaire")

        LoadingUtils.EndLoadingView(rootView)
        val magTocTmp = TmpUtils.readObjectFile<SimpleMagToc>(sumFile)
        val magToc = SimpleMagTocExpandable(magTocTmp)

        //needs refactor, but adds the edito as an Article description.
        //If condition is made in adapter, showing the Edito layout
        val editoList = ArrayList<SimpleMagArticle>()
        editoList.add(SimpleMagArticle(magToc.title, false, magToc.edito))
        val editoCat = SimpleMagCategoryExpandable(SimpleMagCategory("Édito", editoList))

        val artList = magToc.categories
        artList.add(0, editoCat)

        val nadapter = MagSummaryAdapter(artList, num)

        for (i in nadapter.groups.size - 1 downTo 0) {
            if (!nadapter.isGroupExpanded(nadapter.groups[i]));
            nadapter.toggleGroup(nadapter.groups[i])
        }

        recycler = recycleView
        recycler.adapter = nadapter
    }



    override fun add(data: JSONArray, replace: Boolean) {
    }

    override fun fillDataObject(json: JSONObject): SimpleMagCategory {
        return SimpleMagCategory("", ArrayList()) //mock, unused
    }


    override fun clear() {
        recycler.adapter = null
    }

    override fun setLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(activity)

}
