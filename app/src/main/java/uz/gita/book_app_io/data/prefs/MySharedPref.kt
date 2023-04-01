package uz.gita.book_app_io.data.prefs

import android.content.Context
import android.content.SharedPreferences
import uz.gita.book_app_io.utils.SharedPreference
import javax.inject.Inject

class MySharedPref @Inject constructor(
    ctx: Context,
    sharedPreferences: SharedPreferences
) : SharedPreference(ctx, sharedPreferences) {

    var name: String by Strings("madina")

    var id: String by Strings("")

    var image: String by Strings("")

    var password: String by Strings("1234567m")

}
