package xyz.aprildown.theme.app

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private class SharedPreferenceDelegate<T>(
    private val context: Context,
    private val key: String,
    private val defaultValue: T,
    private val getter: SharedPreferences.(String, T) -> T,
    private val setter: Editor.(String, T) -> Editor
) : ReadWriteProperty<Any, T> {

    private val sharedPreferences by lazy(LazyThreadSafetyMode.NONE) {
        context.safeContext.safeSharedPreference
    }

    override fun getValue(thisRef: Any, property: KProperty<*>) =
        sharedPreferences.getter(key, defaultValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
        sharedPreferences.edit().setter(key, value).apply()
}

@Suppress("UNCHECKED_CAST")
fun <T> bindSharedPreference(
    context: Context,
    key: String,
    defaultValue: T
): ReadWriteProperty<Any, T> =
    when (defaultValue) {
        is Boolean ->
            SharedPreferenceDelegate(
                context, key, defaultValue,
                SharedPreferences::getBoolean, Editor::putBoolean
            )
        is Int ->
            SharedPreferenceDelegate(
                context, key, defaultValue,
                SharedPreferences::getInt, Editor::putInt
            )
        is Long ->
            SharedPreferenceDelegate(
                context, key, defaultValue,
                SharedPreferences::getLong, Editor::putLong
            )
        is Float ->
            SharedPreferenceDelegate(
                context, key, defaultValue,
                SharedPreferences::getFloat, Editor::putFloat
            )
        is String ->
            SharedPreferenceDelegate(
                context, key, defaultValue,
                SharedPreferences::getString, Editor::putString
            )
        else -> throw IllegalArgumentException()
    } as ReadWriteProperty<Any, T>

private val Context.safeContext: Context
    get() = takeIf { Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !isDeviceProtectedStorage }?.let {
        ContextCompat.createDeviceProtectedStorageContext(it) ?: it
    } ?: this

val Context.safeSharedPreference: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(safeContext)
