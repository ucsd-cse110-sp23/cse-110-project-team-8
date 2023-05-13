ifeq ($(OS),Windows_NT)
	JFLAGS = -cp lib/json-20230227.jar;.
else
	JFLAGS = -cp lib/json-20230227.jar:.
endif

JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = src/main/java/cse110/AudioRecorder.java src/main/java/cse110/ChatGPT.java src/main/java/cse110/FileWriter.java src/main/java/cse110/MainUI.java src/main/java/cse110/Whisper.java src/main/java/cse110/WhisperAPI.java src/main/java/cse110/SidebarUI.java src/main/java/cse110/History.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) src/*.class

ifeq ($(OS),Windows_NT)
run:
	java -cp ../lib/json-20230227.jar;. src/MainUI
else
run:
	java -cp lib/json-20230227.jar:. src/MainUI
endif
