package db

databaseChangeLog {
    includeAll(
        path: "changelog",
        relativeToChangelogFile: true,
        errorIfMissingOrEmpty: true
    )
}
