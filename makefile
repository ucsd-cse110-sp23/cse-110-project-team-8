ifeq ($(OS),Windows_NT)
	JFLAGS = -cp lib/json-20230227.jar;.
else
	JFLAGS = -cp lib/json-20230227.jar:.
endif

JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = src/AudioRecorder.java src/ChatGPT.java src/FileWriter.java src/MainUI.java src/Whisper.java src/WhisperAPI.java src/SidebarUI.java src/History.java

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