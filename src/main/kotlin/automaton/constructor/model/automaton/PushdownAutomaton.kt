package automaton.constructor.model.automaton

import automaton.constructor.model.data.PushdownAutomatonData
import automaton.constructor.model.memory.StackDescriptor
import automaton.constructor.model.memory.tape.InputTapeDescriptor

/**
 * Pushdown automaton.
 *
 * It's an automaton with an [input tape][inputTape] and several [stacks] as [memory descriptors][memoryDescriptors].
 */
class PushdownAutomaton(
    val inputTape: InputTapeDescriptor,
    val stacks: List<StackDescriptor>
) : AbstractAutomaton(NAME, memoryDescriptors = listOf(inputTape) + stacks) {
    init {
        require(stacks.isNotEmpty()) {
            "Illegal `stacks` argument when creating `PushdownAutomaton`"
        }
    }

    override fun getTypeData() = PushdownAutomatonData(
        inputTape = inputTape.getData(),
        stacks = stacks.map { it.getData() }
    )

    companion object {
        const val NAME = "pushdown automaton"
    }
}
