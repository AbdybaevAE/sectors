package com.cifer.categories.services.sectors;

import com.cifer.categories.entities.Sector;
import com.cifer.categories.exceptions.ServiceInternalException;
import com.cifer.categories.repos.SectorRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SectorsServiceImpl implements SectorsService {

    private final SectorRepository sectorRepository;

    public SectorsServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @Override
    public List<SectorNode> getAll() {
        return build(sectorRepository.findAll());

    }

    /**
     * Build sector tree from sector entities.
     * @param sectors
     * @return
     */
    private List<SectorNode> build(List<Sector> sectors) {
//        create map of sector.value -> sector
        var sectorValueMap = sectors.stream()
                .map(SectorNode::create)
                .collect(
                        Collectors.toMap(SectorNode::getValue, Function.identity())
                );
//      1. Find all "root" sectors
//      2. For each sector do next:
//          2.1 Validate sector parent value
//          2.2 Add current sector as child to parent sector
        var rootSectors = new ArrayList<SectorNode>();
        for (var sector : sectorValueMap.values()) {
            var parentValue = sector.getParentValue();
            if (parentValue.equals(sector.getValue())) {
                rootSectors.add(sector);
                continue;
            }
            if (!sectorValueMap.containsKey(parentValue))
                new ServiceInternalException("Invalid sector parent data " + parentValue + " .");
            sectorValueMap.get(sector.getParentValue()).addChild(sector);
        }

//      Sort root sectors according their order. For root sectors level = 0.
        Collections.sort(rootSectors, Comparator.comparingInt(SectorNode::getOrder));
//      Sort left nodes according their order by using dfs approach
        var stack = new Stack<SectorNode>();
        rootSectors.stream().forEach(stack::add);
        while (!stack.isEmpty()) {
            var node = stack.pop();
            Collections.sort(node.getChildren(), Comparator.comparingInt(SectorNode::getOrder));
//          For each node currentLevel + 1 = parentNodeLevel
            node.getChildren().forEach(childNode -> childNode.setLevel(node.getLevel() + 1));
            stack.addAll(node.getChildren());
        }
        return rootSectors;
    }
}
