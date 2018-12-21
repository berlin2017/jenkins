package com.berlin.lslibrary.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception

class Utils {

    companion object {
        /**
        @fileName 文件名
         **/

        fun readAssets(context: Context, fileName: String): String {

            var result = ""
            try {
                var inputStreamReader = InputStreamReader(context.resources.assets.open(fileName))
                var bufferedReader = BufferedReader(inputStreamReader)
                var line = ""
                while (line != null) {
                    line = bufferedReader.readLine()
                    result += line
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

    }


}