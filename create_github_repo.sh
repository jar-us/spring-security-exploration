#!/bin/bash

# --- Configuration ---
# Uses the current directory name as the default suggestion for the GitHub repository name.
DEFAULT_REPO_NAME=$(basename "$(pwd)")

echo "--- GitHub Repository Automation ---"
echo "This script will create a new GitHub repository and push your current local project to it."

# --- Check for GitHub CLI (gh) ---
if ! command -v gh &> /dev/null; then
    echo "Error: GitHub CLI (gh) is not installed."
    echo "Please install it from https://cli.github.com/ and authenticate ('gh auth login') before running this script."
    exit 1
fi

# --- Check for Git ---
if ! command -v git &> /dev/null; then
    echo "Error: Git is not installed."
    echo "Please install Git from https://git-scm.com/downloads before running this script."
    exit 1
fi

# --- Check for GitHub CLI Authentication Status ---
# This command just checks if gh CLI is authenticated.
if ! gh auth status &> /dev/null; then
    echo "Error: You are not logged in to GitHub CLI."
    echo "Please run 'gh auth login' in your terminal to authenticate your GitHub account."
    exit 1
fi

# --- Get Repository Name from User ---
read -p "Enter desired GitHub repository name (default: $DEFAULT_REPO_NAME): " REPO_NAME
REPO_NAME=${REPO_NAME:-$DEFAULT_REPO_NAME} # If user enters nothing, use default

# --- Get Repository Visibility from User ---
VALID_VISIBILITY=false
while [ "$VALID_VISIBILITY" = false ]; do
    read -p "Choose repository visibility (public/private): " VISIBILITY
    VISIBILITY=$(echo "$VISIBILITY" | tr '[:upper:]' '[:lower:]') # Convert to lowercase
    if [[ "$VISIBILITY" == "public" || "$VISIBILITY" == "private" ]]; then
        VALID_VISIBILITY=true
    else
        echo "Invalid visibility. Please enter 'public' or 'private'."
    fi
done

# --- Initialize Local Git Repository if Not Already Done ---
if [ ! -d .git ]; then
    echo "No local Git repository found. Initializing..."
    git init
    # Set default branch to 'main' for new repos
    git branch -M main
fi

# --- Ensure there's at least one commit ---
# This checks if there are any commits in the current branch.
if ! git log -n 1 --pretty=format:%H &> /dev/null; then
    echo "No commits found. Performing initial commit..."
    git add . # Add all files in the current directory
    git commit -m "Initial commit for $REPO_NAME project"
    if [ $? -ne 0 ]; then
        echo "Error: Initial commit failed. Please check your local changes and try again."
        exit 1
    fi
else
    echo "Local Git repository already has commits. Skipping initial commit."
fi

echo "Attempting to create GitHub repository '$REPO_NAME' as $VISIBILITY..."
echo "Linking local repository and performing initial push..."

# --- Create GitHub Repository and Push ---
# `gh repo create` is a powerful command:
# --source=. : Tells gh to use the current directory's files for the new repo.
# --remote=origin: Automatically adds the new GitHub repo as the 'origin' remote.
# --push: Pushes the current branch (e.g., 'main') to the newly created remote.
if gh repo create "$REPO_NAME" --"$VISIBILITY" --source=. --remote=origin --push; then
    echo ""
    echo "---------------------------------------------------------"
    echo "--- Success! GitHub repository created and pushed. ---"
    # Get the full URL of the newly created repo to display to the user
    REPO_URL=$(gh repo view --json url -q '.url')
    echo "Repository URL: $REPO_URL"
    echo "You can now continue your work and push updates with 'git push'."
    echo "---------------------------------------------------------"
else
    echo ""
    echo "---------------------------------------------------------"
    echo "--- Failed to create GitHub repository or push. ---"
    echo "Please review the error messages above. Common issues include:"
    echo "  - Repository name already exists under your account."
    echo "  - Insufficient permissions (if creating in an organization)."
    echo "  - Network issues."
    echo "---------------------------------------------------------"
    exit 1
fi