package com.cifer.categories.services.sectors;

import com.cifer.categories.entities.Sector;
import com.cifer.categories.exceptions.ServiceInternalException;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class that resolves SectorService.
 * It's always better to have different classes for each layer. Sector - for repository layer and SectorNode for service layer.
 * It's supposed that another part of code would interact with sector service through SectorNode class.
 */
public class SectorNode {
    private final String value;
    private final String description;
//  ordering of sectors between sibling relations
    private final int order;
//    helper field that indicated current node distance from root node
    private int level;
    /**
     * Nodes' children. Each sector can have more detailed children sectors
     */
    private List<SectorNode> children;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private SectorNode(String value, String description, String parentValue, int order) {
        if (Strings.isBlank(value) || Strings.isBlank(description) || Strings.isBlank(parentValue))
            throw new ServiceInternalException("Cannot init SectorNode from " + value + " " + description + " " + parentValue + ".");
        this.value = value;
        this.description = description;
        this.parentValue = parentValue;
        this.order = order;
        this.children = new ArrayList<>();

    }

    /**
     * Prevent using empty constructor
     */
    private SectorNode() {
        throw new ServiceInternalException("Cannot call empty constructor.");
    }

    /**
     * Static factory method
     */
    public static SectorNode create(Sector sectorEntity) {
        return new SectorNode(sectorEntity.getValue(), sectorEntity.getDescription(), sectorEntity.getParentValue(), sectorEntity.getOrder());
    }

    public void addChild(SectorNode node) {
        this.children.add(node);
    }

    public int getOrder() {
        return order;
    }

    public String getParentValue() {
        return parentValue;
    }

    private final String parentValue;

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public List<SectorNode> getChildren() {
        return children;
    }

}
