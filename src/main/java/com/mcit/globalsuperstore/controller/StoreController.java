package com.mcit.globalsuperstore.controller;

import com.mcit.globalsuperstore.Item;
import com.mcit.globalsuperstore.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class StoreController {
    StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }
    @GetMapping("/")
    public String getForm(Model model,@RequestParam(required = false) String id){
        model.addAttribute("item", storeService.getItemById(id));
        return "form";
    }
    @GetMapping("/inventory")
    public String getInventory(Model model){
        model.addAttribute("items",storeService.getAllItems());
        return "inventory";
    }
    @PostMapping("/submitform")
    public String submitForm(@Valid Item item, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()) return "form";
        String status = storeService.submitItem(item);
        redirectAttributes.addFlashAttribute("status",status);
        return "redirect:/inventory";
    }
}
