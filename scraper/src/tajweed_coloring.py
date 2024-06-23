import re

blue = '#34AADC'       
green = '#4CD964'      
red = '#FF3B30'        
yellow = '#FFCC00'     
orange = '#FF9500'     
purple = '#5856D6'     
light_green = '#7ED321'
grey = '#8E8E93'       

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

indopak_stop_sign_three_dots = '\u06DB'  # ARABIC SMALL HIGH THREE DOTS
indopak_stop_sign_circle = '\u06DD'      # ARABIC END OF AYAH (used for circular stop)
indopak_stop_sign_mim = '\u06D1'         # ARABIC SMALL HIGH LIGATURE QAF WITH LAM WITH ALEF MAKSURA (used for Mim)
indopak_stop_sign_lam_alif = '\u06E0'    # ARABIC SMALL HIGH LIGATURE QAF WITH LAM WITH ALEF MAKSURA (used for Lam-Alef)
indopak_stop_sign_toa = '\u06E2'         # ARABIC SMALL HIGH TO WITH INVERTED V (used for Toa)
indopak_stop_sign_jeem = '\u06E3'        # ARABIC SMALL HIGH JEEM (used for Jeem)

indopak_stop_signs = indopak_stop_sign_three_dots + indopak_stop_sign_circle + indopak_stop_sign_mim + indopak_stop_sign_lam_alif + indopak_stop_sign_toa + indopak_stop_sign_jeem
all_stop_signs = uthmani_stop_signs + indopak_stop_signs


arabic_small_high_sign_safha = '\u08D1'
arabic_small_high_sign_shadda_with_damma = '\u08D2'
arabic_small_high_sign_shadda_with_kasra = '\u08D3'
arabic_small_high_four_dots_above = '\u08D4'
arabic_small_high_letter_alef = '\u08D5'
arabic_small_high_ligature_sad_with_lam_with_alef_maksura = '\u08D6'
arabic_small_high_ligature_qaf_with_lam_with_alef_maksura = '\u08D7'

gunnah_pattern = re.compile(f"[{nun}{mim}]{shadda}")
iqlabm_pattern = re.compile(f"[{high_meem}{low_meem}][{sukun}{curvy_sukun}{empty_ya}{empty_alif}]?[{all_stop_signs}]?{space}?{ba}")
qalqala_pattern = re.compile(f"[{qaf}{toa}{ba}{zim}{dal}]({sukun}|{curvy_sukun}|[^{ta_marbuta}]?[^{ta_marbuta}{empty_alif}{empty_ya}]?[^{ta_marbuta}{empty_alif}{alif_hamza}]$)")
idhgham_pattern = re.compile(f"([{nun}{fathatain}{dammatain}{kasratain}][{sukun}{curvy_sukun}{empty_ya}{empty_alif}]?[{all_stop_signs}]?{space}[{nun}{mim}{another_ya}{wow}]{shadda}?)|{mim}[{all_stop_signs}{sukun}{curvy_sukun}]?{space}mim")
idhgham_without_gunnah_pattern = re.compile(f"[{nun}{kasratain}{fathatain}{dammatain}][{sukun}{curvy_sukun}{empty_ya}{empty_alif}]?[{all_stop_signs}]?{space}[{ra}{lam}]")
ikhfa_pattern = re.compile(f"([{nun}{kasratain}{fathatain}{dammatain}][{sukun}{curvy_sukun}{empty_ya}{empty_alif}]?[{all_stop_signs}]?{space}?[{soad}{zaal}{tha}{kaf}{zim}{shin}{qaf}{seen}{dal}{toa}{zha}{fa}{ta}{doad}{zoa}{indopak_kaf}])|{mim}[{sukun}{curvy_sukun}]?{space}?ba")

def set_tajweed(text):
    html_text = text

    # Map regex patterns to hexadecimal color strings
    pattern_to_color = {
        gunnah_pattern: orange,  # Light pink
        qalqala_pattern: red,  # Red
        iqlabm_pattern: grey,  # Orange
        idhgham_pattern: green,  # Blue
        idhgham_without_gunnah_pattern: light_green,  # Green
        ikhfa_pattern: blue,  # Orange (same as iqlabm_pattern)
    }

    output = []
    last_end = 0

    # Apply highlighting for different linguistic features
    for pattern, hex_color in pattern_to_color.items():
        for match in pattern.finditer(text):
            start, end = match.span()
            output.append(text[last_end:start])
            output.append(f'<span style="color:{hex_color};">{text[start:end]}</span>')
            last_end = end

    output.append(text[last_end:])
    html_text = ''.join(output)

    return add_space_after_stop_signs(html_text, all_stop_signs)

def add_space_after_stop_signs(text, stop_signs):
    for sign in stop_signs:
        text = text.replace(sign, sign + ' ')

    return text