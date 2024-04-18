package cz.czechitas.java2webapps.ukol2.controllers;

import cz.czechitas.java2webapps.ukol2.service.QuoteImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    private final QuoteImageService quoteImageService;

    public MainController(QuoteImageService quoteImageService) {
        this.quoteImageService = quoteImageService;
    }

    @GetMapping("/")
    public ModelAndView showQuote() {
        ModelAndView modelAndView = new ModelAndView("quote");
        modelAndView.addObject("quote", quoteImageService.getRandomQuote());

        String imagePath = quoteImageService.getRandomImage();
        modelAndView.addObject("image", imagePath);

        return modelAndView;
    }
}