package fr.tikione.c2e.core.model.web

/**
 * TOC item.
 */
class TocItem {

    var title: String? = null
    var url: String? = null
    var headerUrl: String? = null
    var articles: List<Article>? = null

    constructor() {
    }

    constructor(title: String?, url: String?, headerUrl : String?, articles: List<Article>?) {
        this.title = title
        this.url = url
        this.headerUrl = headerUrl
        this.articles = articles
    }

    override fun toString(): String {
        return "TocItem(title=$title, url=$url, articles=$articles)"
    }
}
