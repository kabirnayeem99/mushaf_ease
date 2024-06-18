import os
from src.files import read_json_files
from src.db import create_database, insert_surah_data
from src.api_client import fetch_verses_by_surah

def main():
    json_dir = './json_files/'

    json_data_list = read_json_files(json_dir)

    db_conn = create_database()

    for surah_data in json_data_list:
        surah_id = list(surah_data.keys())[0]
        surah_info = surah_data[surah_id]
        versesApiList = fetch_verses_by_surah(surah_id, surah_info["name_latin"], int(surah_info["number_of_ayah"])) 
        insert_surah_data(db_conn, surah_id,  surah_info, versesApiList)

    db_conn.close()
    print(f"Database populated successfully.")

if __name__ == '__main__':
    main()
