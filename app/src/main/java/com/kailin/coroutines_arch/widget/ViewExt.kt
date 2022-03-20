package com.kailin.coroutines_arch.widget

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setError(@StringRes res: Int) {
    errorIconDrawable = null
    error = try {
        context.getText(res)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Fragment.navigation(@IdRes resId: Int) {
    view?.let { Navigation.findNavController(it).navigate(resId) }
}


fun Fragment.navigation(directions: NavDirections) {
    view?.let {
        Navigation.findNavController(it).navigate(directions.actionId, directions.arguments, null)
    }
}

fun Fragment.setActionBar(toolbar: Toolbar) {
    if (requireActivity() is AppCompatActivity) {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }
}

fun Fragment.navigationPop() {
    view?.let {
        Navigation.findNavController(it).popBackStack()
    }
}

