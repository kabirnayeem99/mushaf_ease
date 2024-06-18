package io.github.kabirnayeem99.mushafease.domain.entities

sealed class Resource<T>(
    val data: T? = null, val message: ErrorType? = null
)

class Success<T>(data: T) : Resource<T>(data)
class Failed<T> : Resource<T>()
class Loading<T>(data: T? = null) : Resource<T>(data)
class Error<T> : Resource<T>()

enum class ErrorType {
    NoSurah,
    Unknown,
}