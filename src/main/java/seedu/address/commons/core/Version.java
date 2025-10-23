package seedu.address.commons.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents a version with major, minor, and patch numbers, along with an optional early-access (EA) flag.
 * <p>
 * Example version string format: {@code V1.2.3} or {@code V1.2.3ea}.
 */
public class Version implements Comparable<Version> {

    /**
     * Regular expression for matching a valid version string.
     * The format is {@code V<major>.<minor>.<patch>(ea)?}, e.g., {@code V1.0.0ea}.
     */
    public static final String VERSION_REGEX = "V(\\d+)\\.(\\d+)\\.(\\d+)(ea)?";

    /**
     * Exception message template for invalid version strings.
     */
    private static final String EXCEPTION_STRING_NOT_VERSION = "String is not a valid Version. %s";

    /**
     * Compiled {@link Pattern} for validating and parsing version strings.
     */
    private static final Pattern VERSION_PATTERN = Pattern.compile(VERSION_REGEX);

    /** Major version number (e.g., 1 in V1.2.3). */
    private final int major;

    /** Minor version number (e.g., 2 in V1.2.3). */
    private final int minor;

    /** Patch version number (e.g., 3 in V1.2.3). */
    private final int patch;

    /** Indicates whether this version is an early access (EA) version. */
    private final boolean isEarlyAccess;

    /**
     * Constructs a {@code Version} instance with the specified details.
     *
     * @param major the major version number
     * @param minor the minor version number
     * @param patch the patch version number
     * @param isEarlyAccess {@code true} if this is an early access version; {@code false} otherwise
     */
    public Version(int major, int minor, int patch, boolean isEarlyAccess) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.isEarlyAccess = isEarlyAccess;
    }

    /**
     * Returns the major version number.
     *
     * @return the major version
     */
    public int getMajor() {
        return major;
    }

    /**
     * Returns the minor version number.
     *
     * @return the minor version
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Returns the patch version number.
     *
     * @return the patch version
     */
    public int getPatch() {
        return patch;
    }

    /**
     * Returns whether this version is an early access (EA) version.
     *
     * @return {@code true} if this version is early access; {@code false} otherwise
     */
    public boolean isEarlyAccess() {
        return isEarlyAccess;
    }

    /**
     * Parses a version number string and returns a corresponding {@code Version} object.
     * <p>
     * Valid formats:
     * <ul>
     *   <li>{@code V1.2.3}</li>
     *   <li>{@code V1.2.3ea}</li>
     * </ul>
     *
     * @param versionString the version string to parse
     * @return a {@code Version} object representing the given string
     * @throws IllegalArgumentException if the provided string is not in a valid version format
     */
    @JsonCreator
    public static Version fromString(String versionString) throws IllegalArgumentException {
        Matcher versionMatcher = VERSION_PATTERN.matcher(versionString);

        if (!versionMatcher.find()) {
            throw new IllegalArgumentException(String.format(EXCEPTION_STRING_NOT_VERSION, versionString));
        }

        return new Version(
                Integer.parseInt(versionMatcher.group(1)),
                Integer.parseInt(versionMatcher.group(2)),
                Integer.parseInt(versionMatcher.group(3)),
                versionMatcher.group(4) != null
        );
    }

    /**
     * Returns the string representation of this version in the format {@code V<major>.<minor>.<patch>(ea)?}.
     *
     * @return a string representation of this version
     */
    @JsonValue
    @Override
    public String toString() {
        return String.format("V%d.%d.%d%s", major, minor, patch, isEarlyAccess ? "ea" : "");
    }

    /**
     * Compares this version to another {@code Version} object.
     * <p>
     * The comparison order is:
     * <ol>
     *   <li>Major version</li>
     *   <li>Minor version</li>
     *   <li>Patch version</li>
     *   <li>Early access status (non-EA versions are considered greater)</li>
     * </ol>
     *
     * @param other the other version to compare against
     * @return a negative integer, zero, or a positive integer as this version
     *         is less than, equal to, or greater than the specified version
     */
    @Override
    public int compareTo(Version other) {
        if (major != other.major) {
            return major - other.major;
        }
        if (minor != other.minor) {
            return minor - other.minor;
        }
        if (patch != other.patch) {
            return patch - other.patch;
        }
        if (isEarlyAccess == other.isEarlyAccess) {
            return 0;
        }
        return isEarlyAccess ? -1 : 1;
    }

    /**
     * Indicates whether another object is equal to this version.
     * <p>
     * Two versions are equal if they have the same major, minor, patch, and early-access values.
     *
     * @param other the object to compare
     * @return {@code true} if both objects represent the same version; {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Version)) {
            return false;
        }
        Version otherVersion = (Version) other;
        return major == otherVersion.major
                && minor == otherVersion.minor
                && patch == otherVersion.patch
                && isEarlyAccess == otherVersion.isEarlyAccess;
    }

    /**
     * Returns a hash code for this version.
     * <p>
     * The hash is derived from the version components to ensure consistent equality checks.
     *
     * @return the hash code representing this version
     */
    @Override
    public int hashCode() {
        String hash = String.format("%03d%03d%03d", major, minor, patch);
        if (!isEarlyAccess) {
            hash = "1" + hash;
        }
        return Integer.parseInt(hash);
    }
}

