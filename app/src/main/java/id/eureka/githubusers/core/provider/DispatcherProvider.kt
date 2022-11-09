package id.eureka.githubusers.core.provider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatcherProvider {
    fun getDefault(): CoroutineDispatcher
    fun getIO(): CoroutineDispatcher
    fun getMain(): CoroutineDispatcher
    fun getUnconfined(): CoroutineDispatcher
}

class DispatcherProviderImp @Inject constructor() : DispatcherProvider {
    override fun getDefault(): CoroutineDispatcher = Dispatchers.Default
    override fun getIO(): CoroutineDispatcher = Dispatchers.IO
    override fun getMain(): CoroutineDispatcher = Dispatchers.Main
    override fun getUnconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}