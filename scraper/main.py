import os
from src.files import read_json_files
from src.db import create_database, insert_surah_data

def main():
    json_dir = './json_files/'

    json_data_list = read_json_files(json_dir)

    db_conn = create_database()

    for surah_data in json_data_list:
        insert_surah_data(db_conn, surah_data)

    db_conn.close()
    print(f"Database populated successfully.")

if __name__ == '__main__':
    main()
