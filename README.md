The implementation of a local version control system.
This version-control system mimics some of the features of the popular version control system, git.

Supported commands with a short summary of their functionalities:
- init: Creates a new version-control system in the current directory.
- rm: Untrack the file.
- log: Starting at the current head commit,
        display information about each commit backward along the commit tree until the initial commit.
- global log: Like log, except displays information about all commits ever made.
- find: Prints out the ids of all commits that have the given commit message, one per line.
- status: Displays what branches currently exist, and marks the current branch with a *.
- checkout: Can do a few different things depending on what its arguments are.
            There are 3 different checkout functions.
- branch: Creates a new branch with the given name, and points it at the current head node.
- rm-branch: Deletes the branch with the given name.
- reset: Checks out all the files tracked by the given commit.
- merge: Merges files from the given branch into the current branch.

to use, run: java gitlet.Main command
exp: java gitlet.Main init
