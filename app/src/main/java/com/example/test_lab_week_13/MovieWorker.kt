package com.example.test_lab_week_13

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MovieWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val repo = (applicationContext as MovieApplication).movieRepository

        return try {
            // Collect the flow to trigger the network + database logic
            runBlocking {
                repo.fetchMovies().collect { movies ->
                    // nothing needed here — collection triggers the repository logic
                }
            }

            Result.success()

        } catch (e: Exception) {
            Result.retry()
        }
    }
}
