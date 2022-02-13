package automaton.constructor.model.module

import automaton.constructor.model.Automaton
import javafx.beans.value.ObservableValue
import tornadofx.*
import java.util.*

val automatonDescriptionProviderFactory = { automaton: Automaton -> AutomatonDescriptionProvider(automaton) }
val Automaton.automatonDescriptionProvider get() = getModule(automatonDescriptionProviderFactory)
val Automaton.descriptionBinding get() = automatonDescriptionProvider.descriptionBinding

class AutomatonDescriptionProvider(val automaton: Automaton) : AutomatonModule {
    private val determinicityPartBinding = stringBinding(automaton.isDeterministicBinding) {
        if (value) "deterministic" else "nondeterministic"
    }
    private val determinicityPart: String by determinicityPartBinding

    private val epsilonPartBinding: ObservableValue<String> =
        if (automaton.memoryDescriptors.any { memoryUnitDescriptor ->
                (memoryUnitDescriptor.transitionFilters + memoryUnitDescriptor.transitionSideEffects
                        + memoryUnitDescriptor.stateFilters + memoryUnitDescriptor.stateSideEffects)
                    .any { it.canBeDeemedEpsilon }
            }) stringBinding(automaton.hasEpsilonBinding) { (if (value) "with" else "without") + " epsilon transitions" }
        else "".toProperty()
    private val epsilonPart: String by epsilonPartBinding

    val descriptionBinding = stringBinding(determinicityPartBinding, epsilonPartBinding) {
        listOf(determinicityPart, automaton.typeName, epsilonPart).filter { it.isNotEmpty() }
            .joinToString(" ").replaceFirstChar { it.titlecase(Locale.getDefault()) }
    }
}
