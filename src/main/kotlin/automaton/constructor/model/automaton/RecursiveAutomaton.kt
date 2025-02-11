package automaton.constructor.model.automaton

import automaton.constructor.model.action.Action
import automaton.constructor.model.action.transition.EliminateEpsilonTransitionAction
import automaton.constructor.model.automaton.flavours.AutomatonWithInputTape
import automaton.constructor.model.data.RecursiveAutomatonData
import automaton.constructor.model.element.Transition
import automaton.constructor.model.memory.tape.InputTapeDescriptor
import automaton.constructor.utils.I18N

class RecursiveAutomaton(
    override val inputTape: InputTapeDescriptor
) : AbstractAutomaton(
    DISPLAY_NAME,
    memoryDescriptors = listOf(inputTape),
    deterministicAdjective = I18N.messages.getString("RecursiveAutomaton.Deterministic"),
    nondeterministicAdjective = I18N.messages.getString("RecursiveAutomaton.Nondeterministic"),
    untitledAdjective = I18N.messages.getString("RecursiveAutomaton.Untitled")
), AutomatonWithInputTape {
    override fun getTypeData() = RecursiveAutomatonData(
        inputTape = inputTape.getData()
    )

    override val transitionActions: List<Action<Transition>>
        get() = super.transitionActions + EliminateEpsilonTransitionAction(automaton = this)

    override val transformationActions: List<Action<Unit>>
        get() = super.transformationActions

    override fun createEmptyAutomatonOfSameType() = RecursiveAutomaton(inputTape)

    companion object {
        val DISPLAY_NAME: String = I18N.messages.getString("RecursiveAutomaton")
    }
}