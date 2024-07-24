# FAQ:
***
1. *What python version should I use and where to download it?*
> **https://www.python.org/downloads/release/python-380/**
***
2. **I see | ModuleNotFoundError: No module named {x} | What does it mean?**
> It means you don't installed required module. Install it using **pip install {x}**
***
3. **What operation system should I use?**
> Windows 10 as mine. (Microsoft Windows Version 22h2 19045.4529)
***
4. **I see | Can't connect to MySQL server on localhost:3306 | What does it mean?**
> You did not start the mysql service. (Use xampp). Another reasons is that mysql port does not match.
***
5. **I see | sqlite3.ProgrammingError: SQLite objects created in a thread can only be used in that same thread.**
> Disable usage of multiple threads like this **sqlite3.connect('your.db', check_same_thread=False)**
***
6. **I see | Mysql Row size too large | What does it mean?**
> https://stackoverflow.com/questions/15585602/change-limit-for-mysql-row-size-too-large
***
7. **I see | FileNotFoundError: [Errno 2] No such file or directory: | What does it mean?**
> The file is not found, please recreate it.
***
8. **Does it support linux?**
> I guess, just implement colorconsole module because uses WinAPI.
***
9. **I see | configparser.NoOptionError: No option {x} in section: {y} | What does it mean?**
> Open the properties file and insert the option in given section.
***
10. **I see | pymysql.err.OperationalError: (1049, "Unknown database {x}") | What does it mean?**
> Create the database in phpmyadmin. (127.0.0.1:80/phpmyadmin)
***
11. **I see | KeyError/IndexError | What does it mean?**
> It means the server access illegal way. Possible errors may appear in database or config.
