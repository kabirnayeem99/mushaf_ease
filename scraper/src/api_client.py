import time
import random
import math
import requests
import requests_cache

class QuranVerse:
    def __init__(self, id, verse_number, verse_key, hizb_number, rub_el_hizb_number, ruku_number,
                 manzil_number, sajdah_number, page_number, juz_number):
        self.id = id
        self.verse_number = verse_number
        self.verse_key = verse_key
        self.hizb_number = hizb_number
        self.rub_el_hizb_number = rub_el_hizb_number
        self.ruku_number = ruku_number
        self.manzil_number = manzil_number
        self.sajdah_number = sajdah_number
        self.page_number = page_number
        self.juz_number = juz_number

def fetch_verses_by_surah(surah_number, surah_name, surah_ayat):
    base_url = f"https://api.quran.com/api/v4/verses/by_chapter/{surah_number}"
    per_page = adjust_pagination(50, surah_ayat)
    current_page = 1
    total_records = None
    verses = []
    session = requests_cache.CachedSession('by_chapter_cache')

    while True:
        url = f"{base_url}?per_page={per_page}&page={current_page}"
        response = session.get(url, headers={'Accept': 'application/json'})

        if response.status_code == 200:
            data = response.json()
            if not total_records:
                total_records = data['pagination']['total_records']
                print(f"📜 Total verses in {surah_number} - Surah {surah_name}: {total_records}")
                print(f"📖 Total pages in Surah {surah_name}: {math.ceil(total_records/per_page)}")

            current_page = data['pagination']['current_page']
            next_page = data['pagination']['next_page']

            # Parse verses data into QuranVerse objects
            for verse_data in data['verses']:
                verse = QuranVerse(
                    id=verse_data['id'],
                    verse_number=verse_data['verse_number'],
                    verse_key=verse_data['verse_key'],
                    hizb_number=verse_data['hizb_number'],
                    rub_el_hizb_number=verse_data['rub_el_hizb_number'],
                    ruku_number=verse_data['ruku_number'],
                    manzil_number=verse_data['manzil_number'],
                    sajdah_number=verse_data['sajdah_number'],
                    page_number=verse_data['page_number'],
                    juz_number=verse_data['juz_number'],
                )
                verses.append(verse)

            print(f"✅️ Fetched page {current_page}/{data['pagination']['total_pages']}")
            if next_page:
                current_page = next_page
            else:
                break

            delay = random.uniform(0.5, 2.0)
            time.sleep(delay)
        else:
            print(f"❌ Failed to fetch data. Status code: {response.status_code}")
            break
    return verses

def adjust_pagination(per_page, total_records):
    if total_records > per_page:
        return per_page
    else:
        return total_records