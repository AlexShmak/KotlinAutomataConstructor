package automaton.constructor.model.action.state

import automaton.constructor.model.action.ActionAvailability.AVAILABLE
import automaton.constructor.model.action.ActionAvailability.DISABLED
import automaton.constructor.model.action.createAutomatonElementAction
import automaton.constructor.model.automaton.MealyMooreMachine
import automaton.constructor.model.automaton.copyAndAddState
import automaton.constructor.model.automaton.copyAndAddTransition
import automaton.constructor.model.automaton.getOutgoingTransitionsWithoutLoops
import automaton.constructor.model.element.AutomatonVertex.Companion.RADIUS
import automaton.constructor.model.element.State
import automaton.constructor.model.property.EPSILON_VALUE
import automaton.constructor.utils.I18N
import automaton.constructor.utils.partitionToSets
import tornadofx.*

fun createMealyToMooreElementAction(mealyMooreMachine: MealyMooreMachine) =
    createAutomatonElementAction<MealyMooreMachine, State>(
        automaton = mealyMooreMachine,
        displayName = I18N.messages.getString("AutomatonElementAction.MealyToMoore"),
        getAvailabilityFor = { state ->
            if (getIncomingTransitions(state).any { it.outputValue != EPSILON_VALUE }) {
                AVAILABLE
            } else {
                DISABLED
            }
        },
        performOn = { state ->
            val incomingTransitions = getIncomingTransitions(state)
            val (incomingTransitionsWithoutLoops, loops) = getIncomingTransitions(state).partitionToSets { !it.isLoop() }
            val outgoingTransitionsWithoutLoops = getOutgoingTransitionsWithoutLoops(state)

            val stateOutput = state.outputValue

            val incomingTransitionsGroups = incomingTransitions.groupByTo(mutableMapOf()) { it.outputValue }

            if (state.isInitial && EPSILON_VALUE !in incomingTransitionsGroups)
                incomingTransitionsGroups[EPSILON_VALUE] = mutableListOf()

            var i = if (EPSILON_VALUE in incomingTransitionsGroups) 1 else 0
            val mooreOutputStringsToMooreStates = incomingTransitionsGroups.keys.map { transitionOutput ->
                val mooreState = copyAndAddState(
                    state,
                    newPosition = state.position + (if (transitionOutput == EPSILON_VALUE) 0 else i++) * 4 * RADIUS,
                    newIsInitial = state.isInitial && transitionOutput == EPSILON_VALUE
                ).apply {
                    outputValue = ((transitionOutput?.takeIf { it != EPSILON_VALUE } ?: "") +
                            (stateOutput?.takeIf { it != EPSILON_VALUE } ?: "")).ifEmpty { EPSILON_VALUE }
                }
                transitionOutput to mooreState
            }
            val mooreStates = mooreOutputStringsToMooreStates.map { it.second }
            val incomingTransitionsToTargets =
                mooreOutputStringsToMooreStates.flatMap { (mooreOutputString, mooreState) ->
                    incomingTransitionsGroups.getValue(mooreOutputString).map { it to mooreState }
                }.toMap()

            for (t in incomingTransitionsWithoutLoops) {
                copyAndAddTransition(t, newTarget = incomingTransitionsToTargets.getValue(t)).apply {
                    outputValue = EPSILON_VALUE
                }
            }
            for (mooreState in mooreStates) {
                for (t in loops) {
                    copyAndAddTransition(
                        t,
                        newSource = mooreState,
                        newTarget = incomingTransitionsToTargets.getValue(t)
                    ).apply {
                        outputValue = EPSILON_VALUE
                    }
                }
                for (t in outgoingTransitionsWithoutLoops) {
                    copyAndAddTransition(t, newSource = mooreState)
                }
            }

            removeVertex(state)
        }
    )