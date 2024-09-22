package com.luisma.conexiones.models.game

enum class GameState(val rawValue: String) {
    Win(rawValue = "WIN"),
    Lost(rawValue = "LOST"),
    Playing(rawValue = "PLAYING"),
    Free(rawValue = "FREE"),
    Lock(rawValue = "LOCK");

    companion object {
        fun fromRaw(raw: String): GameState {
            val rawValues = entries.map { it.rawValue }
            if (!rawValues.contains(raw)) {
                return Lock
            }
            return entries.first { it.rawValue == raw }
        }
    }

}