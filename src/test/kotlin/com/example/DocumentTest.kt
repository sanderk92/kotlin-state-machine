package com.example

import com.example.DocumentState.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DocumentTest {

    @Test
    fun `Document has correct default value`() {
        val document = Document(emptyList())

        assertThat(document.state).isEqualTo(New)
    }

    @Test
    fun `Document remains in the same state when nothing changes`() {
        val document = Document(emptyList())

        val result = document.updateState()

        assertThat(result.state).isEqualTo(New)
    }

    @Test
    fun `Document transitions through states correctly`() {
        val firstDocument = Document(listOf(1, 2))
        val firstResult = firstDocument.updateState()

        val secondDocument = firstResult.copy(listOf(1, 2, 3, 4, 5))
        val secondResult = secondDocument.updateState()

        assertThat(firstResult.state).isEqualTo(Open)
        assertThat(secondResult.state).isEqualTo(Closed)
    }
}