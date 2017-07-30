package com.nutron.sampledragger.extensions

/**
 * Strips off a potential ", " from the front of a string
 *
 * @param inString A string that may have a ", " prefix
 * @return A string with ", " stripped from the front
 */
inline fun String.stripPrefix(): String {
    if (this.startsWith(", ")) {
        return this.replaceFirst(", ".toRegex(), "")
    }
    return this
}
