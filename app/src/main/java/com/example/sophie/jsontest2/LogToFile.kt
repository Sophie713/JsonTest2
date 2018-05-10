package com.example.sophie.jsontest2

import android.content.Context
import mu.KLogging
import java.io.File
import java.io.IOException
import java.util.logging.FileHandler
import java.util.logging.Logger
import java.util.logging.SimpleFormatter

class LogToFile(context: Context) {
    val formatter = SimpleFormatter()

    // val logger = LoggerFactory.getLogger("MyLog") **WITH THIS LINE...**

    val logger = Logger.getLogger("MyLog") //this line WORKS
    val dest = context.applicationContext.getExternalFilesDir(null);
    val fh = FileHandler(dest.path.plus(File.pathSeparator).plus("data.txt"))

    init {

        //..THIS LINE DOESN'T WORK (NO addHandler is there some ekvivalent for the LoggerFactory?)//

        logger.addHandler(fh)
        fh.formatter = formatter
    }


    fun write(logString: String) {

        try {

            logger.info(logString)

        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}