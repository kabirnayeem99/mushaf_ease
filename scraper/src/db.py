import re
import sqlite3
import os
from src.tajweed_coloring import set_tajweed

def create_database(db_path='generated/indopak_mushaf.db'):
    # Ensure the directory exists
    os.makedirs(os.path.dirname(db_path), exist_ok=True)

    conn = sqlite3.connect(db_path)
    cursor = conn.cursor()

    cursor.execute('''
    DROP TABLE IF EXISTS Surahs
    ''')

    cursor.execute('''
    DROP TABLE IF EXISTS Ayahs
    ''')

    cursor.execute('''
    DROP TABLE IF EXISTS Ayahs_content
    ''')
    
    cursor.execute('''
    DROP TABLE IF EXISTS Ayahs_docsize
    ''')

    cursor.execute('''
    DROP TABLE IF EXISTS Ayahs_segments
    ''')

    cursor.execute('''
    DROP TABLE IF EXISTS Ayahs_stat
    ''')

    cursor.execute("PRAGMA foreign_keys = ON")

    # Create Surahs table
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS Surahs (
        surah_id INTEGER PRIMARY KEY,
        number TEXT,
        name TEXT,
        name_latin TEXT,
        number_of_ayah INTEGER
    )
    ''')

    # Create Ayahs table with full-text search enabled
    cursor.execute('''
    CREATE VIRTUAL TABLE IF NOT EXISTS Ayahs 
    USING FTS4 (
        ayah_id TEXT PRIMARY KEY,
        surah_id INTEGER,
        ayah_number INTEGER,
        text TEXT,
        text_nodiactric TEXT, 
        text_tajweed TEXT, 
        FOREIGN KEY (surah_id) REFERENCES Surahs(surah_id)
    )
    ''')

    conn.commit()
    return conn

def insert_surah_data(conn, surah_data):
    cursor = conn.cursor()
    surah_id = list(surah_data.keys())[0]
    surah_info = surah_data[surah_id]

    # Insert into Surahs table
    cursor.execute('''
    INSERT INTO Surahs (surah_id, number, name, name_latin, number_of_ayah)
    VALUES (?, ?, ?, ?, ?)
    ''', (surah_id, surah_info['number'], surah_info['name'], surah_info['name_latin'], surah_info['number_of_ayah']))

    # Get last inserted surah_id
    surah_id = cursor.lastrowid

    # Insert ayahs into Ayahs table
    for ayah_number, ayah_text in surah_info['text'].items():
        cursor.execute('''
        INSERT INTO Ayahs (ayah_id, surah_id, ayah_number, text, text_nodiactric, text_tajweed)
        VALUES (?, ?, ?, ?, ?, ?)
        ''', (str(surah_id) + "-"+ str(ayah_number),surah_id, int(ayah_number), ayah_text, remove_diacritics(ayah_text), set_tajweed(ayah_text)))

    conn.commit()

arabic_diacritics = re.compile(r"""
                     ّ    | # Tashdid
                     َ    | # Fatha
                     ً    | # Tanwin Fath
                     ُ    | # Damma
                     ٌ    | # Tanwin Damm
                     ِ    | # Kasra
                     ٍ    | # Tanwin Kasr
                     ْ    | # Sukun
                     ـ     # Tatwil/Kashida
                     | ۙ   # Specific sign رِۙ
                     | ۖ   # Small Semicircle
                     | ۗ   # Small Circle
                     | ۘ   # Slightly Above
                     | ۚ   # High Hamza
                     | ۛ   # Low Hamza
                     | ۜ   # Full Stop
                     | ۞   # Circle with Dot
                     | ۟   # Small V
                     | ۠   # High Maddah
                     | ۡ   # High Hamza
                     | ۢ   # Low Hamza
                     | ۣ   # Small Waw
                     | ۤ   # Small Yeh
                     | ۥ   # Small High Noon
                     | ۦ   # High Noon
                     | ۧ   # Low Noon
                     | ۨ   # Small High Meem
                     | ۩   # Circle with Two Dots
                 """, re.VERBOSE)


def normalize_arabic(text):
    # Normalize basic Arabic characters
    text = re.sub("[إأآا]", "ا", text)
    text = re.sub("ى", "ی", text)  # Persian/Urdu 'ye'
    text = re.sub("ؤ", "ء", text)
    text = re.sub("ئ", "ء", text)
    text = re.sub("ة", "ہ", text)  # Urdu 'heh'
    text = re.sub("گ", "گ", text)  # Urdu 'ge'
    
    # Normalize ligatures and special characters
    text = re.sub("ﻻ", "لا", text)  # Arabic/Persian 'laam-alef'
    text = re.sub("ﷲ", "الله", text)  # Allah ligature
    
    return text



def remove_diacritics(text):
    text = normalize_arabic(text)
    text = re.sub(arabic_diacritics, '', text)
    return text

