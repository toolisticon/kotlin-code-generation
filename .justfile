mvn-dependency-updates:
  @mvn versions:display-dependency-updates \
    '-Dmaven.version.ignore=(?i).*(alpha|beta|rc|cr|milestone|m[0-9]+|ea).*' \
  | sed -E 's/^\[INFO\][[:space:]]*//' \
  | sed -E 's/[[:space:]]+\.+[[:space:]]+/ - /' \
  | grep -- '->' || true

mvn-plugin-updates:
  @mvn versions:display-plugin-updates \
    '-Dmaven.version.ignore=(?i).*(alpha|beta|rc|cr|milestone|m[0-9]+|ea).*' \
  | sed -E 's/^\[INFO\][[:space:]]*//' \
  | sed -E 's/[[:space:]]+\.+[[:space:]]+/ - /' \
  | grep -- '->' || true

mvn-updates:
  @just mvn-dependency-updates
  @just mvn-plugin-updates

gh-create-issue title:
  @gh issue create --title "{{title}}" --label="Type: dependencies" -m="0.4.1" --body "Version update." -a @me
