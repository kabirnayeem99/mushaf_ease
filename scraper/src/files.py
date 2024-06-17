import os
import json

def read_json_files(json_dir):
    json_data_list = []
    for filename in os.listdir(json_dir):
        if filename.endswith('.json'):
            file_path = os.path.join(json_dir, filename)
            with open(file_path, 'r', encoding='utf-8') as f:
                surah_data = json.load(f)
                json_data_list.append(surah_data)
    return json_data_list
