package automaton.constructor.model.automaton

import automaton.constructor.model.data.RegisterAutomatonData
import automaton.constructor.model.memory.RegisterDescriptor
import automaton.constructor.model.memory.tape.InputTapeDescriptor

/**
 * Register automaton.
 *
 * It's an automaton with an [input tape][inputTape] and several [registers] as [memory descriptors][memoryDescriptors].
 */
class RegisterAutomaton(
    val inputTape: InputTapeDescriptor,
    val registers: List<RegisterDescriptor>
) : AbstractAutomaton(NAME, memoryDescriptors = listOf(inputTape) + registers) {
    init {
        require(registers.isNotEmpty()) {
            "Illegal `registers` argument when creating `RegisterAutomaton`"
        }
    }

    override fun getTypeData() = RegisterAutomatonData(
        inputTape = inputTape.getData(),
        registers = registers.map { it.getData() }
    )

    companion object {
        const val NAME = "register automaton"
    }
}
