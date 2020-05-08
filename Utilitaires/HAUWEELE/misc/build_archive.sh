#!/usr/bin/env bash
# ! This script only works on GNU/Linux systems !
# This *my own* script to easily make an archive of the project.
# This could also be implemented as a gradle task ;-)

NAME="HAUWEELE"
GRADLE="gradlew"
TEXFILE="./code_rapport/rapport.tex"
PDFGEN="latexmk -pdf"

# Exit the script if any command fails.
set -e

step() {
  echo -e "\e[34;1m$@\e[0m" >&2
}

check_cwd() {
  if [[ $PWD != */misc ]]
  then
    echo -e "\e[31;1mI don't seem to be running from the" \
      "misc/ directory.\e[0m" >&2
    exit 1
  fi
}

gradletest() {
  step "Build and test with gradle. Better be safe than sorry !"
  # The gradle build task actually runs the tests.
  (cd ../ && $GRADLE build)
}

report() {
  step "Generate the report"
  dir="$(dirname $TEXFILE)"
  tex="$(basename $TEXFILE)"
  pdf="${tex%%.tex}.pdf"
  $PDFGEN -quiet -outdir="$dir" "$TEXFILE"
  mv "$dir/$pdf" "../$pdf"
}

clean() {
  step "Clean the repository"
  (cd ../ && $GRADLE clean)
  # Remove unwanted directories.
  find ../ -type d \( -name ".gradle" -o -iname ".DS_Store" \
    -o -iname "__MACOSX" \) \
    -fprintf /dev/stderr "Delete directory %p\n" \
    -print0 | xargs -r0 -- rm -r
  # Remove unwanted files.
  find ../ -type f \( -iname "*.aux" -o -iname "*.toc" -o -iname "*.log" \
    -o -iname "*.out" -o -iname "*.gz" -o -iname "*latexmk" -o -iname "*.fls" \
    -o -iname "*.class" -o -iname "*~" \) \
    -fprintf /dev/stderr "Delete file %p\n" \
    -delete
}

archive() {
  path="../../$NAME.tar.xz"
  step "Create the archive to $path"
  # the --transform is used to put all the files in a directory with the same
  # name as the archive.
  tar --exclude *excl-from-a* --transform "s,^,$NAME/," -cJvf "$path" ..
}

check_cwd
gradletest
report
clean
archive
