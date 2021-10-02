package com.cifer.categories.controllers;

import com.cifer.categories.dto.SaveUserData;
import com.cifer.categories.services.users.UserData;
import com.cifer.categories.services.users.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller that process user data.
 */
@Controller
@RequestMapping("sectors")
public class SectorsController {
    private static String SUCCESS_SAVE_MESSAGE = "Data successfully saved!";
    private final UsersService usersService;

    public SectorsController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Handle user sent form.
     * @param saveUserData
     * @param request
     * @param model
     * @return
     */
    @PostMapping
    public String saveUserSectors(@ModelAttribute SaveUserData saveUserData, HttpServletRequest request, Model model) {
        var sessionId = request.getSession().getId();
        var userData = new UserData(sessionId, saveUserData.getFirstName(), saveUserData.getSectors());
        usersService.createOrUpdate(userData);
        model.addAttribute("message", SUCCESS_SAVE_MESSAGE);
        return "save_user_data_result";
    }
}
