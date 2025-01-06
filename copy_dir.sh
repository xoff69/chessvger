#!/bin/bash

# Vérifie que les arguments source et destination sont fournis
if [ "$#" -ne 2 ]; then
    echo "Usage: $0 source_directory destination_directory"
    exit 1
fi

SOURCE_DIR="$1"
DEST_DIR="$2"

# Vérifie que le répertoire source existe
if [ ! -d "$SOURCE_DIR" ]; then
    echo "Le répertoire source n'existe pas : $SOURCE_DIR"
    exit 1
fi

# Crée le répertoire de destination s'il n'existe pas
mkdir -p "$DEST_DIR"

# Copie les fichiers en excluant '.git' et 'node_modules'
find "$SOURCE_DIR" -type d \( -name '.git' -o -name 'node_modules' -o -name 'build' -o -name 'bin' -o -name 'out' -o -name 'generated' \) -prune -o -print | while read FILE; do
    if [ -d "$FILE" ]; then
        mkdir -p "$DEST_DIR/${FILE#$SOURCE_DIR/}"
    else
        cp "$FILE" "$DEST_DIR/${FILE#$SOURCE_DIR/}"
    fi
done

# Affichage d'un message de confirmation
echo "Folder $SOURCE_DIR copied."
