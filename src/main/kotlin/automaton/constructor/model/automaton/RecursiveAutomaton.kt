package automaton.constructor.model.automaton

import automaton.constructor.model.data.AutomatonTypeData
import automaton.constructor.model.memory.tape.InputTapeDescriptor
import automaton.constructor.utils.I18N

class RecursiveAutomaton(
    val inputTape: InputTapeDescriptor
): AbstractAutomaton(
    DISPLAYNAME,
    memoryDescriptors = listOf(inputTape),
    deterministicAdjective = I18N.messages.getString("RecursiveAutomaton.Deterministic"),
    nondeterministicAdjective = I18N.messages.getString("RecursiveAutomaton.Nondeterministic"),
    untitledAdjective = I18N.messages.getString("RecursiveAutomaton.Untitled")
) {
    override fun getTypeData(): AutomatonTypeData {
        TODO("Not yet implemented")
    }

    override fun createEmptyAutomatonOfSameType(): Automaton {
        TODO("Not yet implemented")
    }

    companion object{
        val DISPLAYNAME = I18N.messages.getString("RecursiveAutomaton")
    }
}