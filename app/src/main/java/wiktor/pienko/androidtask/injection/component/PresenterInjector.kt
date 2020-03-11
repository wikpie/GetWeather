package wiktor.pienko.androidtask.injection.component

import dagger.BindsInstance
import dagger.Component
import wiktor.pienko.androidtask.base.BaseView
import wiktor.pienko.androidtask.injection.module.NetworkModule
import wiktor.pienko.androidtask.injection.module.ContextModule
import wiktor.pienko.androidtask.presenter.MainPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface PresenterInjector {
    /**
     * Injects required dependencies into the specified PostPresenter.
     * @param postPresenter PostPresenter in which to inject the dependencies
     */
    fun inject(mainPresenter: MainPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}