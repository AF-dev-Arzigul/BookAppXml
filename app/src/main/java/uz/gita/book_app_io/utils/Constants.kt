package uz.gita.book_app_io.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import uz.gita.book_app_io.MainActivity

object Constants {


    fun goToPlayMarket(activity: MainActivity) {
        try {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=uz.gita.task_app")
                )
            )
        } catch (e: ActivityNotFoundException) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=uz.gita.task_app")
                )
            )
        }
    }

    fun share(context: Context) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Baby Puzzle")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "link: https://play.google.com/store/apps/details?id=uz.gita."
        )
        context.startActivity(Intent.createChooser(intent, "UpTodo"))
    }
}