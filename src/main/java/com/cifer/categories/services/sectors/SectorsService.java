package com.cifer.categories.services.sectors;

import java.util.List;

/**
 * Sectors service. It contains only 1 method get sector trees(where each node contains info about sector value and description and
 * children). It's better to return sector tree in order to perform query operation on tree.
 * For view purposes tree must be converted to flatSector(according level)
 *
 */
public interface SectorsService {
    List<SectorNode> getAll();
}
