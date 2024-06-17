import sqlite3

def create_database(db_name='indopak_mushaf.db'):
    conn = sqlite3.connect(db_name)
    cursor = conn.cursor()

    
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS Surahs (
        surah_id INTEGER PRIMARY KEY,
        number TEXT,
        name TEXT,
        name_latin TEXT,
        number_of_ayah INTEGER
    )
    ''')

    
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS Ayahs (
        ayah_id INTEGER PRIMARY KEY,
        surah_id INTEGER,
        ayah_number INTEGER,
        text TEXT,
        FOREIGN KEY (surah_id) REFERENCES Surahs(surah_id)
    )
    ''')

    conn.commit()
    return conn

def insert_surah_data(conn, surah_data):
    cursor = conn.cursor()
    surah_id = list(surah_data.keys())[0]
    surah_info = surah_data[surah_id]

    
    cursor.execute('''
    INSERT INTO Surahs (surah_id, number, name, name_latin, number_of_ayah)
    VALUES (?, ?, ?, ?, ?)
    ''', (surah_id, surah_info['number'], surah_info['name'], surah_info['name_latin'], surah_info['number_of_ayah']))

    
    surah_id = cursor.lastrowid

    
    for ayah_number, ayah_text in surah_info['text'].items():
        cursor.execute('''
        INSERT INTO Ayahs (surah_id, ayah_number, text)
        VALUES (?, ?, ?)
        ''', (surah_id, int(ayah_number), ayah_text))

    conn.commit()
