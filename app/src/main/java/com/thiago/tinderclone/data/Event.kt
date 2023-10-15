package com.thiago.tinderclone.data

/**
 * Classe genérica que representa um evento, com conteúdo de tipo [T].
 *
 * @param T O tipo de conteúdo do evento.
 * @property content O conteúdo do evento.
 */
open class Event<out T>(private val content: T) {

    // Uma propriedade que indica se o evento já foi manipulado.
    var hasBeenHandled = false
        private set

    /**
     * Obtém o conteúdo do evento, ou nulo se o evento já foi manipulado.
     *
     * @return O conteúdo do evento ou nulo se já foi manipulado.
     */
    fun getContentOrNull(): T? {
        return if (hasBeenHandled)
            null
        else {
            hasBeenHandled = true
            content
        }
    }
}
