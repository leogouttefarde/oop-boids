# Ensimag 2A POO - TP 2015/16
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     Pour un package (ici gui.jar), il est aussi dans bin.
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testGUI testBalls testBSim testLifeSim testImmSim testSSim testBoids testBoids testEvents testPred

testGUI:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/test/TestGUI.java

testBalls:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/test/TestBalls.java

testBSim:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/test/TestBallsSimulator.java

testLifeSim:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/test/TestLifeSimulator.java

testImmSim:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/test/TestImmigrationSimulator.java

testSSim:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/test/TestSchellingSimulator.java

testBoids:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/test/TestBoidsSimulator.java

testEvents:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/test/TestEventManager.java

testPred:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/test/TestPreyPredatorSimulator.java

doc:
	javadoc -classpath bin/gui.jar -encoding "UTF-8" -docencoding "UTF-8" -charset "UTF-8" -d ./doc -sourcepath ./src -subpackages element event group simulator test utility

# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin TestGUI
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeIHM
exeGUI: testGUI
	java -classpath bin:bin/gui.jar test/TestGUI

exeBalls: testBalls
	java -classpath bin:bin/gui.jar test/TestBalls

exeBSim: testBSim
	java -classpath bin:bin/gui.jar test/TestBallsSimulator

exeLifeSim: testLifeSim
	java -classpath bin:bin/gui.jar test/TestLifeSimulator

exeImmSim: testImmSim
	java -classpath bin:bin/gui.jar test/TestImmigrationSimulator

exeSSim: testSSim
	java -classpath bin:bin/gui.jar test/TestSchellingSimulator

exeBoids: testBoids
	java -classpath bin:bin/gui.jar test/TestBoidsSimulator

exeEvents: testEvents
	java -classpath bin:bin/gui.jar test/TestEventManager

exePred: testPred
	java -classpath bin:bin/gui.jar test/TestPreyPredatorSimulator

clean:
	rm -rf bin/*/*.class

run: exePred


