package wiktor.pienko.androidtask.base

import wiktor.pienko.androidtask.injection.component.DaggerPresenterInjector
import wiktor.pienko.androidtask.injection.component.PresenterInjector
import wiktor.pienko.androidtask.injection.module.NetworkModule
import wiktor.pienko.androidtask.injection.module.ContextModule
import wiktor.pienko.androidtask.presenter.MainPresenter

abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .baseView(view)
        .contextModule(ContextModule)
        .networkModule(NetworkModule)
        .build()

    // ...

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MainPresenter -> injector.inject(this)
        }
    }
}