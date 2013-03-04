all :
	javac shapes/*.java;
	javac *.java;

run: all
	java MyGame
