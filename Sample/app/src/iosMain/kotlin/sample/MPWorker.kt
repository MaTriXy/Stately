package sample

import kotlin.native.concurrent.Future
import kotlin.native.concurrent.TransferMode
import kotlin.native.concurrent.Worker
import kotlin.native.concurrent.freeze

actual class MPWorker actual constructor(){
    val worker = Worker.start()
    actual fun <T> runBackground(backJob: () -> T): MPFuture<T> {
        return MPFuture(worker.execute(TransferMode.SAFE, {backJob.freeze()}){
            it()
        })
    }

    actual fun requestTermination() {
        worker.requestTermination().result
    }
}

actual class MPFuture<T>(private val future: Future<T>) {
    actual fun consume():T = future.result
}