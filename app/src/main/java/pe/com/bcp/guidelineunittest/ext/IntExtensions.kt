package pe.com.bcp.guidelineunittest.ext

import android.content.res.Resources

val Int.dp: Int
    get() = Resources.getSystem()?.let { this * it.displayMetrics.density + 0.5f }?.toInt() ?: -1