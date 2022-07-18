package automaton.constructor.model.automaton

import automaton.constructor.model.data.MealyMooreMachineData
import automaton.constructor.model.memory.output.MealyMooreOutputTapeDescriptor
import automaton.constructor.model.memory.tape.InputTapeDescriptor
import automaton.constructor.utils.I18N.messages

/**
 * Mealy/Moore machine.
 *
 * It's an automaton with an [input tape][inputTape] and a [Mealy/Moore output tape][mealyMooreOutputTape] as [memory descriptors][memoryDescriptors].
 */
class MealyMooreMachine(
    val inputTape: InputTapeDescriptor,
    val mealyMooreOutputTape: MealyMooreOutputTapeDescriptor
) : AbstractAutomaton(DISPLAY_NAME, memoryDescriptors = listOf(inputTape, mealyMooreOutputTape)) {
    override fun getTypeData() = MealyMooreMachineData(
        inputTape = inputTape.getData(),
        mealyMooreOutputTape = mealyMooreOutputTape.getData()
    )

    companion object {
        val DISPLAY_NAME: String = messages.getString("MealyMooreMachine")
    }
}
