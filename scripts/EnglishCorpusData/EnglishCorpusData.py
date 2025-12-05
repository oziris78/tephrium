# Copyright 2024-2025 Oğuzhan Topaloğlu
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


import os


CSV_PATH = os.path.join(os.path.dirname( __file__ ), 'words_pos.csv')
OUT_PATH = os.path.join(os.path.dirname( __file__ ), 'EnglishCorpusData.java')

sp = lambda i: " " * i


def len_st_with(words: list[str], initial: str):
    i = 0
    for word in words:
        if word.startswith(initial):
            i += 1
    return i


def valid_word_tuple(word_tuple) -> bool:
    word = word_tuple[0]
    tag = word_tuple[1]

    if tag in ['VBN', 'VBG', 'VBD', 'NNS']: return False
    if len(word) < 4: return False
    if len(word) > 7: return False

    for c in word:
        if c not in 'abcdefghijklmnopqrstuvwxyz':
            return False
    
    last_c = ''
    for c in word:
        if (c == last_c) and (c in "aeiou"):
            return False
        last_c = c

    vowel_count = 0
    for c in word:
        if c in "aeiou":
            vowel_count += 1
            if vowel_count >= 3:
                return False
        else:
            vowel_count = 0

    return True



# Read the CSV file's lines
with open(CSV_PATH, "r") as csv_file:
    lines = csv_file.read().split('\n')
    lines = lines[1:]
    lines = [line for line in lines if line.strip() != ""]


# Parse the CSV file
words = [(line.split(',')[1], line.split(',')[2]) for line in lines]
words = [wtuple[0] for wtuple in words if valid_word_tuple(wtuple)]

print(f"Word count: {len(words)}")
words.sort()



# Generate EnglishCorpusData.java
with open(OUT_PATH, "a+", encoding="utf-8") as file:
    file.seek(0)
    file.truncate(0)

    file.write("class EnglishCorpusData {\n\n")
    file.write(sp(4) + f"static final int TOTAL_WORD_COUNT = {len(words)};\n\n")

    for letter in 'abcdefghijklmnopqrstuvwxyz':
        class_name = f"CP_{letter.upper()}"
        words_for_letter = [word for word in words if word.startswith(letter)]
        file.write(sp(4) + f"static class {class_name} {{\n")
        file.write(sp(8) + f"static final int L = {len_st_with(words, letter)};\n")
        file.write(sp(8) + f"static final String[] W = new String[] {{")
        if words_for_letter:
            file.write(f'"{words_for_letter[0]}"')
            for word in words_for_letter[1:]:
                file.write(f', "{word}"')
        file.write("};\n")
        file.write(sp(4) + "}\n\n")

    file.write('\n}\n')