rabcasm %~1-0/%~1-0.main.asasm
@echo Parte completada!
abcreplace %~1.swf 0 %~1-0/%~1-0.main.abc
@echo Parte completada!
@echo Criptografado com sucesso!
pause.