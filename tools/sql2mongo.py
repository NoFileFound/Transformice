import sqlite3
import json
import sys
import os

def convert(db_path):
    output = os.path.splitext(db_path)[0] + ".json"
    conn = sqlite3.connect(db_path)
    conn.row_factory = sqlite3.Row
    data = {}
    for table in conn.execute("SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%'").fetchall():
        name = table["name"]
        rows = conn.execute(f'SELECT * FROM "{name}"').fetchall()
        data[name] = [dict(row) for row in rows]

    conn.close()
    with open(output, "w", encoding="utf-8") as file:
        json.dump(data, file, indent=4, ensure_ascii=False)

    print("OK")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: sql2mongo.py <swf_path>")
        sys.exit(1)

    convert(sys.argv[1])