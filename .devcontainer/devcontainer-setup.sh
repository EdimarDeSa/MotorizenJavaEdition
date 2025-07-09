#!/bin/bash
export DEV_CONTAINER=1
export GITHUB_USERNAME="EdimarDeSa"

if [ -f "/workspaces/BusinessCaseMngs/frontend/package.json" ]; then
  cd /workspaces/BusinessCaseMngs/frontend
  npm install
fi

chezmoi init --apply $GITHUB_USERNAME

echo "export DEV_CONTAINER=1" >> ~/.config/.exports
echo "alias frontend='cd /workspaces/BusinessCaseMngs/frontend/'" > ~/.config/.aliases
echo "alias backend='cd /workspaces/BusinessCaseMngs/backend/'" > ~/.config/.aliases