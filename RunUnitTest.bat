@set class_path=.;bin\;lib\swt.jar;lib\junit.jar;
@set test_tool=junit.swingui.TestRunner
@set test=tests.AllTests
java -cp %class_path% %test_tool% %test%
@pause