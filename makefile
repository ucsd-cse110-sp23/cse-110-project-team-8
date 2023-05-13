JFLAGS = -cp lib/json-20230227.jar:.
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = src/main/java/cse110/AudioRecorder.java src/main/java/cse110/ChatGPT.java src/main/java/cse110/FileWriter.java src/main/java/cse110/MainUI.java src/main/java/cse110/Whisper.java src/main/java/cse110/WhisperAPI.java src/main/java/cse110/SidebarUI.java src/main/java/cse110/History.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) src/main/java/cse110/*.class
	
run:
	java -cp lib/json-20230227.jar:. src/main/java/cse110/MainUI