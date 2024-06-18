import re

# Define constants for Arabic characters
fathah = '\u064e'
dhamma = '\u064f'
kasra = '\u0650'
ya_hamza = '\u0626'
joining_hamza = '\u0654'
kasratain = '\u064d'
fathatain = '\u064b'
dammatain = '\u064c'
shadda = '\u0651'
space = ' '
nun = '\u0646'
mim = '\u0645'
qaf = '\u0642'
toa = '\u0637'
ba = '\u0628'
zim = '\u062c'
dal = '\u062f'
soad = '\u0635'
zaal = '\u0630'
tha = '\u062b'
kaf = '\u0643'
wow = '\u0648'
shin = '\u0634'
seen = '\u0633'
zha = '\u0632'
fa = '\u0641'
ta = '\u062a'
doad = '\u0636'
zoa = '\u0638'
ra = '\u0631'
lam = '\u0644'
indopak_kaf = '\u06a9'
stop_sign_zim = '\u06da'
stop_sign_lam = '\u06d9'
stop_sign_high_seen = '\u06dc'
stop_sign_mim = '\u06d8'
stop_sign_three_dots = '\u06db'
stop_sign_qaf_lam = '\u06d7'
stop_sign_soad_lam = '\u06d6'
low_seen = '\u06e3'
sukun = '\u0652'
curvy_sukun = '\u06e1'
small_rounded_zero = '\u06df'
low_meem = '\u06ed'
high_meem = '\u06e2'
alif_hamza = '\u0627'
empty_ya = alif_hamza
empty_alif = '\u0649'
another_ya = '\u064a'
ta_marbuta = '\u0647'
supercript_alif_khara_fatha = '\u0670'
small_waw_elongation = "\u06E5"
small_ya_elongation = "\u06E6"
tatweel_empty_letter = "\u0640"
high_small_ya = "\u06E7"
uthmani_stop_signs = stop_sign_three_dots + stop_sign_zim + stop_sign_qaf_lam + stop_sign_soad_lam + stop_sign_lam + stop_sign_mim

# Define regex patterns for different linguistic features
gunnah_pattern = re.compile(f"[{nun}{mim}]{shadda}")
iqlabm_pattern = re.compile(f"[{high_meem}{low_meem}][{sukun}{curvy_sukun}{empty_ya}{empty_alif}]?[{uthmani_stop_signs}]?{space}?{ba}")
qalqala_pattern = re.compile(f"[{qaf}{toa}{ba}{zim}{dal}]({sukun}|{curvy_sukun}|[^{ta_marbuta}]?[^{ta_marbuta}{empty_alif}{empty_ya}]?[^{ta_marbuta}{empty_alif}{alif_hamza}]$)")
idhgham_pattern = re.compile(f"([{nun}{fathatain}{dammatain}{kasratain}][{sukun}{curvy_sukun}{empty_ya}{empty_alif}]?[{uthmani_stop_signs}]?{space}[{nun}{mim}{another_ya}{wow}]{shadda}?)|{mim}[{uthmani_stop_signs}{sukun}{curvy_sukun}]?{space}mim")
idhgham_without_gunnah_pattern = re.compile(f"[{nun}{kasratain}{fathatain}{dammatain}][{sukun}{curvy_sukun}{empty_ya}{empty_alif}]?[{uthmani_stop_signs}]?{space}[{ra}{lam}]")
ikhfa_pattern = re.compile(f"([{nun}{kasratain}{fathatain}{dammatain}][{sukun}{curvy_sukun}{empty_ya}{empty_alif}]?[{uthmani_stop_signs}]?{space}?[{soad}{zaal}{tha}{kaf}{zim}{shin}{qaf}{seen}{dal}{toa}{zha}{fa}{ta}{doad}{zoa}{indopak_kaf}])|{mim}[{sukun}{curvy_sukun}]?{space}?ba")

def set_tajweed(text):
    html_text = text

    # Map regex patterns to hexadecimal color strings
    pattern_to_color = {
        gunnah_pattern: "#FFAAFF",  # Light pink
        qalqala_pattern: "#FFAAFF",  # Red
        iqlabm_pattern: "#FFAAFF",  # Orange
        idhgham_pattern: "#FFAAFF",  # Blue
        idhgham_without_gunnah_pattern: "#FFAAFF",  # Green
        ikhfa_pattern: "#FFAAFF"  # Orange (same as iqlabm_pattern)
    }

    # Apply highlighting for different linguistic features
    for pattern, hex_color in pattern_to_color.items():
        matcher = pattern.finditer(html_text)
        for match in matcher:
            start = match.start()
            end = match.end()
            matched_text = html_text[start:end]
            highlighted_text = f"<span style=color:{hex_color};>{matched_text}</span>"
            html_text = html_text[:start] + highlighted_text + html_text[end:]

    return html_text
