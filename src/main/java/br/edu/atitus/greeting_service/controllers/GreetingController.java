package br.edu.atitus.greeting_service.controllers;

import br.edu.atitus.greeting_service.configs.GreetingConfig;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingConfig config;

    public GreetingController(GreetingConfig config) {
        this.config = config;
    }

    // GET sem parâmetro ou com RequestParam
    @GetMapping({"", "/"})
    public String getGreeting(
            @RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            name = config.getDefaultName();
        }
        return String.format("%s, %s!!!", config.getGreeting(), name);
    }

    // GET com PathVariable
    @GetMapping("/{name}")
    public String getGreetingByPath(
            @PathVariable String name) {
        if (name == null || name.isEmpty()) {
            name = config.getDefaultName();
        }
        return String.format("%s, %s!!!", config.getGreeting(), name);
    }

    // POST /greeting recebendo JSON {"name": "Leia"}
    @PostMapping
    public String postGreeting(
            @RequestBody(required = false) NameRequest body) {
        String name = (body != null && body.getName() != null && !body.getName().isEmpty())
                ? body.getName()
                : config.getDefaultName();
        return String.format("%s, %s!!!", config.getGreeting(), name);
    }

    // Classe interna para receber o JSON do POST
    static class NameRequest {
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}