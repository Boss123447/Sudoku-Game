Remove-Item -Recurse -Force .\out -ErrorAction SilentlyContinue
javac -d out (Get-ChildItem .\src\sudoku\*.java).FullName
java -cp out sudoku.Main