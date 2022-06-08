package com.example

import com.example.DocumentState.*
import com.statemachine.calculate

data class Document(
    val content: List<Int>,
    val state: DocumentState = New,
) {
    fun updateState(): Document = this.copy(

        state = calculate(this, Document::state) {

            New to Open after {
                it.content.isNotEmpty()
            }

            Open to Closed after {
                it.content.size >= 5
            }
        }
    )
}

enum class DocumentState {
    New,
    Open,
    Closed
}
