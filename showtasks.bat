call runcrud
if "%ERRORLEVEL%" == "0" goto runbrowser
echo.
echo Cannot run runcrud.bat - breaking work.
goto fail

:runbrowser
start chrome http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVERL%" == "0" goto end
echo.
echo Cannot run browser - breaking work.
goto fail

:fail
echo.
echo There were errors.

:end
echo.
echo Work is finished.