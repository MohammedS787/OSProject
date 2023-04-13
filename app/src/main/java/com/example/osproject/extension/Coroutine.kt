package com.example.osproject.extension

import com.google.android.gms.tasks.Task
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/12/2023.
 */

/** TODO Use this extension function to await for a task to complete
 */
suspend fun <T> Task<T>.await() = suspendCoroutine<T> {
    addOnSuccessListener { result -> it.resumeWith(Result.success(result)) }
    addOnFailureListener { exception -> it.resumeWith(Result.failure(exception)) }
}