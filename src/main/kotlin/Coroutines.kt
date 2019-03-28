import kotlinx.coroutines.*

fun main() {
    exampleWithContexts()
}

suspend fun calculateComplexOperations(num: Int): Int {
    //complex operation
    delay(1000)
    println("In suspending function $num")
    return num * 20

}

fun exampleRunBlocking() = runBlocking {
    println("Before suspending function")
    calculateComplexOperations(10)
    println("After suspending function")
}

fun exampleRunBlockingDispatchers() = runBlocking(Dispatchers.IO) {
    println("Before suspending function")
    calculateComplexOperations(10)
    println("After suspending function")
}

fun exampleGlobalScopeLaunch() = runBlocking {
    println("Before suspending function")
    val job = GlobalScope.launch {
        calculateComplexOperations(10)
    }
    println("After suspending function")
    job.join()
}

fun exampleLaunch() = runBlocking {
    println("Before suspending function")
    launch {
        calculateComplexOperations(10)
    }
    println("After suspending function")
}

fun exampleAsyncAwaitParallel() = runBlocking {
    val startTime = System.currentTimeMillis()

    println("Before suspending function")
    val result1 = async(Dispatchers.IO) { calculateComplexOperations(10) }
    val result2 = async(Dispatchers.IO) { calculateComplexOperations(20) }
    val result3 = async(Dispatchers.IO) { calculateComplexOperations(30) }
    val sum = result1.await() + result2.await() + result3.await()
    println("After suspending function result is $sum")
    val endTime = System.currentTimeMillis()
    println("Time  ${endTime - startTime}")
}

fun exampleAsyncAwaitSequential() = runBlocking {
    val startTime = System.currentTimeMillis()
    println("Before suspending function")
    val result1 = async(Dispatchers.IO) { calculateComplexOperations(10) }.await()
    val result2 = async(Dispatchers.IO) { calculateComplexOperations(20) }.await()
    val result3 = async(Dispatchers.IO) { calculateComplexOperations(30) }.await()
    val sum = result1 + result2 + result3
    println("After suspending function result is $sum")
    val endTime = System.currentTimeMillis()
    println("Time  ${endTime - startTime}")
}

fun exampleAsyncAwaitParallelNonBlockingCurrentThread() = runBlocking {
    println("Before suspending function")
    launch(Dispatchers.Default) {
        val result1 = async(Dispatchers.IO) { calculateComplexOperations(10) }
        val result2 = async(Dispatchers.IO) { calculateComplexOperations(20) }
        val result3 = async(Dispatchers.IO) { calculateComplexOperations(30) }
        val sum = result1.await() + result2.await() + result3.await()
        println("result $sum")
    }
    println("After suspending function")
}

fun exampleAsyncAwaitSequentialNonBlockingCurrentThread() = runBlocking {
    println("Before suspending function")
    launch(Dispatchers.IO) {
        val result1 = async(Dispatchers.IO) { calculateComplexOperations(10) }.await()
        val result2 = async(Dispatchers.IO) { calculateComplexOperations(20) }.await()
        val result3 = async(Dispatchers.IO) { calculateComplexOperations(30) }.await()
        val sum = result1 + result2 + result3
        println("result is $sum")
    }
    println("After suspending function")
}

fun exampleWithContexts() = runBlocking  {
    println("Before suspending function")
    withContext(Dispatchers.IO) {
        val result1 = calculateComplexOperations(10)
        val result2 = calculateComplexOperations(20)
        val result3 = calculateComplexOperations(30)
        val sum = result1 + result2 + result3
        println("result is $sum")
    }
    println("After suspending function")
}
