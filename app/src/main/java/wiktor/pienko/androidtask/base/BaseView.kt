package wiktor.pienko.androidtask.base

import android.app.Activity
import android.content.Context

interface BaseView {
    fun getContext():Context
    fun getActivity():Activity
}