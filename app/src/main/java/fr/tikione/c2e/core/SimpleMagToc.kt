package fr.tikione.c2e.core

import android.os.Parcel
import android.os.Parcelable
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import java.io.Serializable

data class SimpleMagToc(val title: String ="",
                        val edito: String ="",
                        val categories : ArrayList<SimpleMagCategory> = ArrayList()) : Serializable

data class SimpleMagCategory(val name: String,
                             val articles : ArrayList<SimpleMagArticle>) : Serializable
{
    constructor() : this("", ArrayList<SimpleMagArticle>())
    var color = 0;
}


data class SimpleMagTocExpandable(val toc: SimpleMagToc)
{
    val title: String = toc.title
    val edito: String = toc.edito
    val categories = ArrayList<SimpleMagCategoryExpandable>()

    init {
        for (elem in toc.categories)
            categories.add(SimpleMagCategoryExpandable(elem))
    }
}


data class SimpleMagCategoryExpandable(private val cat : SimpleMagCategory) : ExpandableGroup<SimpleMagArticle>(cat.name, cat.articles)
{
    val name = cat.name
    val articles = cat.articles
    var color = 0;
}


data class SimpleMagArticle(val title: String,
                            val isRead: Boolean =false,
                            val filename : String = title.hashCode().toString().replace('-', '1'))
    :Serializable, Parcelable{



    constructor() : this("", false, "")


    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeByte(if (isRead) 1 else 0)
        parcel.writeString(filename)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SimpleMagArticle> {
        override fun createFromParcel(parcel: Parcel): SimpleMagArticle {
            return SimpleMagArticle(parcel)
        }

        override fun newArray(size: Int): Array<SimpleMagArticle?> {
            return arrayOfNulls(size)
        }
    }
}
