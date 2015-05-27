@set class_path=lib\swt.jar;lib\junit.jar;
@set bin_dir=bin
@set src_dir=src
@set source=javasources.txt
@REM clean up
@if exist %bin_dir% (
	@del /q %bin_dir%\*.*
	echo deleting existing file...
) else (
	@mkdir %bin_dir%
)

@pushd %src_dir%
@dir /s /b *.java> ../%source%
@popd
@javac -cp %class_path% -d %bin_dir%\ @%source%
@echo completed...
