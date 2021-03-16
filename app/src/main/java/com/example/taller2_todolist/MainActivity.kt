package com.example.taller2_todolist


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import java.io.File
import java.io.PrintStream


class MainActivity : AppCompatActivity() {
    private val FILE_NAME: String="ToDoList.txt"
    private var items = ArrayList<String>()
    private var auxString : String =""
    private var auxArray = ArrayList<String>()
    lateinit var  button : Button
    lateinit var listView: ListView
    lateinit var editText: EditText

    private lateinit var itemsAdapter : ArrayAdapter<String>
    private lateinit var file : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        button.setOnClickListener {
            val txt = editText.text.toString()
            addItemList(txt)
            auxString += txt + ";"
            writeItemInFile(auxString)

        }
        deleteItem()
        startList()

    }

    private fun writeItemInFile(txt: String) {
        val outDir = getExternalFilesDir(null)
        val outFile = File(outDir, FILE_NAME)
        val outPut = PrintStream(outFile)
        outPut.println(txt)
        outPut.close()


    }

    private fun deleteItem() {
        listView.setOnItemLongClickListener { parent, view, position, id ->
            println(itemsAdapter.getItem(position))
            items.remove(itemsAdapter.getItem(position))
            println(items.toString())
            itemsAdapter.notifyDataSetChanged()

            val outDir = getExternalFilesDir(null)
            val outFile = File(outDir,FILE_NAME)
            outFile.delete()
            val outFileReload = File(outDir, FILE_NAME)
            val outPut = PrintStream(outFileReload)

            for (elements in items){
                outPut.println(elements+";")
            }
            outPut.close()
            true

        }
    }

    private fun startList() {

        val file =File(getExternalFilesDir(null),FILE_NAME)
        val contents = file.readText()
        val list = contents.split(";")
        for( ob in list){
            items.add(ob)
        }
        itemsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = itemsAdapter
    }


    private fun addItemList(txt: String) {
        items.add(txt)
        itemsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = itemsAdapter
    }


}