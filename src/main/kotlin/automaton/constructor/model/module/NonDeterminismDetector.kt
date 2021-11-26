package automaton.constructor.model.module

import automaton.constructor.model.Automaton
import automaton.constructor.model.transition.Transition
import automaton.constructor.utils.filteredSet
import javafx.beans.binding.Bindings.isEmpty
import javafx.beans.binding.Bindings.size
import javafx.beans.binding.BooleanBinding
import javafx.collections.SetChangeListener
import tornadofx.*

val nonDeterminismDetectorFactory = { automaton: Automaton -> NonDeterminismDetector(automaton) }
val Automaton.nonDeterminismDetector get() = getModule(nonDeterminismDetectorFactory)
val Automaton.nonDeterministicStates get() = nonDeterminismDetector.nonDeterministicStates
val Automaton.isDeterministicBinding get() = nonDeterminismDetector.isDeterministicBinding
val Automaton.isDeterministic get() = nonDeterminismDetector.isDeterministic

class NonDeterminismDetector(automaton: Automaton) : AutomatonModule {
    val nonDeterministicStates = automaton.states.filteredSet { state ->
        val isNonDeterministic = true.toProperty()
        val transitions = automaton.getTransitions(state)
        fun recheckDeterminism() {
            isNonDeterministic.value = transitions.any { transition1 ->
                transitions.any { transition2 ->
                    transition1 !== transition2 && transition1.filters.map { it.value }
                        .zip(transition2.filters.map { it.value })
                        .all { (v1, v2) -> v1 == null || v2 == null || v1 == v2 }
                }
            }
        }
        recheckDeterminism()

        fun registerTransition(transition: Transition) {
            transition.filters.forEach { it.onChange { recheckDeterminism() } }
        }
        transitions.forEach { registerTransition(it) }
        transitions.addListener(SetChangeListener {
            if (it.wasAdded()) registerTransition(it.elementAdded)
            recheckDeterminism()
        })
        isNonDeterministic
    }
    val isDeterministicBinding: BooleanBinding =
        isEmpty(nonDeterministicStates).and(size(automaton.initialStates).booleanBinding {
            automaton.initialStates.size <= 1
        })
    val isDeterministic by isDeterministicBinding
}
