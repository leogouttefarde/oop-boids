/*
 * Equipe 32 :
 * DOUMA Nejmeddine - GOUTTEFARDE Léo - KACHER Ilyes
 *
 * TP POO - Simulation Orientée Objet de systèmes multiagents
 * Ensimag 2A MMXV
 */


Les questions 1 à 12 ont été réalisées et testées, elles semblent fonctionnelles.


- src : contient les classes du projet, ces classes sont reparties en différents packages

    -> element
        Boid.java  Lighter.java  Predator.java  Prey.java

    -> event
        AutomatonEvent.java  BoidsEvent.java  EventManager.java  MessageEvent.java
        BallsEvent.java  Event.java  LighterEvent.java

    -> group
        Automaton.java  Boids.java  Immigration.java  Schelling.java
        Balls.java  ExtendedAutomaton.java  Life.java

    -> simulator
        AutomatonSimulator.java  BoidsSimulator.java  LifeSimulator.java
        BallsSimulator.java  ImmigrationSimulator.java  SchellingSimulator.java

    -> test
        TestBalls.java           TestImmigrationSimulator.java
        TestBallsSimulator.java  TestLifeSimulator.java
        TestBoidsSimulator.java  TestPreyPredatorSimulator.java
        TestEventManager.java    TestSchellingSimulator.java
        TestGUI.java

    -> utility
        PVector.java  Triangle.java  Type.java


- bin/gui.jar : archive Java contenant les classes de l'interface graphique.

- rapport.pdf : rapport du projet

- UML.png : contient le graphique UML du projet au format PNG

- doc : contient la javadoc en html (à générer à l'aide du Makefile)


- Makefile :

    -> Générer la doc : make doc  

    -> Lancer la compilation d'un test :
        make testGUI        : TestGUI.java
        make testBalls      : TestBalls.java
        make testBSim       : TestBallsSimulator.java
        make testLifeSim    : TestLifeSimulator.java
        make testImmSim     : TestImmigrationSimulator.java
        make testSSim       : TestSchellingSimulator.java
        make testBoids      : TestBoidsSimulator.java
        make testEvents     : TestEventManager.java
        make testPred       : TestPreyPredatorSimulator.java

    -> Lancer la compilation de tous les tests :
        make all

    -> Lancer l'execution d'un test : 
        make exeGUI         : TestGUI.java
        make exeBalls       : TestBalls.java
        make exeBSim        : TestBallsSimulator.java
        make exeLifeSim     : TestLifeSimulator.java
        make exeImmSim      : TestImmigrationSimulator.java
        make exeSSim        : TestSchellingSimulator.java
        make exeBoids       : TestBoidsSimulator.java
        make exeEvents      : TestEventManager.java
        make exePred        : TestPreyPredatorSimulator.java

    -> Nettoyer le répertoire bin : 
        make clean

