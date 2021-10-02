package com.cifer.categories.controllers;

import com.cifer.categories.dto.SaveUserData;
import com.cifer.categories.entities.Sector;
import com.cifer.categories.services.sectors.FlatSector;
import com.cifer.categories.services.sectors.SectorNode;
import com.cifer.categories.services.sectors.SectorsService;
import com.cifer.categories.services.users.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    private final SectorsService sectorsService;
    private final UsersService usersService;

    public IndexController(SectorsService sectorsService, UsersService usersService) {
        this.sectorsService = sectorsService;
        this.usersService = usersService;
    }

    /**
     * Method that is responsible to show user form(according to session data) with filled sectors.
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping
    public String index(Model model, HttpServletRequest request) {
        var userData = new SaveUserData();
        Set<String> selectedSectors = Collections.emptySet();
        var session = request.getSession();
//        If session was already created, we need to check user existence in db
        if (!session.isNew()) {
            var user = usersService.getBySessionId(request.getSession().getId());
//            if user exists in database, then get user data from db and put it to model in order to prefill data with previous saved data.
            if (user.isPresent()) {
                selectedSectors = user.get().getSectors().stream().map(Sector::getValue).collect(Collectors.toSet());
                userData.setAgreement(true);
                userData.setFirstName(user.get().getFirstName());
                userData.setSectors(new ArrayList<>(selectedSectors));
            }
        }
//        convert sectors trees to flat sectors(for select tag) + identify checked attribute value.
        var sectors = buildFlatSectors(sectorsService.getAll(), selectedSectors);
        model.addAttribute("sectors", sectors);
        model.addAttribute("userData", userData);
        return "index";
    }

    /**
     * Convert SectorNode trees to FlatSectors(according tree rules)
     *
     * @param sectors
     * @return
     */
    private List<FlatSector> buildFlatSectors(List<SectorNode> sectors, Set<String> selectedSectorValues) {
        /**
         * We have sectors trees which needs to converted flat sectors list and render html with produced list.
         * We know that each node's children are located in sorted way. We need traverse sectors
         * while adding to list. Traversing must be placed in recursive approach(to save sector ordering).
         * Generally for each node we define next algorithm:
         * 1. Visit node and create flat sector.
         * 2. Traverse all children nodes.
         * I decided to do algorithm in iterative approach
         * Algorithm is next:
         * 1. Add all root sectors nodes to stack in inverse order.
         * 2. Traverse stack while not empty and do next:
         *    2.1 Pop node
         *    2.2 Append space char without break to sector description level times + create flat sector.
         *    2.3 Add all node's children to stack in inverse order.
         */
        var flatSectorsList = new ArrayList<FlatSector>();
        var dfsStack = new Stack<SectorNode>();
        for (int index = sectors.size() - 1; index >= 0; --index) {
            dfsStack.push(sectors.get(index));
        }
        while (!dfsStack.isEmpty()) {
            var node = dfsStack.pop();
            var selected = selectedSectorValues.contains(node.getValue());
            flatSectorsList.add(new FlatSector(node.getValue(), node.getDescription(), node.getLevel(), selected));
            for (int index = node.getChildren().size() - 1; index >= 0; --index) {
                dfsStack.push(node.getChildren().get(index));
            }
        }
        return flatSectorsList;
    }
}
