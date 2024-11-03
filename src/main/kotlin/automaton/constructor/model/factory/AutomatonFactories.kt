package automaton.constructor.model.factory

fun getAllAutomatonFactories() = listOf<AutomatonFactory>(
    FiniteAutomatonFactory(),
    PushdownAutomatonFactory(),
    RegisterAutomatonFactory(),
    RecursiveAutomatonFactory(),
    MealyMooreMachineFactory(),
    TuringMachineFactory(),
    MultiTrackTuringMachineFactory(),
    MultiTapeTuringMachineFactory(),
    TuringMachineWithRegistersFactory()
)
