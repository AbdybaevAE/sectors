package com.cifer.categories.services.sectors;

import lombok.Getter;

/**
 * FlatSector - flat sector that is gonna be rendered in html
 */
@Getter
public class FlatSector {
    private final static String HTML_SPACE_ENTITY = "&nbsp;&nbsp;&nbsp;&nbsp;";

    private final String value;
    private final String description;
    private final boolean selected;

    /**
     * FlatSector constructor
     * @param value - unique option value
     * @param description - option text
     * @param level - distance from root sector
     * @param selected - checked option
     */
    public FlatSector(String value, String description, int level, boolean selected) {
        this.value = value;
        this.description =  HTML_SPACE_ENTITY.repeat(level) + description;
        this.selected = selected;
    }
}
