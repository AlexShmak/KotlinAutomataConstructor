package automaton.constructor.model.factory

fun getAllAutomatonFactories() = listOf<AutomatonFactory>(
    FiniteAutomatonFactory(),
    RecursiveAutomatonFactory(),
    PushdownAutomatonFactory(),
    RegisterAutomatonFactory(),
    MealyMooreMachineFactory(),
    TuringMachineFactory(),
    MultiTrackTuringMachineFactory(),
    MultiTapeTuringMachineFactory(),
    TuringMachineWithRegistersFactory()
)
