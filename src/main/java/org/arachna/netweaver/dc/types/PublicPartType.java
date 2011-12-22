/**
 * 
 */
package org.arachna.netweaver.dc.types;

import java.util.HashMap;
import java.util.Map;

/**
 * Type of a {@link PublicPart}.
 * 
 * @author Dirk Weigenand
 */
public enum PublicPartType {
    /**
     * Denotes a public part that is meant to be included at build time in
     * another development component.
     */
    ASSEMBLY("assembly"),
    /**
     * Denotes a public part that provides a reference to the api of a
     * development component at build and/or run time.
     */
    COMPILE("compilation"),

    /**
     * 
     */
    INFRASTRUCTURE("infrastructure");
    
    /**
     * Allowed types of development component.
     */
    private static final Map<String, PublicPartType> TYPES = new HashMap<String, PublicPartType>();

    static {
        for (final PublicPartType type : values()) {
            TYPES.put(type.toString(), type);
        }
    }

    /**
     * type of public part.
     */
    private String type;

    /**
     * Create an instance of a <code>PublicPartType</code> with the given type.
     * 
     * @param type
     *            the type of the public part.
     */
    PublicPartType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;

    }

    /**
     * factory method for creating public part types. If the given arguments do
     * not match any type in this enum a <code>null</code> is returned.
     * 
     * @param typeName
     *            name of the requested type of development components.
     * @return the matching development component type, <code>null</code>
     *         otherwise.
     */
    public static PublicPartType fromString(final String typeName) {
        return TYPES.get(typeName);
    }
}
