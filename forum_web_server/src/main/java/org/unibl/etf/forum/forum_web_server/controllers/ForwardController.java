
package org.unibl.etf.forum.forum_web_server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {

    @RequestMapping({ "/auth/**", "/home/**", "/admin/**" })
    public String redirectAngularRoutes() {
        System.out.println("op");
        return "forward:/";
    }
}
