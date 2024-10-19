import csv
import sys

class Profile:
    if len(sys.argv) != 3:
        print("Usage: python profile.py <database.csv> <DNA.txt>")
    sys.exit(1)

    database_file = sys.argv[1]
    dna_file = sys.argv[2]

with open(database_file, "r") as f:
    reader = csv.reader(f)
    header = next(reader)  # Skip header row
    profiles = []
    for row in reader:
        name = row[0]
        str_counts = {str_name: int(value) for str_name, value in zip(header[1:], row[1:])}
        profiles.append((name, str_counts))

        with open(dna_file, "r") as f:
            dna_sequence = f.read().strip()


    str_repeats = {}
for str_name in header[1:]:
    longest_repeat = 0
    current_repeat = 0
    for i in range(len(dna_sequence) - len(str_name) + 1):
        if dna_sequence[i:i+len(str_name)] == str_name:
            current_repeat += 1
            if current_repeat > longest_repeat:
                longest_repeat = current_repeat
        else:
            current_repeat = 0
    str_repeats[str_name] = longest_repeat


    for name, profile in profiles:
        match = True
    for str_name, repeat_count in str_repeats.items():
        if profile[str_name] != repeat_count:
            match = False
            break
    if match:
        print(name)
        break
else:
    print("No match")