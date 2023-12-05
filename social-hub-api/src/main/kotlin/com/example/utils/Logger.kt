package com.example.utils

object Logger {
    enum class MessageKind {
        INFO,
        SUCCESS,
        ERROR,
        UNDEFINED,
        DBFETCH,
        APIFETCH,
        LOGGED,
        TESTING
    }

    fun log(message: String, kind: MessageKind = MessageKind.INFO) {
        val icon: String = when (kind) {
            MessageKind.INFO -> "ℹ️"
            MessageKind.SUCCESS -> "✅"
            MessageKind.ERROR -> "🔴"
            MessageKind.DBFETCH -> "💾"
            MessageKind.APIFETCH -> "🌐"
            MessageKind.UNDEFINED -> "❓"
            MessageKind.LOGGED -> "👤"
            MessageKind.TESTING -> "🔨"
        }
        print("$icon $message\n")
    }
}
