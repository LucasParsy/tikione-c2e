package fr.tikione.c2e

import android.util.Log
import compat.Tools
import fr.tikione.c2e.core.SimpleMagArticle
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun date_format() {
        assertEquals("1er déc 2018", DateCustom(2018, 12, true).dateToString())
    }

    @Test
    fun string_split() {
        var url = "background-image: url('/sites/default/files/numeros/381/5876-dices.jpeg');');"
        val res = "/sites/default/files/numeros/381/5876-dices.jpeg"

        url = url.substring(url.indexOf("'") + 1);
        url = url.substring(0, url.indexOf("'"));

        assertEquals(res, url)
    }

    @Test
    fun hash_length() {
        val title = "test"
        val hash = title.hashCode().toString().replace('-', 'a')
        println(hash)
        assert(hash.length < 15)
    }

    @Test
    fun data_classes() {
        val data1 = SimpleMagArticle("titre")
        val hash = data1.filename
        val data2 = data1.copy()

        assertEquals(hash, data2.filename)
    }

    @Test
    fun list_magic() {
        val list = intArrayOf(10, 20, 30, 40, 50).toMutableList()
        list.subList(3, list.size).clear()
        assertEquals(intArrayOf(10, 20, 30).toMutableList(), list)

        val l2 = intArrayOf(10, 20, 30, 40, 50).toMutableList()
        System.out.println(l2.last());

        //for (elem in l2)
            //System.out.println(elem.toString());
    }
}
