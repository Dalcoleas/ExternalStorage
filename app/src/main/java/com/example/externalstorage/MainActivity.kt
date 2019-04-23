package com.example.externalstorage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    private val filepath = "MyFileStorage"
    private var myExternalFile: File?=null
    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return (Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState) }


    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return (Environment.MEDIA_MOUNTED == extStorageState) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bt_save.setOnClickListener{
            myExternalFile = File(getExternalFilesDir(filepath), et_name.text.toString())

            try{
                val fileOutPutStream = FileOutputStream(myExternalFile)
                fileOutPutStream.write(et_data.text.toString().toByteArray())
                fileOutPutStream.close()
            }catch (e:IOException){
                e.printStackTrace()
            }

            Toast.makeText(applicationContext,"Info Guardada",Toast.LENGTH_SHORT).show()
        }

        bt_read.setOnClickListener{
            myExternalFile = File(getExternalFilesDir(filepath), et_name.text.toString())

            val filename = et_name.text.toString()
            myExternalFile = File(getExternalFilesDir(filepath),filename)

            if(filename.trim()!=""){
                var fileInputStream = FileInputStream(myExternalFile)
                var inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader= BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()
                Toast.makeText(applicationContext,stringBuilder.toString(),Toast.LENGTH_SHORT).show()
            }
        }

        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            bt_save.isEnabled = false
        }
    }

}
