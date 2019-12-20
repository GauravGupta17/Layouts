package com.example.utilis

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.facebook.appevents.codeless.internal.Constants
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class WorkerUtils {

    companion object {

        fun blurBitmap(
            bitmap: Bitmap,
            applicationContext: Context,blurLvl:Int
        ): Bitmap {

            var rsContext: RenderScript? = null
            try {

                // Create the output bitmap
                val output = Bitmap.createBitmap(
                    bitmap.width, bitmap.height, bitmap.config
                )

                // Blur the image
                rsContext = RenderScript.create(applicationContext, RenderScript.ContextType.DEBUG)
                val inAlloc = Allocation.createFromBitmap(rsContext, bitmap)
                val outAlloc = Allocation.createTyped(rsContext, inAlloc.getType())
                val theIntrinsic = ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext))
                theIntrinsic.setRadius(5f*blurLvl)
                theIntrinsic.setInput(inAlloc)
                theIntrinsic.forEach(outAlloc)
                outAlloc.copyTo(output)

                return output
            } finally {
                if (rsContext != null) {
                    rsContext!!.finish()
                }
            }
        }


        @Throws(FileNotFoundException::class)
        fun writeBitmapToFile(
            applicationContext: Context,
            bitmap: Bitmap
        ): Uri {

            val name = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString())
            val outputDir = File(applicationContext.filesDir, "blur_filter_outputs")
            if (!outputDir.exists()) {
                outputDir.mkdirs() // should succeed
            }
            val outputFile = File(outputDir, name)
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(outputFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
            } finally {
                if (out != null) {
                    try {
                        out.close()
                    } catch (ignore: IOException) {
                    }

                }
            }
            return Uri.fromFile(outputFile)
        }


    }
}
