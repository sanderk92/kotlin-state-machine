package com.statemachine

import java.lang.IllegalStateException
import kotlin.reflect.KProperty1

fun <Subject, State> calculate(
    subject: Subject,
    property: KProperty1<Subject, State>,
    block: StateMachine<Subject, State>.(Subject) -> Unit
): State {
    val machine = StateMachine(subject, property)
    machine.block(subject)
    return machine.getNewState()
}

data class Transition<State>(
    val from: State,
    val to: State,
)

class StateMachine<Subject, State>(
    private val subject: Subject,
    private val property: KProperty1<Subject, State>,
) {
    private val transitions = mutableMapOf<Transition<State>, () -> Boolean>()

    infix fun State.to(other: State) =
        Transition(this, other)

    infix fun Transition<State>.after(predicate: () -> Boolean) {
        transitions += this to predicate
    }

    fun getNewState(): State {
        val currentState = property.get(subject)

        val applicableStates = transitions
            .filter { (transition, predicate) -> currentState == transition.from && predicate() }
            .map { it.key.to }

        return when (applicableStates.size) {
            0 -> currentState
            1 -> applicableStates.first()
            else -> throw IllegalStateException(
                "Invalid number of state transitions applicable, from '$currentState' to $applicableStates"
            )
        }
    }
}
