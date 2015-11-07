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

all: testGUI 

testGUI:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestGUI.java

testBSim:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestBallsSimulator.java

testLifeSim:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestLifeSimulator.java

testImmSim:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestImmigrationSimulator.java

testSSim:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestSchellingSimulator.java


# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin TestGUI
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeIHM
exeGUI: testGUI
	java -classpath bin:bin/gui.jar TestGUI

exeBSim: testBSim
	java -classpath bin:bin/gui.jar TestBallsSimulator

exeLifeSim: testLifeSim
	java -classpath bin:bin/gui.jar TestLifeSimulator

exeImmSim: testImmSim
	java -classpath bin:bin/gui.jar TestImmigrationSimulator

exeSSim: testSSim
	java -classpath bin:bin/gui.jar TestSchellingSimulator

clean:
	rm -rf bin/*.class

